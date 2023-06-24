package com.example.data.local


import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.local.daos.NoteDao
import com.example.data.local.model.NoteEntity


@Database(entities = [NoteEntity::class], version = 1)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun getNoteDao(): NoteDao
}