package com.lamda.projectnotes.ui.category

import com.lamda.projectnotes.data.data_source.local.model.Category
import com.lamda.projectnotes.data.data_source.local.model.Note
import com.lamda.projectnotes.ui.home.HomeEvents

sealed class CategoryEvents {
    data class CreateCategory(val catName: String) : CategoryEvents()
    data class DeleteCategory(val category: Category) : CategoryEvents()
    data class RenameCategory(val category: Category) : CategoryEvents()
}