package com.lamda.projectnotes.presentation.home

import com.lamda.projectnotes.data.data_source.local.Model.Category
import com.lamda.projectnotes.data.data_source.local.Model.Note

sealed class HomeEvents {
    data class PinUnpinNote(val note:Note):HomeEvents()
    data class SelectCategory(val category:Category):HomeEvents()
    data class CreateCategory(val category: Category):HomeEvents()
    data class ManageNote(val note: Note):HomeEvents()
}

