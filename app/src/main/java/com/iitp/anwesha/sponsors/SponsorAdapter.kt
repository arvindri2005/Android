package com.iitp.anwesha.sponsors

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.iitp.anwesha.R
import com.iitp.anwesha.databinding.SponsorItemBinding

class SponsorAdapter (private val sponsorList: List<SponsorResponse>, val context: Context): RecyclerView.Adapter<SponsorAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(SponsorItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = sponsorList[position]
        holder.sponsorName.text = currentItem.sponsor_name
        val link = "https://anweshabackend.xyz/" + currentItem.sponsor_logo
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