package com.college.anwesha2k23.campusAmbassador

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.lottie.LottieDrawable
import com.college.anwesha2k23.R
import com.college.anwesha2k23.databinding.FragmentCaOpenBinding

class CaOpenFragment : Fragment() {
    private lateinit var binding: FragmentCaOpenBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCaOpenBinding.inflate(inflater, container, false)
        val view = binding.root
        setAnime()

        binding.button.setOnClickListener {
            showFragment()
        }

        return view
    }

    private fun showFragment() {
        val fram = activity?.supportFragmentManager?.beginTransaction()
        fram?.replace(R.id.CaContainer, CaRegisterFragment())
        fram?.addToBackStack("true")
        fram?.commit()
    }

    fun setAnime() {
        binding.animationView.setAnimation(R.raw.ca_anime)
        binding.animationView.repeatCount = LottieDrawable.INFINITE
        binding.animationView.playAnimation()
    }

    companion object {
        fun isConnectionAvailable(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = connectivityManager.activeNetworkInfo
            if (netInfo != null && netInfo.isConnected
                && netInfo.isConnectedOrConnecting
                && netInfo.isAvailable
            ) {
                return true
            }
            return false
        }
    }
}