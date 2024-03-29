package com.lamda.projectnotes.data.data_source.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class Category(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "cat_id")
    val catId: Int?,

    @ColumnInfo(name = "cat_name")
    val catName: String,

    @ColumnInfo(name = "notes_count")
    var notesCount:Int = 0

    )