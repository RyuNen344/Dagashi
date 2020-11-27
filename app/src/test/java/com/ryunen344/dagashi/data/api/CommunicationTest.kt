package com.ryunen344.dagashi.data.api

import com.ryunen344.dagashi.data.api.impl.DagashiApiImpl
import com.ryunen344.dagashi.di.ApiModule
import com.ryunen344.dagashi.test.MainCoroutineTestRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test

class CommunicationTest {

    @get:Rule
    val mainCoroutineTestRule = MainCoroutineTestRule()

    private val api = DagashiApiImpl(ApiModule.provideHttpClient(ApiModule.provideJson()), "https://androiddagashi.github.io")

    @Test
    fun testMilestones() {
        runBlocking(Dispatchers.Default) {
            api.milestones()
        }
    }

    @Test
    fun testMilestonesCursor() {
        runBlocking(Dispatchers.Default) {
            api.milestones("Y3Vyc29yOnYyOpK0MjAxOS0xMi0xNVQwMDowMDowMFrOAEr1dQ==")
        }
    }

    @Test
    fun testIssues() {
        runBlocking(Dispatchers.Default) {
            api.issues("143-2020-10-25")
        }
    }
}
