package com.ryunen344.dagashi.data.repository.impl

import com.ryunen344.dagashi.data.api.DagashiApi
import com.ryunen344.dagashi.data.db.interfaces.MileStoneDatabase
import com.ryunen344.dagashi.data.repository.mapper.MileStoneMapper
import com.ryunen344.dagashi.data.repository.mapper.MileStoneMapper.toModel
import com.ryunen344.dagashi.test.EntityGenerator
import com.ryunen344.dagashi.test.MainCoroutineTestRule
import com.ryunen344.dagashi.test.ResponseGenerator
import com.ryunen344.dagashi.test.runBlockingTest
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
            val response = ResponseGenerator.createSinglePageMileStonesRootResponse()
            coEvery { mockDagashiApi.milestones() } answers { response }
            coEvery { mockMileStoneDatabase.saveMileStone(response.milestones.nodes.map(MileStoneMapper::toEntity)) } answers {}

            mileStoneRepositoryImpl.refresh()

            coVerify(exactly = 1) { mockDagashiApi.milestones() }
            coVerify { mockMileStoneDatabase.saveMileStone(response.milestones.nodes.map(MileStoneMapper::toEntity)) }
            confirmVerified(mockDagashiApi, mockMileStoneDatabase)
        }
    }

    @Test
    fun refreshMultiplePage() {
        mainCoroutineTestRule.runBlockingTest {
            val multiplePageResponse = ResponseGenerator.createMultiplePageMileStonesRootResponse()
            val singlePageResponse = ResponseGenerator.createSinglePageMileStonesRootResponse()
            val result = listOf(
                multiplePageResponse.milestones.nodes.map(MileStoneMapper::toEntity),
                singlePageResponse.milestones.nodes.map(MileStoneMapper::toEntity)
            ).flatten()

            coEvery { mockDagashiApi.milestones() } answers { multiplePageResponse }
            coEvery { mockDagashiApi.milestones(multiplePageResponse.milestones.pageInfo.endCursor!!) } answers { singlePageResponse }
            coEvery { mockMileStoneDatabase.saveMileStone(result) } answers {}

            mileStoneRepositoryImpl.refresh()

            coVerify(exactly = 1) { mockDagashiApi.milestones() }
            coVerify(exactly = 1) { mockDagashiApi.milestones(multiplePageResponse.milestones.pageInfo.endCursor!!) }
            coVerify { mockMileStoneDatabase.saveMileStone(result) }
            confirmVerified(mockDagashiApi, mockMileStoneDatabase)
        }
    }

    @Test
    fun mileStones() {
        mainCoroutineTestRule.runBlockingTest {
            val db = EntityGenerator.createMileStoneWithSummaryIssues()

            coEvery { mockMileStoneDatabase.mileStoneEntity() } answers { flowOf(db) }

            val mappedList = mileStoneRepositoryImpl.mileStones().single()

            coVerify { mockMileStoneDatabase.mileStoneEntity() }

            MatcherAssert.assertThat(mappedList, CoreMatchers.equalTo(db.map(::toModel)))
        }
    }
}
