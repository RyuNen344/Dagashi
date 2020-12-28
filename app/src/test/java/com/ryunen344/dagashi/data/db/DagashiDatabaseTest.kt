package com.ryunen344.dagashi.data.db

import androidx.room.withTransaction
import com.ryunen344.dagashi.data.db.entity.relation.IssueLabelCrossRef
import com.ryunen344.dagashi.test.EntityGenerator
import com.ryunen344.dagashi.test.MainCoroutineTestRule
import com.ryunen344.dagashi.test.runBlockingTest
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.slot
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test

class DagashiDatabaseTest {

    @get:Rule
    val mainCoroutineTestRule = MainCoroutineTestRule()

    private val mockCacheDatabase: CacheDatabase = mockk(relaxed = true)

    private val dagashiDatabase: DagashiDatabase = DagashiDatabase(mockCacheDatabase)

    @Test
    fun mileStoneEntity() {
        coEvery {
            mockCacheDatabase.mileStoneDao.select()
        } returns flowOf(emptyList())

        dagashiDatabase.mileStoneEntity()

        coVerify {
            mockCacheDatabase.mileStoneDao.select()
        }
        confirmVerified(mockCacheDatabase)
    }

    @Test
    fun saveMileStones() {
        mainCoroutineTestRule.runBlockingTest {
            mockkStatic("androidx.room.RoomDatabaseKt")
            val transactionLambda = slot<suspend () -> Any>()
            coEvery { mockCacheDatabase.withTransaction(capture(transactionLambda)) } coAnswers { transactionLambda.captured.invoke() }

            val mileStoneWithSummaryIssues = EntityGenerator.createMileStoneWithSummaryIssues()
            coEvery {
                mockCacheDatabase.mileStoneDao.insertOrUpdate(mileStoneWithSummaryIssues.map { it.mileStoneEntity })
            } returns mockk()
            coEvery {
                mockCacheDatabase.summaryIssueDao.insertOrUpdate(mileStoneWithSummaryIssues.flatMap { it.issues })
            } returns mockk()

            dagashiDatabase.saveMileStone(mileStoneWithSummaryIssues)

            coVerify {
                mockCacheDatabase.mileStoneDao.insertOrUpdate(mileStoneWithSummaryIssues.map { it.mileStoneEntity })
            }
            coVerify {
                mockCacheDatabase.summaryIssueDao.insertOrUpdate(mileStoneWithSummaryIssues.flatMap { it.issues })
            }
            confirmVerified(mockCacheDatabase)
        }
    }

    @Test
    fun issueEntity() {
        coEvery {
            mockCacheDatabase.issueDao.select(0)
        } returns flowOf(emptyList())

        dagashiDatabase.issueEntity(0)

        coVerify {
            mockCacheDatabase.issueDao.select(0)
        }
        confirmVerified(mockCacheDatabase)
    }

    @Test
    fun issueEntityByKeyword() {
        coEvery {
            mockCacheDatabase.issueDao.search("keyword")
        } returns flowOf(emptyList())

        dagashiDatabase.issueEntityByKeyword("keyword")

        coVerify {
            mockCacheDatabase.issueDao.search("keyword")
        }
        confirmVerified(mockCacheDatabase)
    }

    @Test
    fun saveIssue() {
        mainCoroutineTestRule.runBlockingTest {
            mockkStatic("androidx.room.RoomDatabaseKt")
            val transactionLambda = slot<suspend () -> Any>()
            coEvery { mockCacheDatabase.withTransaction(capture(transactionLambda)) } coAnswers { transactionLambda.captured.invoke() }

            val issueWithLabelAndComments = EntityGenerator.createIssueWithLabelAndComments()
            coEvery {
                mockCacheDatabase.issueDao.insertOrUpdate(issueWithLabelAndComments.map { it.issueEntity })
            } returns mockk()
            coEvery {
                mockCacheDatabase.labelDao.insertOrUpdate(issueWithLabelAndComments.flatMap { it.labels }.distinct())
            } returns mockk()
            coEvery {
                mockCacheDatabase.issueLabelCrossRefDao.insertOrUpdate(
                    issueWithLabelAndComments.flatMap { combined ->
                        combined.labels.map { label ->
                            IssueLabelCrossRef(
                                singleUniqueId = combined.issueEntity.singleUniqueId,
                                labelName = label.name
                            )
                        }
                    }
                )
            } returns mockk()
            coEvery {
                mockCacheDatabase.commentDao.insertOrUpdate(issueWithLabelAndComments.flatMap { it.comments })
            } returns mockk()

            dagashiDatabase.saveIssue(issueWithLabelAndComments)

            coVerify {
                mockCacheDatabase.issueDao.insertOrUpdate(issueWithLabelAndComments.map { it.issueEntity })
            }
            coVerify {
                mockCacheDatabase.labelDao.insertOrUpdate(issueWithLabelAndComments.flatMap { it.labels }.distinct())
            }
            coVerify {
                mockCacheDatabase.issueLabelCrossRefDao.insertOrUpdate(
                    issueWithLabelAndComments.flatMap { combined ->
                        combined.labels.map { label ->
                            IssueLabelCrossRef(
                                singleUniqueId = combined.issueEntity.singleUniqueId,
                                labelName = label.name
                            )
                        }
                    }
                )
            }
            coVerify {
                mockCacheDatabase.commentDao.insertOrUpdate(issueWithLabelAndComments.flatMap { it.comments })
            }
            confirmVerified(mockCacheDatabase)
        }
    }
}
