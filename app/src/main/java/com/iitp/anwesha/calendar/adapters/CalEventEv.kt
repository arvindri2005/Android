package com.iitp.anwesha.calendar.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.iitp.anwesha.calendar.dataFiles.EventData
import com.iitp.anwesha.databinding.ScRvDesignBinding
import com.iitp.anwesha.home.EventList
import java.util.*

class CalEventEv(val context: Context
) :
    RecyclerView.Adapter<CalEventEv.ViewHolder>() {
    var events: MutableList<EventData> = mutableListOf()
    private var eventList: List<EventList> = emptyList()
    private lateinit var listener: OnItemClickListener

    interface OnItemClickListener{
        fun onItemClicked(event: EventList)
    }

    fun setOnItemClickListener(mListener: OnItemClickListener){
        listener = mListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ScRvDesignBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val events = events[position]

        holder.eventName.text = events.title
        holder.eventStartTime.text = events.startTime + " - " + events.endTime
        val link : EventList= getDataFileByString(events.id,  eventList as ArrayList<EventList>)!!

        Glide.with(context)
            .load(link.poster).placeholder(com.iitp.anwesha.R.drawable.test_event_image)
            .into(holder.poster)

        holder.itemView.setOnClickListener{
            listener.onItemClicked(getDataFileByString(events.id,
                eventList as ArrayList<EventList>
            )!!)
        }

    }

    override fun getItemCount() = events.size

    class ViewHolder(binding: ScRvDesignBinding) : RecyclerView.ViewHolder(binding.root) {
        val eventName: TextView = binding.eventName
        val eventStartTime: TextView = binding.eventTime
        val poster: ImageView = binding.eventPoster
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: MutableList<EventData>, realList: List<EventList>) {
        events.clear()
        events = list
        eventList = realList
        notifyDataSetChanged()
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