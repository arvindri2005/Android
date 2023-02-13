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
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarToggle: ActionBarDrawerToggle
    private lateinit var navView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        loadFragment(HomeFragment())
        drawerLayout = binding.drawerLayout
        actionBarToggle = ActionBarDrawerToggle(this, drawerLayout, 0, 0)
        drawerLayout.addDrawerListener(actionBarToggle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        actionBarToggle.syncState()
        binding.navBar.setOnClickListener {
            drawerLayout.openDrawer(Gravity.LEFT)
            findViewById<TextView>(R.id.nameText2).text = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)
                .getString(getString(R.string.user_name), "User")
        }






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
        selectingItems()

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

    override fun onSupportNavigateUp(): Boolean {
        drawerLayout.openDrawer(navView)
        return true
    }

    // override the onBackPressed() function to close the Drawer when the back button is clicked
    override fun onBackPressed() {
        val nav = binding.bottomNavigation
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START)
        }
        else if (nav.selectedItemId != R.id.home) {
            nav.selectedItemId = R.id.home
        }
        else {
            super.onBackPressed()
        }
    }


    private fun selectingItems() {
        binding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.sponsor -> {
                    Toast.makeText(this, "sponsor clicked", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.Team -> {
                    Toast.makeText(this, "Team clicked", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.Feedback -> {
                    Toast.makeText(this, "Feedback clicked", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.FAQ -> {
                    Toast.makeText(this, "FAQ clicked", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.Abouts -> {
                    Toast.makeText(this, "Abouts clicked", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.logout -> {
                    signOut()
                    true
                }
                R.id.ca -> {
                    val intent = Intent(this, CaActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    private fun signOut() {
        Toast.makeText(this, "Logout clicked", Toast.LENGTH_SHORT).show()
        val sharedPref = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putBoolean(getString(R.string.user_login_authentication), false)
            apply()
        }
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

}


