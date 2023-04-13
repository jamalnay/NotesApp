package com.lamda.projectnotes.ui.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lamda.projectnotes.data.data_source.local.model.Category
import com.lamda.projectnotes.data.data_source.local.model.Note
import com.lamda.projectnotes.domain.use_cases.CategoryUseCases
import com.lamda.projectnotes.domain.use_cases.NoteUseCases
import com.lamda.projectnotes.ui.AppStates.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class HomeViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    private val categoryUseCases: CategoryUseCases
) : ViewModel() {
    val allCategory = Category(-1, "All",0)

    private val _categoriesState = mutableStateOf(CategoriesState(emptyList()))
    val categoriesState: State<CategoriesState> = _categoriesState


    private val _notesState = mutableStateOf(NotesState(emptyList()))
    val notesState: State<NotesState> = _notesState

    private var getNotesJob: Job? = null
    private var getCategoriesJob: Job? = null
    private var getPinnedNotesJob: Job? = null

    init {
        viewModelScope.launch {
            try {
                categoryUseCases.createUpdateCategory(allCategory)
                getCategoriesList()
                selectCategory(allCategory)
            } catch (_: Exception) {

            }
        }
    }

    override fun onCleared() {
        getNotesJob?.cancel()
        getCategoriesJob?.cancel()
        getPinnedNotesJob?.cancel()
        super.onCleared()
    }

    fun onEvent(homeEvents: HomeEvents) {
        when (homeEvents) {
            is HomeEvents.PinUnpinNote -> pinUnpinNote(homeEvents.note)
            is HomeEvents.SelectCategory -> selectCategory(homeEvents.category)
            is HomeEvents.CreateCategory -> createCategory(homeEvents.category)
        }
    }


    private fun getCategoriesList() {
        getCategoriesJob?.cancel()
        getCategoriesJob = viewModelScope.launch {
            categoryUseCases.getAllCategories()
                .collect { categories ->
                    _categoriesState.value = categoriesState.value.copy(
                        listOfCategories = categories
                    )
                }
        }
    }

    private fun selectCategory(category: Category) {
        getNotesJob?.cancel()
        if (category.catId==-1) {
            getNotesJob = viewModelScope.launch {
                noteUseCases.getAllNotes()
                    .collect { notes ->
                        _notesState.value = notesState.value.copy(
                            listOfNotes = notes
                        )
                    }
            }
        } else {
            getNotesJob = viewModelScope.launch {
                noteUseCases.getNotesForCategory(catId = category.catId!!)
                    .collect { notes ->
                        _notesState.value = notesState.value.copy(
                            listOfNotes = notes
                        )
                    }
            }
        }
    }


    private fun createCategory(category: Category) {
        viewModelScope.launch { categoryUseCases.createUpdateCategory(category) }
    }

    private fun pinUnpinNote(note: Note) {
        val pinnedUnpinnedNote = note.copy(isPinned = !note.isPinned)
        viewModelScope.launch { noteUseCases.createUpdateNote(pinnedUnpinnedNote) }
    }
}


