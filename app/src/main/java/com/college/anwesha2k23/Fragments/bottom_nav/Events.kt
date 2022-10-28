package com.college.anwesha2k23.Fragments.bottom_nav

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.college.anwesha2k23.adapter.EventAdapter
import com.college.anwesha2k23.dataclass.EventList
import com.college.anwesha2k23.R
import com.college.anwesha2k23.databinding.FragmentEventsBinding


class Events : Fragment() {
    private var _binding: FragmentEventsBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: EventAdapter
    private lateinit var newEventView : RecyclerView
    private lateinit var newEventList : ArrayList<EventList>
    private lateinit var eventPoster : Array<Int>
    private lateinit var eventName : Array<String>
    private lateinit var eventLocation :  Array<String>
    private lateinit var eventDate : Array<String>
    private lateinit var eventTime : Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEventsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getUserData()
        val layoutManager = LinearLayoutManager(context)
        newEventView = binding.eventsList
        newEventView.layoutManager = layoutManager
        newEventView.setHasFixedSize(true)
        adapter = EventAdapter(newEventList)
        newEventView.adapter = adapter
    }


    private fun getUserData() {
        newEventList = arrayListOf<EventList>()
        eventPoster = arrayOf(
            R.drawable.poster,
            R.drawable.event
        )
        eventName = arrayOf(
            "Garba Night",
            "To be update"
        )
        eventLocation = arrayOf(
            "Near Block 3",
            "To be update"
        )
        eventDate = arrayOf(
            "29 September 2022",
            "4 December 2022"
        )
        eventTime = arrayOf(
            "18:00",
            "To be update"
        )
        for(i in eventPoster.indices){
            val event = EventList(eventPoster[i], eventName[i], eventLocation[i], eventDate[i], eventTime[i])
            newEventList.add(event)
        }
    }
}