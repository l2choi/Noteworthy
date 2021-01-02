package com.lawrencec.android.noteworthy.database

import androidx.room.TypeConverter
import java.util.*

//Stores TypeConverters to convert objects into a form that can be stored by Room.
class NoteTypeConverters {
    @TypeConverter
    fun fromDate(date: Date?) : Long? {
        return date?.time
    }

    @TypeConverter
    fun toDate(millisSinceEpoch: Long?) : Date? {
        return millisSinceEpoch?.let {
            Date(it)
        }
    }

    @TypeConverter
    fun toUUID(uuid: String?): UUID? {
        return UUID.fromString(uuid)
    }

    @TypeConverter
    fun fromUUID(uuid: UUID?): String? {
        return uuid?.toString()
    }
}