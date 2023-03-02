package com.college.anwesha2k23.profile


import com.college.anwesha2k23.BASE_URL
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET



interface ProfileApi {

    @GET("user/editprofile")
    suspend fun getProfile() : Response<ProfileResponse>

    @GET("event/myevents")
    suspend fun getMyEvents() : Response<MyEvents>

}

object UserProfileApi {
    val profileApi: ProfileApi
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
        profileApi = retrofit.create(ProfileApi::class.java)
    }
}