package com.college.anwesha2k23.events

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.college.anwesha2k23.databinding.EventDesignBinding

class EventAdapter(private val eventList: ArrayList<EventList>): RecyclerView.Adapter<EventAdapter.MyViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(EventDesignBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = eventList[position]
        holder.eventPoster.setImageResource(currentItem.eventPoster)
        holder.eventName.text = currentItem.eventName
        holder.eventLocation.text = currentItem.eventLocation
        holder.eventDate.text = currentItem.eventDate
        holder.eventTime.text = currentItem.eventTime

    }

    override fun getItemCount(): Int {
        return eventList.size
    }

    class MyViewHolder( binding: EventDesignBinding ):RecyclerView.ViewHolder(binding.root){
        val eventPoster : ImageView = binding.eventPoster
        val eventName : TextView = binding.eventName
        val eventLocation: TextView = binding.eventLocation
        val eventDate: TextView = binding.eventDate
        val eventTime: TextView = binding.eventTime


    }

}