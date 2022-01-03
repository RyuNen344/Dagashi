package com.ryunen344.dagashi.data.api

import com.ryunen344.dagashi.data.api.mapper.toModel
import com.ryunen344.dagashi.data.api.response.IssueRootResponse
import com.ryunen344.dagashi.data.api.response.MileStonesRootResponse
import com.ryunen344.dagashi.di.ApiModule
import com.ryunen344.dagashi.test.MainCoroutineTestRule
import io.ktor.client.request.accept
import io.ktor.client.request.get
import io.ktor.client.request.url
import io.ktor.http.ContentType
import org.junit.Rule
import org.junit.Test

class CommunicationTest {

    @get:Rule
    val mainCoroutineTestRule = MainCoroutineTestRule()

    private val httpClient = ApiModule.provideHttpClient(ApiModule.provideJson())

    private val apiEndpoint = ApiModule.provideApiEndpoint()

    @Test
    fun api_index_json() {
        mainCoroutineTestRule.runBlockingTest {
            val firstTime = httpClient.get<MileStonesRootResponse> {
                url("$apiEndpoint/api/index.json")
                accept(ContentType.Application.Json)
            }

            httpClient.get<MileStonesRootResponse> {
                url("$apiEndpoint/api/${firstTime.milestones.pageInfo.endCursor}.json")
                accept(ContentType.Application.Json)
            }
        }
    }

    @Test
    fun api_issue_path_json() {
        mainCoroutineTestRule.runBlockingTest {
            val path = "143-2020-10-25"
            httpClient.get<IssueRootResponse> {
                url("$apiEndpoint/api/issue/$path.json")
                accept(ContentType.Application.Json)
            }.toModel()
        }
    }
}
