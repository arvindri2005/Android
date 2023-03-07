package com.college.anwesha2k23.home.functions

import android.content.Context
import com.college.anwesha2k23.AddCookiesInterceptor
import com.college.anwesha2k23.BASE_URL
import com.college.anwesha2k23.auth.LogoutResponse

import okhttp3.OkHttpClient
import retrofit2.Response


import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

import retrofit2.http.POST

interface LogoutApi {

    @POST("user/logout")
    suspend fun logout() : Response<LogoutResponse>
}

class UserLogout(val context: Context) {
    val logoutApi: LogoutApi

    init {
        val client = OkHttpClient.Builder().addInterceptor(AddCookiesInterceptor(context)).build()

        val retrofit = Retrofit.Builder()
                                .baseUrl(BASE_URL)
                                .client(client)
                                .addConverterFactory(MoshiConverterFactory.create())
                                .build()

        logoutApi = retrofit.create(LogoutApi::class.java)
    }
}