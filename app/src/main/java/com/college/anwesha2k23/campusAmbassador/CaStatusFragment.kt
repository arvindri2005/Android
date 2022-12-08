package com.college.anwesha2k23.campusAmbassador

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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

        followsocialmedia()

        return view
    }

    fun followsocialmedia() {
        binding.instagram.setOnClickListener {
            val insta = R.string.instagram.toString()
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse("https://www.instagram.com/anwesha.iitp/")
            activity?.startActivity(i)
        }

        binding.youtube.setOnClickListener {
            val intent =Intent(Intent.ACTION_VIEW);
            intent.setPackage("com.google.android.youtube");
            intent.setData(Uri.parse("https://www.youtube.com/@AnweshaIITP"));
            activity?.startActivity(intent);
        }
    }

    fun setAnime() {
        binding.animationView.setAnimation(R.raw.success)
        binding.animationView.repeatCount = 0
        binding.animationView.playAnimation()
    }
}