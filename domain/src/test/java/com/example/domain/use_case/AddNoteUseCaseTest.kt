package com.example.domain.use_case

import com.example.domain.model.Note
import com.example.domain.model.SaveNoteState
import com.example.domain.repository.FakeNoteRepository
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


class AddNoteUseCaseTest{

    private lateinit var addNoteUseCase: AddNoteUseCase
    private lateinit var fakeNoteRepository: FakeNoteRepository

    @Before
    fun setUp() {
        fakeNoteRepository = FakeNoteRepository()
        addNoteUseCase = AddNoteUseCase(fakeNoteRepository)
    }


    @Test
    fun `addNoteUseCase() With non empty title and non empty body then return Success`() = runBlocking {
        val note = Note( 1 , "title" ,1 ,"body")

        val result = addNoteUseCase(note)

        Truth.assertThat(result).isInstanceOf(SaveNoteState.Success::class.java)
    }

    @Test
    fun `addNoteUseCase() With empty title then return failure`() = runBlocking {
        val note = Note( 1 , "" ,1 ,"body")

        val result = addNoteUseCase(note)

        Truth.assertThat(result).isInstanceOf(SaveNoteState.Failure::class.java)
    }

    @Test
    fun `addNoteUseCase() With empty body then return failure`() = runBlocking {
        val note = Note( 1 , "title" ,1 ,"")

        val result = addNoteUseCase(note)

        Truth.assertThat(result).isInstanceOf(SaveNoteState.Failure::class.java)
    }

    @Test
    fun `addNoteUseCase() With empty title and empty body then return failure`() = runBlocking {
        val note = Note( 1 , "" ,1 ,"")

        val result = addNoteUseCase(note)

        Truth.assertThat(result).isInstanceOf(SaveNoteState.Failure::class.java)
    }

}