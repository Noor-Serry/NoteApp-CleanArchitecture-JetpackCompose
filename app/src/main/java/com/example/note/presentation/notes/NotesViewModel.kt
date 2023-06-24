package com.example.note.presentation.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Note
import com.example.domain.use_case.DeleteNoteUseCase
import com.example.domain.use_case.GetAllNotesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val getAllNotesUseCase: GetAllNotesUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(NotesState())

    val state = _state.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getAllNotesUseCase().collect { notes ->
                _state.update {
                    it.copy(notes = notes)
                }
            }

        }
    }


    fun onClickDescendingButton() {
        _state.update {
            it.copy(
                notes = it.notes.sortedByDescending { it.timeMillis },
                ascendingButtonChecked = false
            )
        }
    }

    fun onClickAscendingButton() {
        _state.update {
            it.copy(
                notes = it.notes.sortedBy { it.timeMillis }, ascendingButtonChecked = true
            )
        }
    }

    fun onClickToggleOrderSection() {
        _state.update { it.copy(isOrderSectionVisible = !it.isOrderSectionVisible) }
    }


    fun onLazyScrolling(isStop: Boolean) {
        _state.update { it.copy(isAddButtonVisible = !isStop) }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteNoteUseCase(note)
        }
    }
}




