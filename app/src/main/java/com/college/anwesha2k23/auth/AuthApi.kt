package com.college.anwesha2k23.auth

import com.college.anwesha2k23.BASE_URL
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST




interface AuthApi {

    @POST("user/login")
    suspend fun userLogin(@Body userLoginInfo: UserLoginInfo) : Response<LoginResponse>

    @POST("user/register")
    suspend fun userRegister(@Body userRegisterInfo: UserRegisterInfo) : Response<RegisterResponse>
}

object UserAuthApi {
    val userAuthApi: AuthApi
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
        userAuthApi = retrofit.create(AuthApi::class.java)
    }
}