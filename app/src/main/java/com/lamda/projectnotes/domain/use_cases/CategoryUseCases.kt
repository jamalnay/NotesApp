package com.lamda.projectnotes.domain.use_cases

import com.lamda.projectnotes.domain.use_cases.category_use_cases.CreateUpdateCategory
import com.lamda.projectnotes.domain.use_cases.category_use_cases.DeleteCategory
import com.lamda.projectnotes.domain.use_cases.category_use_cases.GetAllCategories
import com.lamda.projectnotes.domain.use_cases.category_use_cases.GetCatById

data class CategoryUseCases(
    val createUpdateCategory: CreateUpdateCategory,
    val deleteCategory: DeleteCategory,
    val getAllCategories: GetAllCategories,
    val getCatById: GetCatById,
)
