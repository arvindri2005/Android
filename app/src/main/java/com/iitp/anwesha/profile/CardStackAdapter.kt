package com.iitp.anwesha.profile


import com.iitp.anwesha.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class CardStackAdapter(
    private var events: List<MyEventDetails> = emptyList()
) : RecyclerView.Adapter<CardStackAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_spot, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = events[position]
        holder.name.text = event.event_name
        holder.venue.text = event.event_venue
        holder.time.text = "At ${event.event_start_time}"

        holder.itemView.setOnClickListener { v ->
            Toast.makeText(v.context, event.event_name, Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return events.size
    }

    fun setEvents(events: List<MyEventDetails>) {
        this.events = events
    }

    fun getEvents(): List<MyEventDetails> {
        return events
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.title_text)
        var venue: TextView = view.findViewById(R.id.location_text)
        var time: TextView = view.findViewById(R.id.date_text)
    }

}