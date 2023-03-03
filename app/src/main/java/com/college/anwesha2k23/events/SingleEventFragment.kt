package com.college.anwesha2k23.events

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

            val startTime = event.start_time
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            val outputFormat = SimpleDateFormat("dd MMMM yyyy, HH:mm", Locale.getDefault())
            inputFormat.timeZone = TimeZone.getTimeZone("UTC+5:30") // set input timezone to UTC
            val startDate = inputFormat.parse(startTime!!) // parse input string into date object
            outputFormat.timeZone = TimeZone.getDefault() // set output timezone to default timezone
            val startTimeString = outputFormat.format(startDate!!) // format date object into output string
            val startTimeSeparatedStrings = startTimeString.split(",").map { it.trim() }
            binding.eventDate.text = startTimeSeparatedStrings[0]
//            binding.eventStartTime.text = separatedStrings[1]

            binding.eventDescription.text = event.description

            if (event.is_solo!!){
                binding.teamSize.text = "Solo Event"
            }
            else{
                binding.teamSize.text = "${event.min_team_size}-${event.max_team_size} Peoples"
            }

            binding.registrationFee.text = "₹"+event.registration_fee

            val endTime = event.registration_deadline
            val endDate = inputFormat.parse(endTime!!)
            val endTimeString = outputFormat.format(endDate!!)
            val endTimeSeparatedString= endTimeString.split(",").map{it.trim()}
            binding.registerDeadline.text = "Registration ends on ${endTimeSeparatedString[0]}"

            binding.eventLocation.text = event.venue

            val organizerT = event.organizer!!
            var organizer = ""
            for (string in organizerT) {
                organizer = organizer+string[0] + " " +string[1]  +"\n"
            }
            binding.organizer.text = organizer

            binding.prize.text = "Prizes worth ₹${event.prize}"
//
            binding.registerBtn.setOnClickListener {
                if(event.is_active!!){
                    if(event.is_online!!){
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(event.registration_link))
                        startActivity(intent)
                    }
                    else{
                        Toast.makeText(context, "Event Registration will open soon", Toast.LENGTH_SHORT).show()
                    }
                }
                else{
                    Toast.makeText(context, "Registration are over", Toast.LENGTH_SHORT).show()
                }
            }

            binding.rulebookBtn.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(event.video))
                startActivity(intent)
            }
        }

        return binding.root
    }
}