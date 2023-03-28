package com.lamda.projectnotes.ui.category

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lamda.projectnotes.data.data_source.local.model.Category
import com.lamda.projectnotes.data.data_source.local.model.CategoryWithNotes
import com.lamda.projectnotes.domain.use_cases.CategoryUseCases
import com.lamda.projectnotes.domain.use_cases.NoteUseCases
import com.lamda.projectnotes.ui.home.NotesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class ManageCategoriesViewModel @Inject constructor(
    private val categoryUseCases: CategoryUseCases,
    private val noteUseCases: NoteUseCases
):ViewModel()  {


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
        getCategoriesJob?.cancel(null)
        super.onCleared()
    }

    fun onEvent(categoryEvents: CategoryEvents){
        when(categoryEvents){
            is CategoryEvents.CreateCategory -> createCategory(categoryEvents.catName)
            is CategoryEvents.DeleteCategory -> deleteCategory(categoryEvents.category)
            is CategoryEvents.RenameCategory -> renameCategory(categoryEvents.category)
        }
    }

    private fun createCategory(catName:String){
        viewModelScope.launch {
            categoryUseCases.createUpdateCategory(Category(null,catName))
        }
    }

    private fun deleteCategory(category: Category){
        viewModelScope.launch {
            categoryUseCases.deleteCategory(category = category)
        }
    }
    private fun renameCategory(category: Category){
        viewModelScope.launch {
            categoryUseCases.createUpdateCategory(category = category)
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
}