package com.lamda.projectnotes.domain.use_cases.note_use_cases

import com.lamda.projectnotes.data.data_source.local.model.Category
import com.lamda.projectnotes.data.data_source.local.model.Note
import com.lamda.projectnotes.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class GetAllNotes(
    private val repository: NoteRepository,
) {
    operator fun invoke(): Flow<List<Note>> = repository.getAllNotes()
}