package com.college.anwesha2k23.events

import retrofit2.Response
import retrofit2.http.GET

const val BASE_URL = "https://backend.anwesha.live/"

interface EventsApi {

    @GET("event/allevents")
    suspend fun getEvents() : Response<EventDetails>
}