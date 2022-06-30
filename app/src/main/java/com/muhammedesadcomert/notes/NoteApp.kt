package com.muhammedesadcomert.notes

import android.app.Application
import com.muhammedesadcomert.notes.data.local.NoteDatabase

class NoteApp : Application() {
    val database: NoteDatabase by lazy { NoteDatabase.getDatabase(this) }
}