package com.muhammedesadcomert.notes.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.muhammedesadcomert.notes.ui.note.model.Note

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("SELECT * from note WHERE id = :id")
    fun getNote(id: Int): Note

    @Query("SELECT * from note ORDER BY title ASC")
    fun getNotes(): LiveData<List<Note>>
}