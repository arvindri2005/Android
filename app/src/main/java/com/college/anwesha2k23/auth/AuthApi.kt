package com.college.anwesha2k23.auth

import android.content.Context
import com.college.anwesha2k23.BASE_URL
import com.college.anwesha2k23.ReceivedCookiesInterceptor
import okhttp3.OkHttpClient
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

class UserAuthApi(val context: Context) {
    val userAuthApi: AuthApi


    init {

        val client = OkHttpClient.Builder().addInterceptor(ReceivedCookiesInterceptor(context)).build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
        userAuthApi = retrofit.create(AuthApi::class.java)
    }
}