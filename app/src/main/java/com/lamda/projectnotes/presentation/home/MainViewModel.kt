package com.lamda.projectnotes.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lamda.projectnotes.data.data_source.local.Model.Note
import com.lamda.projectnotes.domain.use_cases.CategoryUseCases
import com.lamda.projectnotes.domain.use_cases.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    private val categoryUseCases: CategoryUseCases
    ):ViewModel()
{
    private val _notesState = mutableStateOf(NotesState())
    val notesState: State<NotesState> = _notesState

    private val _categoriesState = mutableStateOf(CategoriesState())
    val categoriesState: State<CategoriesState> = _categoriesState



    //Assign jobs to stop calling coroutine flow jobs multiple times
    private var getNotesJob: Job? = null
    private var getCategoriesJob: Job? = null

    init {
        getCategoriesList()
        getNotesList()
    }




    fun onEvent(mainEvents: MainEvents){

        when(mainEvents){
            is MainEvents.PinUnpinNote -> {
                val pinUnpin : Boolean = !mainEvents.note.isPinned
                val updatedNote: Note = Note(
                    mainEvents.note.noteId,
                    mainEvents.note.noteTitle,
                    mainEvents.note.noteContent,
                    mainEvents.note.creationTime,
                    mainEvents.note.noteColor,
                    pinUnpin,
                    mainEvents.note.noteCategory
                )
                viewModelScope.launch { noteUseCases.createUpdateNote(updatedNote) }
            }
            is MainEvents.SelectCategory -> {

                if (mainEvents.category.catId == 0){ //Show all notes if category is 0
                    _notesState.value.selectedCategory = null
                    getNotesList()
                }

                else{
                    getNotesForCategory(mainEvents.category.catId)
                    _notesState.value.selectedCategory = mainEvents.category
                }
            }
        }
    }

    private fun getNotesList(){
        getNotesJob?.cancel()
        getNotesJob = viewModelScope.launch {
            noteUseCases.getAllNotes.invoke().collect{notes ->
                _notesState.value = notesState.value.copy(
                    listOfNotes = notes
                )
            }
        }
    }

    private fun getCategoriesList(){
        getCategoriesJob?.cancel()
        getCategoriesJob = viewModelScope.launch {
            categoryUseCases.getAllCategories.invoke().collect{categories ->
                _categoriesState.value = categoriesState.value.copy(
                    listOfCategories = categories
                )
            }
        }
    }

    private fun getNotesForCategory(catId:Int){
        getNotesJob?.cancel()
        getNotesJob = viewModelScope.launch {
            noteUseCases.getNotesForCategory.invoke(catId = catId).collect {notes ->
                _notesState.value = notesState.value.copy(
                    listOfNotes = notes
                )
            }
        }
    }
}