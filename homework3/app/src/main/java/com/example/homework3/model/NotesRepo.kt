package com.example.homework3.model

class NotesRepo(private val noteDao: NotesDao) {

    val allNotes = noteDao.getAll()

    suspend fun addNote(note: Notes) = noteDao.addNote(note )

    suspend fun deleteNote(note: Notes) = noteDao.deleteNote(note)

    suspend fun updateNote(note: Notes) = noteDao.updateNote(note)

    suspend fun getNoteById(id: Int): Notes = noteDao.getNoteById(id)
}