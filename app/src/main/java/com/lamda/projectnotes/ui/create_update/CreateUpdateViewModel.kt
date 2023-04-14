package com.lamda.projectnotes.ui.create_update

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
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
class CreateUpdateViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    private val categoryUseCases: CategoryUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _categoriesState = mutableStateOf(CategoriesState(emptyList()))
    val categoriesState: State<CategoriesState> = _categoriesState

    private val _selectedCategoryState = mutableStateOf(SelectedCategoryState())
    val selectedCategoryState: State<SelectedCategoryState> = _selectedCategoryState


    private val _noteTitle = mutableStateOf(TextFieldsState())
    val noteTitle: State<TextFieldsState> = _noteTitle

    private val _noteContent = mutableStateOf(TextFieldsState())
    val noteContent: State<TextFieldsState> = _noteContent

    private var getCategoriesJob: Job? = null
    private var saveNoteJob: Job? = null

    private var notesCount = mutableStateOf(0)
    var currentNoteId = savedStateHandle.get<Int>("noteId")?: 0

    init {
        if(currentNoteId != 0) {
                viewModelScope.launch {
                    noteUseCases.getNote(currentNoteId).also { note ->
                        currentNoteId = note.noteId
                        _noteTitle.value = noteTitle.value.copy(
                            text = note.noteTitle,
                        )
                        _noteContent.value = _noteContent.value.copy(
                            text = note.noteContent,
                        )
                        _selectedCategoryState.value = selectedCategoryState.value.copy(
                            category = Category(note.noteCategory,note.categoryName,0)
                        )
                    }

                }
            }

        viewModelScope.launch {
            try {
                getCategoriesList()
            } catch (_: Exception) {

            }
        }
    }

    override fun onCleared() {
        getCategoriesJob?.cancel()
        saveNoteJob?.cancel()
        super.onCleared()
    }


    fun onEvent(event: CreateUpdateEvents) {
        when (event) {
            is CreateUpdateEvents.SaveNote -> saveNote(
                noteId = currentNoteId,
                isPinned = event.isPinned,
                title = event.title,
                content = event.content,
                categoryId = event.categoryId,
                categoryName = event.categoryName
            )
            is CreateUpdateEvents.EnteredContent -> {
                _noteContent.value = _noteContent.value.copy(
                    text = event.value
                )
            }
            is CreateUpdateEvents.EnteredTitle -> {
                _noteTitle.value = noteTitle.value.copy(
                    text = event.value
                )
            }
            is CreateUpdateEvents.SelectCategory -> {
                _selectedCategoryState.value = selectedCategoryState.value.copy(
                    category = event.category
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


    private fun saveNote(
        isPinned: Boolean,
        title: String,
        content: String,
        categoryId: Int,
        categoryName: String,
        noteId:Int
    ) {
        saveNoteJob?.cancel()
        saveNoteJob = viewModelScope.launch {

            if (currentNoteId != 0) {
                //updating note
                noteUseCases.createUpdateNote.invoke(
                    Note(
                        noteId = noteId,
                        noteTitle = title,
                        noteContent = content,
                        noteCategory = categoryId,
                        isPinned = isPinned,
                        noteColor = 1,
                        creationTime = System.currentTimeMillis() / 1000,
                        categoryName = categoryName
                    )
                )
            }
            else {
                //else create new note
                noteUseCases.createUpdateNote.invoke(
                    Note(
                        noteTitle = title,
                        noteContent = content,
                        noteCategory = categoryId,
                        isPinned = isPinned,
                        noteColor = 1,
                        creationTime = System.currentTimeMillis() / 1000,
                        categoryName = categoryName
                    )
                )
                notesCount.value = categoryUseCases.getCatById(categoryId).notesCount +1
                categoryUseCases.createUpdateCategory(Category(
                    categoryId,categoryName,notesCount.value
                ))
            }

        }


    }

}

//viewModelScope.launch {
//    notesCount.value = categoryUseCases.getCatById(categoryId).notesCount +1
//}
//viewModelScope.launch {
//    categoryUseCases.createUpdateCategory(Category(
//        categoryId,categoryName,notesCount.value
//    ))
//}