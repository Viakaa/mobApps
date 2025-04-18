package com.example.homework3.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NotesDao {
    @Query("SELECT * FROM Notes ORDER BY timestamp DESC")
    fun getAll(): LiveData<List<Notes>>

    @Query("SELECT * FROM Notes WHERE id = :id")
    suspend fun getNoteById(id: Int): Notes

    @Insert
    suspend fun addNote(note: Notes)

    @Update
    suspend fun updateNote(note: Notes)

    @Delete
    suspend fun deleteNote(note: Notes)
}
