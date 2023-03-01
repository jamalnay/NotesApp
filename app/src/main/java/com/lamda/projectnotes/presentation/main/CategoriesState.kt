package com.lamda.projectnotes.presentation.main

import com.lamda.projectnotes.data.data_source.local.Model.Category

data class CategoriesState(
    val listOfCategories: List<Category> = emptyList()
)
