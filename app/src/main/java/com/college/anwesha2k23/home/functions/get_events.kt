package com.college.anwesha2k23.home.functions

import android.content.Context
import com.college.anwesha2k23.R
import com.college.anwesha2k23.databinding.FragmentHomeBinding
import com.college.anwesha2k23.events.EventList

class get_events(val binding: FragmentHomeBinding, val context: Context) {
    private lateinit var eventPoster : Array<Int>
    private lateinit var eventName : Array<String>
    private lateinit var eventLocation :Array<String>
    private lateinit var eventDate : Array<String>
    private lateinit var eventTime : Array<String>

    fun getUserData(newEventList : ArrayList<EventList>) {

        eventPoster = arrayOf(
            R.drawable.poster,
            R.drawable.event
        )
        eventName = arrayOf(
            "DJ Night",
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