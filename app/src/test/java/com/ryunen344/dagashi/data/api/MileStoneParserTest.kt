package com.ryunen344.dagashi.data.api

import com.ryunen344.dagashi.data.api.response.MileStonesRootResponse
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.Test

class MileStoneParserTest {
    @Test
    fun testMileStone() {
        val bf = this.javaClass.classLoader!!.getResourceAsStream(FILENAME).bufferedReader()
        val jsonString = bf.use { it.readText() }
        json.decodeFromString<MileStonesRootResponse>(jsonString)
    }

    companion object {
        private const val FILENAME = "index.json"
        private val json = Json {
            ignoreUnknownKeys = true
        }
    }
}
