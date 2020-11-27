package com.ryunen344.dagashi.data.db.dao

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ryunen344.dagashi.data.db.entity.combined.MileStoneWithSummaryIssue
import com.ryunen344.dagashi.test.MainCoroutineTestRule
import com.ryunen344.dagashi.test.ModelGenerator
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MileStoneDaoTest {

    @get:Rule(order = 0)
    val mainCoroutineTestRule = MainCoroutineTestRule()

    @get:Rule(order = 1)
    val daoTestRule = DaoTestRule()

    private lateinit var dao: MileStoneDao

    @Before
    fun setup() {
        dao = daoTestRule.db.mileStoneDao
    }

    @Test
    fun writeSingleMileStone() {
        runBlocking {
            val mileStone = ModelGenerator.createMileStone()
            dao.insert(mileStone)
            val result = dao.select().first()
            MatcherAssert.assertThat(
                result,
                CoreMatchers.equalTo(
                    listOf(
                        MileStoneWithSummaryIssue(
                            mileStone,
                            emptyList()
                        )
                    )
                )
            )
        }
    }
}
