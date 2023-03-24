package com.lamda.projectnotes.data.data_source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.lamda.projectnotes.data.data_source.local.model.Category
import com.lamda.projectnotes.data.data_source.local.model.Note
import com.lamda.projectnotes.data.data_source.local.dao.CategoryDAO
import com.lamda.projectnotes.data.data_source.local.dao.NoteDAO

const val DATABASE_NAME = "notes_database"

@Database(
    entities = [Note::class, Category::class],
    version = 1,
    exportSchema = false
)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDAO
    abstract fun categoryDao(): CategoryDAO
    companion object {
        @Volatile
        private var instance: LocalDatabase? = null
        fun getInstance(context: Context): LocalDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }
        private fun buildDatabase(context: Context): LocalDatabase {
            return Room.databaseBuilder(context, LocalDatabase::class.java, DATABASE_NAME)
                .build()
        }
    }
}
