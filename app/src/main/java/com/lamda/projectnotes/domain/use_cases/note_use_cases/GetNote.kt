package com.lamda.projectnotes.domain.use_cases.note_use_cases

import com.lamda.projectnotes.data.data_source.local.Model.Note
import com.lamda.projectnotes.domain.repository.NoteRepository

class GetNote(private val repository: NoteRepository) {
    suspend operator fun invoke(noteId: Int): Note = repository.getNote(noteId)

}