package com.iitp.anwesha.profile

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.iitp.anwesha.databinding.MySoloEventDesignBinding
import com.iitp.anwesha.databinding.MyTeamEventDesignBinding
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

class ProfileEventsAdapter(private val eventList: List<MyEventDetails>, val context: Context): RecyclerView.Adapter<ProfileEventsAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            MySoloEventDesignBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = eventList[position]
        holder.eventName.text = currentItem.event_name
        holder.eventStartTime.text = getTimeFromDate(currentItem.event_start_time)
        holder.eventDate.text = getDayFromDate(currentItem.event_start_time)
        val venue = currentItem.event_venue.split(",")
        holder.eventVenue.text = venue[0]
    }

    override fun getItemCount(): Int {
        return eventList.size
    }

    class MyViewHolder( binding: MySoloEventDesignBinding ):RecyclerView.ViewHolder(binding.root){

        val eventName: TextView = binding.eventName
        val eventStartTime: TextView = binding.eventTime
        val eventDate: TextView = binding.eventDate
        val eventVenue: TextView = binding.eventVenue


    }

    private fun getTimeFromDate(dateTime: String): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        dateFormat.timeZone = TimeZone.getTimeZone("UTC+5:30")
        val date = dateFormat.parse(dateTime)
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        timeFormat.timeZone = TimeZone.getTimeZone("UTC")
        return timeFormat.format(date!!)
    }

    private fun getDayFromDate(dateString: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        val date = inputFormat.parse(dateString)
        val outputFormat = SimpleDateFormat("dd MMMM", Locale.getDefault())
        return outputFormat.format(date!!)
    }

}

class ProfileTeamsAdapter(private val eventList: List<MyTeamDetails>, val context: Context): RecyclerView.Adapter<ProfileTeamsAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            MyTeamEventDesignBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = eventList[position]
        holder.eventName.text = currentItem.event_name
        holder.eventStartTime.text = getTimeFromDate(currentItem.event_start_time)
        holder.eventDate.text = getDayFromDate(currentItem.event_start_time)

        var anwId =""
        for (i in currentItem.team_members){
            anwId = "$anwId$i, "
        }
    }

    override fun getItemCount(): Int {
        return eventList.size
    }

    class MyViewHolder( binding: MyTeamEventDesignBinding ):RecyclerView.ViewHolder(binding.root){

        val eventName: TextView = binding.eventName
        val eventStartTime: TextView = binding.eventTime
        val eventDate: TextView = binding.eventDate


    }

    private fun getTimeFromDate(dateTime: String): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        dateFormat.timeZone = TimeZone.getTimeZone("UTC+5:30")
        val date = dateFormat.parse(dateTime)
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        timeFormat.timeZone = TimeZone.getTimeZone("UTC")
        return timeFormat.format(date!!)
    }

    private fun getDayFromDate(dateString: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        val date = inputFormat.parse(dateString)
        val outputFormat = SimpleDateFormat("dd MMMM", Locale.getDefault())
        return outputFormat.format(date!!)
    }

}