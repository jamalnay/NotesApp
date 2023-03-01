package com.lamda.projectnotes.data.data_source.local.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lamda.projectnotes.data.data_source.local.Model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryDAO {

    @Query("SELECT * FROM category")
    fun getAllCategories(): Flow<List<Category>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: Category)

    @Delete
    suspend  fun deleteCategory(category: Category)
}