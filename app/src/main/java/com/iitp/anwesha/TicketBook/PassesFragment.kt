package com.iitp.anwesha.TicketBook

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import app.rive.runtime.kotlin.RiveAnimationView
import com.iitp.anwesha.R
import com.iitp.anwesha.databinding.FragmentElitePassBinding
import com.iitp.anwesha.databinding.FragmentPassesBinding
import com.iitp.anwesha.databinding.FragmentProPassBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.lang.Math.abs


class PassesFragment : Fragment() {

    private lateinit var binding: FragmentPassesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPassesBinding.inflate(inflater, container, false)
        val view = binding.root

        val viewPager = binding.viewPager
        val adapter = PassesPagerAdapter(this)
        viewPager.adapter = adapter

        viewPager.setPageTransformer(PassesPageTransformer())

        val dotsIndicator = binding.dotsIndicator
        dotsIndicator.setViewPager2(viewPager)

        binding.getBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack(null, 0)
        }

        return view
    }

    // Custom PagerAdapter to return the appropriate fragment for each page
    private inner class PassesPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> ElitePassFragment()
                else -> ProPassFragment()
            }
        }
    }

    private inner class PassesPageTransformer : ViewPager2.PageTransformer {
        override fun transformPage(page: View, position: Float) {
            val absPosition = abs(position)
            val scaleFactor = if (absPosition > 1) 0f else 1 - absPosition
        }
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

class ElitePassFragment : Fragment() {

    private lateinit var binding: FragmentElitePassBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentElitePassBinding.inflate(inflater, container, false)
        val view = binding.root

        val riveAnimationView = view.findViewById<RiveAnimationView>(R.id.animation_view)
        riveAnimationView.play()

        val button = binding.cilcker
        button.setOnClickListener {
            val dialog = Dialog(requireContext())
            dialog.setContentView(R.layout.redirection_layout)
            val imageView = dialog.findViewById<ImageView>(R.id.redirect)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()
            imageView.setOnClickListener {
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(requireActivity().getString(R.string.elite_pass))
                )
                startActivity(intent)
                dialog.dismiss()
            }
        }

        return view
    }
}

class ProPassFragment : Fragment() {
    private lateinit var binding: FragmentProPassBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProPassBinding.inflate(inflater, container, false)
        val view = binding.root

        val riveAnimationView = view.findViewById<RiveAnimationView>(R.id.animation_view)
        riveAnimationView.play()

        val button = binding.cilcker
        button.setOnClickListener {
            val dialog = Dialog(requireContext())
            dialog.setContentView(R.layout.redirection_layout)
            val imageView = dialog.findViewById<ImageView>(R.id.redirect)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()
            imageView.setOnClickListener {
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(requireActivity().getString(R.string.pro_pass))
                )
                startActivity(intent)
                dialog.dismiss()
            }
        }
        return view
    }
}
