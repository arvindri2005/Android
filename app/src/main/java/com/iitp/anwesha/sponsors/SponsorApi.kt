package com.iitp.anwesha.sponsors

import android.content.Context
import com.iitp.anwesha.AddCookiesInterceptor
import com.iitp.anwesha.BASE_URL
import com.iitp.anwesha.profile.ProfileApi
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

interface SponsorApi {
    @GET("sponsors/allsponsors")
    suspend fun getSponsor() : Response<List<SponsorResponse>>
}

class sponsorapi(val context: Context) {
    val sponsorApi: SponsorApi
    init {
        val client = OkHttpClient.Builder().addInterceptor(AddCookiesInterceptor(context)).build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
        sponsorApi = retrofit.create(SponsorApi::class.java)
    }
}