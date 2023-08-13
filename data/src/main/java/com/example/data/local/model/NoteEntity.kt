package com.example.data.local.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.model.Note



@Entity(tableName = "NoteEntity")
data class NoteEntity(@PrimaryKey(autoGenerate = true)var id:Int = 0 ,val title: String, val timeMillis: Long, val body: String)

fun NoteEntity.toNote(): Note = Note(id, title, timeMillis, body)

fun Note.toNoteEntity():NoteEntity =NoteEntity(
    title = title,
    timeMillis = timeMillis,
    body = body
)






