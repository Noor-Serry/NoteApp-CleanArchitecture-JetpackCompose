package com.example.domain.use_case

import com.example.domain.repository.NoteRepository

class GetAllNotesUseCase (private val noteRepository: NoteRepository) {

   suspend operator fun invoke () = noteRepository.getAllNotes()
}