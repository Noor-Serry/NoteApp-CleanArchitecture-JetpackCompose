package com.example.domain.use_case

import com.example.domain.model.Note
import com.example.domain.repository.FakeNoteRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


class GetNoteByIdUseCaseTest {

    private lateinit var getNoteByIdUseCase: GetNoteByIdUseCase
    private lateinit var fakeNoteRepository: FakeNoteRepository

    @BeforeEach
    fun setUp() {
        fakeNoteRepository = FakeNoteRepository()
        getNoteByIdUseCase = GetNoteByIdUseCase(fakeNoteRepository)
        runTest {
            fakeNoteRepository.insertNote(Note(1,"title", 1,"body"))
        }

    }

    @Test
    fun `GetNoteByIdUseCase() with valid id Then return note`() = runTest {
      val  result =  getNoteByIdUseCase(1)

        assertThat(result).isNotNull()
    }

    @Test
    fun `GetNoteByIdUseCase() with not valid id Then return null`() = runTest {
        val  result =  getNoteByIdUseCase(0)

        assertThat(result).isNull()
    }


}