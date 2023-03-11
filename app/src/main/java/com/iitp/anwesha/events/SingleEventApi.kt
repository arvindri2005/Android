package com.iitp.anwesha.events

import android.content.Context
import com.iitp.anwesha.AddCookiesInterceptor
import com.iitp.anwesha.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface EventApi{
    @GET("/event/id/{eventId}")
    suspend fun getEvent(@Path("eventId") eventId: String): Response<Event>

}

class SingleEventApi(val context: Context) {
    val singleEventApi: EventApi
    init {

        val client = OkHttpClient.Builder().addInterceptor(AddCookiesInterceptor(context)).build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
        singleEventApi = retrofit.create(EventApi::class.java)
    }
}