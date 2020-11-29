package com.ryunen344.dagashi.data.db.converter

import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Test
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.ZoneOffset
import org.threeten.bp.format.DateTimeParseException

class OffsetDateTimeConverterTest {

    private val target = OffsetDateTime.of(2020, 11, 29, 6, 30, 0, 0, ZoneOffset.UTC)

    private val targetString = "2020-11-29T06:30:00Z"

    @Test
    fun fromStringSuccess() {
        val fromConverter = OffsetDateTimeConverter.fromString(targetString)
        MatcherAssert.assertThat(fromConverter, CoreMatchers.equalTo(target))
    }

    @Test
    fun fromStringInputNull() {
        val fromConverter = OffsetDateTimeConverter.fromString(null)
        MatcherAssert.assertThat(fromConverter, CoreMatchers.nullValue())
    }

    @Test(expected = DateTimeParseException::class)
    fun fromStringInputIncorrect() {
        OffsetDateTimeConverter.fromString("hoge")
    }

    @Test
    fun toStringSuccess() {
        val fromConverter = OffsetDateTimeConverter.toString(target)
        MatcherAssert.assertThat(fromConverter, CoreMatchers.equalTo(targetString))
    }

    @Test
    fun toStringInputNull() {
        val fromConverter = OffsetDateTimeConverter.toString(null)
        MatcherAssert.assertThat(fromConverter, CoreMatchers.nullValue())
    }
}
