package com.iitp.anwesha.calendar.Functions

import android.content.Context
import android.widget.LinearLayout
import android.widget.TextView
import com.iitp.anwesha.R

class ButtonsFunction() {

    fun selectButton(
        selectedButto: TextView,
        context: Context,
        oth_but1: TextView,
        oth_but2: TextView,
        linear: LinearLayout
    ) {
        selectedButto.setBackgroundColor(context.resources.getColor(R.color.violet_cl))
        selectedButto.setTextColor(context.resources.getColor(R.color.white))
        oth_but1.setBackgroundColor(context.resources.getColor(R.color.white))
        oth_but1.setTextColor(context.resources.getColor(R.color.violet_cl))
        oth_but2.setBackgroundColor(context.resources.getColor(R.color.white))
        oth_but2.setTextColor(context.resources.getColor(R.color.violet_cl))
        linear.setBackgroundResource(R.drawable.sc_days_bg)

    }
}