package com.ryunen344.dagashi.data.api.impl

import com.ryunen344.dagashi.data.api.DagashiApi
import com.ryunen344.dagashi.data.api.mapper.toModel
import com.ryunen344.dagashi.data.api.response.IssueRootResponse
import com.ryunen344.dagashi.data.api.response.MileStonesRootResponse
import com.ryunen344.dagashi.di.ApiEndpoint
import com.ryunen344.dagashi.model.Issue
import com.ryunen344.dagashi.model.MileStone
import io.ktor.client.HttpClient
import io.ktor.client.request.accept
import io.ktor.client.request.get
import io.ktor.client.request.url
import io.ktor.http.ContentType
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

class DagashiApiImpl @Inject constructor(
    private val httpClient: HttpClient,
    @ApiEndpoint private val apiEndpoint: String,
) : DagashiApi {
    override suspend fun milestones(): List<MileStone> {
        val mutex = Mutex()
        val result = mutableSetOf<MileStone>()
        var hasNextPage = true
        var previousEndCursor: String? = null
        while (hasNextPage) {
            mutex.withLock {
                val response = requestMilestones(previousEndCursor)
                result.addAll(response.toModel())
                hasNextPage = response.milestones.pageInfo.hasNextPage
                previousEndCursor = response.milestones.pageInfo.endCursor
            }
        }

        return result.toList()
    }

    private suspend fun requestMilestones(previousEndCursor: String?): MileStonesRootResponse {
        val lastPath = if (previousEndCursor == null) "index.json" else "$previousEndCursor.json"
        return httpClient.get {
            url("$apiEndpoint/api/$lastPath")
            accept(ContentType.Application.Json)
        }
    }

    override suspend fun issues(path: String): List<Issue> {
        return httpClient.get<IssueRootResponse> {
            url("$apiEndpoint/api/issue/$path.json")
            accept(ContentType.Application.Json)
        }.toModel()
    }
}
