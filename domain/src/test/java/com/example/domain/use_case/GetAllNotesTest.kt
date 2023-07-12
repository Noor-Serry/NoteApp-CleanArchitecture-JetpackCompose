package com.example.domain.use_case

import com.example.domain.model.Note
import com.example.domain.repository.FakeNoteRepository
import com.example.domain.util.OrderType

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetAllNotesTest {
    lateinit var getAllNotesUseCase: GetAllNotesUseCase
    lateinit var fakeNoteRepository: FakeNoteRepository

    @Before
    fun setUp() {
        fakeNoteRepository = FakeNoteRepository()
        getAllNotesUseCase = GetAllNotesUseCase(fakeNoteRepository)
    }

    @Test
    fun `GetAllNotes() With empty list Then return  empty list`() = runBlocking {
        val result = getAllNotesUseCase.invoke().first()
        assertThat(result).isEmpty()
    }

    @Test
    fun `GetAllNotes() With non empty list Then return  non empty list`() = runBlocking {

        val notes = mutableListOf<Note>()
        notes.add(Note(1, "title", 1, ""))
        notes.add(Note(2, "title", 2, ""))
        notes.forEach { fakeNoteRepository.insertNote(it) }

        val result = getAllNotesUseCase.invoke().first()
        assertThat(result).isEqualTo(notes)
    }

    @Test
    fun `GetAllNotes() in Ascending With empty list Then return  empty list`() = runBlocking {
        val result = getAllNotesUseCase.invoke(OrderType.ASCENDING).first()
        assertThat(result).isEmpty()
    }

    @Test
    fun `GetAllNotes() in ascending by timeMillis With random notes Then return  ascending notes`() =
        runBlocking {

            val notes = mutableListOf<Note>()
            notes.add(Note(1, "title", 2, ""))
            notes.add(Note(2, "title", 1, ""))
            notes.add(Note(2, "title", 3, ""))
            notes.forEach { fakeNoteRepository.insertNote(it) }

            val result = getAllNotesUseCase.invoke(OrderType.ASCENDING).first()

            for (i in 0 until notes.size - 1)
                assertThat(result[i].timeMillis).isLessThan(result[i + 1].timeMillis)

        }

    @Test
    fun `GetAllNotes() in descending With empty list Then return  empty list`() = runBlocking {
        val result = getAllNotesUseCase.invoke(OrderType.DESCENDING).first()
        assertThat(result).isEmpty()
    }

    @Test
    fun `GetAllNotes() in descending by timeMillis With random notes Then return  ascending notes`() =
        runBlocking {

            val notes = mutableListOf<Note>()
            notes.add(Note(1, "title", 2, ""))
            notes.add(Note(2, "title", 1, ""))
            notes.add(Note(2, "title", 3, ""))
            notes.forEach { fakeNoteRepository.insertNote(it) }

            val result = getAllNotesUseCase.invoke(OrderType.DESCENDING).first()

            for (i in 0 until notes.size - 1)
                assertThat(result[i].timeMillis).isGreaterThan(result[i + 1].timeMillis)

        }


}