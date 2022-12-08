package com.college.anwesha2k23.campusAmbassador

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.college.anwesha2k23.R
import com.college.anwesha2k23.databinding.FragmentCaRegisterBinding
import com.google.android.material.snackbar.BaseTransientBottomBar.ANIMATION_MODE_SLIDE
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody


class CaRegisterFragment : Fragment() {
    private lateinit var binding: FragmentCaRegisterBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCaRegisterBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.registerCA.setOnClickListener {
            val name = checkValue(binding.name) ?: return@setOnClickListener
            val email = checkValue(binding.emailId) ?: return@setOnClickListener
            val contact = checkValue(binding.number) ?: return@setOnClickListener
            val college = checkValue(binding.college) ?: return@setOnClickListener
            val years = checkValue(binding.year) ?: return@setOnClickListener
            val referral = binding.referral
            val pass = checkValue(binding.password) ?: return@setOnClickListener
            val confirmPass = checkValue(binding.cPassword) ?: return@setOnClickListener
            if(pass != confirmPass) {
                binding.cPassword.error = "Password does not match!"
                return@setOnClickListener
            }
            binding.cPassword.error = null


            Thread {
                val client = OkHttpClient()
                val mediaType = "application/json".toMediaTypeOrNull()
                val data =
                    "{\n  \"password\" : \"${pass}\",\n  \"phone_number\":  \"$contact\" ,\n  \"email_id\"  : \"$email\",\n  \"full_name\" : \"$name\" ,\n  \"college_name\" : \" $college\" ,\n  \"refferal_code\" : \"$referral\" ,\n  \"years_of_study\" : \"$years\"\n}"
                val body = data.toRequestBody(mediaType)
                val request = Request.Builder()
                    .url("https://backend.anwesha.live/campasambassador/register")
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .build()

                try {
                    val response = client.newCall(request).execute()
                    if (response.isSuccessful) {
                        val sharedPref = requireActivity().getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)
                        with(sharedPref.edit()) {
                            putBoolean(getString(R.string.user_ca_authentication), true)
                            apply()
                        }
                        showFragment()
                    }
                    else
                        Snackbar.make(view, "Could not verify the user!", Snackbar.LENGTH_LONG)
                            .setAnimationMode(ANIMATION_MODE_SLIDE).show()

                } catch (e: Exception) {
                    Snackbar.make(
                        view,
                        "Oops! It seems like an error... ${e.message}",
                        Snackbar.LENGTH_LONG
                    )
                        .setAnimationMode(ANIMATION_MODE_SLIDE).show()
                }
            }.start()

        }

        return view
    }

    private fun checkValue(input: TextInputLayout): String? {
        val value = input.editText?.text.toString()
        if(value == "") {
            input.error = "Required Field!"
            return null
        }
        input.error = null
        return value
    }


    private fun showFragment() {
        val fram = activity?.supportFragmentManager?.beginTransaction()
        fram?.replace(R.id.CaContainer, CaStatusFragment())
        fram?.commit()
    }
}