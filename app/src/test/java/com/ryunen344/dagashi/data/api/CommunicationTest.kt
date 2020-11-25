package com.ryunen344.dagashi.data.api

import com.ryunen344.dagashi.data.api.impl.DagashiApiImpl
import com.ryunen344.dagashi.di.ApiModule
import com.ryunen344.dagashi.test.CoroutineTestRule
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test

class CommunicationTest {

    @get:Rule
    val rule = CoroutineTestRule()

    private val api = DagashiApiImpl(ApiModule.provideHttpClient(ApiModule.provideJson()), "https://androiddagashi.github.io")

    @Test
    fun testMilestones() {
        runBlocking {
            api.milestones()
        }
    }

    @Test
    fun testMilestonesCursor() {
        runBlocking {
            api.milestones("Y3Vyc29yOnYyOpK0MjAxOS0xMi0xNVQwMDowMDowMFrOAEr1dQ==")
        }
    }

    @Test
    fun testIssues() {
        runBlocking {
            api.issues("143-2020-10-25")
        }
    }
}
