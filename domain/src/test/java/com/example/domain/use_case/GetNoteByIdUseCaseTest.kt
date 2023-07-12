package com.example.domain.use_case

import com.example.domain.model.Note
import com.example.domain.repository.FakeNoteRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking

import org.junit.Before
import org.junit.Test

class GetNoteByIdUseCaseTest {

    private lateinit var getNoteByIdUseCase: GetNoteByIdUseCase
    private lateinit var fakeNoteRepository: FakeNoteRepository

    @Before
    fun setUp() {
        fakeNoteRepository = FakeNoteRepository()
        getNoteByIdUseCase = GetNoteByIdUseCase(fakeNoteRepository)
        runBlocking {
            fakeNoteRepository.insertNote(Note(1,"title", 1,"body"))
        }

    }

    @Test
    fun `GetNoteByIdUseCase() with valid id Then return note`() = runBlocking {
      val  result =  getNoteByIdUseCase(1)

        assertThat(result).isNotNull()
    }

    @Test
    fun `GetNoteByIdUseCase() with not valid id Then return null`() = runBlocking {
        val  result =  getNoteByIdUseCase(0)

        assertThat(result).isNull()
    }


}