package com.example.note.presentation.add_edit_screen


data class AddEditState(
    val title: String = "",
    val body: String = "",
    val errorMessage : String? = null,
    val isSuccess : Boolean = false,
)
