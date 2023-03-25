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
import kotlinx.coroutines.Job
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

data class DeletedNotesState(
    val listOfNotes: List<Note>,
)

@HiltViewModel
class NoteManagementViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private var _noteState = mutableStateOf(NoteUiState())
    val noteState: State<NoteUiState> = _noteState

    private var _deletedNotesState = mutableStateOf(DeletedNotesState(emptyList()))
    val deletedNotesState: State<DeletedNotesState> = _deletedNotesState

    private var noteId: Int? = null
    private var getDeletedNotesJob: Job? = null

    init {
        noteId = savedStateHandle.get<Int>("noteId")
        if (noteId != null)
            viewModelScope.launch {
                _noteState.value = noteState.value.copy(
                    note = noteUseCases.getNote(noteId = noteId!!)
                )
            }
        viewModelScope.launch {
            try {
                getDeletedNotesList()
            } catch (_: Exception) {
            }
        }

    }

    override fun onCleared() {
        getDeletedNotesJob?.cancel()
        super.onCleared()
    }

    fun onEvent(manageNoteEvents: ManageNoteEvents) {
        when (manageNoteEvents) {
            is ManageNoteEvents.PinUnpinNote -> pinUnpinNote(manageNoteEvents.note)
            is ManageNoteEvents.MoveToTrash -> moveToTrash(manageNoteEvents.note)
            is ManageNoteEvents.DeleteNote -> deleteNote(manageNoteEvents.note)
            is ManageNoteEvents.RestoreNote -> restoreNote(manageNoteEvents.note)
        }
    }

    private fun getDeletedNotesList() {
        getDeletedNotesJob?.cancel()
        getDeletedNotesJob = viewModelScope.launch {
            noteUseCases.getDeletedNotes()
                .collect { notes ->
                    _deletedNotesState.value = deletedNotesState.value.copy(
                        listOfNotes = notes
                    )
                }
        }
    }


    private fun pinUnpinNote(note: Note) {
        val pinnedUnpinnedNote = note.copy(isPinned = !note.isPinned)
        viewModelScope.launch { noteUseCases.createUpdateNote(pinnedUnpinnedNote) }
    }

    private fun moveToTrash(note: Note) {
        val deletedNote = note.copy(isDeleted = true)
        viewModelScope.launch { noteUseCases.createUpdateNote(deletedNote) }
    }

    private fun deleteNote(note: Note) {
        viewModelScope.launch { noteUseCases.deleteNote(note) }
    }

    private fun restoreNote(note: Note) {
        val restoredNote = note.copy(isDeleted = false)
        viewModelScope.launch { noteUseCases.createUpdateNote(restoredNote) }
    }



}