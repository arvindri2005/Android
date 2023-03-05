package com.college.anwesha2k23.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.college.anwesha2k23.MainActivity
import com.college.anwesha2k23.MyDialog
import com.college.anwesha2k23.R
import com.college.anwesha2k23.checkValue
import com.college.anwesha2k23.databinding.FragmentSigninBinding
import com.college.anwesha2k23.home.HomeFragment
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

        if(sharedPref.getBoolean(getString(R.string.user_login_authentication), false)) {
            startActivity(intent)
        }

        val myDialog = MyDialog(requireContext())

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
                            putString(getString(R.string.user_name), response.body()?.name)
                            putBoolean(getString(R.string.user_login_authentication), true)
                            apply()
                        }
                        startActivity(intent)
                    }

                    else {
                        Snackbar.make(view, "Could not verify the user!", Snackbar.LENGTH_LONG)
                            .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE).show()
                    }
//                }
//                catch(e: Exception) {
//                    myDialog.showErrorAlertDialog("Oops! It seems like an error... ${e.message}")
//                    Log.e("inside signin: ", "${e.stackTrace}")
//                }
                myDialog.dismissProgressDialog()
            }

//            startActivity(intent)


        }

        binding.signupbutton.setOnClickListener {
            loadFragment(Signup())
        }

        return view
    }

    private fun loadFragment(fragment: Fragment){
        val fragmentTransaction = activity?.supportFragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.login_container, fragment)
        fragmentTransaction.commit()
    }
}