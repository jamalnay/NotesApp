package com.lamda.projectnotes.data.data_source.local.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class Category(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "cat_id")
    val catId: Int = 0,

    @ColumnInfo(name = "cat_name")
    val catName: String,

    )