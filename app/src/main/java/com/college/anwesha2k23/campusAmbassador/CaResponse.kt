package com.college.anwesha2k23.campusAmbassador

data class CaResponse(var msg : String){

    fun setMessage(message: String){
        msg = message
    }

    fun getMessage(): String{
        return msg
    }
}

