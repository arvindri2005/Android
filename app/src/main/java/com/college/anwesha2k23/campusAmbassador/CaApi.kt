package com.college.anwesha2k23.campusAmbassador

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

const val BASE_URL = "https://backend.anwesha.live/"

interface CaApi {

    @POST("campasambassador/register")
    suspend fun registerCA( @Body caBody: CaInfo) : Response<CaInfo>
}

object CampusApi {
    val campusApi: CaApi
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
        campusApi = retrofit.create(CaApi::class.java)
    }
}