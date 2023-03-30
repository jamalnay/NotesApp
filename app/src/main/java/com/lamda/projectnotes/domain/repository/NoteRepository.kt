package com.lamda.projectnotes.domain.repository


import com.lamda.projectnotes.data.data_source.local.model.Category
import com.lamda.projectnotes.data.data_source.local.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    fun getAllNotes(): Flow<List<Note>>

    fun getDeletedNotes(): Flow<List<Note>>

    fun getNotesForCategory(catId: Int): Flow<List<Note>>

    suspend fun getNote(noteId: Int): Note

    suspend fun insertNote(note: Note)

    suspend fun deleteNote(note: Note)

}