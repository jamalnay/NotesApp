package com.lamda.projectnotes.ui.category

import com.lamda.projectnotes.data.data_source.local.Model.Category

data class CategoriesState(
    val listOfCategories: List<Category>,
)

data class SelectedCategoryState(
    val selectedCategory: Category?,
)