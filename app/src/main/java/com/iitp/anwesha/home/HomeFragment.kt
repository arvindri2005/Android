package com.iitp.anwesha.home

import android.animation.ValueAnimator
import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.iitp.anwesha.R
import com.iitp.anwesha.TicketBook.PassesFragment
import com.iitp.anwesha.databinding.FragmentHomeBinding
import com.iitp.anwesha.events.SingleEventFragment
import com.iitp.anwesha.home.functions.MapClickHandle
import com.iitp.anwesha.home.functions.nav_items_functions
import java.text.SimpleDateFormat
import java.util.*


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var eventViewModel: EventsViewModel
    private lateinit var eventRecyclerView: RecyclerView
    private lateinit var newEventList: ArrayList<EventList>
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarToggle: ActionBarDrawerToggle
    private lateinit var adapter: EventAdapter
    private lateinit var behavior: BottomSheetBehavior<ConstraintLayout>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        newEventList = arrayListOf()
        drawerLayout = binding.frameLayout
        actionBarToggle = ActionBarDrawerToggle(activity, drawerLayout, 0, 0)
        drawerLayout.addDrawerListener(actionBarToggle)
        actionBarToggle.syncState()


        binding.deliveryShimmer.startShimmer()
        val sharedPref =
            requireActivity().getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)

        binding.navBar.setOnClickListener {
            requireActivity().findViewById<TextView>(R.id.nameText2).text =
                sharedPref.getString(getString(R.string.user_name), "User")
            requireActivity().findViewById<TextView>(R.id.textView).text =
                sharedPref.getString("anwesha_id", "id")
            drawerLayout.openDrawer(GravityCompat.START)
        }
        if (sharedPref.getString("user_type", "user_type")=="iitp_student"){
            binding.festPasses.visibility = View.GONE
        }

        val bottomSheet = binding.eventBottomSheet
        behavior = BottomSheetBehavior.from(bottomSheet)
        behavior.peekHeight = 1000
        binding.hintImg.visibility = View.GONE
        binding.hintTxt.visibility = View.GONE

        binding.map.setOnClickListener {
            slideDown()
            binding.hintTxt.visibility = View.VISIBLE
            binding.hintImg.visibility = View.VISIBLE
        }

        MapClickHandle(requireContext(), binding).mapClick()


        //Handle click when venues are clicked
        binding.nes.setOnClickListener {
            slideUp()
            binding.eventText.text ="Events at Nescafe"
            venueClicked("Nescafe, IIT PATNA")

        }
        binding.gym.setOnClickListener {
            slideUp()
            binding.eventText.text ="Events at Gymkhana"
            venueClicked("Gymkhana, IIT PATNA")
        }
        binding.sac.setOnClickListener {
            slideUp()
            binding.eventText.text = "Events at sac"
            venueClicked("SAC Main Hall, IIT PATNA")
        }
        binding.mainStage.setOnClickListener {
            slideUp()
            binding.eventText.text = "Events at main stage"
            venueClicked("Main Stage, IIT PATNA")
        }
        binding.basketball.setOnClickListener {
            slideUp()
            binding.eventText.text = "Events at basketball court"
            venueClicked("Basketball Court, IIT PATNA")
        }
        binding.nsit.setOnClickListener {
            slideUp()
            binding.eventText.text = "Events at nsit wall"
            venueClicked("NSIT Wall, IIT Patna")
        }
        binding.foodCourt.setOnClickListener {
            slideUp()
            binding.eventText.text = "Events at food court"
            venueClicked("FOOD COURT, IIT PATNA")
        }
        binding.senate.setOnClickListener {
            slideUp()
            binding.eventText.text = "Events at Senate hall"
            venueClicked("Senate Hall, IIT PATNA")
        }
        binding.lh2.setOnClickListener {
            slideUp()
            binding.eventText.text = "Events at lecture hall"
            venueClicked("Lecture Hall, IIT PATNA")
        }
        binding.lh1.setOnClickListener {
            slideUp()
            binding.eventText.text = "Events at tutorial block"
            venueClicked("Lecture Hall, IIT PATNA")
        }
        binding.helipad.setOnClickListener {
            slideUp()
            binding.eventText.text = "Events at helipad"
            venueClicked("Helipad Stage, IIT PATNA")
        }

        binding.festPasses.setOnClickListener {
            loadPassesFragment(PassesFragment())
        }

        nav_items_functions(binding, requireActivity(), requireActivity()).selectingItems()
        eventViewModel = ViewModelProvider(this)[EventsViewModel::class.java]
        return binding.root

    }

    private fun slideDown(){
        val slideDown = ValueAnimator.ofInt(1000, dpToPx(150))
        slideDown.duration = 500
        slideDown.addUpdateListener {
            behavior.peekHeight = it.animatedValue as Int
        }
        if(behavior.peekHeight!=dpToPx(150)){
            slideDown.start()
        }
    }

    private fun slideUp(){
        val slideUp = ValueAnimator.ofInt(200, 1000)
        slideUp.duration = 500
        slideUp.addUpdateListener {
            behavior.peekHeight = it.animatedValue as Int
        }
        if(behavior.peekHeight!=1000){
            slideUp.start()
        }
    }

    private fun dpToPx(dp: Int): Int {
        val density = resources.displayMetrics.density
        return (dp.toFloat() * density + 0.5f).toInt()
    }

    private fun venueClicked(venue: String) {
        binding.hintTxt.visibility = View.GONE
        binding.hintImg.visibility = View.GONE
        val venueEvent = ArrayList<EventList>()
        for(i in newEventList){
            if (i.venue==venue){
                venueEvent.add(i)
            }
        }
        binding.isEmpty.visibility = View.GONE
        adapter.setEvents(venueEvent)
        adapter.notifyDataSetChanged()

        binding.allDay.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.black))
        binding.day1.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.black))
        binding.day2.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.black))
        binding.day3.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.black))
        binding.allDay.setBackgroundResource(R.drawable.home_day_btn_bg)
        binding.day1.setBackgroundResource(R.drawable.home_day_btn_bg)
        binding.day2.setBackgroundResource(R.drawable.home_day_btn_bg)
        binding.day3.setBackgroundResource(R.drawable.home_day_btn_bg)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadEvents()

