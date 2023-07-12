package com.example.domain.repository

import com.example.domain.model.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeNoteRepository : NoteRepository {

    private val notes = mutableListOf<Note>()

    override suspend fun getAllNotes(): Flow<List<Note>> = flow { emit(notes) }

    override suspend fun getNoteById(id: Int): Note? {
        return notes.find { it.id == id }
    }

    override suspend fun deleteNote(note: Note) {
        notes.remove(note)
    }

    override suspend fun insertNote(note: Note) {
        notes.add(note)
    }

    override suspend fun updateNote(note: Note) {
         notes.find { it.id == note.id }?.let {
            val noteIndex = notes.indexOf(it)
                 notes.removeAt(noteIndex)
                 notes.add(noteIndex, note)
        }

    }

}