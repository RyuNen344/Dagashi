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

    private val targetString = "\"2020-11-29T06:30:00Z\""

    private val json = ApiModule.provideJson()

    @Test
    fun decodeCorrect() {
        val decodeFromString = json.decodeFromString(OffsetDateTimeSerializer, getOffsetDateTimeJsonString(target))
        MatcherAssert.assertThat(decodeFromString, CoreMatchers.equalTo(target))
    }

    @Test(expected = DateTimeParseException::class)
    fun decodeInputIncorrect() {
        json.decodeFromString(OffsetDateTimeSerializer, getOffsetDateTimeJsonString("hoge"))
    }

    @Test(expected = SerializationException::class)
    fun decodeInputNull() {
        json.decodeFromString(OffsetDateTimeSerializer, getOffsetDateTimeJsonString(null))
    }

    @Test
    fun encodeCorrect() {
        val encoded = json.encodeToString(OffsetDateTimeSerializer, target)
        MatcherAssert.assertThat(encoded, CoreMatchers.equalTo(targetString))
    }

    private fun getOffsetDateTimeJsonString(target: Any?): String {
        return if (target != null) "\"$target\"" else "null"
    }
}
