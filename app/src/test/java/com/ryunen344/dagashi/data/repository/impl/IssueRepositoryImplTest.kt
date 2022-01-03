package com.ryunen344.dagashi.data.repository.impl

import com.ryunen344.dagashi.data.api.DagashiApi
import com.ryunen344.dagashi.data.db.interfaces.IssueDatabase
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

class IssueRepositoryImplTest {

    @get:Rule
    val mainCoroutineTestRule = MainCoroutineTestRule()

    private val mockDagashiApi: DagashiApi = mockk()

    private val mockIssueDatabase: IssueDatabase = mockk()

    private val issueRepositoryImpl = IssueRepositoryImpl(mockDagashiApi, mockIssueDatabase, mainCoroutineTestRule.dispatcher)

    @Test
    fun refresh() {
        mainCoroutineTestRule.runBlockingTest {
            val path = "path"
            val issues = listOf(
                ModelGenerator.createIssue()
            )
            coEvery { mockDagashiApi.issues(path) } coAnswers { issues }
            coEvery { mockIssueDatabase.saveIssue(issues) } just Runs

            issueRepositoryImpl.refresh(path)

            coVerify { mockDagashiApi.issues(path) }
            coVerify { mockIssueDatabase.saveIssue(issues) }
            confirmVerified(mockDagashiApi, mockIssueDatabase)
        }
    }

    @Test
    fun stashIssue() {
        mainCoroutineTestRule.runBlockingTest {
            val issue = ModelGenerator.createIssue().copy(isStashed = false)

            coEvery { mockIssueDatabase.stashIssue(issue) } just Runs

            issueRepositoryImpl.stashIssue(issue)

            coVerify { mockIssueDatabase.stashIssue(issue) }
            confirmVerified(mockIssueDatabase)
        }
    }

    @Test
    fun unStashIssue() {
        mainCoroutineTestRule.runBlockingTest {
            val issue = ModelGenerator.createIssue().copy(isStashed = true)

            coEvery { mockIssueDatabase.unStashIssue(issue) } just Runs

            issueRepositoryImpl.unStashIssue(issue)

            coVerify { mockIssueDatabase.unStashIssue(issue) }
            confirmVerified(mockIssueDatabase)
        }
    }

    @Test
    fun issues() {
        mainCoroutineTestRule.runBlockingTest {
            val issue = ModelGenerator.createIssue()
            val number = issue.singleUniqueId.substringBefore("_").toInt()

            coEvery { mockIssueDatabase.issues(number) } answers { flowOf(listOf(issue)) }

            val mappedList = issueRepositoryImpl.issues(number).single()

            coVerify { mockIssueDatabase.issues(number) }

            MatcherAssert.assertThat(mappedList, CoreMatchers.equalTo(listOf(issue)))
        }
    }

    @Test
    fun stashedIssues() {
        mainCoroutineTestRule.runBlockingTest {
            val issue = ModelGenerator.createIssue().copy(isStashed = true)

            coEvery { mockIssueDatabase.stashedIssues() } answers { flowOf(listOf(issue)) }

            val mappedList = issueRepositoryImpl.stashedIssues().single()

            coVerify { mockIssueDatabase.stashedIssues() }

            MatcherAssert.assertThat(mappedList, CoreMatchers.equalTo(listOf(issue)))
        }
    }

    @Test
    fun issuesByKeyword() {
        mainCoroutineTestRule.runBlockingTest {
            val issue = ModelGenerator.createIssue()
            val keyword = "keyword"

            coEvery { mockIssueDatabase.issuesByKeyword(keyword) } answers { flowOf(listOf(issue)) }

            val mappedList = issueRepositoryImpl.issuesByKeyword(keyword).single()

            coVerify { mockIssueDatabase.issuesByKeyword(keyword) }

            MatcherAssert.assertThat(mappedList, CoreMatchers.equalTo(listOf(issue)))
        }
    }
}
