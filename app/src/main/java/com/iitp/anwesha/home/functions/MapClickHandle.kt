package com.iitp.anwesha.home.functions

import android.content.Context
import android.widget.FrameLayout
import com.iitp.anwesha.databinding.FragmentHomeBinding

class MapClickHandle(val context: Context, val binding: FragmentHomeBinding) {
    fun mapClick(){

        val nescafe = binding.nes
        val admin = binding.admin
        val sac = binding.sac
        val gym =binding.gym

        val layoutParams = FrameLayout.LayoutParams(
            300,
            300
        )
        layoutParams.leftMargin =  1200
        layoutParams.topMargin = 1000
        nescafe.layoutParams = layoutParams

        val layoutParams2 = FrameLayout.LayoutParams(
            300,
            300
        )
        layoutParams2.leftMargin = 1385
        layoutParams2.topMargin = 1260
        admin.layoutParams = layoutParams2

        val layoutParams3 = FrameLayout.LayoutParams(
            300,
            300
        )
        layoutParams3.leftMargin = 1840
        layoutParams3.topMargin = 1020
        sac.layoutParams = layoutParams3

        val layoutParams4= FrameLayout.LayoutParams(
            300,
            300
        )
        layoutParams4.leftMargin = 2781
        layoutParams4.topMargin = 1100
        gym.layoutParams = layoutParams4

    }
}