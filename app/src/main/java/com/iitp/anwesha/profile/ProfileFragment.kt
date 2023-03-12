package com.iitp.anwesha.profile

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.iitp.anwesha.databinding.FragmentProfileBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ProfileFragment(context : Context) : Fragment(){
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private var isEditProfile = false

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
                    binding.profileName.setText(userInfo.full_name.toString())
                    binding.anweshaId.setText(userInfo.anwesha_id)
                    binding.anweshaId2.setText(userInfo.anwesha_id)
                    binding.phoneNumber.setText(userInfo.phone_number)
                    binding.emailId.setText(userInfo.email_id)
                    binding.collegeName.setText(userInfo.college_name )
                    binding.gender.setText(userInfo.gender ?: "LIQUID" )
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
                requireActivity().runOnUiThread(Runnable {
                    binding.rvRegistered.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                     binding.rvRegistered.adapter = ProfileEventsAdapter(eventsInfo.solo)
                })
            }

        }


        binding.editProfile.setOnClickListener {
            val animation =
                AnimationUtils.loadAnimation(context, com.airbnb.lottie.R.anim.abc_fade_in)
            it.startAnimation(animation)
            if(isEditProfile) {
                setEditable(false, binding.profileName, InputType.TYPE_NULL)
                setEditable(false, binding.phoneNumber, InputType.TYPE_NULL)
                setEditable(false, binding.emailId, InputType.TYPE_NULL)
                setEditable(false, binding.collegeName, InputType.TYPE_NULL)
                CoroutineScope(Dispatchers.IO).launch {
                    editProfile()
                    isEditProfile = !isEditProfile

                }
                (it as ImageView).setImageDrawable(
                    ResourcesCompat.getDrawable(
                        resources,
                        com.iitp.anwesha.R.drawable.edit_profile_icon,
                        resources.newTheme()
                    )
                )

            }
            else {
                (it as ImageView).setImageDrawable(
                    ResourcesCompat.getDrawable(
                        resources,
                        com.iitp.anwesha.R.drawable.edit_profile,
                        resources.newTheme()
                    )
                )
                setEditable(true, binding.profileName, InputType.TYPE_CLASS_TEXT)
                setEditable(true, binding.phoneNumber, InputType.TYPE_CLASS_PHONE)
                setEditable(true, binding.emailId, InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
                setEditable(true, binding.collegeName, InputType.TYPE_CLASS_TEXT)
                isEditProfile = !isEditProfile
            }
        }
    }

    private fun setEditable(editable: Boolean, field: EditText, inputType: Int) {
        field.isClickable = editable
        field.isFocusable = editable
        field.isFocusableInTouchMode = editable
        field.isCursorVisible = editable
        field.inputType = inputType
    }


    private suspend fun editProfile() {
        val name = binding.profileName.text.toString()
        val phone = binding.phoneNumber.text.toString()
        val college = binding.collegeName.text.toString()

        val updateProfile = UpdateProfile(phone, name, college)

        val response = UserProfileApi(requireContext()).profileApi.updateProfile(updateProfile)

        requireActivity().runOnUiThread {
            if (response.isSuccessful) {
                Toast.makeText(context, "Profile Updated Successfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Could not update profile", Toast.LENGTH_SHORT).show()
            }
        }

    }

}