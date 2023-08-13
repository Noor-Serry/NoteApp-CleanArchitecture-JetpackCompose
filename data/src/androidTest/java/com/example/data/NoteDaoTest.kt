package com.example.data

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.example.data.local.LocalDatabase
import com.example.data.local.daos.NoteDao
import com.example.data.local.model.NoteEntity
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


class NoteDaoTest {


    private lateinit var myDataBase: LocalDatabase
    private lateinit var dao: NoteDao

    @BeforeEach
    fun setup() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        myDataBase = Room.inMemoryDatabaseBuilder(appContext, LocalDatabase::class.java).build()
        dao = myDataBase.getNoteDao()
    }

    @AfterEach
    fun tearDown() {
        myDataBase.close()
    }



    @Test
    fun insertNote() = runBlocking {
        val note = NoteEntity(1, "title", 1L, "body")

        dao.insertNote(note)
        val allNotes = dao.getAllNotes().first()

        assertThat(allNotes).contains(note)
    }

    @Test
    fun getAllNotes() = runBlocking {
        val note = NoteEntity(1, "title", 1L, "body")

        dao.insertNote(note)
        val allNotes = dao.getAllNotes().first()

        allNotes.forEach {
            assertThat(it).isEqualTo(note)
        }

    }
    @Test
    fun getNoteById_withExistId_thenNote() = runBlocking {
        val note = NoteEntity(1, "title", 1L, "body")
        dao.insertNote(note)

        val result =  dao.getNote(1)

        assertThat(result).isEqualTo(note)
    }
    @Test
    fun getNoteById_withNotExistId_thenReturnNull() = runBlocking {
        val result =  dao.getNote(1)
        assertThat(result).isNull()
    }

    @Test
    fun deleteNote() = runBlocking {
        val note = NoteEntity(1, "title", 1L, "body")
        dao.insertNote(note)

        dao.deleteNote(note)
        val allNotes = dao.getAllNotes().first()

            assertThat(allNotes).doesNotContain(note)

    }

    @Test
    fun updateNote()  = runBlocking{
        val note = NoteEntity(1, "title", 1L, "body")
        dao.insertNote(note)

       val noteAfterEdit =  note.copy(title = "title2")
        dao.updateNote(noteAfterEdit)

        assertThat(dao.getNote(1)).isEqualTo(noteAfterEdit)

    }


}