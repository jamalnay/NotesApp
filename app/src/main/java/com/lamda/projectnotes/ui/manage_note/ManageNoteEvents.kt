package com.lamda.projectnotes.ui.manage_note

import com.lamda.projectnotes.data.data_source.local.model.Note

sealed class ManageNoteEvents {
    data class PinUnpinNote(val note: Note) : ManageNoteEvents()
    data class DeleteNote(val note: Note) : ManageNoteEvents()

}