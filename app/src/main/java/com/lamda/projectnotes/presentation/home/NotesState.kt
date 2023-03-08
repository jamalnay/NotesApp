package com.lamda.projectnotes.presentation.home

import com.lamda.projectnotes.data.data_source.local.Model.Category
import com.lamda.projectnotes.data.data_source.local.Model.Note

data class NotesState (
    val listOfNotes:List<Note> = emptyList(),
    var selectedCategory: Category? = null
        )

data class PinnedNoteState(
    val isNotePinned:Boolean = false
)