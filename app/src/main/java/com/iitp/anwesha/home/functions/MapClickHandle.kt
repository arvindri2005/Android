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

        setparama(layoutParams, nescafe, dpToPx(492), dpToPx(406))
        setparama(layoutParams1, sac, dpToPx(792), dpToPx(452))
        setparama(layoutParams2, gym, dpToPx(1126), dpToPx(455))
        setparama(layoutParams3, basketball, dpToPx(1080), dpToPx(516))
        setparama(layoutParams4, nsit, dpToPx(806), dpToPx(523))
        setparama(layoutParams5, food_court, dpToPx(730), dpToPx(477))
        setparama(layoutParam6, senate, dpToPx(564), dpToPx(501))
        setparama(layoutParams7, lh2, dpToPx(439), dpToPx(396))
        setparama(layoutParams8, lh1, dpToPx(390), dpToPx(376))
        setparama(layoutParams9, helipad, dpToPx(299), dpToPx(633))
        setparama(layoutParams10, main_stage, dpToPx(458), dpToPx(532))
    }


    private fun dpToPx(dp: Int): Int {
        val density = context.resources.displayMetrics.density
        return (dp.toFloat() * density + 0.5f).toInt()
    }

    fun setparama(layoutParams: LayoutParams,image: ImageView,  leftmargin: Int, topmargin: Int){
        layoutParams.leftMargin = leftmargin
        layoutParams.topMargin = topmargin
        image.layoutParams = layoutParams
    }
}