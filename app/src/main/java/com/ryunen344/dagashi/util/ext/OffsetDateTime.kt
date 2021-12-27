package com.ryunen344.dagashi.util.ext

import timber.log.Timber
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

inline val OffsetDateTime?.isPassedDay: Boolean
    get() {
        this ?: return true
        return this < OffsetDateTime.now().truncatedTo(ChronoUnit.DAYS)
    }

fun OffsetDateTime.format(): String {
    return DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(this)
}

fun String.parse(): OffsetDateTime? {
    var result: OffsetDateTime? = null
    runCatching {
        OffsetDateTime.parse(this, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
    }.onSuccess {
        result = it
    }.onFailure {
        Timber.e(it)
    }
    return result
}
