package com.college.anwesha2k23.home

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieDrawable
import com.college.anwesha2k23.R
import com.college.anwesha2k23.databinding.FragmentHomeBinding
import com.college.anwesha2k23.events.EventAdapter
import com.college.anwesha2k23.events.EventList
import com.college.anwesha2k23.events.EventsViewModel
import com.college.anwesha2k23.events.SingleEventFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior


class HomeFragment : Fragment() {
    private var _binding : FragmentHomeBinding? =  null
    private val binding get()  = _binding!!
    private lateinit var eventViewModel: EventsViewModel
    private lateinit var adapter: EventAdapter
    private lateinit var newEventView : RecyclerView
    private lateinit var mContext: Context

    override fun onAttach(context: Context) {
        mContext = context
        super.onAttach(context)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        val bottomSheet = binding.eventBottomSheet
        val behavior = BottomSheetBehavior.from(bottomSheet)
        behavior.peekHeight = 1000
        behavior.state = BottomSheetBehavior.STATE_COLLAPSED

        eventViewModel= ViewModelProvider(this)[EventsViewModel::class.java]
        return binding.root



    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //preparing the recycler view
        val layoutManager = LinearLayoutManager(context)
        newEventView = binding.eventsList
        newEventView.layoutManager = layoutManager
        newEventView.setHasFixedSize(true)
        adapter = EventAdapter(eventViewModel.events)               //getting list of events from Events view Moder
        newEventView.adapter = adapter
        adapter.setOnItemClickListener(object : EventAdapter.OnItemClickListener{
            override fun onItemClicked(event: EventList) {          //when any event from the recycler view is clicked
                loadEvent(event)
            }

        })
        setAnime()

        binding.dayOne.setOnClickListener{
            Toast.makeText(context, "Day 1 is clicked", Toast.LENGTH_SHORT).show()
            //On click it will refresh the recycler view and show the list of event happening on that day
        }
        binding.dayTwo.setOnClickListener{
            Toast.makeText(context, "Day 2 is clicked", Toast.LENGTH_SHORT).show()
        }
        binding.dayThree.setOnClickListener{
            Toast.makeText(context, "Day 3 is clicked", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadEvent(event: EventList){
        val bundle = Bundle()
        bundle.putString("eventName", event.eventName)
        val fragment = SingleEventFragment()
        fragment.arguments = bundle
        val fragmentTransaction = activity?.supportFragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
    private fun setAnime() {
        binding.animationView.setAnimation(R.raw.map_replace)
        binding.animationView.repeatCount = LottieDrawable.INFINITE
        binding.animationView.playAnimation()
    }



}