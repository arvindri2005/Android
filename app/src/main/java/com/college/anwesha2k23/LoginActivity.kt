package com.college.anwesha2k23

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.college.anwesha2k23.auth.SignIn
import com.college.anwesha2k23.R
import com.college.anwesha2k23.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        showFragment()


    }

    private fun showFragment() {
        val fram = supportFragmentManager.beginTransaction()
        fram.replace(
            R.id.login_container, SignIn()
        )
        fram.commit()
    }

}