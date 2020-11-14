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
    suspend fun milestones(): MileStonesRootResponse {
        return httpClient.get {
            url("$apiEndpoint/api/index.json")
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

