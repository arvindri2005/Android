package com.college.anwesha2k23.profile

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.college.anwesha2k23.R
import com.college.anwesha2k23.databinding.FragmentProfileBinding
import com.college.anwesha2k23.events.EventAdapter
import com.college.anwesha2k23.events.ProfileEventsAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding?= null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        container?.removeAllViews()
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.myEvents.adapter = ProfileEventsAdapter(arrayListOf())



        CoroutineScope(Dispatchers.IO).launch {
            val response = UserProfileApi.profileApi.getProfile()
            if(response.isSuccessful) {
                val userInfo = response.body()!!
                binding.profileName.text = userInfo.full_name
                binding.anweshaId.text = userInfo.anwesha_id
                binding.phoneNumber.text = userInfo.phone_number
                binding.emailId.text = userInfo.email_id
                binding.collegeName.text = userInfo.college_name
            }

            val response2 = UserProfileApi.profileApi.getMyEvents()
            if(response2.isSuccessful) {
                val eventsInfo = response2.body()!!
                binding.myEvents.adapter = ProfileEventsAdapter(eventsInfo.solo)
            }
        }
    }
}
