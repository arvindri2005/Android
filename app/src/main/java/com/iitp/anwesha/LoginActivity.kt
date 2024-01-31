package com.iitp.anwesha

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.iitp.anwesha.auth.SignIn
import com.iitp.anwesha.databinding.ActivityLoginBinding

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
        val fragment = supportFragmentManager.beginTransaction()
        fragment.replace(
            R.id.login_container, SignIn()
        )
        fragment.commit()
    }

}