package com.lamda.projectnotes.data.data_source.local.repository

import com.lamda.projectnotes.data.data_source.local.model.Note
import com.lamda.projectnotes.data.data_source.local.dao.NoteDAO
import com.lamda.projectnotes.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(private val noteDao: NoteDAO) : NoteRepository {
    override fun getAllNotes(): Flow<List<Note>> {
        return noteDao.getAllNotes()
    }

    override fun getNotesForCategory(catId: Int): Flow<List<Note>> {
        return noteDao.getNotesForCategory(catId)
    }

    override suspend fun getNote(noteId: Int): Note {
        return noteDao.getNote(noteId)
    }

    override suspend fun insertNote(note: Note) {
        noteDao.insertNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(note)
    }

}