package com.iitp.anwesha.calendar.functions

import android.content.Context
import android.util.Log
import com.iitp.anwesha.R
import com.iitp.anwesha.calendar.dataFiles.EventData
import com.iitp.anwesha.home.EventList
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

private const val TAG = "CalendarFunctions"
class CalendarFunctions {

    fun getEventsByLocation(filteredList: List<EventData>): Map<String, List<EventData>>{
        val eventDataByLocationMap: Map<String, List<EventData>> = filteredList.groupBy { it.venue }
        Log.d(TAG, eventDataByLocationMap.toString())
        return eventDataByLocationMap
    }

    fun retHeight(event: EventData, context: Context): Int {
        val eventHeightPerHour = context.resources.getDimension(R.dimen.event_height_per_hour)

        val startHour = event.startTime.split(":").first().toInt() - 4
        val endHour = event.endTime.split(":").first().toInt() - 4

        val top = (startHour * eventHeightPerHour).toInt()
        val bottom = (endHour * eventHeightPerHour).toInt()

        return if (event.startdate == event.enddate) {
            bottom - top
        } else {
            eventHeightPerHour.toInt()
        }
    }

    fun calMargin(sortedEvents: List<EventData>, context: Context): ArrayList<Int>{
        val margins = ArrayList<Int>()

        for (i in sortedEvents.indices) {

            val eventHeightPerHour = context.resources.getDimension(R.dimen.event_height_per_hour)
            val eventHeightPerMinute = eventHeightPerHour / 60

            val startHour = ((sortedEvents[i].startTime.split(":").first()
                .toInt() - 6) * 60) + (sortedEvents[i].startTime.split(":").last().toInt())



            val top = if (i > 0 && sortedEvents[i].startdate == sortedEvents[i].enddate) {
                val startHourPrev = (sortedEvents[i - 1].endTime.split(":").first()
                    .toInt() - 6) * 60 + (sortedEvents[i - 1].endTime.split(":").last().toInt())
                ((startHour * eventHeightPerMinute) - (startHourPrev * eventHeightPerMinute)).toInt()
            } else {
                (startHour * eventHeightPerMinute).toInt()

            }

            margins.add(top)
        }
        Log.d(TAG, margins.size.toString())

        return margins
    }

    fun usefulData(eventList: ArrayList<EventList>): List<EventData>{
        val newEventList : ArrayList<EventData> = ArrayList()
        for(i in eventList){
            newEventList.add(EventData(i.id.toString(),i.name.toString(), getTimeFromDate(i.start_time.toString()), getTimeFromDate(i.end_time.toString()), getDayFromDate(i.start_time.toString()), getDayFromDate(i.end_time.toString()), i.venue.toString()) )
        }
        return newEventList
    }

    private fun getTimeFromDate(dateTime: String): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")
        val date = dateFormat.parse(dateTime)
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        return timeFormat.format(date!!)
    }

    private fun getDayFromDate(dateString: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        val date = inputFormat.parse(dateString)
        val outputFormat = SimpleDateFormat("dd", Locale.getDefault())
        return outputFormat.format(date!!)
    }
}