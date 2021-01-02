package com.lawrencec.android.noteworthy

import android.app.Application

//Application subclass.
//Initializes the database as soon as the application is launched.
class NoteworthyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        NoteRepository.initialize(this)
    }
}