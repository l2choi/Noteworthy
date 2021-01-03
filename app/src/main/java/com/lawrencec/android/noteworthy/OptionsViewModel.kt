package com.lawrencec.android.noteworthy

import androidx.lifecycle.ViewModel

//ViewModel to hold repository functions that need to be accessed within OptionsFragment.
class OptionsViewModel : ViewModel() {
    private val noteRepository = NoteRepository.get()

    fun deleteAllNotes() {
        noteRepository.deleteAllNotes()
    }
}