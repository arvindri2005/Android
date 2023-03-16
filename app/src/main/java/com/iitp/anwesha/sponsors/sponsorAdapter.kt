package com.iitp.anwesha.sponsors

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.iitp.anwesha.R
import com.iitp.anwesha.databinding.MyeventDesignBinding
import com.iitp.anwesha.databinding.SponsorItemBinding
import com.iitp.anwesha.profile.MyEventDetails
import java.text.SimpleDateFormat
import java.util.*

class sponsorAdapter (private val sponsorList: List<SponsorResponse>,val context: Context): RecyclerView.Adapter<sponsorAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(SponsorItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = sponsorList[position]
        holder.sponsorName.text = currentItem.sponsor_name
        val link = "https://backend.anwesha.live/" + currentItem.sponsor_logo.toString()
        Glide.with(context)
            .load(link).placeholder(R.drawable.sponsor_mock)
            .into(holder.sponsorImage)
    }

    override fun getItemCount(): Int {
        return sponsorList.size
    }

    class MyViewHolder( binding: SponsorItemBinding): RecyclerView.ViewHolder(binding.root){

        val sponsorName: TextView = binding.sponsorTitle
        val sponsorImage: ImageView = binding.sponsorIv

    }

}