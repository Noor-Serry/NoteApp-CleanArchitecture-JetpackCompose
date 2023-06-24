package com.example.data.local

import com.example.data.local.daos.NoteDao
import com.example.data.local.model.toNote
import com.example.data.local.model.toNoteEntity
import com.example.data.repository.local.LocalNote
import com.example.domain.model.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class LocalNoteImpl(private val noteDao: NoteDao) : LocalNote {


    override suspend fun getAllNotes(): Flow<List<Note>> =
        noteDao.getAllNotes().map { it.map { it.toNote() } }


    override suspend fun getNoteById(id: Int): Note? = noteDao.getNote(id)?.toNote()


    override suspend fun deleteNote(note: Note) {
        val noteEntity = note.toNoteEntity()
        noteEntity.id = note.id
        noteDao.deleteNote(noteEntity)
    }


    override suspend fun insertNote(note: Note) = noteDao.insertNote(note.toNoteEntity())


    override suspend fun updateNote(note: Note) {
        val noteEntity = note.toNoteEntity()
        noteEntity.id = note.id
        noteDao.updateNote(noteEntity)
    }
}