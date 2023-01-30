package com.college.anwesha2k23.events

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.college.anwesha2k23.databinding.FragmentSingleEventBinding

class SingleEventFragment : Fragment() {
    private var _binding: FragmentSingleEventBinding? = null
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
        _binding = FragmentSingleEventBinding.inflate(inflater,container,false)
        val args = this.arguments
        if (args != null) {
            binding.eventName.text = args.getString("eventName")
        }
        return binding.root
    }
}