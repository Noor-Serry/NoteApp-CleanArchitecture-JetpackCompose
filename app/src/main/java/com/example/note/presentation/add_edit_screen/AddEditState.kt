package com.example.note.presentation.add_edit_screen

import com.example.domain.model.SaveNoteState

data class AddEditState(
    val title: String = "",
    val body: String = "",
    val saveState: SaveNoteState? = null
)
