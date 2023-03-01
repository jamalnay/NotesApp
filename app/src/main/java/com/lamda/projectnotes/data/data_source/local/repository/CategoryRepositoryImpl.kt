package com.lamda.projectnotes.data.data_source.local.repository

import com.lamda.projectnotes.data.data_source.local.dao.CategoryDAO
import com.lamda.projectnotes.data.data_source.local.Model.Category
import com.lamda.projectnotes.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(private val categoryDao: CategoryDAO):CategoryRepository {
    override fun getAllCategories(): Flow<List<Category>> {
        return categoryDao.getAllCategories()
    }

    override suspend fun insertCategory(category: Category) {
        categoryDao.insertCategory(category)
    }

    override suspend fun deleteCategory(category: Category) {
        categoryDao.deleteCategory(category)
    }
}