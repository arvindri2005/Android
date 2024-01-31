package com.iitp.anwesha

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.iitp.anwesha.calendar.CalendarFragment
import com.iitp.anwesha.databinding.ActivityMainBinding
import com.iitp.anwesha.home.HomeFragment
import com.iitp.anwesha.profile.ProfileFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        loadFragment(HomeFragment())
        binding.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home->{
                    loadFragment(HomeFragment())
                    true
                }
                R.id.calendar->{
                    loadFragment(CalendarFragment())
                    true
                }
                R.id.profile->{
                loadFragment(ProfileFragment())
                true
            }
                else->{
                    loadFragment(HomeFragment())
                    true
                }
            }
        }

    }


    private fun loadFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager.beginTransaction()
        fragmentManager.replace(R.id.fragmentContainer, fragment)
        fragmentManager.commit()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (binding.bottomNavigation.selectedItemId != R.id.home) {
            binding.bottomNavigation.selectedItemId = R.id.home
        }
        else {
            super.onBackPressed()
        }
    }
}