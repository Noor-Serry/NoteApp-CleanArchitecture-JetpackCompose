package com.example.note.di

import android.app.Application
import androidx.room.Room
import com.example.data.local.LocalDatabase
import com.example.data.local.LocalNoteImpl
import com.example.data.repository.local.LocalNote
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDB() {

    @Singleton
    @Provides
    fun provideLocalDB(app: Application): LocalDatabase {
       return Room.databaseBuilder(app, LocalDatabase::class.java,"LocalDatabase.db").build()

    }

    @Singleton
    @Provides
    fun provideNoteImpl(localDatabase: LocalDatabase):LocalNote = LocalNoteImpl(localDatabase.getNoteDao())
}