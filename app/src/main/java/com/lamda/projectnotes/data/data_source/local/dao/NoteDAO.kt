package com.lamda.projectnotes.data.data_source.local.dao

import androidx.room.*
import com.lamda.projectnotes.data.data_source.local.model.Note
import kotlinx.coroutines.flow.Flow


@Dao
interface NoteDAO {

    @Query("SELECT * FROM note ORDER BY NOT is_pinned,creation_time DESC") //TODO() solve the ordering problem
    fun getAllNotes(): Flow<List<Note>>

    //Grab notes of selected categories
    @Transaction
    @Query("SELECT * FROM note WHERE note_cat_id = :cat_id ORDER BY NOT is_pinned,creation_time DESC")
    fun getNotesForCategory(cat_id: Int): Flow<List<Note>>

    @Query("SELECT * FROM note WHERE note_id = :noteId")
    suspend fun getNote(noteId: Int): Note

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)
}
