package com.iitp.anwesha.events

import android.content.Context
import com.iitp.anwesha.AddCookiesInterceptor
import com.iitp.anwesha.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST


interface AllEventsApi {

    @POST("event/registration/solo")
    suspend fun soloEventRegistration(@Body soloRegistration: SoloRegistration) : Response<SoloRegistrationResponse>

    @POST("event/registration/team")
    suspend fun teamEventRegistration(@Body teamRegistration: TeamRegistration) : Response<TeamRegistrationResponse>

//    @POST("event/registration/verification")
//    suspend fun registrationVerification(@Body registrationVerification: RegistrationVerification) : Response<RegistrationVerificationResponse>
}

class EventsRegistrationApi(val context: Context) {
    val allEventsApi: AllEventsApi

    init {
        val client = OkHttpClient.Builder().addInterceptor(AddCookiesInterceptor(context)).build()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        allEventsApi = retrofit.create(AllEventsApi::class.java)
    }
}