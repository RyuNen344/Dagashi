package com.ryunen344.dagashi.data.api

import com.ryunen344.dagashi.data.api.impl.DagashiApiImpl
import com.ryunen344.dagashi.data.api.mapper.toModel
import com.ryunen344.dagashi.di.ApiModule
import com.ryunen344.dagashi.test.MainCoroutineTestRule
import com.ryunen344.dagashi.test.ResponseGenerator
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.fullPath
import io.ktor.http.headersOf
import kotlinx.serialization.encodeToString
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Rule
import org.junit.Test

class MileStonePagingTest {

    @get:Rule
    val mainCoroutineTestRule = MainCoroutineTestRule()

    @Test
    fun pagingLoop() {
        mainCoroutineTestRule.runBlockingTest {
            val apiEndpoint = ApiModule.provideApiEndpoint()
            val json = ApiModule.provideJson()

            val multiplePageResponse = ResponseGenerator.createMultiplePageMileStonesRootResponse()
            val singlePageResponse = ResponseGenerator.createSinglePageMileStonesRootResponse()
            val expect = multiplePageResponse.toModel().plus(singlePageResponse.toModel())

            val httpClient = HttpClient(MockEngine) {
                install(JsonFeature) {
                    serializer = KotlinxSerializer(json)
                }
                engine {
                    addHandler { request ->
                        when (request.url.fullPath) {
                            "/api/index.json" -> {
                                val response = json.encodeToString(multiplePageResponse)
                                respond(
                                    content = response,
                                    status = HttpStatusCode.OK,
                                    headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                                )
                            }
                            "/api/endCursor.json" -> {
                                val response = json.encodeToString(singlePageResponse)
                                respond(
                                    content = response,
                                    status = HttpStatusCode.OK,
                                    headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                                )
                            }
                            else -> error("Unhandled ${request.url.fullPath}")
                        }
                    }
                }
            }

            val api = DagashiApiImpl(httpClient, apiEndpoint)

            val actual = api.milestones()

            MatcherAssert.assertThat(actual, CoreMatchers.equalTo(expect))
        }
    }
}
