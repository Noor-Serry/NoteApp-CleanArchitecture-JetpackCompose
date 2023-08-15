package com.example.note.di

import androidx.lifecycle.SavedStateHandle
import com.example.domain.repository.NoteRepository
import com.example.domain.use_case.AddNoteUseCase
import com.example.domain.use_case.DeleteNoteUseCase
import com.example.domain.use_case.EditNoteUseCase
import com.example.domain.use_case.GetAllNotesUseCase
import com.example.domain.use_case.GetNoteByIdUseCase
import com.example.note.presentation.add_edit_screen.AddEditScreenArgs
import com.example.note.presentation.utils.dispatcher.DefaultDispatcherProvider
import com.example.note.presentation.utils.dispatcher.DispatcherProvider
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

    @Provides
    fun provideDispatcherProvider() : DispatcherProvider = DefaultDispatcherProvider()
    @Provides
    fun provideAddEditScreenArgs (savedStateHandle: SavedStateHandle) = AddEditScreenArgs(savedStateHandle)
}