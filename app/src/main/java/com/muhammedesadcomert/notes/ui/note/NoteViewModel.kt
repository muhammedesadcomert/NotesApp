package com.muhammedesadcomert.notes.ui.note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhammedesadcomert.notes.data.repository.NoteRepository
import com.muhammedesadcomert.notes.ui.note.model.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(private val noteRepository: NoteRepository) : ViewModel() {

    var notes: Flow<List<Note>> = noteRepository.getNotes()

    private fun insertNote(note: Note) {
        viewModelScope.launch {
            noteRepository.insert(note)
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
            noteRepository.update(note)
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
            noteRepository.delete(note)
        }
    }

    fun retrieveNote(id: Int) = noteRepository.getNote(id)

    fun isEntryValid(noteTitle: String, noteText: String): Boolean {
        if (noteTitle.isBlank() || noteText.isBlank())
            return false
        return true
    }
}