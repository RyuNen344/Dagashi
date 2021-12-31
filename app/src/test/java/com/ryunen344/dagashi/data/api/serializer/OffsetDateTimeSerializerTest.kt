package com.ryunen344.dagashi.data.api.serializer

import com.ryunen344.dagashi.di.ApiModule
import kotlinx.serialization.SerializationException
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Test
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeParseException

class OffsetDateTimeSerializerTest {

    private val target = OffsetDateTime.of(2020, 11, 29, 6, 30, 0, 0, ZoneOffset.UTC)

    private val decodedTarget = "\"2020-11-29T06:30:00Z\""

    private val decodedNull = "null"

    private val json = ApiModule.provideJson()

    @Test
    fun decode_string_to_instance() {
        val decoded = json.decodeFromString(OffsetDateTimeSerializer, decodedTarget)
        MatcherAssert.assertThat(decoded, CoreMatchers.equalTo(target))
    }

    @Test(expected = SerializationException::class)
    fun decode_null_throws_exception() {
        json.decodeFromString(OffsetDateTimeSerializer, decodedNull)
    }

    @Test(expected = DateTimeParseException::class)
    fun decode_invalid_throws_exception() {
        json.decodeFromString(OffsetDateTimeSerializer, "\"hoge\"")
    }

    @Test
    fun encode_instance_to_string() {
        val encoded = json.encodeToString(OffsetDateTimeSerializer, target)
        MatcherAssert.assertThat(encoded, CoreMatchers.equalTo(decodedTarget))
    }
}
