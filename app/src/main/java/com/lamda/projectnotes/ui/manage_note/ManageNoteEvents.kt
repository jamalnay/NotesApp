package com.lamda.projectnotes.ui.manage_note

import com.lamda.projectnotes.data.data_source.local.Model.Note

sealed class ManageNoteEvents {
    data class PinUnpinNote(val note: Note) : ManageNoteEvents()
}