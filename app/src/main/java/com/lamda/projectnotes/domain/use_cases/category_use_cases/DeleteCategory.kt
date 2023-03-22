package com.lamda.projectnotes.domain.use_cases.category_use_cases

import com.lamda.projectnotes.data.data_source.local.Model.Category
import com.lamda.projectnotes.domain.repository.CategoryRepository

class DeleteCategory(private val repository: CategoryRepository) {
    suspend operator fun invoke(category: Category) = repository.deleteCategory(category)
}