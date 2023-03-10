package com.college.anwesha2k23.calendar.Adapters

import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.college.anwesha2k23.R
import com.college.anwesha2k23.calendar.CustomItemDecoration
import com.college.anwesha2k23.calendar.DataFiles.EventData
import com.college.anwesha2k23.calendar.Functions.CalendarFunctions
import com.college.anwesha2k23.home.EventList
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class EventAdapter(val reallist: ArrayList<EventList>, private val onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<EventAdapter.ViewHolder>() {
    var events: MutableList<EventData> = mutableListOf()
    var eventList: List<List<EventData>> = emptyList()
    var locationlist: List<String> = emptyList()

    interface OnItemClickListener {
        fun onItemClick(verticalItem: EventList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.vertical_rv, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val location: String = locationlist[position]
        val events: List<EventData> = eventList[position]
        Log.d("checker", events.toString())
        holder.bind(location, events)
    }

    override fun getItemCount() = eventList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tv_location = itemView.findViewById<TextView>(R.id.LocationName)
        private val verticalRecyclerView: RecyclerView =
            itemView.findViewById(R.id.rv_events)

        fun bind(location: String, event: List<EventData>) {
            tv_location.text = location
            event.sortedBy { it.startTime.split(":").first().toInt()}
            val margins = CalendarFunctions().cal_margin(event, itemView.context)

            val verticalAdapter = VerticalAdapter(event, reallist,margins, object : VerticalAdapter.OnItemClickListener {
                override fun onItemClick(verticalItem: EventList) {
                    onItemClickListener.onItemClick(verticalItem)
                }
            })


            verticalRecyclerView.layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.VERTICAL, false)
            verticalRecyclerView.isNestedScrollingEnabled = false
            verticalRecyclerView.adapter = verticalAdapter
        }
    }

    fun setList(list: MutableList<EventData>, ls1: List<List<EventData>>,ls2: List<String>) {
        events.clear()
        events = list
        eventList = ls1
        locationlist = ls2
        notifyDataSetChanged()
    }

}
class VerticalAdapter(
    val data: List<EventData>,
    val reallist: ArrayList<EventList>,
    val margins: ArrayList<Int>,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<VerticalAdapter.ViewHolder>() {

    var sorted_data: List<EventData> = data.sortedBy { it.startTime.split(":").first().toInt() }

    interface OnItemClickListener {
        fun onItemClick(verticalItem: EventList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.calendat_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(sorted_data[position])

        val layoutParams = RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.MATCH_PARENT,
            1680
        )
        layoutParams.topMargin = margins[position]
        val displayMetrics = holder.itemView.resources.displayMetrics
        layoutParams.height = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 70f, displayMetrics).toInt()

        holder.itemView.layoutParams = layoutParams


    }

    override fun getItemCount(): Int {
        return sorted_data.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.tv_event_title)
        private val light = itemView.findViewById<View>(R.id.view_light)
        private val dark = itemView.findViewById<View>(R.id.view_dark)

        fun bind(event: EventData) {
            itemView.setOnClickListener {
                onItemClickListener.onItemClick(getDataFileByString(event.id, reallist)!!)
            }
            titleTextView.text = event.title

//            itemView.layoutParams.height = CalendarFunctions().retHeight(event, itemView.context)

            val colors = itemView.resources.getIntArray(R.array.androidcolors)
            val randomColor = colors[Random().nextInt(colors.size)]

            val view = light
            val view2 = dark
            view.setBackgroundColor(randomColor)
            view2.setBackgroundColor(randomColor)
            view.alpha = 0.1f
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
