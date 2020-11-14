package com.ryunen344.dagashi.data.api

import com.ryunen344.dagashi.di.ApiModule
import com.ryunen344.dagashi.test.CoroutineTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test

class CommunicationTest {

    @get:Rule
    @ExperimentalCoroutinesApi
    val rule = CoroutineTestRule()

    private val api = DagashiApi(ApiModule.provideHttpClient(ApiModule.provideJson()), "https://androiddagashi.github.io")

    @Test
    @ExperimentalCoroutinesApi
    fun testMilestones() {
        runBlocking {
            api.milestones()
        }
    }

    @Test
    @ExperimentalCoroutinesApi
    fun testIssues() {
        runBlocking {
            api.issues("143-2020-10-25")
        }
    }
}
