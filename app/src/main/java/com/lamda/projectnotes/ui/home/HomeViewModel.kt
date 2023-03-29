package com.lamda.projectnotes.ui.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
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
    private val categoryUseCases: CategoryUseCases,
) : ViewModel() {
    private val allCategory = Category(-1, "All")

    private val _notesState = mutableStateOf(NotesState(emptyList()))
    val notesState: State<NotesState> = _notesState



    private val _categoriesState = mutableStateOf(CategoriesState(emptyList()))
    val categoriesState: State<CategoriesState> = _categoriesState

    private val _selectedCategoryState = mutableStateOf(SelectedCategoryState(Category(-1, "All")))
    val selectedCategoryState: State<SelectedCategoryState> = _selectedCategoryState

    private var getNotesJob: Job? = null
    private var getCategoriesJob: Job? = null

    init {
        viewModelScope.launch {
            try {
                categoryUseCases.createUpdateCategory(allCategory)
                getCategoriesList()
                getNotesList()
            } catch (_: Exception) {

            }
        }
//  prepopulate some data
//        viewModelScope.launch {
//            categoryUseCases.createUpdateCategory.invoke(Category(catName = "Work Out"))
//            categoryUseCases.createUpdateCategory.invoke(Category(catName = "Life"))
//            categoryUseCases.createUpdateCategory.invoke(Category(catName = "Family"))
//            categoryUseCases.createUpdateCategory.invoke(Category(catName = "Work"))
//            noteUseCases.createUpdateNote.invoke(Note( noteTitle = "What lorem ipsum text is?", noteContent = "Hello everyone this is " +
//                    "my first note, happy to meet you all, Thank you. In publishing and graphic design, Lorem ipsum is " +
//                    "a placeholder text commonly used to demonstr " +
//                    "ate the visual form of a document or a typeface", creationTime = 1677658911, noteColor = Note.noteColors.random().toArgb(),
//                isPinned = true, noteCategory = 1, noteCategoryName = "Workout"))
//            noteUseCases.createUpdateNote.invoke(Note( noteTitle = "Some comment from Facebook", noteContent = "Last year, I got 5 internship interviews in 1 week through Linkedin. Of course. it is quite an unintuitive website, and it can take years to get better at it, but at least in Denmark it is very useful. It might be different in other job markets though.",
//                creationTime = 1677658911, noteColor = Note.noteColors.random().toArgb(),
//                isPinned = true, noteCategory = 1, noteCategoryName = "Workout"))
//            noteUseCases.createUpdateNote.invoke(Note( noteTitle = "Referencing complex data using Room", noteContent = "Room provides functionality for converting between primitive and boxed types but doesn't allow for object references between entities. This document explains how to use type converters and why Room doesn't support object references.",
//                creationTime = 1677658911, noteColor = Note.noteColors.random().toArgb(),
//                isPinned = true, noteCategory = 2, noteCategoryName = "Life"))
//            noteUseCases.createUpdateNote.invoke(Note( noteTitle = "App install location", noteContent = "Beginning with API Level 8, you can allow your application to be installed on the external storage (for example, the device's SD card). This is an optional feature you can declare for your application with the android:installLocation manifest attribute. If you do not declare this attribute, your application will be installed on the internal storage only and it cannot be moved to the external storage.",
//                creationTime = 1677658911, noteColor = Note.noteColors.random().toArgb(),
//                isPinned = true, noteCategory = 4, noteCategoryName = "Work"))
//
//        }

    }

    override fun onCleared() {
        getNotesJob?.cancel()
        getCategoriesJob?.cancel()
        super.onCleared()
    }

    fun onEvent(homeEvents: HomeEvents) {
        when (homeEvents) {
            is HomeEvents.PinUnpinNote -> pinUnpinNote(homeEvents.note)
            is HomeEvents.SelectCategory -> selectCategory(homeEvents.category)
            is HomeEvents.CreateCategory -> createCategory(homeEvents.category)
        }
    }

    private fun getNotesList() {
        getNotesJob?.cancel()
        getNotesJob = viewModelScope.launch {
            noteUseCases.getAllNotes()
                .collect { notes ->
                    _notesState.value = notesState.value.copy(
                        listOfNotes = notes
                    )
                }
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

    private fun getNotesForCategory(catId: Int) {
        getNotesJob?.cancel()
        getNotesJob = viewModelScope.launch {
            noteUseCases.getNotesForCategory(catId = catId)
                .collect { notes ->
                    _notesState.value = notesState.value.copy(
                        listOfNotes = notes
                    )
                }
        }
    }

    private fun selectCategory(category: Category) {
        _selectedCategoryState.value = selectedCategoryState.value.copy(
            selectedCategory = category
        )
        if (category.catId == -1) {
            getNotesList()
        } else {
            getNotesForCategory(category.catId!!)
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


//  prepopulate some data
//        viewModelScope.launch {
//            categoryUseCases.createUpdateCategory.invoke(Category(catName = "Work Out"))
//            categoryUseCases.createUpdateCategory.invoke(Category(catName = "Life"))
//            categoryUseCases.createUpdateCategory.invoke(Category(catName = "Family"))
//            categoryUseCases.createUpdateCategory.invoke(Category(catName = "Work"))
//            noteUseCases.createUpdateNote.invoke(Note( noteTitle = "What lorem ipsum text is?", noteContent = "Hello everyone this is " +
//                    "my first note, happy to meet you all, Thank you. In publishing and graphic design, Lorem ipsum is " +
//                    "a placeholder text commonly used to demonstr " +
//                    "ate the visual form of a document or a typeface", creationTime = 1677658911, noteColor = Note.noteColors.random().toArgb(),
//                isPinned = true, noteCategory = 1, noteCategoryName = "Workout"))
//            noteUseCases.createUpdateNote.invoke(Note( noteTitle = "Some comment from Facebook", noteContent = "Last year, I got 5 internship interviews in 1 week through Linkedin. Of course. it is quite an unintuitive website, and it can take years to get better at it, but at least in Denmark it is very useful. It might be different in other job markets though.",
//                creationTime = 1677658911, noteColor = Note.noteColors.random().toArgb(),
//                isPinned = true, noteCategory = 1, noteCategoryName = "Workout"))
//            noteUseCases.createUpdateNote.invoke(Note( noteTitle = "Referencing complex data using Room", noteContent = "Room provides functionality for converting between primitive and boxed types but doesn't allow for object references between entities. This document explains how to use type converters and why Room doesn't support object references.",
//                creationTime = 1677658911, noteColor = Note.noteColors.random().toArgb(),
//                isPinned = true, noteCategory = 2, noteCategoryName = "Life"))
//            noteUseCases.createUpdateNote.invoke(Note( noteTitle = "App install location", noteContent = "Beginning with API Level 8, you can allow your application to be installed on the external storage (for example, the device's SD card). This is an optional feature you can declare for your application with the android:installLocation manifest attribute. If you do not declare this attribute, your application will be installed on the internal storage only and it cannot be moved to the external storage.",
//                creationTime = 1677658911, noteColor = Note.noteColors.random().toArgb(),
//                isPinned = true, noteCategory = 4, noteCategoryName = "Work"))
//
//        }
