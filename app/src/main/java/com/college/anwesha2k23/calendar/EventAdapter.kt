package com.college.anwesha2k23.calendar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.college.anwesha2k23.R
import java.text.SimpleDateFormat
import java.util.*

class EventAdapter(val events: List<EventData>) : RecyclerView.Adapter<EventAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.calendat_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = events[position]
        holder.bind(event)
    }

    override fun getItemCount() = events.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val textViewTitle = itemView.findViewById<TextView>(R.id.textView4)
        private val light = itemView.findViewById<View>(R.id.view_light)
        private val dark = itemView.findViewById<View>(R.id.view_dark)

        fun bind(event: EventData) {
            textViewTitle.text = event.title


            itemView.layoutParams.height = CalendarFunctions().retHeight(event, itemView.context)

            val colors = itemView.resources.getIntArray(R.array.androidcolors)
            val randomColor = colors[Random().nextInt(colors.size)]

            val view = light
            val view2 = dark
            view.setBackgroundColor(randomColor)
            view2.setBackgroundColor(randomColor)
            view.alpha = 0.1f

        }
    }

    fun getTimeFromDate(dateTime: String): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")
        val date = dateFormat.parse(dateTime)
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        return timeFormat.format(date)
    }
}
