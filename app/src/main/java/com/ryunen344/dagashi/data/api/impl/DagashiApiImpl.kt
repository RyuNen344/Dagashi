package com.ryunen344.dagashi.data.api.impl

import com.ryunen344.dagashi.data.api.DagashiApi
import com.ryunen344.dagashi.data.api.response.IssueRootResponse
import com.ryunen344.dagashi.data.api.response.MileStonesRootResponse
import com.ryunen344.dagashi.di.ApiEndpoint
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import javax.inject.Inject

class DagashiApiImpl @Inject constructor(
    private val httpClient: HttpClient,
    @ApiEndpoint private val apiEndpoint: String,
) : DagashiApi {
    override suspend fun milestones(): MileStonesRootResponse {
        return httpClient.get {
            url("$apiEndpoint/api/index.json")
            accept(ContentType.Application.Json)
        }
    }

    override suspend fun milestones(previousEndCursor: String): MileStonesRootResponse {
        return httpClient.get {
            url("$apiEndpoint/api/$previousEndCursor.json")
            accept(ContentType.Application.Json)
        }
    }

    override suspend fun issues(path: String): IssueRootResponse {
        return httpClient.get {
            url("$apiEndpoint/api/issue/$path.json")
            accept(ContentType.Application.Json)
        }
    }
}
