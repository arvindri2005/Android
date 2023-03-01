package com.college.anwesha2k23.calendar.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.college.anwesha2k23.R

class TimeAdapter : RecyclerView.Adapter<TimeAdapter.ViewHolder>() {

    private val timeSlots = (9..23).map { "$it:00" }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.calendar_time_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val timeSlot = timeSlots[position]
        holder.bind(timeSlot)
    }

    override fun getItemCount() = timeSlots.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val textViewTimeSlot = itemView.findViewById<TextView>(R.id.time_text_start)

        fun bind(timeSlot: String) {
            textViewTimeSlot.text = timeSlot
        }
    }
}
