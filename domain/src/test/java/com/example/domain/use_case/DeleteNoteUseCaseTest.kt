package com.example.domain.use_case

import com.example.domain.model.Note
import com.example.domain.repository.FakeNoteRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class DeleteNoteUseCaseTest {

    private lateinit var deleteNoteUseCase: DeleteNoteUseCase
    private lateinit var fakeNoteRepository: FakeNoteRepository

    @Before
    fun setUp() {
        fakeNoteRepository = FakeNoteRepository()
        deleteNoteUseCase = DeleteNoteUseCase(fakeNoteRepository)
    }

    @Test
    fun `DeleteNoteUseCase() Then invoke delete note`() = runBlocking {
        val note1 = Note(1, "test", 1, "test")
        val note2 = Note(2, "test", 1, "test")
        fakeNoteRepository.insertNote(note1)
        fakeNoteRepository.insertNote(note2)

        deleteNoteUseCase(note1)

        val allNotes = fakeNoteRepository.getAllNotes().toList()
        allNotes.forEach {
            assertThat(it).doesNotContain(note1)
        }
    }

}



