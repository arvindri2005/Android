package com.college.anwesha2k23.home

import com.college.anwesha2k23.auth.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface EventsApi {
    @GET("event/allevents")
    fun getEvents(): Call<MutableList<EventList>>
}

object EventsApiService {

    private val client = OkHttpClient.Builder().build()
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    fun <T> buildService(service: Class<T>):T {
        return retrofit.create(service)
    }
}