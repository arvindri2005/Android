package com.college.anwesha2k23.calendar

import android.content.Context
import android.graphics.Color
import com.college.anwesha2k23.R
import java.util.*

class CalendarFunctions() {

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

}