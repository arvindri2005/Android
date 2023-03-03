package com.college.anwesha2k23.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.college.anwesha2k23.MyDialog
import com.college.anwesha2k23.auth.AuthApi
import com.college.anwesha2k23.auth.UserAuthApi
import com.college.anwesha2k23.databinding.FragmentProfileBinding
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

            // user login first

            val response = UserProfileApi(requireContext()).profileApi.getProfile()
            if(response.isSuccessful) {
                val userInfo = response.body()!!
                Log.d("userinfo: ", "${userInfo.anwesha_id}, ${userInfo.full_name}")
                requireActivity().runOnUiThread ( Runnable {
                    binding.profileName.text = userInfo.full_name
                    binding.anweshaId.text = userInfo.anwesha_id
                    binding.phoneNumber.text = userInfo.phone_number
                    binding.emailId.text = userInfo.email_id
                    binding.collegeName.text = userInfo.college_name ?: "XXXXXXXXXXX"
                } )
            }
            else {
                requireActivity().runOnUiThread ( Runnable {
                    Toast.makeText(context, "error found: ${response.message()}", Toast.LENGTH_SHORT).show()
                } )
            }

            val response2 = UserProfileApi(requireContext()).profileApi.getMyEvents()
            if(response2.isSuccessful) {
                val eventsInfo = response2.body()!!
                requireActivity().runOnUiThread(Runnable {
                    binding.myEvents.adapter = ProfileEventsAdapter(eventsInfo.solo)

                })
            }
        }
    }
}
