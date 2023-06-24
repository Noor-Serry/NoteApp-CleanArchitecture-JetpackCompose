package com.example.data.repository.local

import com.example.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface LocalNote {

    suspend fun getAllNotes(): Flow<List<Note>>
    suspend fun getNoteById(id: Int): Note?
    suspend fun deleteNote(note: Note)
    suspend fun insertNote(note: Note)
    suspend fun updateNote(note: Note)
}