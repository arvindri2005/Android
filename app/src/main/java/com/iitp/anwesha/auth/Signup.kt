package com.iitp.anwesha.auth

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
import android.widget.Spinner
import android.widget.Toast
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.iitp.anwesha.MyDialog
import com.iitp.anwesha.R
import com.iitp.anwesha.checkValue
import com.iitp.anwesha.databinding.FragmentSignupBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class Signup : Fragment() {
    private lateinit var binding: FragmentSignupBinding
    private lateinit var email: String
    private lateinit var phone: String
    private lateinit var password: String
    private lateinit var cPassword: String
    private lateinit var name: String
    private lateinit var college: String
    private lateinit var userType: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignupBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.signinbutton.setOnClickListener {
            loadFragment(SignIn())
        }

        binding.confirmPassword.setContent {
            ConfirmPasswordInput()
        }
        binding.password.setContent {
            PasswordInput()
        }

        val myDialog = MyDialog(requireContext())

        if(binding.iitStudentBtn.isChecked){
            college = "IIT Patna"
            userType = "iitp_student"
        }

        binding.iitStudentBtn.setOnClickListener {
            if (binding.iitStudentBtn.isChecked){
                binding.collegeName.visibility = View.GONE
                binding.anweshaCollege.visibility = View.GONE
                binding.anweshaUserType.visibility = View.GONE
                binding.userType.visibility = View.GONE

            }
            else{
                binding.collegeName.visibility = View.VISIBLE
                binding.anweshaCollege.visibility = View.VISIBLE
                binding.anweshaUserType.visibility = View.VISIBLE
                binding.userType.visibility = View.VISIBLE
            }
        }

            binding.SignupButton.setOnClickListener {
                if(binding.acceptTermButton.isChecked) {

                    name = checkValue(binding.anweshaFullName)?.trimEnd() ?: return@setOnClickListener
                    email = checkValue(binding.anweshaEmailId)?.trimEnd() ?: return@setOnClickListener

                    if(binding.iitStudentBtn.isChecked){
                        if (!isValidIITPEmail(email)){
                            Snackbar.make(view, "Please enter institute email address", Snackbar.LENGTH_LONG)
                                .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
                                .show()
                            return@setOnClickListener
                        }
                    }

                    else{
                        if(!isValidEmail(email)){
                            Snackbar.make(view, "Please enter valid email address", Snackbar.LENGTH_LONG)
                                .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
                                .show()
                        }
                    }

                    phone = checkValue(binding.anweshaPhoneNum) ?: return@setOnClickListener

                    if (!binding.iitStudentBtn.isChecked){
                        college = checkValue(binding.anweshaCollege)?.trimEnd() ?: return@setOnClickListener
                        userType = checkSpinnerValue(binding.anweshaUserType) ?: return@setOnClickListener
                    }

                    if (password != cPassword) {
                        Snackbar.make(view, "Password doesn't match", Snackbar.LENGTH_LONG)
                            .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
                            .show()
                        return@setOnClickListener
                    }

                    myDialog.showProgressDialog(this@Signup)

                    CoroutineScope(Dispatchers.Main).launch {

                        val registerUser = UserRegisterInfo(email, password, name, phone, college, userType)

                        try {
                            val response =
                                UserAuthApi(requireContext()).userAuthApi.userRegister(registerUser)

                            if (response.isSuccessful) {
                                if(binding.iitStudentBtn.isChecked){
                                    Snackbar.make(
                                        view,
                                        "Please Verify your email through slick",
                                        Snackbar.LENGTH_LONG
                                    )
                                        .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
                                        .show()
                                }
                                else{
                                    Snackbar.make(
                                        view,
                                        "You have successfully Registered!",
                                        Snackbar.LENGTH_LONG
                                    )
                                        .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
                                        .show()
                                }
                                loadFragment(SignIn())
                            } else {
                                Snackbar.make(view, "Could not register!", Snackbar.LENGTH_LONG)
                                    .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
                                    .show()
                            }
                        }
                        catch (e: Exception) {
                            Snackbar.make(view, "No Internet Connection", Snackbar.LENGTH_LONG)
                                .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE).show()
                            myDialog.dismissProgressDialog()
                        }
                    }
                }
                else{
                    Snackbar.make(view, "Please select Terms and Conditions", Snackbar.LENGTH_LONG)
                        .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE).show()
                }
            }

            val text = "I accept the Terms and conditions."
            val spannableString = SpannableString(text)

            spannableString.setSpan(UnderlineSpan(), 13, text.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

            val clickableSpan = object : ClickableSpan() {
                override fun onClick(view: View) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://anwesha.iitp.ac.in/terms"))
                    view.context.startActivity(intent)
                }
                override fun updateDrawState(textPaint: TextPaint) {
                    textPaint.color = requireContext().resources.getColor(R.color.white)
                    textPaint.isUnderlineText = true
                }
            }

        spannableString.setSpan(clickableSpan, 13, text.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        binding.tvAcceptTerms.text = spannableString
        binding.tvAcceptTerms.movementMethod = LinkMovementMethod.getInstance()

        return view
    }

    private fun loadFragment(fragment: Fragment){
        val fragmentTransaction = activity?.supportFragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.login_container, fragment)
        fragmentTransaction.commit()
    }

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
        return emailRegex.matches(email)
    }

    private fun isValidIITPEmail(email: String): Boolean {
        val emailRegex = Regex("^\\w+@iitp\\.ac\\.in\$")
        return emailRegex.matches(email)
    }



    private fun checkSpinnerValue(spinner: Spinner) : String? {
        var value = spinner.selectedItem.toString()
        if(value.isBlank()) {
            Toast.makeText(context, "Please Enter the user type", Toast.LENGTH_SHORT).show()
            return null
        }
        value = when(value) {
            "Student" -> "student"
            "Not a Student" -> "non-student"
            "Alumni" -> "alumni"
            "Faculty" -> "faculty"
            else -> "non-student"
        }
        return value

    }

    @Preview
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ConfirmPasswordInput() {
        val password = remember { mutableStateOf("") }
        val passwordVisibility = remember{ mutableStateOf(false) }
        OutlinedTextField(
            value =password.value ,
            onValueChange ={input->
                password.value = input
                cPassword = input
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
            placeholder = {
                Text(text = "Confirm Password",
                    style = TextStyle(
                        color = Color.Black,
                    )
                )
            },
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
            maxLines = 1,
            singleLine = true,
        )
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
            placeholder = {
                Text(text = "Password")
            },
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
            maxLines = 1,
            singleLine = true,
        )
    }
}