//      Day wise event loading
        binding.allDay.setOnClickListener {
            changeBg(0)
            slideUp()
            adapter.setEvents(newEventList)
            adapter.notifyDataSetChanged()
        }
        binding.day1.setOnClickListener {
            changeBg(1)
            slideUp()
            loadDayEvents("17")
        }
        binding.day2.setOnClickListener {
            changeBg(2)
            slideUp()
            loadDayEvents("18")
        }
        binding.day3.setOnClickListener {
            changeBg(3)
            loadDayEvents("19")
        }
    }

    private fun changeBg(day: Int) {
        when (day){
            0->{
                binding.allDay.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.white))
                binding.day1.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.black))
                binding.day2.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.black))
                binding.day3.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.black))
                binding.allDay.setBackgroundResource(R.drawable.home_day_btn_selected_bg)
                binding.day1.setBackgroundResource(R.drawable.home_day_btn_bg)
                binding.day2.setBackgroundResource(R.drawable.home_day_btn_bg)
                binding.day3.setBackgroundResource(R.drawable.home_day_btn_bg)
            }
            1->{
                binding.allDay.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.black))
                binding.day1.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.white))
                binding.day2.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.black))
                binding.day3.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.black))
                binding.allDay.setBackgroundResource(R.drawable.home_day_btn_bg)
                binding.day1.setBackgroundResource(R.drawable.home_day_btn_selected_bg)
                binding.day2.setBackgroundResource(R.drawable.home_day_btn_bg)
                binding.day3.setBackgroundResource(R.drawable.home_day_btn_bg)
            }
            2->{
                binding.allDay.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.black))
                binding.day1.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.black))
                binding.day2.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.white))
                binding.day3.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.black))
                binding.allDay.setBackgroundResource(R.drawable.home_day_btn_bg)
                binding.day1.setBackgroundResource(R.drawable.home_day_btn_bg)
                binding.day2.setBackgroundResource(R.drawable.home_day_btn_selected_bg)
                binding.day3.setBackgroundResource(R.drawable.home_day_btn_bg)
            }
            3->{
                binding.allDay.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.black))
                binding.day1.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.black))
                binding.day2.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.black))
                binding.day3.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.white))
                binding.allDay.setBackgroundResource(R.drawable.home_day_btn_bg)
                binding.day2.setBackgroundResource(R.drawable.home_day_btn_bg)
                binding.day1.setBackgroundResource(R.drawable.home_day_btn_bg)
                binding.day3.setBackgroundResource(R.drawable.home_day_btn_selected_bg)
            }
        }
    }

    private fun loadDayEvents(day: String) {
        binding.eventText.text = "Events"
        val dayEvent = ArrayList<EventList>()
        for (i in newEventList) {
            val da = getDayFromDate(i.start_time!!)
            if (day == da) {
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

                binding.deliveryShimmer.visibility = View.GONE
                binding.animationView.visibility = View.VISIBLE
                binding.deliveryShimmer.stopShimmer()
                binding.animationView.smoothScrollTo(dpToPx(150),0)
                binding.verticalScroll.smoothScrollTo(0, dpToPx(480))
            } else {
                Toast.makeText(context, "Error in getting Events", Toast.LENGTH_SHORT).show()
            }
        }

        eventViewModel.makeApiCall(requireContext())
    }

    private fun loadPassesFragment(fragment: Fragment) {
        val fragmentManager = requireActivity().supportFragmentManager.beginTransaction()
        fragmentManager.replace(R.id.fragmentContainer, fragment)
        fragmentManager.addToBackStack(null)
        fragmentManager.commit()
    }

    private fun loadSingleEventFragment(event: EventList) {
        val bundle = Bundle()
//        bundle.putString("eventID", event.id)
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