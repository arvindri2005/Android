package com.iitp.anwesha.home.functions

import android.content.Context
import android.media.Image
import android.text.BoringLayout
import android.widget.FrameLayout
import android.widget.FrameLayout.LayoutParams
import android.widget.ImageView
import com.iitp.anwesha.databinding.FragmentHomeBinding

class MapClickHandle(val context: Context, val binding: FragmentHomeBinding) {
    fun mapClick(){

        val nescafe = binding.nes
        val sac = binding.sac
        val gym =binding.gym
        val basketball = binding.basketball
        val nsit = binding.nsit
        val food_court = binding.foodCourt
        val senate = binding.senate
        val helipad = binding.helipad
        val main_stage = binding.mainStage
        val lh1 = binding.lh1
        val lh2 = binding.lh2

        val layoutParams = FrameLayout.LayoutParams(
            200,
            200
        )
        val layoutParams1 = FrameLayout.LayoutParams(
            200,
            200
        )
        val layoutParams2 = FrameLayout.LayoutParams(
            200,
            200
        )

        val layoutParams3 = FrameLayout.LayoutParams(
            200,
            200
        )
        val layoutParams4 = FrameLayout.LayoutParams(
            200,
            200
        )
        val layoutParams5 = FrameLayout.LayoutParams(
            200,
            200
        )
        val layoutParam6 = FrameLayout.LayoutParams(
            200,
            200
        )
        val layoutParams7 = FrameLayout.LayoutParams(
            200,
            200
        )
        val layoutParams8 = FrameLayout.LayoutParams(
            200,
            200
        )
        val layoutParams9 = FrameLayout.LayoutParams(
            200,
            200
        )
        val layoutParams10 = FrameLayout.LayoutParams(
            200,
            200
        )

        setparama(layoutParams, nescafe, 1200, 1000)
        setparama(layoutParams1, sac, 1840, 980)
        setparama(layoutParams2, gym, 2781, 1100)
        setparama(layoutParams3, basketball, calleft(1080), caltop(516))
        setparama(layoutParams4, nsit, calleft(806), caltop(523))
        setparama(layoutParams5, food_court, calleft(730), caltop(477))
        setparama(layoutParam6, senate, calleft(564), caltop(501))
        setparama(layoutParams7, lh2, calleft(439), caltop(396))
        setparama(layoutParams8, lh1, calleft(390), caltop(376))
        setparama(layoutParams9, helipad, calleft(299), caltop(633))
        setparama(layoutParams10, main_stage, calleft(458), caltop(532))


    }

    fun caltop(margin : Int) : Int{
        return (margin.toFloat()*(2.46305f)).toInt()
    }
    fun calleft(margin : Int) : Int{
        return (margin.toFloat()*(2.4390f)).toInt()
    }
    fun setparama(layoutParams: LayoutParams,image: ImageView,  leftmargin: Int, topmargin: Int){
        layoutParams.leftMargin = leftmargin
        layoutParams.topMargin = topmargin
        image.layoutParams = layoutParams
    }
}