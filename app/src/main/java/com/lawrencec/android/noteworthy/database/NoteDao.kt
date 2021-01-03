package com.lawrencec.android.noteworthy.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.lawrencec.android.noteworthy.Note
import java.util.*

//Database Access Object class. Contains the methods used to interact with the database.
@Dao
interface NoteDao {

    //Use LiveData because Room does not allow database access on the main thread. This tells
    //the application to do it on a background thread instead.
    @Query("SELECT * FROM note")
    fun getNotes() : LiveData<List<Note>>

    @Query("SELECT * FROM note WHERE id=(:id)")
    fun getNoteById(id: UUID): LiveData<Note?>

    @Insert
    fun addNote(note: Note)

    @Query("DELETE FROM note")
    fun deleteAllNotes()
}