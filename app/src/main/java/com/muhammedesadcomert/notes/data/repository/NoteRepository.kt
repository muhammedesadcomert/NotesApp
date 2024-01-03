package com.muhammedesadcomert.notes.data.repository

import com.muhammedesadcomert.notes.data.local.NoteDao
import com.muhammedesadcomert.notes.data.local.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NoteRepository @Inject constructor(private val noteDao: NoteDao) {
    suspend fun insert(note: Note) = withContext(Dispatchers.IO) {
        noteDao.insert(note)
    }

    suspend fun update(note: Note) = withContext(Dispatchers.IO) {
        noteDao.update(note)
    }

    suspend fun delete(note: Note) = withContext(Dispatchers.IO) {
        noteDao.delete(note)
    }

    suspend fun getNote(id: Int): Flow<Note> = withContext(Dispatchers.IO) {
        noteDao.getNote(id)
    }

    fun getNotes(): Flow<List<Note>> = noteDao.getNotes()
}