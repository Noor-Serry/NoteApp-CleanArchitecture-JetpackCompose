package com.example.data.repository


import com.example.data.repository.local.LocalNote
import com.example.domain.model.Note
import com.example.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow


class NoteRepositoryImpl (private val localNote: LocalNote) : NoteRepository {


    override suspend fun getAllNotes(): Flow<List<Note>>  = localNote.getAllNotes()


    override suspend fun getNoteById(id: Int): Note? = localNote.getNoteById(id)


    override suspend fun deleteNote(note: Note) = localNote.deleteNote(note)


    override suspend fun insertNote(note: Note) = localNote.insertNote(note)


    override suspend fun updateNote(note: Note) = localNote.updateNote(note)


}