package com.example.domain.use_case

import com.example.domain.model.Note
import com.example.domain.model.SaveNoteState
import com.example.domain.repository.FakeNoteRepository
import com.google.common.truth.Truth
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


class AddNoteUseCaseTest{

    private lateinit var addNoteUseCase: AddNoteUseCase
    private lateinit var fakeNoteRepository: FakeNoteRepository

    @BeforeEach
    fun setUp() {
        fakeNoteRepository = FakeNoteRepository()
        addNoteUseCase = AddNoteUseCase(fakeNoteRepository)
    }


    @Test
    fun `addNoteUseCase() With non empty title and non empty body then return Success`() = runTest {
        val note = Note( 1 , "title" ,1 ,"body")

        val result = addNoteUseCase(note)

        Truth.assertThat(result).isInstanceOf(SaveNoteState.Success::class.java)
    }

    @Test
    fun `addNoteUseCase() With empty title then return failure`() = runTest {
        val note = Note( 1 , "" ,1 ,"body")

        val result = addNoteUseCase(note)

        Truth.assertThat(result).isInstanceOf(SaveNoteState.Failure::class.java)
    }

    @Test
    fun `addNoteUseCase() With empty body then return failure`() = runTest {
        val note = Note( 1 , "title" ,1 ,"")

        val result = addNoteUseCase(note)

        Truth.assertThat(result).isInstanceOf(SaveNoteState.Failure::class.java)
    }

    @Test
    fun `addNoteUseCase() With empty title and empty body then return failure`() = runTest {
        val note = Note( 1 , "" ,1 ,"")

        val result = addNoteUseCase(note)

        Truth.assertThat(result).isInstanceOf(SaveNoteState.Failure::class.java)
    }

}