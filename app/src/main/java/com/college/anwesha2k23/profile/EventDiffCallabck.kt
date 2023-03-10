package com.college.anwesha2k23.profile

import androidx.recyclerview.widget.DiffUtil

class EventDiffCallabck(
    private val old: List<MyEventDetails>,
    private val new: List<MyEventDetails>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return old.size
    }

    override fun getNewListSize(): Int {
        return new.size
    }

    override fun areItemsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        return old[oldPosition].event_id == new[newPosition].event_id
    }

    override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        return old[oldPosition] == new[newPosition]
    }

}