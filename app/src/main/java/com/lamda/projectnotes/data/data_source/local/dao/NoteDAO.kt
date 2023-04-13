package com.lamda.projectnotes.data.data_source.local.dao

import androidx.room.*
import com.lamda.projectnotes.data.data_source.local.model.Category
import com.lamda.projectnotes.data.data_source.local.model.Note
import kotlinx.coroutines.flow.Flow


@Dao
interface NoteDAO {

    @Query(
            """
            SELECT * FROM note 
            JOIN(SELECT cat_id,cat_name FROM category) category 
            ON note.cat_id = category.cat_id 
            WHERE is_deleted = FALSE
            ORDER BY NOT is_pinned,creation_time DESC
            """
    ) //TODO() solve the ordering problem
    fun getAllNotes(): Flow<List<Note>>

    @Query(
        """
            SELECT * FROM note 
            JOIN(SELECT cat_id,cat_name FROM category) category 
            ON note.cat_id = category.cat_id 
            WHERE is_deleted = FALSE AND is_pinned = TRUE
            ORDER BY creation_time DESC
            """
    ) //TODO() solve the ordering problem
    fun getPinnedNotes(): Flow<List<Note>>

    @Query(
            """
            SELECT * FROM note 
            JOIN(SELECT cat_id,cat_name FROM category) category 
            ON note.cat_id = category.cat_id  
            WHERE is_deleted = TRUE 
            ORDER BY NOT is_pinned,creation_time DESC
            """
    ) //TODO() solve the ordering problem
    fun getDeletedNotes(): Flow<List<Note>>

    //Grab notes of selected categories
    @Query(
            """
            SELECT * FROM note
            JOIN(SELECT cat_id,cat_name FROM category) category 
            ON note.cat_id = category.cat_id  
            WHERE note.cat_id = :cat_id AND is_deleted = FALSE AND is_pinned = FALSE
            ORDER BY NOT is_pinned,creation_time DESC
            """
    )
    fun getNotesForCategory(cat_id: Int): Flow<List<Note>>

    @Query(
            """
            SELECT * FROM note
            INNER JOIN(SELECT cat_id,cat_name FROM category) 
            category ON note.cat_id = category.cat_id 
            WHERE note_id = :noteId
            """
    )
    suspend fun getNote(noteId: Int): Note


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)
}
