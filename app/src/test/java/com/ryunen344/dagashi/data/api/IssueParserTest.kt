package com.ryunen344.dagashi.data.api

import com.ryunen344.dagashi.data.api.response.IssueRootResponse
import com.ryunen344.dagashi.di.ApiModule
import kotlinx.serialization.decodeFromString
import org.junit.Test

class IssueParserTest {
    @Test
    fun testIssues() {
        val bf = requireNotNull(javaClass.classLoader).getResourceAsStream(FILENAME).bufferedReader()
        val jsonString = bf.use { it.readText() }
        json.decodeFromString<IssueRootResponse>(jsonString)
    }

    companion object {
        private const val FILENAME = "issue_143-2020-10-25.json"
        private val json = ApiModule.provideJson()
    }
}
