package com.muhammedesadcomert.notes.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.muhammedesadcomert.notes.data.local.model.Note

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}