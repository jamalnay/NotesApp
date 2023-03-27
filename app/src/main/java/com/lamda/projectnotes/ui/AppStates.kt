package com.lamda.projectnotes.ui

import com.lamda.projectnotes.data.data_source.local.model.Note
import com.lamda.projectnotes.ui.theme.White80

class AppStates {
    data class NoteUiState(
        val note: Note = Note(
            noteId = 0,
            noteTitle = "",
            noteContent = "",
            creationTime = System.currentTimeMillis() / 1000,
            noteColor = Note.noteColors.indexOf(White80),
            isPinned = false,
            noteCategory = 0,
            noteCategoryName = ""
        ),
    )

    data class DeletedNotesState(
        val listOfNotes: List<Note>,
    )

}

