package com.lamda.projectnotes.presentation.home

import com.lamda.projectnotes.data.data_source.local.Model.Category
import com.lamda.projectnotes.data.data_source.local.Model.Note

sealed class MainEvents {
    data class PinUnpinNote(val note:Note):MainEvents()
    data class SelectCategory(val category:Category):MainEvents()
}

