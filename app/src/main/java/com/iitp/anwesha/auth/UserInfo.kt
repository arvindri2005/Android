package com.iitp.anwesha.auth

data class UserLoginInfo(
    val username: String,
    val password: String
)

data class UserRegisterInfo(
    val email_id: String,
    val password: String,
    val full_name: String,
    val phone_number: String,
    val college_name: String,
    val user_type: String
)

data class UserForget(
    val email: String
)

data class LoginResponse(
    val success: Boolean,
    val name: String
)

data class LogoutResponse(
    val message: String,
    val status: String
)

data class RegisterResponse(
    val message: String
)