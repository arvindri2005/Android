package com.iitp.anwesha.profile

import android.content.Context
import com.iitp.anwesha.AddCookiesInterceptor
import com.iitp.anwesha.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

interface QrApi {
    @GET("user/regenerateqr/")
    suspend fun getQr() : Response<QrResponse>
}

class QrGetApi(val context: Context) {
    val qrApi: QrApi
    init {
        val client = OkHttpClient.Builder().addInterceptor(AddCookiesInterceptor(context)).build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
        qrApi = retrofit.create(QrApi::class.java)
    }
}