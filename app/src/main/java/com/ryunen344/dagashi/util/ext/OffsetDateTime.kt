package com.ryunen344.dagashi.util.ext

import org.threeten.bp.OffsetDateTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.temporal.ChronoUnit
import timber.log.Timber

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
