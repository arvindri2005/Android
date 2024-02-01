package com.iitp.anwesha.auth

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import com.iitp.anwesha.MainActivity
import com.iitp.anwesha.MyDialog
import com.iitp.anwesha.R
import com.iitp.anwesha.databinding.FragmentSigninBinding
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.iitp.anwesha.checkValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignIn : Fragment() {
    private lateinit var binding: FragmentSigninBinding
    private lateinit var password: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSigninBinding.inflate(inflater, container, false)
        val view = binding.root

        val sharedPref = requireActivity().getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)

        val intent = Intent(activity, MainActivity::class.java)

        val myDialog = MyDialog(requireContext())

        binding.password.setContent {
            PasswordInput()
        }

        binding.Forgotpassword.setOnClickListener {
            val email = checkValue(binding.AnweshaId) ?: return@setOnClickListener

            myDialog.showProgressDialog(this@SignIn)

            CoroutineScope(Dispatchers.IO).launch {

                try {

                    UserAuthApi(requireContext()).userAuthApi.userForget(UserForget(email))
                    Snackbar.make(view, "Forget password email sent", Snackbar.LENGTH_LONG)
                        .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE).show()
                    myDialog.dismissProgressDialog()

                }
                catch (e: Exception) {

                    Snackbar.make(view, "No Internet Connection", Snackbar.LENGTH_LONG)
                        .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE).show()
                    myDialog.dismissProgressDialog()

                }
            }
        }

        // user login using API
        binding.LoginButton.setOnClickListener{

            val email = checkValue(binding.AnweshaId) ?: return@setOnClickListener

            myDialog.showProgressDialog(this@SignIn)
            CoroutineScope(Dispatchers.IO).launch {
                val userLogin = UserLoginInfo(email, password)

                try {

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
                catch (e: Exception) {

                    Snackbar.make(view, "No Internet Connection", Snackbar.LENGTH_LONG)
                        .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE).show()
                    myDialog.dismissProgressDialog()

                }
            }



        }

        binding.signupbutton.setOnClickListener {
            loadFragment(Signup())
        }

        val text2 = "Having trouble login? Report here"
        val spannableString2= SpannableString(text2)

        spannableString2.setSpan(UnderlineSpan(), 22, text2.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        val clickableSpan2 = object : ClickableSpan() {
            override fun onClick(view: View) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(requireContext().getString(R.string.feedback)))
                requireContext().startActivity(intent)
            }
            override fun updateDrawState(textPaint: TextPaint) {
                textPaint.color = requireContext().resources.getColor(R.color.white)
                textPaint.isUnderlineText = true
            }
        }

        spannableString2.setSpan(clickableSpan2, 22, text2.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        binding.problem.text = spannableString2
        binding.problem.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(requireContext().getString(R.string.feedback)))
            requireContext().startActivity(intent)
        }

        return view
    }

    private fun loadFragment(fragment: Fragment){
        val fragmentTransaction = activity?.supportFragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.login_container, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun PasswordInput() {
        val password1 = remember { mutableStateOf("") }
        val passwordVisibility = remember{ mutableStateOf(false) }
        OutlinedTextField(
            value =password1.value ,
            onValueChange ={input->
                password1.value = input
                password = input
            },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(5.dp))
                .background(Color.White),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                containerColor = Color.Transparent,
                textColor = Color.Black
            ),
            shape = RoundedCornerShape(10.dp),
            trailingIcon = {
                IconButton(onClick = {
                    passwordVisibility.value = !passwordVisibility.value
                }) {
                    Icon(painter = painterResource(
                        id = if (!passwordVisibility.value) {
                            R.drawable.icon_hide
                        } else {
                            R.drawable.icon_hide
                        }
                    ),
                        contentDescription = null )
                }
            },
            visualTransformation = if(passwordVisibility.value){
                VisualTransformation.None
            }else{
                PasswordVisualTransformation()
            },
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = 14.sp
            ),
            maxLines = 1,
            singleLine = true,
        )
    }
}