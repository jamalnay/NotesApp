package com.lamda.projectnotes.ui.home

import com.lamda.projectnotes.data.data_source.local.model.Category
import com.lamda.projectnotes.data.data_source.local.model.Note

sealed class HomeEvents {
    data class PinUnpinNote(val note: Note) : HomeEvents()
    data class SelectCategory(val category: Category) : HomeEvents()
    data class CreateCategory(val category: Category) : HomeEvents()
}

