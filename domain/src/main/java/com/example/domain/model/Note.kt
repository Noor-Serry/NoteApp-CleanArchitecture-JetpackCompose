package com.example.domain.model


import java.text.SimpleDateFormat
import java.util.Date


data class Note(val id: Int, val title: String, val timeMillis: Long, val body: String){

    fun convertTimeMillisToDate(): String =
        SimpleDateFormat("dd/M/yyyy hh:mm:ss").format(Date(timeMillis))


}


sealed class SaveNoteState(){
    object Success : SaveNoteState()
    class Failure(val message :String) : SaveNoteState()
}