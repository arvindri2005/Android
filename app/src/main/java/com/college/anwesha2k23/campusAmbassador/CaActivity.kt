package com.college.anwesha2k23.campusAmbassador

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.college.anwesha2k23.R
import com.college.anwesha2k23.databinding.ActivityCampusambassadorBinding

class CaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCampusambassadorBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCampusambassadorBinding.inflate(layoutInflater)
        val view = binding.root
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        setFragment(CaOpenFragment())

        setContentView(view)
    }
    private fun setFragment(fragment: Fragment) {
        val frame =  supportFragmentManager.beginTransaction()
        frame.replace(R.id.CaContainer, fragment)
        frame.commit()
    }
}