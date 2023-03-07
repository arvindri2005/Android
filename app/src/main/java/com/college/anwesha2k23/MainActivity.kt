package com.college.anwesha2k23

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.college.anwesha2k23.calendar.CalendarFragment
import com.college.anwesha2k23.databinding.ActivityMainBinding
import com.college.anwesha2k23.home.HomeFragment
import com.college.anwesha2k23.notification.NotificationFragment
import com.college.anwesha2k23.profile.ProfileFragment
import nl.joery.animatedbottombar.AnimatedBottomBar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        loadFragment(HomeFragment())
        binding.bottomNavigation.setOnTabSelectListener(object : AnimatedBottomBar.OnTabSelectListener{
            override fun onTabSelected(
                lastIndex: Int,
                lastTab: AnimatedBottomBar.Tab?,
                newIndex: Int,
                newTab: AnimatedBottomBar.Tab
            ) {
                when(newIndex){
                    0-> loadFragment(HomeFragment())
                    1-> loadFragment(CalendarFragment())
                    2-> loadFragment(ProfileFragment())
                }
            }

        })

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
        if (binding.bottomNavigation.selectedIndex != 0) {
            loadFragment(HomeFragment())
            binding.bottomNavigation.selectTabAt(0,true)
        }
        else {
            super.onBackPressed()
        }
    }
}