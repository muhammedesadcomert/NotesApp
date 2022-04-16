package com.muhammedesadcomert.notes.data

import androidx.room.*
import com.muhammedesadcomert.notes.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("SELECT * from note WHERE id = :id")
    fun getNote(id: Int): Flow<Note>

    @Query("SELECT * from note ORDER BY title ASC")
    fun getNotes(): Flow<List<Note>>
}