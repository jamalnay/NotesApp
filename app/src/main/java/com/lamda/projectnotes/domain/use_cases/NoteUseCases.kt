package com.lamda.projectnotes.domain.use_cases

import com.lamda.projectnotes.domain.use_cases.note_use_cases.*

data class NoteUseCases(
    val createUpdateNote: CreateUpdateNote,
    val deleteNote: DeleteNote,
    val getAllNotes: GetAllNotes,
    val getNotesForCategory: GetNotesForCategory,
    val getNote: GetNote,
)