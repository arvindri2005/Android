package com.college.anwesha2k23.events

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.college.anwesha2k23.R
import com.college.anwesha2k23.databinding.EventDesignBinding
import com.college.anwesha2k23.databinding.MyEventDesignBinding
import com.college.anwesha2k23.home.EventList
import com.college.anwesha2k23.profile.MyEventDetails

class ProfileEventsAdapter(private val eventList: List<MyEventDetails>): RecyclerView.Adapter<ProfileEventsAdapter.MyViewHolder>(){

    private lateinit var listener: OnItemClickListener

    //Interface that will tell what happens when a event is clicked
    interface OnItemClickListener{
        fun onItemClicked(event: EventList)
    }

    fun setOnItemClickListener(mListener: OnItemClickListener){
        listener = mListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(MyEventDesignBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = eventList[position]
        val animation = AnimationUtils.loadAnimation(holder.itemView.context, android.R.anim.slide_in_left)

        holder.eventName.text = currentItem.event_name
        holder.eventStartTime.text = currentItem.event_start_time
        holder.eventEndTime.text = currentItem.event_end_time
        holder.eventVenue.text = currentItem.event_venue
        holder.itemView.startAnimation(animation)

    }

    override fun getItemCount(): Int {
        return eventList.size
    }

    class MyViewHolder( binding: MyEventDesignBinding ):RecyclerView.ViewHolder(binding.root){


        val eventName: TextView = binding.eventName
        val eventStartTime: TextView = binding.eventStartTime
        val eventEndTime: TextView = binding.eventEndTime
        val eventVenue: TextView = binding.eventVenue


    }

}