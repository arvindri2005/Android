package com.iitp.anwesha.profile

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import com.iitp.anwesha.databinding.FragmentProfileBinding
import com.yuyakaido.android.cardstackview.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ProfileFragment(context : Context) : Fragment(){
    private var _binding: FragmentProfileBinding? = null
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

        binding.copyId.setOnClickListener {
            val text = binding.anweshaId.text.toString()
            val clipboard = requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Anwesha ID", text)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(requireContext(), "$text copied to clipboard", Toast.LENGTH_SHORT).show()
        }

        CoroutineScope(Dispatchers.IO).launch {

            val response = UserProfileApi(requireContext()).profileApi.getProfile()
            if (response.isSuccessful) {
                val userInfo = response.body()!!
                Log.d("userinfo: ", "${userInfo.anwesha_id}, ${userInfo.full_name}")
                requireActivity().runOnUiThread(Runnable {
                    binding.profileName.text = userInfo.full_name
                    binding.anweshaId.text = userInfo.anwesha_id
                    binding.phoneNumber.text = userInfo.phone_number
                    binding.emailId.text = userInfo.email_id
                    binding.collegeName.text = userInfo.college_name ?: "XXXXXXXXXXX"

                    binding.visibleFrag.visibility = View.VISIBLE
                    binding.deliveryShimmer.visibility = View.GONE
                })
            } else {
                requireActivity().runOnUiThread(Runnable {
                    Toast.makeText(
                        context,
                        "error found: ${response.message()}",
                        Toast.LENGTH_SHORT
                    ).show()
                })
            }

            val response2 = UserProfileApi(requireContext()).profileApi.getMyEvents()
            if (response2.isSuccessful) {
                val eventsInfo = response2.body()!!
                Log.e("PRINT", eventsInfo.toString())
                //TODO delete recycler view
                requireActivity().runOnUiThread(Runnable {
                    // binding.myEvents.adapter = ProfileEventsAdapter(eventsInfo.solo)
//                    adapter = CardStackAdapter(eventsInfo.solo)
//                    setupStackedCards()
                })
            }

        }
    }
}