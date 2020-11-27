package com.ryunen344.dagashi.data.db.dao

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ryunen344.dagashi.data.db.entity.combined.IssueWithLabelAndComment
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
class IssueDaoTest {

    @get:Rule(order = 0)
    val mainCoroutineTestRule = MainCoroutineTestRule()

    @get:Rule(order = 1)
    val daoTestRule = DaoTestRule()

    private lateinit var dao: IssueDao

    @Before
    fun setup() {
        dao = daoTestRule.db.issueDao
    }

    @Test
    fun writeSingleIssue() {
        runBlocking {
            val issue = ModelGenerator.createIssue()
            dao.insert(issue)
            val result = dao.select(issue.number).first()
            MatcherAssert.assertThat(
                result,
                CoreMatchers.equalTo(
                    listOf(
                        IssueWithLabelAndComment(
                            issue,
                            emptyList(),
                            emptyList()
                        )
                    )
                )
            )
        }
    }

    @Test
    fun writeMultipleIssue() {
        runBlocking {
            val issues = ModelGenerator.createIssues()
            dao.insert(issues)
            val result = dao.select(issues.first().number).first()
            MatcherAssert.assertThat(
                result,
                CoreMatchers.equalTo(
                    issues.map {
                        IssueWithLabelAndComment(
                            it,
                            emptyList(),
                            emptyList()
                        )
                    }
                )
            )
        }
    }
}


