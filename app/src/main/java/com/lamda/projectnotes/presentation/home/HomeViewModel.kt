package com.lamda.projectnotes.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lamda.projectnotes.data.data_source.local.Model.Category
import com.lamda.projectnotes.data.data_source.local.Model.Note
import com.lamda.projectnotes.domain.use_cases.CategoryUseCases
import com.lamda.projectnotes.domain.use_cases.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    private val categoryUseCases: CategoryUseCases
    ):ViewModel()
{
    private val _notesState = mutableStateOf(NotesState(emptyList()))
    val notesState: State<NotesState> = _notesState

    private val _categoriesState = mutableStateOf(CategoriesState(emptyList()))
    val categoriesState: State<CategoriesState> = _categoriesState

    private val _selectedCategoryState = mutableStateOf(SelectedCategoryState(Category(-1,"All")))
    val selectedCategoryState: State<SelectedCategoryState> = _selectedCategoryState




    //Assign jobs to stop calling coroutine flow jobs multiple times
    private var getNotesJob: Job? = null
    private var getCategoriesJob: Job? = null

    init {
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

        //initially create "All" category if it does not exist
        viewModelScope.launch { categoryUseCases.createUpdateCategory(Category(-1,"All")) }
        getCategoriesList()
        getNotesList()
    }




    fun onEvent(homeEvents: HomeEvents){

        when(homeEvents){
            is HomeEvents.PinUnpinNote -> {
                val pinUnpin : Boolean = !homeEvents.note.isPinned
                val updatedNote = Note(
                    homeEvents.note.noteId,
                    homeEvents.note.noteTitle,
                    homeEvents.note.noteContent,
                    homeEvents.note.creationTime,
                    homeEvents.note.noteColor,
                    pinUnpin,
                    homeEvents.note.noteCategory,
                    homeEvents.note.noteCategoryName
                )
                viewModelScope.launch { noteUseCases.createUpdateNote(updatedNote) }
            }
            is HomeEvents.SelectCategory -> {

                if (homeEvents.category.catId == -1){ //Show all notes if category is 0
                    _selectedCategoryState.value = selectedCategoryState.value.copy(
                        selectedCategory = Category(-1,"All")
                    )
                    getNotesList()
                }

                else{
                    _selectedCategoryState.value = selectedCategoryState.value.copy(
                        selectedCategory = homeEvents.category
                    )
                    getNotesForCategory(selectedCategoryState.value.selectedCategory.catId)
                }
            }
            is HomeEvents.CreateCategory -> {
                viewModelScope.launch { categoryUseCases.createUpdateCategory.invoke(homeEvents.category) }
            }
            is HomeEvents.ManageNote -> {
                //i was going to use Bottom Sheet to manage notes from the home screen, i might consider doing it with an AlertDialogue
                //managing includes:
                // Editing: take the use to the edit note Screen
                //Obscure: this suppose to blur the note so no one can see it, to unblure use must provide some kind of a password
                //Share: this will allow sharing note to other apps
                //Move to trash: this will move note to Trash before permanent deletion
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

    private fun getCategoryForNote(note: Note){
        viewModelScope.launch {

        }
    }
}