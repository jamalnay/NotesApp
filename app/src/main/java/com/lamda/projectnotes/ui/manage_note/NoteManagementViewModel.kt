package com.lamda.projectnotes.ui.manage_note

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lamda.projectnotes.data.data_source.local.model.Note
import com.lamda.projectnotes.domain.use_cases.NoteUseCases
import com.lamda.projectnotes.ui.theme.White80
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


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

@HiltViewModel
class NoteManagementViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private var _noteState = mutableStateOf(NoteUiState())
    val noteState: State<NoteUiState> = _noteState

    private var noteId: Int? = null

    init {
        noteId = savedStateHandle.get<Int>("noteId")
        if (noteId != null)
            viewModelScope.launch {
                _noteState.value = noteState.value.copy(
                    note = noteUseCases.getNote(noteId = noteId!!)
                )
            }
    }

    fun onEvent(manageNoteEvents: ManageNoteEvents) {
        when (manageNoteEvents) {
            is ManageNoteEvents.PinUnpinNote -> pinUnpinNote(manageNoteEvents.note)
        }
    }


    private fun pinUnpinNote(note: Note) {
        val pinnedUnpinnedNote = note.copy(isPinned = !note.isPinned)
        viewModelScope.launch { noteUseCases.createUpdateNote(pinnedUnpinnedNote) }
    }



}