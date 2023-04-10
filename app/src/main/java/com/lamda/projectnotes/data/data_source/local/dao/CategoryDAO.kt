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

    @Query(
        """
            SELECT COUNT(note_id) AS notes_count FROM note 
            WHERE cat_id = :category
        """
    )
    suspend fun getNotesCountForCategory(category: Int): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: Category)

    @Delete
    suspend fun deleteCategory(category: Category)
}