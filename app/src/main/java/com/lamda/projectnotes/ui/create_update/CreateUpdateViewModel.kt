package com.lamda.projectnotes.ui.create_update

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lamda.projectnotes.data.data_source.local.model.Category
import com.lamda.projectnotes.data.data_source.local.model.Note
import com.lamda.projectnotes.domain.use_cases.CategoryUseCases
import com.lamda.projectnotes.domain.use_cases.NoteUseCases
import com.lamda.projectnotes.ui.category.CategoriesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateUpdateViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    private val categoryUseCases: CategoryUseCases,
) : ViewModel() {
    private val _categoriesState = mutableStateOf(CategoriesState(emptyList()))
    val categoriesState: State<CategoriesState> = _categoriesState

    private var getCategoriesJob: Job? = null

    init {
        viewModelScope.launch {
            try {
                getCategoriesList()
            } catch (_: Exception) {

            }
        }
    }

    override fun onCleared() {
        getCategoriesJob?.cancel()
        super.onCleared()
    }


    fun onEvent(event: CreateUpdateEvents) {
        when (event) {
            is CreateUpdateEvents.SaveNote -> saveNote(
                isPinned = event.isPinned,
                title = event.title,
                content = event.content,
                categoryId = event.categoryId,
                categoryName = event.categoryName
            )
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
    ) {
        viewModelScope.launch {
            noteUseCases.createUpdateNote.invoke(
                Note(
                    noteTitle = title,
                    noteContent = content,
                    noteCategoryName = categoryName,
                    noteCategory = categoryId,
                    isPinned = isPinned,
                    noteColor = Note.noteColors.random().toArgb(),
                    creationTime = System.currentTimeMillis() / 1000
                )
            )

            //TODO() fix the category counter
            categoryUseCases.createUpdateCategory(Category(
                categoryId,categoryName,+1
            ))
        }


    }

}