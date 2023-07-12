package com.example.note.presentation.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Note
import com.example.domain.use_case.DeleteNoteUseCase
import com.example.domain.use_case.GetAllNotesUseCase
import com.example.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
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

    private var getNotesJob: Job? = null

    init {
        getAllNotes()
    }


    fun onClickDescendingButton() {
        getAllNotes(OrderType.DESCENDING)
        _state.update {
            it.copy(ascendingButtonChecked = false)
        }
    }

    fun onClickAscendingButton() {
        getAllNotes(OrderType.ASCENDING)
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
    private fun getAllNotes(orderType: OrderType? = null) {
        getNotesJob?.cancel()
        getNotesJob = viewModelScope.launch(Dispatchers.IO) {
            getAllNotesUseCase(orderType).collect { notes ->
                _state.update {
                    it.copy(notes = notes)
                }
            }
        }
    }

}




