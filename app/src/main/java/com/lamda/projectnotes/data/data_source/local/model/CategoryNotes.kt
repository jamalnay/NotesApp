package com.lamda.projectnotes.data.data_source.local.model

import androidx.room.Embedded
import androidx.room.Relation


data class CategoryWithNotes(
    @Embedded
    val category: Category,

    @Relation(
        parentColumn = "cat_id",
        entityColumn = "note_id",
    )
    val notes: List<Note>,
)
