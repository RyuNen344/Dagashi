package com.ryunen344.dagashi.data.repository.impl

import com.ryunen344.dagashi.data.api.DagashiApi
import com.ryunen344.dagashi.data.api.mapper.toModel
import com.ryunen344.dagashi.data.db.interfaces.MileStoneDatabase
import com.ryunen344.dagashi.data.db.mapper.toModel
import com.ryunen344.dagashi.test.EntityGenerator
import com.ryunen344.dagashi.test.MainCoroutineTestRule
import com.ryunen344.dagashi.test.ResponseGenerator
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Rule
import org.junit.Test

class MileStoneRepositoryImplTest {

    @get:Rule
    val mainCoroutineTestRule = MainCoroutineTestRule()

    private val mockDagashiApi: DagashiApi = mockk()

    private val mockMileStoneDatabase: MileStoneDatabase = mockk()

    private val mileStoneRepositoryImpl = MileStoneRepositoryImpl(mockDagashiApi, mockMileStoneDatabase, mainCoroutineTestRule.dispatcher)

    @Test
    fun refreshSinglePage() {
        mainCoroutineTestRule.runBlockingTest {
            val response = ResponseGenerator.createSinglePageMileStonesRootResponse().toModel()
            coEvery { mockDagashiApi.milestones() } answers { response }
            coEvery { mockMileStoneDatabase.saveMileStones(response) } answers {}

            mileStoneRepositoryImpl.refresh()

            coVerify(exactly = 1) { mockDagashiApi.milestones() }
            coVerify { mockMileStoneDatabase.saveMileStones(response) }
            confirmVerified(mockDagashiApi, mockMileStoneDatabase)
        }
    }

    @Test
    fun refreshMultiplePage() {
        mainCoroutineTestRule.runBlockingTest {
            val multiplePageResponse = ResponseGenerator.createMultiplePageMileStonesRootResponse()
            val singlePageResponse = ResponseGenerator.createSinglePageMileStonesRootResponse()
            val result = multiplePageResponse.toModel() + singlePageResponse.toModel()

            coEvery { mockDagashiApi.milestones() } answers { result }
            coEvery { mockMileStoneDatabase.saveMileStones(result) } answers {}

            mileStoneRepositoryImpl.refresh()

            coVerify(exactly = 1) { mockDagashiApi.milestones() }
            coVerify { mockMileStoneDatabase.saveMileStones(result) }
            confirmVerified(mockDagashiApi, mockMileStoneDatabase)
        }
    }

    @Test
    fun mileStones() {
        mainCoroutineTestRule.runBlockingTest {
            val db = EntityGenerator.createMileStoneWithSummaryIssues().map { it.toModel() }

            coEvery { mockMileStoneDatabase.mileStones() } answers { flowOf(db) }

            val mappedList = mileStoneRepositoryImpl.mileStones().single()

            coVerify { mockMileStoneDatabase.mileStones() }

            MatcherAssert.assertThat(mappedList, CoreMatchers.equalTo(db))
        }
    }
}
