package com.college.anwesha2k23.calendar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.college.anwesha2k23.R
import com.college.anwesha2k23.databinding.FragmentCalendarBinding

class CalendarFragment : Fragment() {
    private lateinit var binding: FragmentCalendarBinding

    private lateinit var recyclerViewEvents: RecyclerView
    private lateinit var recyclerViewTimeSlots: RecyclerView

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

        val events = ArrayList<EventData>()
        events.add(EventData("Test 1", "9:00 AM", "10:00AM"))
        events.add(EventData("Test 5", "11:00 AM", "14:00AM"))
        events.add(EventData("Test 4", "16:00 AM", "19:00AM"))
        events.add(EventData("Test 3", "21:00 AM", "23:00AM"))



        val sortedEvents = events.sortedBy { it.startTime.split(":").first().toInt() }

        val margins = CalendarFunctions().cal_margin(sortedEvents, requireActivity())

        val decoration = CustomItemDecoration(margins)
        recyclerViewEvents.addItemDecoration(decoration)
        recyclerViewEvents.isNestedScrollingEnabled = false
        recyclerViewTimeSlots.isNestedScrollingEnabled = false
        recyclerViewEvents.adapter = EventAdapter(sortedEvents)


        binding.day1.setOnClickListener {
            ButtonsFunction().selectButton(binding.day1, requireActivity(), binding.day2, binding.day3)
        }
        binding.day2.setOnClickListener {
            ButtonsFunction().selectButton(binding.day2, requireActivity(), binding.day1, binding.day3)
        }
        binding.day3.setOnClickListener {
            ButtonsFunction().selectButton(binding.day3, requireActivity(), binding.day2, binding.day1)
        }
        return view
    }


}