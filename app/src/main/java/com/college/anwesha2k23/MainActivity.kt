package com.college.anwesha2k23

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.college.anwesha2k23.calendar.CalendarFragment
import com.college.anwesha2k23.databinding.ActivityMainBinding
import com.college.anwesha2k23.events.EventsFragment
import com.college.anwesha2k23.home.HomeFragment
import com.college.anwesha2k23.notification.NotificationFragment
import com.college.anwesha2k23.profile.ProfileFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Hide the time and battery percent from the status bar
        // Set the status bar to be transparent
//        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//        window.statusBarColor = Color.TRANSPARENT



        loadFragment(HomeFragment())
        binding.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home->{
                    loadFragment(HomeFragment())
                    true
                }
                R.id.event->{
                    loadFragment(EventsFragment())
                    true
                }
                R.id.profile->{
                loadFragment(ProfileFragment())
                true
                }
                R.id.calendar->{
                    loadFragment(CalendarFragment())
                    true
                }

                else->{
                    loadFragment(HomeFragment())
                    true
                }
            }
        }

        binding.bottomNavigation.setOnItemReselectedListener {
            when(it.itemId){
                R.id.home->{
                    //Do Nothing because home fragment is added in back stack
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
//        fragmentManager.addToBackStack(null)
        fragmentManager.commit()
    }

    // override the onBackPressed() function to return back to home
    override fun onBackPressed() {
        if (binding.bottomNavigation.selectedItemId != R.id.home) {
            binding.bottomNavigation.selectedItemId = R.id.home
        }
        else {
            super.onBackPressed()
        }
    }
}