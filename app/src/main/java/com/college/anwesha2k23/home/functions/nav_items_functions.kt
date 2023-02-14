package com.college.anwesha2k23.home.functions

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.college.anwesha2k23.LoginActivity
import com.college.anwesha2k23.R
import com.college.anwesha2k23.campusAmbassador.CaActivity
import com.college.anwesha2k23.databinding.FragmentHomeBinding

class nav_items_functions(val binding: FragmentHomeBinding, val context: Context) {

    fun selectingItems() {
        binding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.sponsor -> {
                    Toast.makeText(context, "sponsor clicked", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.Team -> {
                    Toast.makeText(context, "Team clicked", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.Feedback -> {
                    Toast.makeText(context, "Feedback clicked", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.FAQ -> {
                    Toast.makeText(context, "FAQ clicked", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.Abouts -> {
                    Toast.makeText(context, "Abouts clicked", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.logout -> {
                    signOut()
                    true
                }
                R.id.ca -> {
                    val intent = Intent(context, CaActivity::class.java)
                    context.startActivity(intent)
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    fun signOut() {
        Toast.makeText(context, "Logout clicked", Toast.LENGTH_SHORT).show()
        val intent = Intent(context, LoginActivity::class.java)
        context.startActivity(intent)
    }
}