package com.lamda.projectnotes.ui.create_update

import androidx.compose.ui.focus.FocusState
import com.lamda.projectnotes.data.data_source.local.model.Category

sealed class CreateUpdateEvents {
    data class SelectCategory(val category:Category): CreateUpdateEvents()
    data class EnteredTitle(val value: String): CreateUpdateEvents()
    data class EnteredContent(val value: String): CreateUpdateEvents()
    data class SaveNote(
        val isPinned: Boolean,
        val title: String,
        val content: String,
        val categoryId: Int,
        val categoryName: String,
    ) : CreateUpdateEvents()
}