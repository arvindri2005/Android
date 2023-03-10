package com.college.anwesha2k23

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.college.anwesha2k23.calendar.CalendarFragment
import com.college.anwesha2k23.databinding.ActivityMainBinding
import com.college.anwesha2k23.home.HomeFragment
import com.college.anwesha2k23.notification.NotificationFragment
import com.college.anwesha2k23.profile.ProfileFragment

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
                loadFragment(ProfileFragment(this@MainActivity))
                true
            }
                else->{
                    loadFragment(HomeFragment())
                    true
                }
            }
        }

    }



    private fun loadNotification() {
        val fragmentManager = supportFragmentManager.beginTransaction()
        fragmentManager.replace(R.id.fragmentContainer, NotificationFragment())
        fragmentManager.addToBackStack(null)
        fragmentManager.commit()
    }

    private fun loadFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager.beginTransaction()
        fragmentManager.replace(R.id.fragmentContainer, fragment)
        fragmentManager.commit()
    }

    override fun onBackPressed() {
        if (binding.bottomNavigation.selectedItemId != R.id.home) {
            binding.bottomNavigation.selectedItemId = R.id.home
        }
        else {
            super.onBackPressed()
        }
    }
}