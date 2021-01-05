package com.lawrencec.android.noteworthy

import androidx.lifecycle.ViewModel
import java.util.*

class NotePreviewViewModel : ViewModel() {
    private val noteRepository = NoteRepository.get()

    //Delete an individual note.
    fun deleteNoteById(id: UUID) {
        noteRepository.deleteNoteById(id)
    }
}