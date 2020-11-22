package com.ryunen344.dagashi.data.db.converter

import androidx.room.TypeConverter
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.format.DateTimeFormatter

object OffsetDateTimeConverter {
    @TypeConverter
    fun fromString(value: String?): OffsetDateTime? {
        return value?.let {
            OffsetDateTime.parse(it, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
        }
    }

    @TypeConverter
    fun toString(value: OffsetDateTime?): String? {
        return value?.let {
            DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(it)
        }
    }
}
