package com.lawrencec.android.noteworthy

import androidx.lifecycle.ViewModel

//ViewModel to hold repository functions that need to be accessed within CalendarFragment.
class CalendarViewModel : ViewModel() {
    private val noteRepository = NoteRepository.get()

    //For now, simply fetch all notes. Eventually, update this to fetch based on the selected
    //date in the CalendarFragment.
    val noteListLiveData = noteRepository.getNotes()

}