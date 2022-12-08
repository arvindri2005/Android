package com.college.anwesha2k23.campusAmbassador

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.college.anwesha2k23.R
import com.college.anwesha2k23.databinding.FragmentCaRegisterBinding

class CaRegisterFragment : Fragment() {
    private lateinit var binding: FragmentCaRegisterBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCaRegisterBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.registerCA.setOnClickListener {
            showFragment()
        }

        return view
    }
    private fun showFragment() {
        val fram = activity?.supportFragmentManager?.beginTransaction()
        fram?.replace(R.id.CaContainer, CaStatusFragment())
        fram?.commit()
    }
}