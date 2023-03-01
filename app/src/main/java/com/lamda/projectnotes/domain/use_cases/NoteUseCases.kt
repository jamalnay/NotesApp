package com.lamda.projectnotes.domain.use_cases

import com.lamda.projectnotes.domain.use_cases.note_use_cases.CreateUpdateNote
import com.lamda.projectnotes.domain.use_cases.note_use_cases.DeleteNote
import com.lamda.projectnotes.domain.use_cases.note_use_cases.GetAllNotes
import com.lamda.projectnotes.domain.use_cases.note_use_cases.GetNotesForCategory

data class NoteUseCases (
    val createUpdateNote: CreateUpdateNote,
    val deleteNote: DeleteNote,
    val getAllNotes: GetAllNotes,
    val getNotesForCategory: GetNotesForCategory
        )