package com.college.anwesha2k23.home

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventsViewModel: ViewModel() {
    var eventList: MutableLiveData<ArrayList<EventList>> = MutableLiveData()

    fun getEventListObserver(): MutableLiveData<ArrayList<EventList>>{
        return eventList
    }

    fun makeApiCall(context: Context){
        val serviceGenerator = EventsApiService(context).buildService(EventsApi::class.java)
        val call = serviceGenerator.getEvents()
        call.enqueue(object : Callback<MutableList<EventList>>{
            override fun onResponse(
                call: Call<MutableList<EventList>>,
                response: Response<MutableList<EventList>>
            ){
                eventList.postValue(response.body() as ArrayList)

            }

            override fun onFailure(call: Call<MutableList<EventList>>, t: Throwable) {

            }
        })
    }

}