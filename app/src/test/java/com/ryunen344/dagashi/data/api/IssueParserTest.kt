package com.ryunen344.dagashi.data.api

import com.ryunen344.dagashi.data.api.response.IssueRootResponse
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.Test

class IssueParserTest {
    @Test
    fun testIssues() {
        val bf = this.javaClass.classLoader!!.getResourceAsStream(FILENAME).bufferedReader()
        val jsonString = bf.use { it.readText() }
        json.decodeFromString<IssueRootResponse>(jsonString)
    }

    companion object {
        private const val FILENAME = "issue_143-2020-10-25.json"
        private val json = Json {
            ignoreUnknownKeys = true
        }
    }
}
