package com.college.anwesha2k23.profile

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.college.anwesha2k23.R
import com.college.anwesha2k23.databinding.FragmentProfileBinding
import com.college.anwesha2k23.events.ProfileEventsAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding?= null
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

    @RequiresApi(Build.VERSION_CODES.O)
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
                    binding.profileName.setText(userInfo.full_name)
                    binding.anweshaId.setText(userInfo.anwesha_id)
                    binding.phoneNumber.setText(userInfo.phone_number)
                    binding.emailId.setText(userInfo.email_id)
                    binding.collegeName.setText(userInfo.college_name ?: "XXXXXXXXXXX")
//                    binding.profileAge.setText(userInfo.age ?: 0)
                    binding.profileAge.setText(userInfo.age.toString().ifBlank { "0" })
                    binding.profileGender.setText(userInfo.gender ?: "XXXXXXXX")
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

        binding.editProfile.setOnClickListener {
            val animation =
                AnimationUtils.loadAnimation(context, com.airbnb.lottie.R.anim.abc_fade_in)
            it.startAnimation(animation)
            if(isEditProfile) {
                setEditable(false, binding.profileName, InputType.TYPE_NULL)
                setEditable(false, binding.phoneNumber, InputType.TYPE_NULL)
                setEditable(false, binding.emailId, InputType.TYPE_NULL)
                setEditable(false, binding.collegeName, InputType.TYPE_NULL)
                setEditable(false, binding.profileAge, InputType.TYPE_CLASS_NUMBER)
                setEditable(false, binding.profileGender, InputType.TYPE_CLASS_TEXT)
                CoroutineScope(Dispatchers.IO).launch {
                    editProfile()
                    isEditProfile = !isEditProfile

                }
                (it as ImageView).setImageDrawable(
                    ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.edit_profile,
                        resources.newTheme()
                    )
                )

            }
            else {
                (it as ImageView).setImageDrawable(
                    ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.correct,
                        resources.newTheme()
                    )
                )
                setEditable(true, binding.profileName, InputType.TYPE_CLASS_TEXT)
                setEditable(true, binding.phoneNumber, InputType.TYPE_CLASS_PHONE)
                setEditable(true, binding.emailId, InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
                setEditable(true, binding.collegeName, InputType.TYPE_CLASS_TEXT)
                setEditable(true, binding.profileAge, InputType.TYPE_CLASS_NUMBER)
                setEditable(true, binding.profileGender, InputType.TYPE_CLASS_TEXT)
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
        val age = binding.profileAge.text.toString().toInt()

        val updateProfile = UpdateProfile(phone, name, college, age)

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
