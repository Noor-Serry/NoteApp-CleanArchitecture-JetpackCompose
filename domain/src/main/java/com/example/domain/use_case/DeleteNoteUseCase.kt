package com.example.domain.use_case

import com.example.domain.model.Note
import com.example.domain.repository.NoteRepository

class DeleteNoteUseCase (private val noteRepository: NoteRepository) {

    suspend operator fun invoke(note: Note) = noteRepository.deleteNote(note)
}