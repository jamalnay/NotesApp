package com.lamda.projectnotes.domain.use_cases.category_use_cases

import com.lamda.projectnotes.data.data_source.local.Model.Category
import com.lamda.projectnotes.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow

class GetAllCategories(private val repository: CategoryRepository) {
    operator fun invoke(): Flow<List<Category>> = repository.getAllCategories()
}