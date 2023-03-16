package com.iitp.anwesha.profile

import android.R
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.iitp.anwesha.databinding.FragmentProfileBinding

import com.yuyakaido.android.cardstackview.*

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.reflect.Type


class ProfileFragment(context: Context) : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private var isEditProfile = false
    private lateinit var fragmentContext: Context
    private val passes= ArrayList<MyEventDetails>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentContext = context
    }

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
            val clipboard =
                requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Anwesha ID", text)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(requireContext(), "$text copied to clipboard", Toast.LENGTH_SHORT).show()
        }

        binding.btRegenerate.setOnClickListener {
            regenerate()
        }

        CoroutineScope(Dispatchers.IO).launch {
            val response = UserProfileApi(requireContext()).profileApi.getProfile()
            if (response.isSuccessful) {
                val userInfo = response.body()!!
                Log.d("userinfo: ", "${userInfo.anwesha_id}, ${userInfo.full_name}")
                withContext(Dispatchers.Main) {
                    binding.profileName.setText(userInfo.full_name.toString())
                    binding.anweshaId.setText(userInfo.anwesha_id.toString().toUpperCase())
                    binding.anweshaId2.setText(userInfo.anwesha_id)
                    binding.phoneNumber.setText(userInfo.phone_number)
                    binding.emailId.setText(userInfo.email_id)
                    binding.collegeName.setText(userInfo.college_name)
                    val gender = userInfo.gender ?: "Liquid"
                    binding.gender.setText(gender.toString())
                    Glide.with(fragmentContext)
                        .load(userInfo.qr_code).placeholder(com.iitp.anwesha.R.drawable.qr_mock_bg)
                        .into(binding.imageView)
                    binding.visibleFrag.visibility = View.VISIBLE
                    binding.deliveryShimmer.visibility = View.GONE

                }
            } else {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        context,
                        "error found: ${response.message()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            withContext(Dispatchers.Main) {
                val response2 = UserProfileApi(fragmentContext).profileApi.getMyEvents()
                if (response2.isSuccessful) {
                    val eventsInfo = response2.body()!!
                    Log.e("PRINT", eventsInfo.toString())
                    val soloEvents = arrayListOf<MyEventDetails>()
                    for(i in eventsInfo.solo){
                        if (i.event_tags!="6"){
                            soloEvents.add(i)
                        }
                        else{
                            passes.add(i)
                        }

                    }
                    binding.rvRegistered.layoutManager =
                        LinearLayoutManager(fragmentContext, LinearLayoutManager.HORIZONTAL, false)
                    binding.rvRegistered.adapter =
                        ProfileEventsAdapter(soloEvents, fragmentContext)

                    binding.rvTeamAdapter.layoutManager =
                        LinearLayoutManager(fragmentContext, LinearLayoutManager.HORIZONTAL, false)
                    binding.rvTeamAdapter.adapter =
                        ProfileTeamsAdapter(eventsInfo.team, fragmentContext)
                }
            }
        }

        binding.editProfile.setOnClickListener {
            val animation =
                AnimationUtils.loadAnimation(context, com.airbnb.lottie.R.anim.abc_fade_in)
            it.startAnimation(animation)
            if (isEditProfile) {
                setEditable(false, binding.profileName, InputType.TYPE_NULL)
                setEditable(false, binding.phoneNumber, InputType.TYPE_NULL)
                setEditable(false, binding.collegeName, InputType.TYPE_NULL)
                setEditable(false, binding.gender, InputType.TYPE_NULL)
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
                val inputMethodManager =
                    requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)

            } else {
                (it as ImageView).setImageDrawable(
                    ResourcesCompat.getDrawable(
                        resources,
                        com.iitp.anwesha.R.drawable.edit_profile,
                        resources.newTheme()
                    )
                )
                setEditable(true, binding.profileName, InputType.TYPE_CLASS_TEXT)
                setEditable(true, binding.phoneNumber, InputType.TYPE_CLASS_PHONE)
                setEditable(true, binding.collegeName, InputType.TYPE_CLASS_TEXT)
                setEditable(true, binding.gender, InputType.TYPE_CLASS_TEXT)
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

    private fun regenerate() {
        binding.progressBar2.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                val response = QrGetApi(fragmentContext).qrApi.getQr()
                if (response.isSuccessful) {
                    val newqr = response.body()
                    Glide.with(fragmentContext)
                        .load(newqr!!.qr_code)
                        .into(binding.imageView)
                    binding.progressBar2.visibility = View.GONE
                }
            }
        }
    }

    private suspend fun editProfile() {
        val name = binding.profileName.text.toString()
        val phone = binding.phoneNumber.text.toString()
        val college = binding.collegeName.text.toString()
        val gender = binding.gender.text.toString()

        val updateProfile = UpdateProfile(phone, name, college, gender)

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