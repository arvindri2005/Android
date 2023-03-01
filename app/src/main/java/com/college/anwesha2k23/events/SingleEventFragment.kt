package com.college.anwesha2k23.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.college.anwesha2k23.databinding.FragmentSingleEventBinding
import com.college.anwesha2k23.home.EventList
import java.text.SimpleDateFormat
import java.util.*

class SingleEventFragment : Fragment() {
    private lateinit var binding : FragmentSingleEventBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSingleEventBinding.inflate(inflater,container,false)
        val event = arguments?.getSerializable("event") as? EventList

        if (event != null) {
            Glide.with(requireContext())
                .load(event.poster)
                .into(binding.eventPoster)

            binding.eventName.text = event.name

            val inputString = event.start_time
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            val outputFormat = SimpleDateFormat("dd MMMM yyyy, HH:mm", Locale.getDefault())
            inputFormat.timeZone = TimeZone.getTimeZone("UTC+5:30") // set input timezone to UTC
            val date = inputFormat.parse(inputString!!) // parse input string into date object
            outputFormat.timeZone = TimeZone.getDefault() // set output timezone to default timezone
            val outputString = outputFormat.format(date!!) // format date object into output string
            val separatedStrings = outputString.split(",").map { it.trim() }
            binding.eventDate.text = separatedStrings[0]
            binding.eventStartTime.text = separatedStrings[1]

            binding.description.text = event.description

            if (event.is_solo!!){
                binding.teamSize.text = "Solo Event"
            }
            else{
                binding.teamSize.text = "${event.min_team_size}-${event.min_team_size} Peoples"
            }

//            val organizerT = event.organizer!!
//            var organizer = ""
//            for (string in organizerT) {
//                organizer = organizer+string +"\n"
//            }
//            binding.organizer.text = organizer


        }
        return binding.root
    }
}