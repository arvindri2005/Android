package com.college.anwesha2k23.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieDrawable
import com.college.anwesha2k23.R
import com.college.anwesha2k23.databinding.FragmentHomeBinding
import com.college.anwesha2k23.events.SingleEventFragment
import com.college.anwesha2k23.home.functions.nav_items_functions
import com.google.android.material.bottomsheet.BottomSheetBehavior


class HomeFragment : Fragment() {
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

        binding.navBar.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        val bottomSheet = binding.eventBottomSheet
        val behavior = BottomSheetBehavior.from(bottomSheet)
        behavior.peekHeight = 1000
        behavior.state = BottomSheetBehavior.STATE_COLLAPSED


        nav_items_functions(binding, requireActivity()).selectingItems()
        eventViewModel= ViewModelProvider(this)[EventsViewModel::class.java]
        return binding.root

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadEvents()

        setAnime()

        binding.dayOne.setOnClickListener{
            Toast.makeText(context, "Day 1 is clicked", Toast.LENGTH_SHORT).show()
            //On click it will refresh the recycler view and show the list of event happening on that day
        }
        binding.dayTwo.setOnClickListener{
            Toast.makeText(context, "Day 2 is clicked", Toast.LENGTH_SHORT).show()
        }
        binding.dayThree.setOnClickListener{
            Toast.makeText(context, "Day 3 is clicked", Toast.LENGTH_SHORT).show()
        }
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

        eventViewModel.makeApiCall()
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

    private fun setAnime() {
        binding.animationView.setAnimation(R.raw.map_replace)
        binding.animationView.repeatCount = LottieDrawable.INFINITE
        binding.animationView.playAnimation()
    }
}