package com.college.anwesha2k23

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.college.anwesha2k23.databinding.ActivityMainBinding

import com.google.android.material.bottomnavigation.BottomNavigationView
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

        drawerLayout = binding.drawerLayout
        actionBarToggle = ActionBarDrawerToggle(this, drawerLayout, 0, 0)
        drawerLayout.addDrawerListener(actionBarToggle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        actionBarToggle.syncState()

        binding.navBar.setOnClickListener {
            drawerLayout.openDrawer(Gravity.LEFT)
        }

        val navView: BottomNavigationView = binding.bottomNavigation
        val navController = findNavController(R.id.fragmentContainer)
        navView.setupWithNavController(navController)

        selectingItems()

    }

    override fun onSupportNavigateUp(): Boolean {
        drawerLayout.openDrawer(navView)
        return true
    }

    // override the onBackPressed() function to close the Drawer when the back button is clicked
    override fun onBackPressed() {
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
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
                else -> {
                    false
                }
            }
        }
    }

    private fun signOut() {
        Toast.makeText(this, "Logout clicked", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

}


