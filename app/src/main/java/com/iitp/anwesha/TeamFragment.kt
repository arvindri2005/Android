package com.iitp.anwesha

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Binder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.iitp.anwesha.databinding.FragmentTeamBinding


class TeamFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }
private lateinit var binding: FragmentTeamBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTeamBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.backBtn.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack(null, 0)
        }

        binding.linkedinButtonArv.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.linkedin.com/in/arvindri2005/")
            )
            startActivity(intent)
        }
        binding.linkedinButtonPrv.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.linkedin.com/in/praveen-kumar2004/")
            )
            startActivity(intent)
        }
        binding.linkedinButtonDvt.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.linkedin.com/in/divit-ajmera")
            )
            startActivity(intent)
        }
        binding.gmailButtonArv.setOnClickListener {
            val clipboard =
                requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Anwesha ID", "arvindri2005@gmail.com")
            clipboard.setPrimaryClip(clip)
            Toast.makeText(requireContext(), "Email copied to clipboard", Toast.LENGTH_SHORT).show()
        }
        binding.gmailButtonPrv.setOnClickListener {
            val clipboard =
                requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Anwesha ID", "praveen1142004@gmail.com")
            clipboard.setPrimaryClip(clip)
            Toast.makeText(requireContext(), "Email copied to clipboard", Toast.LENGTH_SHORT).show()
        }
        binding.gmailButtonDvt.setOnClickListener {
            val clipboard =
                requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Anwesha ID", "abc@gmail.com")
            clipboard.setPrimaryClip(clip)
            Toast.makeText(requireContext(), "Email copied to clipboard", Toast.LENGTH_SHORT).show()
        }


        return view
    }

    override fun onResume() {
        super.onResume()
        val bottomNavigationView =
            activity?.findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigationView?.visibility = View.GONE
    }

    override fun onPause() {
        super.onPause()
        val bottomNavigationView =
            activity?.findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigationView?.visibility = View.VISIBLE
    }
}