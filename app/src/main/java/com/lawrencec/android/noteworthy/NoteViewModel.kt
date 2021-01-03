package com.lawrencec.android.noteworthy

import androidx.lifecycle.ViewModel

//ViewModel to hold repository functions that need to be accessed within NoteFragment.
class NoteViewModel : ViewModel() {

    private val noteRepository = NoteRepository.get()

    //Called by NoteFragment when saving a note.
    fun addNote(note: Note) {
        noteRepository.addNote(note)
    }

}