package com.college.anwesha2k23.events

import androidx.lifecycle.ViewModel
import com.college.anwesha2k23.R

class EventsViewModel: ViewModel() {

    private var newEventList : ArrayList<EventList>

    private val _events = ArrayList<EventList>().apply {
        newEventList = arrayListOf()
        for(i in 1..10){
            val event = EventList(
                R.drawable.poster,
                "Event Name $i",
                "Event Location $i",
                "$i March 2023",
                "$i:$i PM")
            newEventList.add(event)
        }
    }
    val events: ArrayList<EventList> = newEventList
}