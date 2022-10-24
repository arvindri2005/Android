package com.college.anwesha2k23

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.college.anwesha2k23.Gallery
import com.college.anwesha2k23.Home
import com.college.anwesha2k23.Profile
import com.college.anwesha2k23.R.*
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private  lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)
        supportActionBar?.hide() //Hide App title
        loadFragment(Home())
        bottomNav = findViewById(id.bottomNavigation)
        bottomNav.setOnItemSelectedListener {
            when(it.itemId){
                id.home -> {
                    loadFragment(Home())
                    true
                }
                id.gallery -> {
                    loadFragment(Gallery())
                    true
                }
                id.profile -> {
                    loadFragment(Profile())
                    true
                }
                else->{
                    loadFragment(Home())
                    true
                }
            }
        }
    }
    private fun loadFragment(fragment: Fragment){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(id.fragmentContainer, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}