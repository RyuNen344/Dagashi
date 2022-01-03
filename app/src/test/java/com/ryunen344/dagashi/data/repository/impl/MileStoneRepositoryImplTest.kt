package com.ryunen344.dagashi.data.repository.impl

import com.ryunen344.dagashi.data.api.DagashiApi
import com.ryunen344.dagashi.data.db.interfaces.MileStoneDatabase
import com.ryunen344.dagashi.test.MainCoroutineTestRule
import com.ryunen344.dagashi.test.ModelGenerator
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.just
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
    fun refresh() {
        mainCoroutineTestRule.runBlockingTest {
            val milestones = listOf(
                ModelGenerator.createMileStone()
            )
            coEvery { mockDagashiApi.milestones() } coAnswers { milestones }
            coEvery { mockMileStoneDatabase.saveMileStones(milestones) } just Runs

            mileStoneRepositoryImpl.refresh()

            coVerify { mockDagashiApi.milestones() }
            coVerify { mockMileStoneDatabase.saveMileStones(milestones) }
            confirmVerified(mockDagashiApi, mockMileStoneDatabase)
        }
    }

    @Test
    fun mileStones() {
        mainCoroutineTestRule.runBlockingTest {
            val milestones = listOf(
                ModelGenerator.createMileStone()
            )

            coEvery { mockMileStoneDatabase.mileStones() } answers { flowOf(milestones) }

            val mappedList = mileStoneRepositoryImpl.mileStones().single()

            coVerify { mockMileStoneDatabase.mileStones() }

            MatcherAssert.assertThat(mappedList, CoreMatchers.equalTo(milestones))
        }
    }
}
