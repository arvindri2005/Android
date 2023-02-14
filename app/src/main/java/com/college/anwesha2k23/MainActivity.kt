package com.college.anwesha2k23

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.core.view.get
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.college.anwesha2k23.auth.SignIn
import com.college.anwesha2k23.campusAmbassador.CaActivity
import com.college.anwesha2k23.databinding.ActivityMainBinding
import com.college.anwesha2k23.events.EventsFragment
import com.college.anwesha2k23.home.HomeFragment
import com.college.anwesha2k23.notification.NotificationFragment
import com.college.anwesha2k23.profile.ProfileFragment

import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.navigation.NavigationView

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
                R.id.event->{
                    loadFragment(EventsFragment())
                    true
                }R.id.profile->{
                loadFragment(ProfileFragment())
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
        binding.notificationBtn.setOnClickListener {
            loadNotification()
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
        if (nav.selectedItemId != R.id.home) {
            nav.selectedItemId = R.id.home
        }
        else {
            super.onBackPressed()
        }
    }
}