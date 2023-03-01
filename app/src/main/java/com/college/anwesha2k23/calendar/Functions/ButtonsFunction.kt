package com.college.anwesha2k23.calendar.Functions

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.college.anwesha2k23.R
import com.college.anwesha2k23.databinding.FragmentCalendarBinding
import org.w3c.dom.Text

class ButtonsFunction() {

    fun selectButton(selectedButto: TextView, context: Context, oth_but1: TextView, oth_but2: TextView) {
        selectedButto.setBackgroundColor(context.resources.getColor(R.color.black))
        selectedButto.setTextColor(context.resources.getColor(R.color.white))
        oth_but1.setBackgroundColor(context.resources.getColor(R.color.white))
        oth_but1.setTextColor(context.resources.getColor(R.color.black))
        oth_but2.setBackgroundColor(context.resources.getColor(R.color.white))
        oth_but2.setTextColor(context.resources.getColor(R.color.black))

    }
}