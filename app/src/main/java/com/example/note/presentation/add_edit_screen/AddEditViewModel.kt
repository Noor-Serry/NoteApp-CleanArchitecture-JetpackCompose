package com.example.note.presentation.add_edit_screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Note
import com.example.domain.model.SaveNoteState
import com.example.domain.use_case.AddNoteUseCase
import com.example.domain.use_case.EditNoteUseCase
import com.example.domain.use_case.GetNoteByIdUseCase
import com.example.note.presentation.utils.NULL_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _stateTitle = MutableStateFlow("")
    val stateTitle = _stateTitle.asStateFlow()

    private val _stateBody = MutableStateFlow("")
    val stateBody = _stateBody.asStateFlow()

    private val _stateSave = MutableStateFlow<SaveNoteState?>(null)
    val stateSave = _stateSave.asStateFlow()

    private val args = AddEditScreenArgs(savedStateHandle)

    init {
        if (args.noteId != NULL_ID)
            getNoteById(args.noteId!!)
    }

    fun onTitleChange(newTitle: String) {
        _stateTitle.update { newTitle }
    }

    fun onBodyChange(newBody: String) {
        _stateBody.update { newBody }
    }

    private fun getNoteById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val note = getNoteByIdUseCase(id)
            _stateTitle.update { note!!.title }
            _stateBody.update { note!!.body }
        }
    }

    fun onSaveButtonClick() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = if (args.noteId == NULL_ID)
                addNoteUseCase(
                    Note(
                        id = args.noteId,
                        title = _stateTitle.value,
                        body = _stateBody.value,
                        timeMillis = System.currentTimeMillis()
                    )
                )
            else editNoteUseCase(
                Note(
                    id = args.noteId!!,
                    title = _stateTitle.value,
                    body = _stateBody.value,
                    timeMillis = System.currentTimeMillis()
                )
            )
            _stateSave.update { result }
        }


    }

}