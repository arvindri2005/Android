package com.iitp.anwesha.calendar.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.iitp.anwesha.R

class LocationAdapter :
    RecyclerView.Adapter<LocationAdapter.ViewHolder>() {
    private var locationList: List<String> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.location_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val location: String = locationList[position]
        holder.bind(location)
    }

    override fun getItemCount() = locationList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvLocation = itemView.findViewById<TextView>(R.id.LocationName)

        fun bind(location: String) {
            tvLocation.text = location
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(ls2: List<String>) {
        locationList = ls2
        notifyDataSetChanged()
    }

}