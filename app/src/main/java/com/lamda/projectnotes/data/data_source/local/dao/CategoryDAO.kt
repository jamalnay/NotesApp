package com.lamda.projectnotes.data.data_source.local.dao

import androidx.room.*
import com.lamda.projectnotes.data.data_source.local.model.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDAO {

    @Query("SELECT * FROM category")
    fun getAllCategories(): Flow<List<Category>>

    @Query("SELECT * FROM category where cat_id = :catId")
    suspend fun getCatById(catId: Int): Category

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: Category)

    @Delete
    suspend fun deleteCategory(category: Category)
}