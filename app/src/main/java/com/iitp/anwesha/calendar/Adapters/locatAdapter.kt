package com.iitp.anwesha.calendar.Adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.iitp.anwesha.R

class locatAdapter() :
    RecyclerView.Adapter<locatAdapter.ViewHolder>() {
    var locationlist: List<String> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.location_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val location: String = locationlist[position]
        holder.bind(location)
    }

    override fun getItemCount() = locationlist.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tv_location = itemView.findViewById<TextView>(R.id.LocationName)

        fun bind(location: String) {
            tv_location.text = location
        }
    }

    fun setList(ls2: List<String>) {
        locationlist = ls2
        notifyDataSetChanged()
    }

}