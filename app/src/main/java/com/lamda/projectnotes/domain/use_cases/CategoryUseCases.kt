package com.lamda.projectnotes.domain.use_cases

import com.lamda.projectnotes.domain.use_cases.category_use_cases.*

data class CategoryUseCases(
    val createUpdateCategory: CreateUpdateCategory,
    val deleteCategory: DeleteCategory,
    val getAllCategories: GetAllCategories,
    val getCatById: GetCatById,
    val getNotesCountForCategory: GetNotesCountForCategory
)
