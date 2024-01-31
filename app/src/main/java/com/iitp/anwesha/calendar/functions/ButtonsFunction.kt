package com.iitp.anwesha.calendar.functions

import android.content.Context
import android.widget.LinearLayout
import android.widget.TextView
import com.iitp.anwesha.R

class ButtonsFunction() {

    fun selectButton(
        selectedButton: TextView,
        context: Context,
        othBtn1: TextView,
        othBtn2: TextView,
        linear: LinearLayout
    ) {
        selectedButton.setBackgroundColor(context.resources.getColor(R.color.violet_cl))
        selectedButton.setTextColor(context.resources.getColor(R.color.white))
        othBtn1.setBackgroundColor(context.resources.getColor(R.color.white))
        othBtn1.setTextColor(context.resources.getColor(R.color.violet_cl))
        othBtn2.setBackgroundColor(context.resources.getColor(R.color.white))
        othBtn2.setTextColor(context.resources.getColor(R.color.violet_cl))
        linear.setBackgroundResource(R.drawable.sc_days_bg)

    }
}