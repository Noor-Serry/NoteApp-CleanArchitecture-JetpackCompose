package com.example.note.presentation.notes

import com.example.domain.model.Note


data class NotesState(
    val notes: List<Note> = emptyList(),
    val isOrderSectionVisible: Boolean = false,
    val isAddButtonVisible: Boolean = true,
    val ascendingButtonChecked : Boolean = true
)