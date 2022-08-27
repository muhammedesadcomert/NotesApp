package com.muhammedesadcomert.notes.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.muhammedesadcomert.notes.ui.note.model.Note

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}