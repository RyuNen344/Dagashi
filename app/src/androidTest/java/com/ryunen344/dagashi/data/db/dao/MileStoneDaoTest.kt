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

    @get:Rule
    val mainCoroutineTestRule = MainCoroutineTestRule()

    @get:Rule
    val daoTestRule = DaoTestRule()

    private lateinit var dao: MileStoneDao

    @Before
    fun setup() {
        dao = daoTestRule.db.mileStoneDao
    }

    @Test
    fun insertSingleMileStone() {
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

    @Test
    fun insertMultipleMiltStone() {
        runBlocking {
            val mileStones = ModelGenerator.createMileStones()
            dao.insert(mileStones)
            val result = dao.select().first()
            MatcherAssert.assertThat(
                result,
                CoreMatchers.equalTo(
                    mileStones
                        .sortedByDescending {
                            it.number
                        }.map {
                            MileStoneWithSummaryIssue(
                                it,
                                emptyList()
                            )
                        }
                )
            )
        }
    }

    @Test
    fun insertSameSingleMileStone() {
        runBlocking {
            val before = ModelGenerator.createMileStone()
            dao.insert(before)
            val after = before.copy(title = "update title")
            dao.insert(after)
            val result = dao.select().first()
            MatcherAssert.assertThat(
                result,
                CoreMatchers.equalTo(
                    listOf(
                        MileStoneWithSummaryIssue(
                            before,
                            emptyList()
                        )
                    )
                )
            )
        }
    }

    @Test
    fun insertSameMultipleMileStone() {
        runBlocking {
            val before = ModelGenerator.createMileStones()
            dao.insert(before)
            val after = before.toMutableList().apply {
                removeAt(0)
                add(0, before[0].copy(title = "update title"))
            }
            dao.insert(after)
            val result = dao.select().first()
            MatcherAssert.assertThat(
                result,
                CoreMatchers.equalTo(
                    before
                        .sortedByDescending {
                            it.number
                        }.map {
                            MileStoneWithSummaryIssue(
                                it,
                                emptyList()
                            )
                        }
                )
            )
        }
    }

    @Test
    fun updateSingleMileStone() {
        runBlocking {
            val before = ModelGenerator.createMileStone()
            dao.insert(before)
            val after = before.copy(title = "update title")
            dao.update(after)
            val result = dao.select().first()
            MatcherAssert.assertThat(
                result,
                CoreMatchers.equalTo(
                    listOf(
                        MileStoneWithSummaryIssue(
                            after,
                            emptyList()
                        )
                    )
                )
            )
        }
    }

    @Test
    fun updateMultipleMileStone() {
        runBlocking {
            val before = ModelGenerator.createMileStones()
            dao.insert(before)
            val after = before.toMutableList().apply {
                removeAt(0)
                add(0, before[0].copy(title = "update title"))
            }

            dao.insertOrUpdate(after)
            val result = dao.select().first()
            MatcherAssert.assertThat(
                result,
                CoreMatchers.equalTo(
                    after
                        .sortedByDescending {
                            it.number
                        }.map {
                            MileStoneWithSummaryIssue(
                                it,
                                emptyList()
                            )
                        }
                )
            )
        }
    }

    @Test
    fun insertOrUpdateSingleMileStone() {
        runBlocking {
            val before = ModelGenerator.createMileStone()
            dao.insert(before)
            val insertResult = dao.select().first()
            MatcherAssert.assertThat(
                insertResult,
                CoreMatchers.equalTo(
                    listOf(
                        MileStoneWithSummaryIssue(
                            before,
                            emptyList()
                        )
                    )
                )
            )

            val after = before.copy(title = "update title")
            dao.insertOrUpdate(after)
            val updateResult = dao.select().first()
            MatcherAssert.assertThat(
                updateResult,
                CoreMatchers.equalTo(
                    listOf(
                        MileStoneWithSummaryIssue(
                            after,
                            emptyList()
                        )
                    )
                )
            )
        }
    }

    @Test
    fun insertOrUpdateMultipleMileStone() {
        runBlocking {
            val before = ModelGenerator.createMileStones()
            dao.insert(before)
            val after = before.toMutableList().apply {
                removeAt(0)
                add(0, before[0].copy(title = "update title"))
            }
            dao.insertOrUpdate(after)
            val result = dao.select().first()
            MatcherAssert.assertThat(
                result,
                CoreMatchers.equalTo(
                    after
                        .sortedByDescending {
                            it.number
                        }.map {
                            MileStoneWithSummaryIssue(
                                it,
                                emptyList()
                            )
                        }
                )
            )
        }
    }
}
