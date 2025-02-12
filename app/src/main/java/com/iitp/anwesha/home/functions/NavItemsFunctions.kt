package com.iitp.anwesha.home.functions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.iitp.anwesha.AboutUsFragment
import com.iitp.anwesha.LoginActivity
import com.iitp.anwesha.R
import com.iitp.anwesha.TeamFragment
import com.iitp.anwesha.databinding.FragmentHomeBinding
import com.iitp.anwesha.sponsors.SponsorsFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NavItemsFunctions(
    val binding: FragmentHomeBinding,
    val context: Context,
    val activity: FragmentActivity
) {

    fun selectingItems() {
        binding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.sponsor -> {
                    loadFragment(SponsorsFragment())
                    true
                }
                R.id.Team -> {
                    loadFragment(TeamFragment())
                    true
                }
                R.id.Feedback -> {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(context.getString(R.string.feedback)))
                    context.startActivity(intent)
                    true
                }
                R.id.Abouts -> {
                    loadFragment(AboutUsFragment())
                    true
                }
                R.id.logout -> {
                    CoroutineScope(Dispatchers.IO).launch {
                        if (signOut()) {
                            (context as Activity).runOnUiThread {
                                Toast.makeText(context, "Could Not logout", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                        else {
                            (context as Activity).runOnUiThread {
                                val intent = Intent(context, LoginActivity::class.java)
                                context.startActivity(intent)
                                context.finish()
                            }
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

    private fun loadFragment(fragment: Fragment) {
        val fragmentManager = activity.supportFragmentManager.beginTransaction()
        fragmentManager.replace(R.id.fragmentContainer, fragment)
        fragmentManager.addToBackStack(null)
        fragmentManager.commit()
    }


    private suspend fun signOut() : Boolean {
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