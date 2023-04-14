package com.lamda.projectnotes.domain.use_cases

import com.lamda.projectnotes.domain.use_cases.note_use_cases.*

data class NoteUseCases(
    val createUpdateNote: CreateUpdateNote,
    val deleteNote: DeleteNote,
    val getAllNotes: GetAllNotes,
    val getPinnedNotes: GetPinnedNotes,
    val getDeletedNotes: GetDeletedNotes,
    val getNotesForCategory: GetNotesForCategory,
    val getNote: GetNote
)