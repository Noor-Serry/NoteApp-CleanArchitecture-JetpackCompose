package com.example.domain.use_case

import com.example.domain.model.Note
import com.example.domain.repository.NoteRepository
import com.example.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAllNotesUseCase (private val noteRepository: NoteRepository) {

   suspend operator fun invoke (orderType: OrderType? = null) : Flow<List<Note>> {
      return noteRepository.getAllNotes().map {
         when (orderType) {
            OrderType.ASCENDING -> it.sortedBy { it.timeMillis }
            OrderType.DESCENDING -> it.sortedByDescending  { it.timeMillis }
            null -> it
         }
      }
   }
}