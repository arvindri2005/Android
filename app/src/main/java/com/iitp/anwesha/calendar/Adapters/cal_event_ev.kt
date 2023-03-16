package com.iitp.anwesha.calendar.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.iitp.anwesha.calendar.DataFiles.EventData
import com.iitp.anwesha.databinding.ScRvDesignBinding
import com.iitp.anwesha.home.EventAdapter
import com.iitp.anwesha.home.EventList
import java.text.SimpleDateFormat
import java.util.*

class cal_event_ev(val context: Context
) :
    RecyclerView.Adapter<cal_event_ev.ViewHolder>() {
    var events: MutableList<EventData> = mutableListOf()
    var event_list: List<EventList> = emptyList()
    private lateinit var listener: cal_event_ev.OnItemClickListener

    interface OnItemClickListener{
        fun onItemClicked(event: EventList)
    }

    fun setOnItemClickListener(mListener: cal_event_ev.OnItemClickListener){
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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val events = events[position]

        holder.eventName.text = events.title.toString()
        holder.eventStartTime.text = events.startTime + " - " + events.endTime
        val link : EventList= getDataFileByString(events.id,  event_list as ArrayList<EventList>)!!

        Glide.with(context)
            .load(link.poster).placeholder(com.iitp.anwesha.R.drawable.test_event_image)
            .into(holder.poster)

        holder.itemView.setOnClickListener{
            listener.onItemClicked(getDataFileByString(events.id,
                event_list as ArrayList<EventList>
            )!!)
        }

    }

    override fun getItemCount() = events.size

    class ViewHolder(binding: ScRvDesignBinding) : RecyclerView.ViewHolder(binding.root) {
        val eventName: TextView = binding.eventName
        val eventStartTime: TextView = binding.eventTime
        val poster: ImageView = binding.eventPoster
    }

    fun setList(list: MutableList<EventData>, reallist: List<EventList>) {
        events.clear()
        events = list
        event_list = reallist
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