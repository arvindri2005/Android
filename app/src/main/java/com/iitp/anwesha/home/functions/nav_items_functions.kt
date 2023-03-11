package com.iitp.anwesha.home.functions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.core.view.GravityCompat
import com.iitp.anwesha.LoginActivity
import com.iitp.anwesha.R
import com.iitp.anwesha.databinding.FragmentHomeBinding
import com.iitp.anwesha.team.TeamPage
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
                    binding.frameLayout.closeDrawer(GravityCompat.START)
                    context.startActivity(Intent(context, TeamPage::class.java))
                    true
                }
                R.id.Feedback -> {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(context.getString(R.string.feedback)))
                    context.startActivity(intent)
                    true
                }
                R.id.Abouts -> {
                    Toast.makeText(context, "Abouts clicked", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.logout -> {
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
                                context.finish()
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