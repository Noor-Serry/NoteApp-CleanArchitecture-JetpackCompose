package com.example.domain.use_case

import com.example.domain.model.Note
import com.example.domain.model.SaveNoteState
import com.example.domain.repository.NoteRepository

class AddNoteUseCase(private val noteRepository: NoteRepository) {


    suspend operator fun invoke(note: Note) : SaveNoteState {
        return if (note.body.isBlank() || note.title.isBlank())
            SaveNoteState.Failure("can't save empty note")
        else {
            noteRepository.insertNote(note)
            SaveNoteState.Success
        }
    }
}