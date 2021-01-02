package com.lawrencec.android.noteworthy

import androidx.lifecycle.ViewModel

class CalendarViewModel : ViewModel() {
    private val noteRepository = NoteRepository.get()

    fun deleteAllNotes() {
        noteRepository.deleteAllNotes()
    }
}