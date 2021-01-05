package com.lawrencec.android.noteworthy

import androidx.lifecycle.ViewModel

class SearchViewModel(sT: String) : ViewModel() {
    private val noteRepository = NoteRepository.get()

    var searchTerm = sT

    val notesByLikeTitle = noteRepository.getNotesByLikeTitle(searchTerm)
}