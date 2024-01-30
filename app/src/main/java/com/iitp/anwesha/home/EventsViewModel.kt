package com.iitp.anwesha.home

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventsViewModel: ViewModel() {
    private var eventList: MutableLiveData<ArrayList<EventList>> = MutableLiveData()

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
                filterEvents(response.body() as ArrayList)
            }

            override fun onFailure(call: Call<MutableList<EventList>>, t: Throwable) {

            }
        })
    }

    fun filterEvents(eventList1: ArrayList<EventList>){
        val filteredList = ArrayList<EventList>()
        for (event in eventList1){
            if (!(event.id == "EVTe96c6"
                || event.id == "EVT8e600"
                || event.id == "EVT7a8a7"
                || event.id == "EVT691bc"
                || event.id == "EVTcac95"
                || event.id == "EVTcf525"
                || event.id == "EVT49870"
                || event.id == "EVT66e40"
                || event.id == "EVT68cb3")){
                filteredList.add(event)
            }
        }
        eventList.postValue(filteredList)
    }

}