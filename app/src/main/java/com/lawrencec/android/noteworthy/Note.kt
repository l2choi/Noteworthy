package com.lawrencec.android.noteworthy

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

//Defines the structure of the table used to store notes.
@Entity
data class Note (
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    var title: String = "",
    var date: Date = Date(),
    var contents: String = ""
)
