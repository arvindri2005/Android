package com.college.anwesha2k23.calendar.Functions

import android.content.Context
import android.os.Bundle
import android.util.Log
import com.college.anwesha2k23.R
import com.college.anwesha2k23.calendar.DataFiles.EventData
import com.college.anwesha2k23.events.SingleEventFragment
import com.college.anwesha2k23.home.EventList
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CalendarFunctions() {

    fun get_events_by_location(filteredList: List<EventData>): Map<String, List<EventData>>{
        val eventDataByLocationMap: Map<String, List<EventData>> = filteredList.groupBy { it.venue }
        Log.d("checker", eventDataByLocationMap.toString())
        return eventDataByLocationMap
    }

    fun retHeight(event: EventData, context: Context): Int{
        val eventHeightPerHour = context.resources.getDimension(R.dimen.event_height_per_hour)
        val timeSlotHeight = eventHeightPerHour

        val totalHours = 15
        val hourHeight = timeSlotHeight.toFloat()

        val startHour = event.startTime.split(":").first().toInt() - 9
        val endHour = event.endTime.split(":").first().toInt() - 9

        val top = (startHour * hourHeight).toInt()
        val bottom = (endHour * hourHeight).toInt()

        return (bottom-top)
    }

    fun cal_margin(sortedEvents: List<EventData>, context: Context): ArrayList<Int>{
        val margins = ArrayList<Int>()
        for (i in 0..sortedEvents.size-1){
            val eventHeightPerHour = context.resources.getDimension(R.dimen.event_height_per_hour)
            val timeSlotHeight = eventHeightPerHour

            val hourHeight = timeSlotHeight.toFloat() // assume each time slot is one hour
            val startHour = sortedEvents[i].startTime.split(":").first().toInt() - 9
            val endHour = sortedEvents[i].endTime.split(":").first().toInt() - 9


            var top = 0;
            if (i==0){
                top = (startHour * hourHeight).toInt()
            }else{
                val startHourPrev = sortedEvents[i-1].endTime.split(":").first().toInt() - 9
                top = (startHour * hourHeight).toInt() - (startHourPrev*hourHeight).toInt()
            }
            val bottom = (endHour * hourHeight).toInt()

            margins.add(top)
        }
        return margins
    }

    fun Usefull_data(eventList: ArrayList<EventList>): List<EventData>{
        var neweventList : ArrayList<EventData> = ArrayList()
        for(i in eventList){
            neweventList.add(EventData(i.id.toString(),i.name.toString(), getTimeFromDate(i.start_time.toString()), getTimeFromDate(i.end_time.toString()), getDayFromDate(i.start_time.toString()), getDayFromDate(i.end_time.toString()), i.venue.toString()) )
        }
        return neweventList
    }

    fun getTimeFromDate(dateTime: String): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        dateFormat.timeZone = TimeZone.getTimeZone("UTC+5:30")
        val date = dateFormat.parse(dateTime)
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        timeFormat.timeZone = TimeZone.getTimeZone("UTC")
        return timeFormat.format(date)
    }

    fun getDayFromDate(dateString: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        val date = inputFormat.parse(dateString)
        val outputFormat = SimpleDateFormat("dd", Locale.getDefault())
        return outputFormat.format(date)
    }
}