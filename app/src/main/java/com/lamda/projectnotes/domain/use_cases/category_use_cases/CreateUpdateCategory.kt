package com.lamda.projectnotes.domain.use_cases.category_use_cases

import com.lamda.projectnotes.data.data_source.local.model.Category
import com.lamda.projectnotes.domain.repository.CategoryRepository

class CreateUpdateCategory(private val repository: CategoryRepository) {
    suspend operator fun invoke(category: Category) = repository.insertCategory(category)
}