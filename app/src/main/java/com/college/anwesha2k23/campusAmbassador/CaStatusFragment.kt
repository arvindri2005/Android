package com.college.anwesha2k23.campusAmbassador

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.lottie.LottieDrawable
import com.college.anwesha2k23.R
import com.college.anwesha2k23.databinding.FragmentCaStatusBinding

class CaStatusFragment : Fragment() {
    private lateinit var binding: FragmentCaStatusBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCaStatusBinding.inflate(inflater, container, false)
        val view = binding.root

        setAnime()

        return view
    }

    fun setAnime() {
        binding.animationView.setAnimation(R.raw.success)
        binding.animationView.repeatCount = 0
        binding.animationView.playAnimation()
    }
}