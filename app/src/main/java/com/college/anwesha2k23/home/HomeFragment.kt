package com.college.anwesha2k23.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieDrawable
import com.college.anwesha2k23.R
import com.college.anwesha2k23.databinding.FragmentHomeBinding
import com.college.anwesha2k23.events.EventAdapter
import com.college.anwesha2k23.events.EventList
import com.google.android.material.bottomsheet.BottomSheetBehavior


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: EventAdapter
    private lateinit var newEventView : RecyclerView
    private lateinit var newEventList : ArrayList<EventList>
    private lateinit var eventPoster : Array<Int>
    private lateinit var eventName : Array<String>
    private lateinit var eventLocation :  Array<String>
    private lateinit var eventDate : Array<String>
    private lateinit var eventTime : Array<String>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater,container,false)

        val bottomSheet = binding.eventBottomSheet
        val behavior = BottomSheetBehavior.from(bottomSheet)
        behavior.peekHeight = 400
        behavior.state = BottomSheetBehavior.STATE_COLLAPSED
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getUserData()
        val layoutManager = LinearLayoutManager(context)
        newEventView = binding.eventsList
        newEventView.layoutManager = layoutManager
        newEventView.setHasFixedSize(true)
        adapter = EventAdapter(newEventList)
        newEventView.adapter = adapter

        setAnime()
    }

    fun setAnime() {
        binding.animationView.setAnimation(R.raw.map_replace)
        binding.animationView.repeatCount = LottieDrawable.INFINITE
        binding.animationView.playAnimation()
    }

    private fun getUserData() {
        newEventList = arrayListOf()
        eventPoster = arrayOf(
            R.drawable.poster,
            R.drawable.event
        )
        eventName = arrayOf(
            "DJ Night",
            "To be update"
        )
        eventLocation = arrayOf(
            "Near Block 3",
            "To be update"
        )
        eventDate = arrayOf(
            "29 September 2022",
            "4 December 2022"
        )
        eventTime = arrayOf(
            "18:00",
            "To be update"
        )
        for(i in eventPoster.indices){
            val event = EventList(eventPoster[i], eventName[i], eventLocation[i], eventDate[i], eventTime[i])
            newEventList.add(event)
        }
    }


}