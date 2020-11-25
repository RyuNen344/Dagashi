package com.ryunen344.dagashi.data.api

import com.ryunen344.dagashi.data.api.response.IssueRootResponse
import com.ryunen344.dagashi.data.api.response.MileStonesRootResponse
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import javax.inject.Inject

class DagashiApi @Inject constructor(
    private val httpClient: HttpClient,
    private val apiEndpoint: String,
) {
    suspend fun milestones(previousEndCursor: String? = null): MileStonesRootResponse {
        return httpClient.get {
            if (previousEndCursor == null) {
                url("$apiEndpoint/api/index.json")
            } else {
                url("$apiEndpoint/api/$previousEndCursor.json")
            }
            accept(ContentType.Application.Json)
        }
    }

    suspend fun issues(path: String): IssueRootResponse {
        return httpClient.get {
            url("$apiEndpoint/api/issue/$path.json")
            accept(ContentType.Application.Json)
        }
    }
}

