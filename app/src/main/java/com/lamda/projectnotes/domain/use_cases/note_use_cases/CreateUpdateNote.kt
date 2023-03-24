package com.lamda.projectnotes.domain.use_cases.note_use_cases

import com.lamda.projectnotes.data.data_source.local.model.Note
import com.lamda.projectnotes.domain.repository.NoteRepository

class CreateUpdateNote(private val repository: NoteRepository) {
    suspend operator fun invoke(note: Note) = repository.insertNote(note)

}