package com.lamda.projectnotes.ui


import com.lamda.projectnotes.data.data_source.local.model.Category
import com.lamda.projectnotes.data.data_source.local.model.Note

class AppStates {
    data class NoteUiState(
        val note: Note = Note(
            noteId = 0,
            noteTitle = "",
            noteContent = "",
            creationTime = System.currentTimeMillis() / 1000,
            noteColor = 1,
            isPinned = false,
            isDeleted = false,
            categoryName = "",
            noteCategory = 0
        )
    )

    data class NotesState(
        val listOfNotes: List<Note>,
    )

    data class DeletedNotesState(
        val listOfNotes: List<Note>,
    )

    data class CategoriesState(
        val listOfCategories: List<Category>,
    )

    data class SelectedCategoryState(
        val category: Category = Category(
            0,"",0
        )
    )

    data class TextFieldsState(
        val text:String = "",
        )




}

