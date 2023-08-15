package com.example.note.presentation.add_edit_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Note
import com.example.domain.model.SaveNoteState
import com.example.domain.use_case.AddNoteUseCase
import com.example.domain.use_case.EditNoteUseCase
import com.example.domain.use_case.GetNoteByIdUseCase
import com.example.note.presentation.utils.NULL_ID
import com.example.note.presentation.utils.dispatcher.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditViewModel @Inject constructor(
    private val getNoteByIdUseCase: GetNoteByIdUseCase,
    private val editNoteUseCase: EditNoteUseCase,
    private val addNoteUseCase: AddNoteUseCase,
    private val args: AddEditScreenArgs,
    private val Dispatchers : DispatcherProvider
) : ViewModel() {

    private val _state = MutableStateFlow(AddEditState())
    val state = _state.asStateFlow()

    init {
        if (args.noteId != NULL_ID)
            getNoteById(args.noteId!!)
    }

    fun onTitleChange(newTitle: String) {
        _state.update { it.copy(title = newTitle) }
    }

    fun onBodyChange(newBody: String) {
        _state.update { it.copy(body = newBody) }
    }

    private fun getNoteById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val note = getNoteByIdUseCase(id)!!
            _state.update { it.copy(title = note.title, body = note.body) }
        }
    }

    fun onSaveButtonClick() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = if (args.noteId == NULL_ID)
                addNoteUseCase(
                    Note(
                        id = args.noteId,
                        title = _state.value.title,
                        body = _state.value.body,
                        timeMillis = System.currentTimeMillis()
                    )
                )
            else editNoteUseCase(
                Note(
                    id = args.noteId!!,
                    title = _state.value.title,
                    body = _state.value.body,
                    timeMillis = System.currentTimeMillis()
                )
            )
            if (result is SaveNoteState.Success)
            _state.update { it.copy(isSuccess = true) }
            else _state.update { it.copy(errorMessage = (result as SaveNoteState.Failure).message) }
        }


    }

}