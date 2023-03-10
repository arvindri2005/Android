package com.college.anwesha2k23

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.college.anwesha2k23.databinding.ActivitySplashBinding
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.messaging.FirebaseMessaging

class SplashActivity : AppCompatActivity() {

    private val SPLASH_TIME_OUT = 2000L

    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        FirebaseMessaging.getInstance().subscribeToTopic("notification")

        FirebaseAnalytics.getInstance(this);
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true);

        val sharedPref = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)

        Handler(Looper.getMainLooper()).postDelayed({
            if(sharedPref.getBoolean(getString(R.string.user_login_authentication), false)) {
                moveToMainActivity()
            }else{
                moveToLoginActivity()
//                moveToMainActivity()
            }
        }, SPLASH_TIME_OUT)
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

