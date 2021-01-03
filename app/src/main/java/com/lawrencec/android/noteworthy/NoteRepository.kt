package com.lawrencec.android.noteworthy

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.lawrencec.android.noteworthy.database.NoteDatabase
import java.lang.IllegalStateException
import java.util.*
import java.util.concurrent.Executors

private const val DATABASE_NAME = "note-database"

//Repository class. Determines how to store/retrieve data.
//Is a singleton; only one instance should be active at all times.
class NoteRepository private constructor(context: Context) {

    private val database: NoteDatabase = Room.databaseBuilder(
        context.applicationContext,
        NoteDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val noteDao = database.noteDao()
    private val executor = Executors.newSingleThreadExecutor()

    fun getNotes(): LiveData<List<Note>> = noteDao.getNotes()
    fun getNoteById(id: UUID): LiveData<Note?> = noteDao.getNoteById(id)
    fun addNote(note: Note) {
        executor.execute {
            noteDao.addNote(note)
        }
    }
    fun deleteAllNotes() {
        executor.execute {
            noteDao.deleteAllNotes()
        }
    }

    companion object {
        private var INSTANCE: NoteRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = NoteRepository(context)
            }
        }

        fun get(): NoteRepository {
            return INSTANCE ?: throw IllegalStateException("NoteRepository not initialized")
        }
    }
}