package com.iitp.anwesha.calendar.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iitp.anwesha.R
import com.iitp.anwesha.calendar.dataFiles.EventData
import com.iitp.anwesha.calendar.functions.CalendarFunctions
import com.iitp.anwesha.home.EventList
import java.util.Random

class EventAdapter(val realList: ArrayList<EventList>, private val onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<EventAdapter.ViewHolder>() {
    var events: MutableList<EventData> = mutableListOf()
    private var eventList: List<List<EventData>> = emptyList()
    private var locationList: List<String> = emptyList()

    interface OnItemClickListener {
        fun onItemClick(verticalItem: EventList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.vertical_rv, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val events: List<EventData> = eventList[position]
        Log.d("checker", events.toString())
        holder.bind(events)
    }

    override fun getItemCount() = eventList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val verticalRecyclerView: RecyclerView =
            itemView.findViewById(R.id.rv_events)

        fun bind(event: List<EventData>) {
            event.sortedBy { it.startTime.split(":").first().toInt()}
            val margins = CalendarFunctions().calMargin(event, itemView.context)

            val verticalAdapter = VerticalAdapter(event, realList,margins, object : VerticalAdapter.OnItemClickListener {
                override fun onItemClick(verticalItem: EventList) {
                    onItemClickListener.onItemClick(verticalItem)
                }
            })


            verticalRecyclerView.layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            verticalRecyclerView.isNestedScrollingEnabled = false
            verticalRecyclerView.adapter = verticalAdapter
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: MutableList<EventData>, ls1: List<List<EventData>>, ls2: List<String>) {
        events.clear()
        events = list
        eventList = ls1
        locationList = ls2
        notifyDataSetChanged()
    }

}
class VerticalAdapter(
    val data: List<EventData>,
    val realList: ArrayList<EventList>,
    private val margins: ArrayList<Int>,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<VerticalAdapter.ViewHolder>() {

    private var sortedData: List<EventData> = data.sortedBy { it.startTime.split(":").first().toInt() }

    interface OnItemClickListener {
        fun onItemClick(verticalItem: EventList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.calendat_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(sortedData[position])

        val layoutParams = RecyclerView.LayoutParams(
            2100,
            RecyclerView.LayoutParams.MATCH_PARENT
        )
        layoutParams.leftMargin = margins[position]
        layoutParams.width = CalendarFunctions().retHeight(sortedData[position], holder.itemView.context)

        holder.itemView.layoutParams = layoutParams
    }

    override fun getItemCount(): Int {
        return sortedData.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.tv_event_title)
        private val light = itemView.findViewById<View>(R.id.view_light)
        private val dark = itemView.findViewById<View>(R.id.view_dark)

        fun bind(event: EventData) {
            itemView.setOnClickListener {
                onItemClickListener.onItemClick(getDataFileByString(event.id, realList)!!)
            }
            titleTextView.text = event.title

            val colors = itemView.resources.getIntArray(R.array.androidcolors)
            val randomColor = colors[Random().nextInt(colors.size)]

            val view = light
            val view2 = dark
            view2.setBackgroundColor(randomColor)
            view.alpha = 0.5f
        }
    }

    private fun getDataFileByString(id: String, dataList: ArrayList<EventList>): EventList? {
        for (data in dataList) {
            if (data.id == id) {
                return data
            }
        }
        return null
    }
}
