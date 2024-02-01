package com.iitp.anwesha

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.messaging.FirebaseMessaging
import com.iitp.anwesha.databinding.ActivitySplashBinding
import com.iitp.anwesha.profile.UserProfileApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "SplashActivity"

class SplashActivity : AppCompatActivity() {

    private val SPLASH_TIME_OUT = 1000L

    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        actionBar?.hide()

        FirebaseMessaging.getInstance().subscribeToTopic("notification")

        FirebaseAnalytics.getInstance(this);
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true);


        Handler(Looper.getMainLooper()).postDelayed({
            apiCall(view)
        }, SPLASH_TIME_OUT)
    }



    private fun apiCall(view: ConstraintLayout) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = UserProfileApi(this@SplashActivity).profileApi.getProfile()
                runOnUiThread {
                    if (response.isSuccessful) {
                        moveToMainActivity()
                    } else {
                        moveToLoginActivity()
                    }
                }
            } catch (e: Exception) {
                runOnUiThread {
                    val snackBar = Snackbar.make(view, "No Internet Connection", 500000)
                    snackBar.setAction("Retry") {
                        apiCall(view) // Retry network call when "Retry" is clicked
                    }
                    snackBar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
                    snackBar.show()
                }
            }
        }
    }



    private fun moveToLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun moveToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}

