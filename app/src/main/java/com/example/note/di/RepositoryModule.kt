package com.example.note.di


import com.example.data.repository.NoteRepositoryImpl
import com.example.data.repository.local.LocalNote
import com.example.domain.repository.NoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {
    @Singleton
    @Provides
    fun provideNoteRepository(localNote: LocalNote) :NoteRepository = NoteRepositoryImpl(localNote)

}