package com.ryunen344.dagashi.data.api

import com.ryunen344.dagashi.data.api.response.MileStonesRootResponse
import com.ryunen344.dagashi.di.ApiModule
import kotlinx.serialization.decodeFromString
import org.junit.Test

class MileStoneParserTest {
    @Test
    fun testMileStone() {
        val bf = requireNotNull(javaClass.classLoader).getResourceAsStream(FILENAME).bufferedReader()
        val jsonString = bf.use { it.readText() }
        json.decodeFromString<MileStonesRootResponse>(jsonString)
    }

    companion object {
        private const val FILENAME = "index.json"
        private val json = ApiModule.provideJson()
    }
}
