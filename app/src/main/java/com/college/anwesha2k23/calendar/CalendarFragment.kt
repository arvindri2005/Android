package com.college.anwesha2k23.calendar

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
import com.college.anwesha2k23.R
import com.college.anwesha2k23.calendar.Adapters.EventAdapter
import com.college.anwesha2k23.calendar.Adapters.TimeAdapter
import com.college.anwesha2k23.calendar.DataFiles.EventData
import com.college.anwesha2k23.calendar.Functions.ButtonsFunction
import com.college.anwesha2k23.calendar.Functions.CalendarFunctions
import com.college.anwesha2k23.databinding.FragmentCalendarBinding
import com.college.anwesha2k23.events.SingleEventFragment
import com.college.anwesha2k23.home.EventList
import com.college.anwesha2k23.home.EventsViewModel
import kotlin.math.log

class CalendarFragment : Fragment() {
    private lateinit var binding: FragmentCalendarBinding

    private lateinit var recyclerViewEvents: RecyclerView
    private lateinit var recyclerViewTimeSlots: RecyclerView

    private lateinit var eventViewModel: EventsViewModel
    private lateinit var newEventList : ArrayList<EventList>
    private lateinit var sortedEvents  : List<EventData>
    private lateinit var adapter: EventAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCalendarBinding.inflate(inflater, container, false)
        val view = binding.root

        recyclerViewTimeSlots = binding.recyclerViewTimeSlots
        recyclerViewTimeSlots.layoutManager = LinearLayoutManager(activity)
        recyclerViewTimeSlots.adapter = TimeAdapter()

        recyclerViewEvents = binding.recyclerViewEvents
        recyclerViewEvents.layoutManager = LinearLayoutManager(activity)
        eventViewModel= ViewModelProvider(this)[EventsViewModel::class.java]
        newEventList = arrayListOf()


        getEvents("16")

        binding.day1.setOnClickListener {
            ButtonsFunction().selectButton(binding.day1, requireActivity(), binding.day2, binding.day3)
            getEvent_bydate("16", newEventList)
        }
        binding.day2.setOnClickListener {
            ButtonsFunction().selectButton(binding.day2, requireActivity(), binding.day1, binding.day3)
            getEvent_bydate("17",newEventList)
        }
        binding.day3.setOnClickListener {
            ButtonsFunction().selectButton(binding.day3, requireActivity(), binding.day2, binding.day1)
            getEvent_bydate("18",newEventList)
        }
        return view
    }

    private fun getEvents(date: String) {
        eventViewModel.getEventListObserver().observe(viewLifecycleOwner) {
            if (it != null) {
                newEventList = it
                val events : ArrayList<EventData> = CalendarFunctions().Usefull_data(it)

                sortedEvents = events.sortedBy { it.startTime.split(":").first().toInt() }

                Log.d("abcd", it.toString())
                Log.d("abcd", sortedEvents.toString())
                val margins = CalendarFunctions().cal_margin(sortedEvents, requireActivity())

                val decoration = CustomItemDecoration(margins)
                recyclerViewEvents.addItemDecoration(decoration)
                recyclerViewEvents.isNestedScrollingEnabled = false
                recyclerViewTimeSlots.isNestedScrollingEnabled = false

                val filteredList = sortedEvents.filter { it.startdate == date.toString()}
                adapter = EventAdapter(filteredList, newEventList)
                adapter.setOnItemClickListener(object :
                    EventAdapter.OnItemClickListener {
                    override fun onItemClicked(event: EventList?) {          //when any event from the recycler view is clicked
                        loadSingleEventFragment(event)
                    }
                })
                recyclerViewEvents.adapter = adapter
            } else {
                Toast.makeText(context, "Error in getting Events", Toast.LENGTH_SHORT).show()
            }
        }

        eventViewModel.makeApiCall(requireContext())
    }

    fun getEvent_bydate(date: String, reallist: ArrayList<EventList>){
        val filteredList = sortedEvents.filter { it.startdate == date.toString()}
        adapter = EventAdapter(filteredList, reallist)
        adapter.setOnItemClickListener(object :
            EventAdapter.OnItemClickListener {
            override fun onItemClicked(event: EventList?) {          //when any event from the recycler view is clicked
                loadSingleEventFragment(event)
            }
        })
        recyclerViewEvents.adapter = adapter
    }

    private fun loadSingleEventFragment(event: EventList?){
        val bundle = Bundle()
        bundle.putSerializable("event", event)
        val fragment = SingleEventFragment()
        fragment.arguments = bundle
        val fragmentTransaction = activity?.supportFragmentManager!!.beginTransaction()
        fragmentTransaction.add(R.id.fragmentContainer, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

}