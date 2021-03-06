package com.ryunen344.dagashi.data.db.dao

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ryunen344.dagashi.data.db.entity.combined.IssueWithLabelAndComment
import com.ryunen344.dagashi.data.db.entity.combined.IssueWithLabelAndCommentOnStash
import com.ryunen344.dagashi.test.EntityGenerator
import com.ryunen344.dagashi.test.MainCoroutineTestRule
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class IssueDaoTest {

    @get:Rule
    val mainCoroutineTestRule = MainCoroutineTestRule()

    @get:Rule
    val daoTestRule = DaoTestRule()

    private lateinit var dao: IssueDao

    @Before
    fun setup() {
        dao = daoTestRule.db.issueDao
    }

    @Test
    fun writeSingleIssue() {
        runBlocking {
            val issue = EntityGenerator.createIssue()
            dao.insert(issue)
            val result = dao.select(issue.number).first()
            MatcherAssert.assertThat(
                result,
                CoreMatchers.equalTo(
                    listOf(
                        IssueWithLabelAndCommentOnStash(
                            IssueWithLabelAndComment(
                                issue,
                                emptyList(),
                                emptyList()
                            ),
                            false
                        )
                    )
                )
            )
        }
    }

    @Test
    fun writeMultipleIssue() {
        runBlocking {
            val issues = EntityGenerator.createIssues()
            dao.insert(issues)
            val result = dao.select(issues.first().number).first()
            MatcherAssert.assertThat(
                result,
                CoreMatchers.equalTo(
                    issues.map {
                        IssueWithLabelAndCommentOnStash(
                            IssueWithLabelAndComment(
                                it,
                                emptyList(),
                                emptyList()
                            ),
                            false
                        )
                    }
                )
            )
        }
    }

    @Test
    fun updateSingleIssue() {
        runBlocking {
            val before = EntityGenerator.createIssue()
            dao.insert(before)
            val after = before.copy(title = "update title")
            dao.update(after)
            val result = dao.select(after.number).first()
            MatcherAssert.assertThat(
                result,
                CoreMatchers.equalTo(
                    listOf(
                        IssueWithLabelAndCommentOnStash(
                            IssueWithLabelAndComment(
                                after,
                                emptyList(),
                                emptyList()
                            ),
                            false
                        )
                    )
                )
            )
        }
    }

    @Test
    fun updateMultipleIssue() {
        runBlocking {
            val before = EntityGenerator.createIssues()
            dao.insert(before)
            val after = before.toMutableList().apply {
                removeAt(0)
                add(0, before[0].copy(title = "update title"))
            }
            dao.update(after)
            val result = dao.select(after.first().number).first()
            MatcherAssert.assertThat(
                result,
                CoreMatchers.equalTo(
                    after
                        .sortedByDescending { it.number }
                        .map {
                            IssueWithLabelAndCommentOnStash(
                                IssueWithLabelAndComment(
                                    it,
                                    emptyList(),
                                    emptyList()
                                ),
                                false
                            )
                        }
                )
            )
        }
    }

    @Test
    fun insertOrUpdateSingleIssue() {
        runBlocking {
            val before = EntityGenerator.createIssue()
            dao.insert(before)
            val after = before.copy(title = "update title")
            dao.insertOrUpdate(after)
            val result = dao.select(after.number).first()
            MatcherAssert.assertThat(
                result,
                CoreMatchers.equalTo(
                    listOf(
                        IssueWithLabelAndCommentOnStash(
                            IssueWithLabelAndComment(
                                after,
                                emptyList(),
                                emptyList()
                            ),
                            false
                        )
                    )
                )
            )
        }
    }

    @Test
    fun insertOrUpdateMultipleIssue() {
        runBlocking {
            val before = EntityGenerator.createIssues()
            dao.insert(before)
            val after = before.toMutableList().apply {
                removeAt(0)
                add(0, before[0].copy(title = "update title"))
            }
            dao.insertOrUpdate(after)
            val result = dao.select(after.first().number).first()
            MatcherAssert.assertThat(
                result,
                CoreMatchers.equalTo(
                    after
                        .sortedByDescending {
                            it.number
                        }.map {
                            IssueWithLabelAndCommentOnStash(
                                IssueWithLabelAndComment(
                                    it,
                                    emptyList(),
                                    emptyList()
                                ),
                                false
                            )
                        }
                )
            )
        }
    }

    @Test
    fun selectKeywordIssue() {
        runBlocking {
            val issue = EntityGenerator.createIssue()
            dao.insert(issue)
            val result = dao.search(issue.title.take(1)).first()
            MatcherAssert.assertThat(
                result,
                CoreMatchers.equalTo(
                    listOf(
                        IssueWithLabelAndCommentOnStash(
                            IssueWithLabelAndComment(
                                issue,
                                emptyList(),
                                emptyList()
                            ),
                            false
                        )
                    )
                )
            )
        }
    }
}
