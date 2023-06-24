package com.example.domain.use_case

import com.example.domain.repository.NoteRepository

class GetNoteByIdUseCase(private val noteRepository: NoteRepository) {

    suspend operator fun invoke(id : Int) = noteRepository.getNoteById(id)
}