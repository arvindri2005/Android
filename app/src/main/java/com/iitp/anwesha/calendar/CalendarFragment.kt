package com.iitp.anwesha.calendar

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iitp.anwesha.R
import com.iitp.anwesha.calendar.Adapters.EventAdapter
import com.iitp.anwesha.calendar.Adapters.TimeAdapter
import com.iitp.anwesha.calendar.Adapters.cal_event_ev
import com.iitp.anwesha.calendar.Adapters.locatAdapter
import com.iitp.anwesha.calendar.DataFiles.EventData
import com.iitp.anwesha.calendar.Functions.ButtonsFunction
import com.iitp.anwesha.calendar.Functions.CalendarFunctions
import com.iitp.anwesha.databinding.FragmentCalendarBinding
import com.iitp.anwesha.events.SingleEventFragment
import com.iitp.anwesha.home.EventList
import com.iitp.anwesha.home.EventsViewModel

class CalendarFragment : Fragment() {
    private lateinit var binding: FragmentCalendarBinding
    private lateinit var recyclerViewEvents: RecyclerView
    private lateinit var recyclerViewTimeSlots: RecyclerView
    private lateinit var eventViewModel: EventsViewModel
    private lateinit var newEventList: ArrayList<EventList>
    private var usefull_list: List<EventData> = emptyList()
    private lateinit var adapter: EventAdapter
    private lateinit var day_adapter: cal_event_ev

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

        getEvents("17")

        binding.day1.setOnClickListener {
            ButtonsFunction().selectButton(
                binding.day1,
                requireActivity(),
                binding.day2,
                binding.day3,
                binding.scDaysLinear
            )
            getEvent_bydate("17", newEventList)
        }
        binding.day2.setOnClickListener {
            ButtonsFunction().selectButton(
                binding.day2,
                requireActivity(),
                binding.day1,
                binding.day3,
                binding.scDaysLinear
            )
            getEvent_bydate("18", newEventList)
        }
        binding.day3.setOnClickListener {
            ButtonsFunction().selectButton(
                binding.day3,
                requireActivity(),
                binding.day2,
                binding.day1,
                binding.scDaysLinear
            )
            getEvent_bydate("19", newEventList)
        }
        return view
    }

    private fun getEvents(date: String) {
        eventViewModel.getEventListObserver().observe(viewLifecycleOwner) {
            if (it != null) {
                newEventList = it
                binding.visibleFrag.visibility = View.VISIBLE
                binding.deliveryShimmer.stopShimmer()
                binding.deliveryShimmer.visibility = View.GONE

                day_adapter = cal_event_ev()
                binding.calEventsRv.adapter = day_adapter

                adapter = EventAdapter(newEventList, object : EventAdapter.OnItemClickListener {
                    override fun onItemClick(verticalItem: EventList) {
                        Log.d("checker", verticalItem.toString())
                        loadSingleEventFragment(verticalItem)
                    }
                })

                usefull_list = CalendarFunctions().Usefull_data(newEventList)
                Log.d("checker", it.toString())
                recyclerViewEvents.adapter = adapter

                getEvent_bydate("17", newEventList)
            } else {
                Toast.makeText(context, "Error in getting Events", Toast.LENGTH_SHORT).show()
            }
        }

        eventViewModel.makeApiCall(requireContext())
    }

    fun getEvent_bydate(date: String, reallist: ArrayList<EventList>) {
        binding.deliveryShimmer.startShimmer()
        binding.deliveryShimmer.visibility = View.VISIBLE
        val filteredList = usefull_list.filter { it.startdate == date.toString() }.toMutableList()
        val event_by_Location = CalendarFunctions().get_events_by_location(filteredList)
        val eventList: List<List<EventData>> = mapToList(event_by_Location)
        val locationlist: List<String> = mapToKeys(event_by_Location)
        adapter.setList(filteredList, eventList, locationlist)
        adapter.notifyDataSetChanged()

        val newadaptv = locatAdapter()
        binding.recyclerViewLocat.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewLocat.isNestedScrollingEnabled = false
        binding.recyclerViewLocat.adapter = newadaptv
        newadaptv.setList(locationlist)
        newadaptv.notifyDataSetChanged()

        day_adapter.setList(filteredList)

        binding.visibleFrag.visibility = View.VISIBLE
        binding.deliveryShimmer.visibility = View.GONE
        binding.deliveryShimmer.stopShimmer()
    }

    fun mapToList(map: Map<String, List<EventData>>): List<List<EventData>> {
        val result = mutableListOf<List<EventData>>()
        for ((_, value) in map) {
            result.add(value)
        }
        return result
    }

    fun mapToKeys(map: Map<String, List<EventData>>): List<String> {
        val result = mutableListOf<String>()
        for ((key, value) in map) {
            result.add(key)
        }
        return result
    }

    private fun loadSingleEventFragment(event: EventList?) {
        val bundle = Bundle()
//        bundle.putString("eventID", event!!.id)
        bundle.putSerializable("event", event)
        val fragment = SingleEventFragment()
        fragment.arguments = bundle
        val fragmentTransaction = activity?.supportFragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

}