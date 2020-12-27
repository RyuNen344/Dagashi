package com.ryunen344.dagashi.data.api

import com.ryunen344.dagashi.di.ApiModule
import com.ryunen344.dagashi.test.MainCoroutineTestRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Rule
import org.junit.Test

class CommunicationTest {

    @get:Rule
    val mainCoroutineTestRule = MainCoroutineTestRule()

    private val api = ApiModule.provideDagashiApiImpl(ApiModule.provideHttpClient(ApiModule.provideJson()), ApiModule.provideApiEndpoint())

    @Test
    fun testMilestones() {
        runBlocking(Dispatchers.Default) {
            api.milestones()
        }
    }

    @Test
    fun testMilestonesCursor() {
        runBlocking(Dispatchers.Default) {
            val milestones = api.milestones()
            MatcherAssert.assertThat(
                milestones.milestones.pageInfo.endCursor?.let {
                    api.milestones(it)
                },
                CoreMatchers.notNullValue()
            )
        }
    }

    @Test
    fun testIssues() {
        runBlocking(Dispatchers.Default) {
            api.issues("143-2020-10-25")
        }
    }
}
