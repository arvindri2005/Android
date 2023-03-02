package com.college.anwesha2k23.events

import retrofit2.Response
import retrofit2.http.GET



interface EventsApi {

    @GET("event/allevents")
    suspend fun getEvents() : Response<EventDetails>
}