package com.iitp.anwesha.profile

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.iitp.anwesha.databinding.MySoloEventDesignBinding
import java.text.SimpleDateFormat
import java.util.*

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
        holder.eventStartTime.text = getTimeFromDate(currentItem.event_start_time.toString())
        holder.eventDate.text = getDayFromDate(currentItem.event_start_time.toString())
        val venue = currentItem.event_venue.split(",")
        holder.eventVenue.text = venue[0]
        if (currentItem.payment_done){
            holder.paymentDetails.visibility = View.GONE
        }
        else{

        }
    }

    override fun getItemCount(): Int {
        return eventList.size
    }

    class MyViewHolder( binding: MySoloEventDesignBinding ):RecyclerView.ViewHolder(binding.root){

        val eventName: TextView = binding.eventName
        val eventStartTime: TextView = binding.eventTime
        val eventDate: TextView = binding.eventDate
        val eventVenue: TextView = binding.eventVenue
        val paymentDetails: Button = binding.paymentBtn


    }

    fun getTimeFromDate(dateTime: String): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        dateFormat.timeZone = TimeZone.getTimeZone("UTC+5:30")
        val date = dateFormat.parse(dateTime)
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        timeFormat.timeZone = TimeZone.getTimeZone("UTC")
        return timeFormat.format(date)
    }

    fun getDayFromDate(dateString: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        val date = inputFormat.parse(dateString)
        val outputFormat = SimpleDateFormat("dd MMMM", Locale.getDefault())
        return outputFormat.format(date)
    }

}