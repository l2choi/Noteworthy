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

    //Get all notes. Note the use of LiveData because Room does not allow database access on the
    //main thread. This tells the application to do it on a background thread instead.
    @Query("SELECT * FROM note")
    fun getNotes() : LiveData<List<Note>>

    //Search for notes containing the search term.
    @Query("SELECT * FROM note WHERE title LIKE (:title)")
    fun getNotesByLikeTitle(title: String): LiveData<List<Note>>

    //Add new note.
    @Insert
    fun addNote(note: Note)

    //Delete a specific note.
    @Query("DELETE FROM NOTE WHERE id=(:id)")
    fun deleteNoteById(id: UUID)

    //Delete all notes.
    @Query("DELETE FROM note")
    fun deleteAllNotes()
}