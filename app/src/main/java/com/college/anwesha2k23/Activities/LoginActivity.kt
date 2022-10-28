package com.college.anwesha2k23.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.college.anwesha2k23.Fragments.signin.SigninFragment
import com.college.anwesha2k23.R
import com.college.anwesha2k23.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        executefun()
    }

    private fun executefun(){
        showFragment()
    }

    private fun showFragment() {
        val fram = supportFragmentManager.beginTransaction()
        fram.replace(
            R.id.fragment_container, SigninFragment()
        )
        fram.commit()
    }

}