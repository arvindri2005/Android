package com.iitp.anwesha


import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

//
//    @Test
//    fun postApi() {
//        val client = OkHttpClient()
//        val mediaType = "application/json".toMediaTypeOrNull()
//        val data = "{\n  \"password\" : \"Yourpassword\",\n  \"phone_number\":  \"123456789\" ,\n  \"email_id\"  : \"divitajmera15@gmail.com\",\n  \"full_name\" : \"Your full_name\" ,\n  \"college_name\" : \" Your college_name\" ,\n  \"refferal_code\" : \"181fvdf\"}"
//        val body = data.toRequestBody(mediaType)
//        val request = Request.Builder()
//            .url("https://backend.anwesha.live/campasambassador/register")
//            .post(body)
//            .addHeader("Content-Type", "application/json")
//            .build()
//
//
//        val response = client.newCall(request).execute()
//        println(response.networkResponse.toString())
//        assert(response.isSuccessful)
//    }
}