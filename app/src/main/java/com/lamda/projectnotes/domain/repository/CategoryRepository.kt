package com.lamda.projectnotes.domain.repository

import com.lamda.projectnotes.data.data_source.local.Model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    fun getAllCategories(): Flow<List<Category>>

    suspend fun insertCategory(category: Category)

    suspend  fun deleteCategory(category: Category)
}