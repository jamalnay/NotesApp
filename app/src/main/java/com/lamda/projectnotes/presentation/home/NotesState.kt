package com.lamda.projectnotes.presentation.home

import com.lamda.projectnotes.data.data_source.local.Model.Note

data class NotesState (
    val listOfNotes:List<Note>
        )

data class PinnedNoteState(
    val isNotePinned:Boolean = false
)