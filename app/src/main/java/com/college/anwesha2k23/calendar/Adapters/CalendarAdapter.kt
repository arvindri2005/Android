package com.college.anwesha2k23.calendar.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.college.anwesha2k23.R
import com.college.anwesha2k23.calendar.DataFiles.EventData
import com.college.anwesha2k23.calendar.Functions.CalendarFunctions
import com.college.anwesha2k23.home.EventList
import java.util.*

class EventAdapter(val events: List<EventData>, val reallist: ArrayList<EventList>) : RecyclerView.Adapter<EventAdapter.ViewHolder>() {

    private lateinit var listener: OnItemClickListener

    interface OnItemClickListener{
        fun onItemClicked(event: EventList?)
    }

    fun setOnItemClickListener(mListener: OnItemClickListener){
        listener = mListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.calendat_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = events[position]
        holder.bind(event)

        holder.itemView.setOnClickListener{
            listener.onItemClicked(getDataFileByString(event.id.toString(), reallist))
        }
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
    fun getDataFileByString(id: String, dataList: ArrayList<EventList>): EventList?{
        for (data in dataList) {
            if (data.id == id) {
                return data
            }
        }
        return null
    }
}
