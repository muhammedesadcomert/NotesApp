package com.muhammedesadcomert.notes.ui.note

import androidx.lifecycle.*
import com.muhammedesadcomert.notes.data.local.NoteDao
import com.muhammedesadcomert.notes.data.model.Note
import kotlinx.coroutines.launch

class NoteViewModel(private val noteDao: NoteDao) : ViewModel() {

    val allNotes: LiveData<List<Note>> = noteDao.getNotes().asLiveData()

    private fun insertNote(note: Note) {
        viewModelScope.launch {
            noteDao.insert(note)
        }
    }

    private fun getNewNoteEntry(noteTitle: String, noteText: String): Note {
        return Note(
            title = noteTitle,
            text = noteText
        )
    }

    fun addNewNote(noteTitle: String, noteText: String) {
        val newNote = getNewNoteEntry(noteTitle, noteText)
        insertNote(newNote)
    }

    private fun updateNote(note: Note) {
        viewModelScope.launch {
            noteDao.update(note)
        }
    }

    private fun getUpdatedNoteEntry(noteId: Int, noteTitle: String, noteText: String): Note {
        return Note(
            id = noteId,
            title = noteTitle,
            text = noteText
        )
    }

    fun updateNote(noteId: Int, noteTitle: String, noteText: String) {
        val updatedNote = getUpdatedNoteEntry(noteId, noteTitle, noteText)
        updateNote(updatedNote)
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            noteDao.delete(note)
        }
    }

    fun retrieveNote(id: Int): LiveData<Note> {
        return noteDao.getNote(id).asLiveData()
    }

    fun isEntryValid(noteTitle: String, noteText: String): Boolean {
        if (noteTitle.isBlank() || noteText.isBlank())
            return false
        return true
    }
}

class NoteViewModelFactory(private val itemDao: NoteDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NoteViewModel(itemDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}