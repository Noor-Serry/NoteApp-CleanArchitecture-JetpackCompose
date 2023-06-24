package com.example.note.di

import com.example.domain.repository.NoteRepository
import com.example.domain.use_case.AddNoteUseCase
import com.example.domain.use_case.DeleteNoteUseCase
import com.example.domain.use_case.EditNoteUseCase
import com.example.domain.use_case.GetAllNotesUseCase
import com.example.domain.use_case.GetNoteByIdUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@InstallIn(ViewModelComponent::class)
@Module
class UsesCaseModule {

    @Provides
    fun provideAddNoteUseCase(noteRepository: NoteRepository) = AddNoteUseCase(noteRepository)

    @Provides
    fun provideDeleteNoteUseCase(noteRepository: NoteRepository) = DeleteNoteUseCase(noteRepository)

    @Provides
    fun provideEditNoteUseCase(noteRepository: NoteRepository) = EditNoteUseCase(noteRepository)

    @Provides
    fun provideGetNoteByIdUseCase(noteRepository: NoteRepository) = GetNoteByIdUseCase(noteRepository)

    @Provides
    fun provideGetAllNotesUseCase(noteRepository: NoteRepository) = GetAllNotesUseCase(noteRepository)

}