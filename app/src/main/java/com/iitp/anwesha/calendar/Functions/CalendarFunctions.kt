package com.iitp.anwesha.calendar.Functions

import android.content.Context
import android.util.Log
import com.iitp.anwesha.R
import com.iitp.anwesha.calendar.DataFiles.EventData
import com.iitp.anwesha.home.EventList
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

        val hourHeight = timeSlotHeight.toFloat()

        val startHour = event.startTime.split(":").first().toInt() - 4
        val endHour = event.endTime.split(":").first().toInt() -4

        val top = (startHour * hourHeight).toInt()
        val bottom = (endHour * hourHeight).toInt()

        if (event.startdate == event.enddate){
            return bottom-top
        }else{
            return hourHeight.toInt()
        }
    }

    fun cal_margin(sortedEvents: List<EventData>, context: Context): ArrayList<Int>{
        val margins = ArrayList<Int>()

        for (i in sortedEvents.indices){

            val eventHeightPerHour = context.resources.getDimension(R.dimen.event_height_per_hour)
            val eventHeightPerMinute = eventHeightPerHour/60
            val timeSlotHeight = eventHeightPerHour
            val minuteHeight = eventHeightPerMinute.toFloat()

            val hourHeight = timeSlotHeight.toFloat() // assume each time slot is one hour
            val startHour = ((sortedEvents[i].startTime.split(":").first().toInt() - 3 )*60) + (sortedEvents[i].startTime.split(":").last().toInt())
            val endHour = (sortedEvents[i].endTime.split(":").first().toInt() - 3)*60 +  (sortedEvents[i].endTime.split(":").last().toInt())

            var top = 0;

            if (i > 0 && sortedEvents[i].startdate == sortedEvents[i].enddate){
                val startHourPrev = (sortedEvents[i-1].endTime.split(":").first().toInt() - 3)*60  + (sortedEvents[i-1].endTime.split(":").last().toInt())
                top = ((startHour * minuteHeight)- (startHourPrev* minuteHeight)).toInt()
            }else{
                top = (startHour * minuteHeight).toInt()

            }
            val bottom = (endHour * minuteHeight).toInt()

            margins.add(top)
        }
        Log.d("123 margin", margins.size.toString())

        return margins
    }

    fun Usefull_data(eventList: ArrayList<EventList>): List<EventData>{
        val neweventList : ArrayList<EventData> = ArrayList()
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