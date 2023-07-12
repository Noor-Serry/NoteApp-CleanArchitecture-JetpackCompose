package com.example.domain.use_case

import com.example.domain.model.Note
import com.example.domain.model.SaveNoteState
import com.example.domain.repository.FakeNoteRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class EditNoteUseCaseTest {

    private lateinit var editNoteUseCase: EditNoteUseCase
    private lateinit var fakeNoteRepository: FakeNoteRepository

    @Before
    fun setUp() {
        fakeNoteRepository = FakeNoteRepository()
        editNoteUseCase = EditNoteUseCase(fakeNoteRepository)
    }

    @Test
    fun `EditNoteUseCase()  With non empty title and non empty body Then return Success`() =
        runBlocking {

            val oldNote = Note(1, "test", 1, "test")
            fakeNoteRepository.insertNote(oldNote)
            val newNote = Note(1, "new title", 1, "new title")

            val state = editNoteUseCase(newNote)
            val result = fakeNoteRepository.getNoteById(1)

            assertThat(result).isEqualTo(newNote)
            assertThat(state).isInstanceOf(SaveNoteState.Success::class.java)

        }

    @Test
    fun `EditNoteUseCase() With empty title Then return Failure`() =
        runBlocking {

            val oldNote = Note(1, "test", 1, "test")
            fakeNoteRepository.insertNote(oldNote)
            val newNote = Note(1, "", 1, "new title")

            val state = editNoteUseCase(newNote)

            assertThat(state).isInstanceOf(SaveNoteState.Failure::class.java)
        }

    @Test
    fun `EditNoteUseCase() With empty body Then return Failure`() =
        runBlocking {

            val oldNote = Note(1, "test", 1, "test")
            fakeNoteRepository.insertNote(oldNote)
            val newNote = Note(1, "new title", 1, "")

            val state = editNoteUseCase(newNote)

            assertThat(state).isInstanceOf(SaveNoteState.Failure::class.java)
        }

    @Test
    fun `EditNoteUseCase() With  empty body and empty title Then return Failure`() =
        runBlocking {

            val oldNote = Note(1, "test", 1, "test")
            fakeNoteRepository.insertNote(oldNote)
            val newNote = Note(1, "", 1, "")

            val state = editNoteUseCase(newNote)

            assertThat(state).isInstanceOf(SaveNoteState.Failure::class.java)
        }
}