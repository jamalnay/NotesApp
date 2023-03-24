package com.lamda.projectnotes.data.data_source.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lamda.projectnotes.ui.theme.*


@Entity(tableName = "note")
data class Note(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "note_id")
    val noteId: Int = 0, // must be 0 by default ??

    @ColumnInfo(name = "note_title")
    val noteTitle: String,

    @ColumnInfo(name = "note_content")
    val noteContent: String,

    @ColumnInfo(name = "creation_time")
    val creationTime: Long, //timestamp

    @ColumnInfo(name = "note_color")
    val noteColor: Int,

    @ColumnInfo(name = "is_pinned")
    val isPinned: Boolean,

    @ColumnInfo(name = "note_cat_id")
    val noteCategory: Int,

    @ColumnInfo(name = "note_cat_name") //a quick fix to show the right category for each note, this is not an optimal solution
    val noteCategoryName: String,

    @ColumnInfo(name = "is_deleted")
    val isDeleted: Boolean = false,
) {
    companion object {
        val noteColors =
            listOf(White80, Orange80, Aqua80, Turquoise80, Red80, Rose80, Lime80, Grey80)
    }
}
