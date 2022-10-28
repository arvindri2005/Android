package com.college.anwesha2k23

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.college.anwesha2k23.Fragments.bottom_nav.Gallery
import com.college.anwesha2k23.R.*
import com.college.anwesha2k23.databinding.ActivityMainBinding
import com.college.anwesha2k23.Fragments.bottom_nav.Events
import com.college.anwesha2k23.Fragments.bottom_nav.Home
import com.college.anwesha2k23.Fragments.bottom_nav.Profile

import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private  lateinit var bottomNav: BottomNavigationView
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide() //Hide App title

        loadFragment(Home())

        bottomNav = binding.bottomNavigation
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
                id.event -> {
                    loadFragment(Events())
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