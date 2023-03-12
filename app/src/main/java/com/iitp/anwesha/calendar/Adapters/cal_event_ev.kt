package com.iitp.anwesha.calendar.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.iitp.anwesha.calendar.DataFiles.EventData
import com.iitp.anwesha.databinding.ScRvDesignBinding
import java.text.SimpleDateFormat
import java.util.*

class cal_event_ev(
) :
    RecyclerView.Adapter<cal_event_ev.ViewHolder>() {
    var events: MutableList<EventData> = mutableListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ScRvDesignBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val events = events[position]

        holder.eventName.text = events.title.toString()
        holder.eventStartTime.text = events.startTime + " - " + events.endTime
    }

    override fun getItemCount() = events.size

    class ViewHolder(binding: ScRvDesignBinding) : RecyclerView.ViewHolder(binding.root) {
        val eventName: TextView = binding.eventName
        val eventStartTime: TextView = binding.eventTime
    }

    fun setList(list: MutableList<EventData>) {
        events.clear()
        events = list
        notifyDataSetChanged()
    }

    fun getTimeFromDate(dateTime: String): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        dateFormat.timeZone = TimeZone.getTimeZone("UTC+5:30")
        val date = dateFormat.parse(dateTime)
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        timeFormat.timeZone = TimeZone.getTimeZone("UTC")
        return timeFormat.format(date)
    }


}