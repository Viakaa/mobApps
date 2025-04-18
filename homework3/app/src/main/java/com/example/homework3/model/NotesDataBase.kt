package com.example.homework3.model

import android.content.Context
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Notes::class], version = 2)
abstract class NotesDataBase : RoomDatabase() {
    abstract fun noteDao(): NotesDao

    companion object {
        private var INSTANCE: NotesDataBase? = null

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE Note ADD COLUMN timestamp INTEGER NOT NULL DEFAULT 0")
            }
        }

        fun getDatabase(context: Context): NotesDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NotesDataBase::class.java,
                    "notes_db"
                )
                    .addMigrations(MIGRATION_1_2)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
