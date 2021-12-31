package com.ryunen344.dagashi.data.db

import androidx.room.withTransaction
import com.ryunen344.dagashi.data.db.entity.relation.IssueLabelCrossRef
import com.ryunen344.dagashi.data.db.mapper.toModel
import com.ryunen344.dagashi.test.EntityGenerator
import com.ryunen344.dagashi.test.MainCoroutineTestRule
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

        dagashiDatabase.mileStones()

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

            dagashiDatabase.saveMileStones(mileStoneWithSummaryIssues.map { it.toModel() })

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

        dagashiDatabase.issues(0)

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

        dagashiDatabase.issuesByKeyword("keyword")

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

            val issueWithLabelAndComments = EntityGenerator.createIssueWithLabelAndCommentOnStashes()
            coEvery {
                mockCacheDatabase.issueDao.insertOrUpdate(issueWithLabelAndComments.map { it.issueWithLabelAndComment.issueEntity })
            } returns mockk()
            coEvery {
                mockCacheDatabase.labelDao.insertOrUpdate(issueWithLabelAndComments.flatMap { it.issueWithLabelAndComment.labels }.distinct())
            } returns mockk()
            coEvery {
                mockCacheDatabase.issueLabelCrossRefDao.insertOrUpdate(
                    issueWithLabelAndComments.flatMap { combined ->
                        combined.issueWithLabelAndComment.labels.map { label ->
                            IssueLabelCrossRef(
                                singleUniqueId = combined.issueWithLabelAndComment.issueEntity.singleUniqueId,
                                labelName = label.name
                            )
                        }
                    }
                )
            } returns mockk()
            coEvery {
                mockCacheDatabase.commentDao.insertOrUpdate(issueWithLabelAndComments.flatMap { it.issueWithLabelAndComment.comments })
            } returns mockk()

            dagashiDatabase.saveIssue(issueWithLabelAndComments.map { it.toModel() })

            coVerify {
                mockCacheDatabase.issueDao.insertOrUpdate(issueWithLabelAndComments.map { it.issueWithLabelAndComment.issueEntity })
            }
            coVerify {
                mockCacheDatabase.labelDao.insertOrUpdate(issueWithLabelAndComments.flatMap { it.issueWithLabelAndComment.labels }.distinct())
            }
            coVerify {
                mockCacheDatabase.issueLabelCrossRefDao.insertOrUpdate(
                    issueWithLabelAndComments.flatMap { combined ->
                        combined.issueWithLabelAndComment.labels.map { label ->
                            IssueLabelCrossRef(
                                singleUniqueId = combined.issueWithLabelAndComment.issueEntity.singleUniqueId,
                                labelName = label.name
                            )
                        }
                    }
                )
            }
            coVerify {
                mockCacheDatabase.commentDao.insertOrUpdate(issueWithLabelAndComments.flatMap { it.issueWithLabelAndComment.comments })
            }
            confirmVerified(mockCacheDatabase)
        }
    }
}
