package com.college.anwesha2k23.home.functions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.college.anwesha2k23.LoginActivity
import com.college.anwesha2k23.R
import com.college.anwesha2k23.databinding.FragmentHomeBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
                    Toast.makeText(context, "Logout clicked", Toast.LENGTH_SHORT).show()
                    CoroutineScope(Dispatchers.IO).launch {
                        if (signOut()) {
                            (context as Activity).runOnUiThread(Runnable {
                                Toast.makeText(context, "Could Not logout", Toast.LENGTH_SHORT).show()
                            })
                        }
                        else {
                            (context as Activity).runOnUiThread(Runnable {
                                val intent = Intent(context, LoginActivity::class.java)
                                context.startActivity(intent)
                            })
                        }
                    }

                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    suspend fun signOut() : Boolean {
        val sharedPref = context.getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)
        val response = UserLogout(context).logoutApi.logout()

        if(!response.isSuccessful) {
            return true
        }
        sharedPref.edit().putBoolean(context.getString(R.string.user_login_authentication), false).apply()
        sharedPref.edit().remove(context.getString(R.string.cookies)).apply()
        return false
    }
}