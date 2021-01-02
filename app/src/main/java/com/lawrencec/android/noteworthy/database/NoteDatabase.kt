package com.lawrencec.android.noteworthy.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lawrencec.android.noteworthy.Note

//Represents a database in the app.
//The entities denote the classes that will be used when creating/managing tables in the database.
@Database(entities = arrayOf(Note::class), version = 1)
@TypeConverters(NoteTypeConverters::class)
abstract class NoteDatabase : RoomDatabase() {

    //Register an instance of the DAO, allowing the usage of the functions in the DAO.
    abstract fun noteDao(): NoteDao
}