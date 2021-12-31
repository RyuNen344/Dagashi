package com.ryunen344.dagashi.data.db.converter

import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Test
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeParseException

class OffsetDateTimeConverterTest {

    private val target = OffsetDateTime.of(2020, 11, 29, 6, 30, 0, 0, ZoneOffset.UTC)

    @Test
    fun string_to_instance() {
        val fromConverter = OffsetDateTimeConverter.fromString(target.toString())
        MatcherAssert.assertThat(fromConverter, CoreMatchers.equalTo(target))
    }

    @Test
    fun null_string_to_null() {
        val fromConverter = OffsetDateTimeConverter.fromString(null)
        MatcherAssert.assertThat(fromConverter, CoreMatchers.nullValue())
    }

    @Test(expected = DateTimeParseException::class)
    fun invalid_string_throws_exception() {
        OffsetDateTimeConverter.fromString("hoge")
    }

    @Test
    fun instance_to_string() {
        val fromConverter = OffsetDateTimeConverter.toString(target)
        MatcherAssert.assertThat(fromConverter, CoreMatchers.equalTo(target.toString()))
    }

    @Test
    fun null_instance_to_null() {
        val fromConverter = OffsetDateTimeConverter.toString(null)
        MatcherAssert.assertThat(fromConverter, CoreMatchers.nullValue())
    }
}
