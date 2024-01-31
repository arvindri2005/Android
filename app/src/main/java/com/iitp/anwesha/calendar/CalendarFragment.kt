package com.iitp.anwesha.calendar

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iitp.anwesha.R
import com.iitp.anwesha.calendar.adapters.CalEventEv
import com.iitp.anwesha.calendar.adapters.EventAdapter
import com.iitp.anwesha.calendar.adapters.LocationAdapter
import com.iitp.anwesha.calendar.adapters.TimeAdapter
import com.iitp.anwesha.calendar.dataFiles.EventData
import com.iitp.anwesha.calendar.functions.ButtonsFunction
import com.iitp.anwesha.calendar.functions.CalendarFunctions
import com.iitp.anwesha.databinding.FragmentCalendarBinding
import com.iitp.anwesha.events.SingleEventFragment
import com.iitp.anwesha.home.EventList
import com.iitp.anwesha.home.EventsViewModel

private const val TAG = "CalendarFragment"
class CalendarFragment : Fragment() {
    private lateinit var binding: FragmentCalendarBinding
    private lateinit var recyclerViewEvents: RecyclerView
    private lateinit var recyclerViewTimeSlots: RecyclerView
    private lateinit var eventViewModel: EventsViewModel
    private lateinit var newEventList: ArrayList<EventList>
    private var usefulList: List<EventData> = emptyList()
    private lateinit var adapter: EventAdapter
    private lateinit var dayAdapter: CalEventEv

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCalendarBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.deliveryShimmer.startShimmer()

        recyclerViewTimeSlots = binding.recyclerViewTimeSlots
        recyclerViewTimeSlots.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewTimeSlots.adapter = TimeAdapter()

        recyclerViewEvents = binding.recyclerViewLocations
        recyclerViewEvents.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        eventViewModel = ViewModelProvider(this)[EventsViewModel::class.java]
        newEventList = arrayListOf()
        recyclerViewEvents.isNestedScrollingEnabled = false

        binding.calEventsRv.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        getEvents()

        binding.day1.setOnClickListener {
            ButtonsFunction().selectButton(
                binding.day1,
                requireActivity(),
                binding.day2,
                binding.day3,
                binding.scDaysLinear
            )
            eventByDate("02")
        }
        binding.day2.setOnClickListener {
            ButtonsFunction().selectButton(
                binding.day2,
                requireActivity(),
                binding.day1,
                binding.day3,
                binding.scDaysLinear
            )
            eventByDate("03")
        }
        binding.day3.setOnClickListener {
            ButtonsFunction().selectButton(
                binding.day3,
                requireActivity(),
                binding.day2,
                binding.day1,
                binding.scDaysLinear
            )
            eventByDate("04")
        }
        return view
    }

    private fun getEvents() {
        eventViewModel.getEventListObserver().observe(viewLifecycleOwner) {
            if (it != null) {
                newEventList = it
                binding.visibleFrag.visibility = View.VISIBLE
                binding.deliveryShimmer.stopShimmer()
                binding.deliveryShimmer.visibility = View.GONE

                dayAdapter = CalEventEv(requireContext())
                binding.calEventsRv.adapter = dayAdapter

                dayAdapter.setOnItemClickListener(object : CalEventEv.OnItemClickListener {
                    override fun onItemClicked(event: EventList) {          //when any event from the recycler view is clicked
                        loadSingleEventFragment(event)
                    }
                })

                adapter = EventAdapter(newEventList, object : EventAdapter.OnItemClickListener {
                    override fun onItemClick(verticalItem: EventList) {
                        Log.d(TAG, verticalItem.toString())
                        loadSingleEventFragment(verticalItem)
                    }
                })

                usefulList = CalendarFunctions().usefulData(newEventList)
                Log.d(TAG, it.toString())
                recyclerViewEvents.adapter = adapter

                eventByDate("02")
            } else {
                Toast.makeText(context, "Error in getting Events", Toast.LENGTH_SHORT).show()
            }
        }

        eventViewModel.makeApiCall(requireContext())
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun eventByDate(date: String) {
        binding.deliveryShimmer.startShimmer()
        binding.deliveryShimmer.visibility = View.VISIBLE
        val filteredList = usefulList.filter { it.startdate == date }.toMutableList()
        val eventByLocation = CalendarFunctions().getEventsByLocation(filteredList)
        val eventList: List<List<EventData>> = mapToList(eventByLocation)
        val locationList: List<String> = mapToKeys(eventByLocation)
        adapter.setList(filteredList, eventList, locationList)
        adapter.notifyDataSetChanged()


        val newAdapterV = LocationAdapter()
        binding.recyclerViewLocat.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewLocat.isNestedScrollingEnabled = false
        binding.recyclerViewLocat.adapter = newAdapterV
        newAdapterV.setList(locationList)
        newAdapterV.notifyDataSetChanged()


        dayAdapter.setList(filteredList, newEventList)

        binding.visibleFrag.visibility = View.VISIBLE
        binding.deliveryShimmer.visibility = View.GONE
        binding.deliveryShimmer.stopShimmer()
    }

    private fun mapToList(map: Map<String, List<EventData>>): List<List<EventData>> {
        val result = mutableListOf<List<EventData>>()
        for ((_, value) in map) {
            result.add(value)
        }
        return result
    }

    private fun mapToKeys(map: Map<String, List<EventData>>): List<String> {
        val result = mutableListOf<String>()
        for ((key, _) in map) {
            result.add(key)
        }
        return result
    }

    private fun loadSingleEventFragment(event: EventList?) {
        val bundle = Bundle()
        bundle.putSerializable("event", event)
        val fragment = SingleEventFragment()
        fragment.arguments = bundle
        val fragmentTransaction = activity?.supportFragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.commit()
    }

}