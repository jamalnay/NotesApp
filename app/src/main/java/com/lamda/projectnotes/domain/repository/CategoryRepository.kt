package com.lamda.projectnotes.domain.repository

import com.lamda.projectnotes.data.data_source.local.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    fun getAllCategories(): Flow<List<Category>>
    suspend fun getCatById(catId: Int): Category
    suspend fun insertCategory(category: Category)
    suspend fun deleteCategory(category: Category)
}