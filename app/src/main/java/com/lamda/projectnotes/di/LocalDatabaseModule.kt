package com.lamda.projectnotes.di

import android.content.Context
import com.lamda.projectnotes.data.data_source.local.LocalDatabase
import com.lamda.projectnotes.data.data_source.local.repository.CategoryRepositoryImpl
import com.lamda.projectnotes.data.data_source.local.repository.NoteRepositoryImpl
import com.lamda.projectnotes.domain.repository.CategoryRepository
import com.lamda.projectnotes.domain.repository.NoteRepository
import com.lamda.projectnotes.domain.use_cases.CategoryUseCases
import com.lamda.projectnotes.domain.use_cases.NoteUseCases
import com.lamda.projectnotes.domain.use_cases.category_use_cases.CreateUpdateCategory
import com.lamda.projectnotes.domain.use_cases.category_use_cases.DeleteCategory
import com.lamda.projectnotes.domain.use_cases.category_use_cases.GetAllCategories
import com.lamda.projectnotes.domain.use_cases.category_use_cases.GetCatById
import com.lamda.projectnotes.domain.use_cases.note_use_cases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDatabaseModule {

    @Singleton
    @Provides
    fun provideLocalDatabase(@ApplicationContext context: Context): LocalDatabase {
        return LocalDatabase.getInstance(context)
    }


    @Singleton
    @Provides
    fun provideNoteRepository(database: LocalDatabase): NoteRepository {
        return NoteRepositoryImpl(database.noteDao())
    }

    @Singleton
    @Provides
    fun provideCategoryRepository(database: LocalDatabase): CategoryRepository {
        return CategoryRepositoryImpl(database.categoryDao())
    }


    @Singleton
    @Provides
    fun provideNoteUseCases(repository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            createUpdateNote = CreateUpdateNote(repository),
            deleteNote = DeleteNote(repository),
            getAllNotes = GetAllNotes(repository),
            getDeletedNotes = GetDeletedNotes(repository),
            getNotesForCategory = GetNotesForCategory(repository),
            getNote = GetNote(repository)
        )
    }

    @Singleton
    @Provides
    fun provideCategoryUseCases(repository: CategoryRepository): CategoryUseCases {
        return CategoryUseCases(
            createUpdateCategory = CreateUpdateCategory(repository),
            deleteCategory = DeleteCategory(repository),
            getAllCategories = GetAllCategories(repository),
            getCatById = GetCatById(repository)
        )
    }


}