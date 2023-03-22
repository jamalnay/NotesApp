package com.lamda.projectnotes.ui.create_update

sealed class CreateUpdateEvents {
    data class SaveNote(
        val isPinned: Boolean,
        val title: String,
        val content: String,
        val categoryId: Int,
        val categoryName: String,
    ) : CreateUpdateEvents()
}