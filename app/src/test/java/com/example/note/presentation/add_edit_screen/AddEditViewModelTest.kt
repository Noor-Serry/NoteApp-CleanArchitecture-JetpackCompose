package com.example.note.presentation.add_edit_screen

import com.example.domain.model.Note
import com.example.domain.model.SaveNoteState
import com.example.domain.use_case.AddNoteUseCase
import com.example.domain.use_case.EditNoteUseCase
import com.example.domain.use_case.GetNoteByIdUseCase
import com.example.note.presentation.TestDispatcherProvider
import com.example.note.presentation.utils.NULL_ID
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class AddEditViewModelTest {

    val getNoteByIdUseCase: GetNoteByIdUseCase = mockk()
    val editNoteUseCase: EditNoteUseCase = mockk()
    val addNoteUseCase: AddNoteUseCase = mockk()
    val args: AddEditScreenArgs = mockk()
    private var testDispatcherProvider = TestDispatcherProvider()
    private var testScope = TestScope(testDispatcherProvider.testDispatcher)

    lateinit var addEditViewModel: AddEditViewModel

    @BeforeEach
    fun setup() {
        every { args.noteId } returns NULL_ID
        addEditViewModel = AddEditViewModel(
            getNoteByIdUseCase = getNoteByIdUseCase,
            addNoteUseCase = addNoteUseCase,
            editNoteUseCase = editNoteUseCase,
            args = args,
            Dispatchers = testDispatcherProvider
        )
    }

    @Test
    fun `initialize addEditViewModel with null id then do not call getNoteByIdUseCase()`() {
        coVerify(exactly = 0) { getNoteByIdUseCase(any<Int>()) }
    }

    @Test
    fun `initialize addEditViewModel with not null id then call getNoteByIdUseCase`() {
        val customArgs: AddEditScreenArgs = mockk()
        val customGetNoteById: GetNoteByIdUseCase = mockk()
        coEvery { customGetNoteById(1) } returns Note(1, "title1", 1L, "body1")
        every { customArgs.noteId } returns 1

        val addEditViewModel = AddEditViewModel(
            getNoteByIdUseCase = customGetNoteById,
            addNoteUseCase = addNoteUseCase,
            editNoteUseCase = editNoteUseCase,
            args = customArgs,
            Dispatchers = testDispatcherProvider
        )
        testScope.advanceUntilIdle()

        coVerify(exactly = 1) { customGetNoteById(1) }
        assertThat(addEditViewModel.state.value.title).isEqualTo("title1")
    }

    @Test
    fun `onTitleChange() then update title`() {
        assertThat(addEditViewModel.state.value.title).isEqualTo("")
        addEditViewModel.onTitleChange("newTitle")
        assertThat(addEditViewModel.state.value.title).isEqualTo("newTitle")
    }


    @Test
    fun `onBodyChange() then update body`() {
        assertThat(addEditViewModel.state.value.body).isEqualTo("")
        addEditViewModel.onBodyChange("newBody")
        assertThat(addEditViewModel.state.value.body).isEqualTo("newBody")
    }

    @Test
    fun `onSaveButtonClick() with NULL_ID and with out error then update isSuccess`() {
        coEvery { addNoteUseCase(any()) } returns SaveNoteState.Success

        addEditViewModel.onSaveButtonClick()

        testScope.advanceUntilIdle()
        coVerify (exactly = 1){ addNoteUseCase(any()) }
        assertThat(addEditViewModel.state.value.isSuccess).isEqualTo(true)

    }


    @Test
    fun `onSaveButtonClick() with NULL_ID and  error then update errorMessage`() {
        val errorMessage = "errorMessage"
        coEvery { addNoteUseCase(any()) } returns SaveNoteState.Failure(errorMessage)

        addEditViewModel.onSaveButtonClick()

        testScope.advanceUntilIdle()
        coVerify (exactly = 1){ addNoteUseCase(any()) }
        assertThat(addEditViewModel.state.value.isSuccess).isEqualTo(false)
        assertThat(addEditViewModel.state.value.errorMessage).isEqualTo(errorMessage)
    }

    @Test
    fun `onSaveButtonClick() with not NULL_ID and with out error then update isSuccess`() {

      every {args.noteId } returns 1
        coEvery { editNoteUseCase(any()) } returns SaveNoteState.Success

        addEditViewModel.onSaveButtonClick()

        testScope.advanceUntilIdle()
        coVerify (exactly = 1){ editNoteUseCase(any()) }
        assertThat(addEditViewModel.state.value.isSuccess).isEqualTo(true)
    }

    @Test
    fun `onSaveButtonClick() with not NULL_ID and  error then update errorMessage`() {
        val errorMessage = "errorMessage"
        every {args.noteId } returns 1
        coEvery { editNoteUseCase(any()) } returns SaveNoteState.Failure(errorMessage)

        addEditViewModel.onSaveButtonClick()

        testScope.advanceUntilIdle()
        coVerify (exactly = 1){ editNoteUseCase(any()) }
        assertThat(addEditViewModel.state.value.isSuccess).isEqualTo(false)
        assertThat(addEditViewModel.state.value.errorMessage).isEqualTo(errorMessage)
    }

}