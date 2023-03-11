package com.college.anwesha2k23.home

import android.animation.ValueAnimator
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.college.anwesha2k23.R
import com.college.anwesha2k23.TicketBook.PassesFragment
import com.college.anwesha2k23.databinding.FragmentHomeBinding
import com.college.anwesha2k23.events.SingleEventFragment
import com.college.anwesha2k23.home.functions.nav_items_functions
import com.college.anwesha2k23.profile.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.internal.ViewUtils.dpToPx
import com.google.android.material.navigation.NavigationView
import java.text.SimpleDateFormat
import java.util.*


class HomeFragment : Fragment()  {
    private lateinit var binding : FragmentHomeBinding
    private lateinit var eventViewModel: EventsViewModel
    private lateinit var eventRecyclerView: RecyclerView
    private lateinit var newEventList : ArrayList<EventList>
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarToggle: ActionBarDrawerToggle
    private lateinit var adapter: EventAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        newEventList = arrayListOf()
        drawerLayout = binding.frameLayout
        actionBarToggle = ActionBarDrawerToggle(activity, drawerLayout, 0, 0)
        drawerLayout.addDrawerListener(actionBarToggle)
        actionBarToggle.syncState()

        val sharedPref = requireActivity().getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)

        binding.navBar.setOnClickListener {
            requireActivity().findViewById<TextView>(R.id.nameText2).text = sharedPref.getString(getString(R.string.user_name), "User")

            drawerLayout.openDrawer(GravityCompat.START)
        }
        val bottomSheet = binding.eventBottomSheet
        val behavior = BottomSheetBehavior.from(bottomSheet)
        behavior.peekHeight = 1000
        binding.hintImg.visibility = View.GONE
        binding.hintTxt.visibility = View.GONE

        val slideDown = ValueAnimator.ofInt(1000, dpToPx(130))
        slideDown.duration = 500
        slideDown.addUpdateListener {
            behavior.peekHeight = it.animatedValue as Int
        }

        val slideUp = ValueAnimator.ofInt(200, 1000)
        slideUp.duration = 500
        slideUp.addUpdateListener {
            behavior.peekHeight = it.animatedValue as Int
        }

        binding.map.setOnClickListener {
            slideDown.start()
            binding.hintTxt.visibility =View.VISIBLE
            binding.hintImg.visibility = View.VISIBLE
        }

        //Handle click when venues are clicked
        binding.firstImage.setOnClickListener {
            slideUp.start()
            venueClicked("First Image")

        }
        binding.secondImage.setOnClickListener {
            slideUp.start()
            venueClicked("Second Image")
        }

        binding.festPasses.setOnClickListener {
            loadPassesFragment(PassesFragment())
        }



        nav_items_functions(binding, requireActivity()).selectingItems()
        eventViewModel= ViewModelProvider(this)[EventsViewModel::class.java]
        return binding.root

    }

    private fun dpToPx(dp: Int): Int {
        val density = resources.displayMetrics.density
        return (dp.toFloat() * density + 0.5f).toInt()
    }

    private fun venueClicked(venue: String) {

        Toast.makeText(context, "$venue clicked", Toast.LENGTH_SHORT).show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadEvents()

//      Day wise event loading
        binding.dayOne.setOnClickListener{
            loadDayEvents("17")
        }
        binding.dayTwo.setOnClickListener{
            loadDayEvents("18")
        }
        binding.dayThree.setOnClickListener{
            loadDayEvents("19")
        }
    }

    private fun loadDayEvents(day: String) {
        val dayEvent  = ArrayList<EventList>()
        for (i in newEventList){
            val da = getDayFromDate(i.start_time!!)
            if(day == da ){
                dayEvent.add(i)
            }
        }
        adapter.setEvents(dayEvent)
        adapter.notifyDataSetChanged()
    }

    private fun getDayFromDate(dateString: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        val date = inputFormat.parse(dateString)
        val outputFormat = SimpleDateFormat("dd", Locale.getDefault())
        return outputFormat.format(date!!)
    }

    private fun loadEvents() {
        eventRecyclerView = binding.eventsList
        eventRecyclerView.layoutManager = LinearLayoutManager(context)
        adapter = EventAdapter(requireContext())
        eventRecyclerView.adapter = adapter
        getEvents()
    }

    private fun getEvents() {
        eventViewModel.getEventListObserver().observe(viewLifecycleOwner) {
            if (it != null) {
                newEventList = it
                adapter.setEvents(it)
                adapter.notifyDataSetChanged()
                adapter.setOnItemClickListener(object : EventAdapter.OnItemClickListener {
                    override fun onItemClicked(event: EventList) {          //when any event from the recycler view is clicked
                        loadSingleEventFragment(event)
                    }
                })
            } else {
                Toast.makeText(context, "Error in getting Events", Toast.LENGTH_SHORT).show()
            }
        }

        eventViewModel.makeApiCall(requireContext())
    }

    private fun loadFragment(fragment: Fragment) {
        val fragmentManager = requireActivity().supportFragmentManager.beginTransaction()
        fragmentManager.replace(R.id.fragmentContainer, fragment)
        fragmentManager.commit()
    }

    private fun loadPassesFragment(fragment: Fragment){
        val fragmentManager = requireActivity().supportFragmentManager.beginTransaction()
        fragmentManager.replace(R.id.fragmentContainer, fragment)
        fragmentManager.addToBackStack(null)
        fragmentManager.commit()
    }

    private fun loadSingleEventFragment(event: EventList){
        val bundle = Bundle()
        bundle.putSerializable("event", event)
        val fragment = SingleEventFragment()
        fragment.arguments = bundle
        val fragmentTransaction = activity?.supportFragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()

    }




//    private fun setAnime() {
//        binding.animationView.setAnimation(R.raw.map_replace)
//        binding.animationView.repeatCount = LottieDrawable.INFINITE
//        binding.animationView.playAnimation()
//    }
}