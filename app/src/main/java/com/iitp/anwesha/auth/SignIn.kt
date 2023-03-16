package com.iitp.anwesha.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.iitp.anwesha.MainActivity
import com.iitp.anwesha.MyDialog
import com.iitp.anwesha.R
import com.iitp.anwesha.checkValue
import com.iitp.anwesha.databinding.FragmentSigninBinding
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignIn : Fragment() {
    private lateinit var binding: FragmentSigninBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSigninBinding.inflate(inflater, container, false)
        val view = binding.root

        val sharedPref = requireActivity().getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)

        val intent = Intent(activity, MainActivity::class.java)

        val myDialog = MyDialog(requireContext())

        binding.Forgotpassword.setOnClickListener {
            val email = checkValue(binding.AnweshaId) ?: return@setOnClickListener

            myDialog.showProgressDialog(this@SignIn)
            CoroutineScope(Dispatchers.IO).launch {
                val userforget = UserForget(email)

                val response = UserAuthApi(requireContext()).userAuthApi.userForget(userforget)

                Snackbar.make(view, "Forget password email sent to $email", Snackbar.LENGTH_LONG)
                    .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE).show()

                myDialog.dismissProgressDialog()
            }
        }

        // user login using API
        binding.LoginButton.setOnClickListener{

            val email = checkValue(binding.AnweshaId) ?: return@setOnClickListener
            val password = checkValue(binding.AnweshaPassword) ?: return@setOnClickListener

            myDialog.showProgressDialog(this@SignIn)
            CoroutineScope(Dispatchers.IO).launch {
                val userLogin = UserLoginInfo(email, password)

//                try {


                    val response = UserAuthApi(requireContext()).userAuthApi.userLogin(userLogin)

                    if(response.body()?.success == true) {
                        with(sharedPref.edit()) {
                            putString("anwesha_id", response.body()?.anwesha_id)
                            putString("user_type", response.body()?.user_type)
                            putString(getString(R.string.user_name), response.body()?.name)
                            putString(getString(R.string.anwesha_id), response.body()?.anwesha_id)
                            putString(getString(R.string.user_type), response.body()?.user_type)
                            putBoolean(getString(R.string.user_login_authentication), true)
                            putString(getString(R.string.qr_code), response.body()?.qr_code)
                            apply()
                        }
                        startActivity(intent)
                        requireActivity().finish()
                    }

                    else {
                        Snackbar.make(view, "Could not verify the user!", Snackbar.LENGTH_LONG)
                            .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE).show()
                    }
                myDialog.dismissProgressDialog()
            }



        }

        binding.signupbutton.setOnClickListener {
            loadFragment(Signup())
        }

        return view
    }

    private fun loadFragment(fragment: Fragment){
        val fragmentTransaction = activity?.supportFragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.login_container, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}