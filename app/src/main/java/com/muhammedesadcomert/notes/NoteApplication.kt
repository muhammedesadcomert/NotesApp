package com.muhammedesadcomert.notes

import android.app.Application
import com.muhammedesadcomert.notes.data.NoteRoomDatabase

class NoteApplication : Application() {
    val database: NoteRoomDatabase by lazy { NoteRoomDatabase.getDatabase(this) }
}