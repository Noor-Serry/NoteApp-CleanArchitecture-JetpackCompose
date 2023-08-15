package com.example.note.presentation.notes

import com.example.domain.model.Note
import com.example.domain.use_case.DeleteNoteUseCase
import com.example.domain.use_case.GetAllNotesUseCase
import com.example.domain.util.OrderType
import com.example.note.presentation.TestDispatcherProvider
import com.google.common.truth.Truth.assertThat
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class NotesViewModelTest {

    private lateinit var testDispatcherProvider: TestDispatcherProvider
    private lateinit var testScope: TestScope
    lateinit var getAllNotesUseCase: GetAllNotesUseCase
    lateinit var deleteNotesUseCase: DeleteNoteUseCase
    lateinit var notesViewModel: NotesViewModel

    @BeforeEach
    fun setup() {
        testDispatcherProvider = TestDispatcherProvider()
        testScope = TestScope(testDispatcherProvider.testDispatcher)
        getAllNotesUseCase = mockk()
        deleteNotesUseCase = mockk()
        coEvery { getAllNotesUseCase() } returns flowOf(emptyList())
        notesViewModel =
            NotesViewModel(getAllNotesUseCase, deleteNotesUseCase, testDispatcherProvider)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `getAllNotes with empty repo then update state notes`() {
        testScope.advanceUntilIdle()

        assertThat(notesViewModel.state.value.notes).isEmpty()
    }


    @Test
    fun `getAllNotes with non empty repo then update state notes`() {
        val notes = listOf(Note(1, "title", 1L, "body"))

        coEvery { getAllNotesUseCase() } returns flowOf(notes)
        val notesViewModel =
            NotesViewModel(getAllNotesUseCase, deleteNotesUseCase, testDispatcherProvider)

        testScope.advanceUntilIdle()

        assertThat(notesViewModel.state.value.notes).isEqualTo(notes)
    }


    @Test
    fun `onClickDescendingButton() then update notes to be descending && ascendingButtonChecked to be false `() {
        val notes = listOf(
            Note(1, "title", 1L, "body"),
            Note(2, "title", 2L, "body")
        )
        coEvery { getAllNotesUseCase(OrderType.DESCENDING) } returns flowOf(notes.sortedByDescending { it.timeMillis })
        val notesViewModel =
            NotesViewModel(getAllNotesUseCase, deleteNotesUseCase, testDispatcherProvider)
        notesViewModel.onClickDescendingButton()

        testScope.advanceUntilIdle()

        assertThat(notesViewModel.state.value.ascendingButtonChecked).isFalse()
        assertThat(notesViewModel.state.value.notes).isEqualTo(notes.sortedByDescending { it.timeMillis })
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `onClickAscendingButton() then update notes to be ascending && ascendingButtonChecked to be true `() {
        val notes = listOf(
            Note(2, "title", 2L, "body"),
            Note(1, "title", 1L, "body")
        )

        coEvery { getAllNotesUseCase(OrderType.ASCENDING) } returns flowOf(notes.sortedBy { it.timeMillis })
        val notesViewModel =
            NotesViewModel(getAllNotesUseCase, deleteNotesUseCase, testDispatcherProvider)
        notesViewModel.onClickAscendingButton()

        testScope.advanceUntilIdle()

        assertThat(notesViewModel.state.value.ascendingButtonChecked).isTrue()
        assertThat(notesViewModel.state.value.notes).isEqualTo(notes.sortedBy { it.timeMillis })
    }

    @Test
    fun `onClickToggleOrderSection() then update isOrderSectionVisible to true `() {
        notesViewModel.onClickToggleOrderSection()

        assertThat(notesViewModel.state.value.isOrderSectionVisible).isTrue()
    }

    @Test
    fun `onLazyScrolling() with true then update isAddButtonVisible to false `() {
        notesViewModel.onLazyScrolling(true)

        assertThat(notesViewModel.state.value.isAddButtonVisible).isFalse()
    }

    @Test
    fun `onLazyScrolling() with false then update isAddButtonVisible to true `() {
        notesViewModel.onLazyScrolling(false)

        assertThat(notesViewModel.state.value.isAddButtonVisible).isTrue()
    }

    @Test
    fun `deleteNote() then invoke this note`() {
        val notes = listOf(
            Note(2, "title", 2L, "body"),
            Note(1, "title", 1L, "body")
        )

        coEvery { getAllNotesUseCase() } returns flowOf(notes)
        coEvery { deleteNotesUseCase(notes[0]) } just Runs

        notesViewModel.deleteNote(notes[0])

        testScope.advanceUntilIdle()
        coVerify(exactly = 1) { deleteNotesUseCase(notes[0]) }
    }
}