package com.example.homework3.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework3.model.Notes
import com.example.homework3.model.NotesDataBase
import com.example.homework3.model.NotesRepo
import kotlinx.coroutines.launch

class NotesViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: NotesRepo

    val allNotes = NotesDataBase.Companion.getDatabase(application).noteDao().let {
        repository = NotesRepo(it)
        repository.allNotes
    }

    fun addNote(note: Notes) = viewModelScope.launch {
        repository.addNote(note)
    }

    fun updateNote(note: Notes) = viewModelScope.launch {
        repository.updateNote(note)
    }

    fun deleteNote(note: Notes) = viewModelScope.launch {
        repository.deleteNote(note)
    }

    suspend fun getNoteById(id: Int): Notes {
        return repository.getNoteById(id)
    }


}