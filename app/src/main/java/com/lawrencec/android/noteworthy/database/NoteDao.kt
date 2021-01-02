package com.lawrencec.android.noteworthy.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.lawrencec.android.noteworthy.Note
import java.util.*

//Database Access Object class. Contains the methods used to interact with the database.
@Dao
interface NoteDao {

    @Query("SELECT * FROM note")
    fun getNotes() : List<Note>

    @Query("SELECT * FROM note WHERE id=(:id)")
    fun getNoteById(id: UUID): Note?

    @Insert
    fun addNote(note: Note)

    @Query("DELETE FROM note")
    fun deleteAllNotes()
}