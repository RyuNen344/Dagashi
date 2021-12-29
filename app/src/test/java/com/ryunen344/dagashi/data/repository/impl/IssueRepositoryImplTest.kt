package com.ryunen344.dagashi.data.repository.impl

import com.ryunen344.dagashi.data.api.DagashiApi
import com.ryunen344.dagashi.data.api.mapper.toModel
import com.ryunen344.dagashi.data.db.interfaces.IssueDatabase
import com.ryunen344.dagashi.data.db.mapper.toModel
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

class IssueRepositoryImplTest {

    @get:Rule
    val mainCoroutineTestRule = MainCoroutineTestRule()

    private val mockDagashiApi: DagashiApi = mockk()

    private val mockIssueDatabase: IssueDatabase = mockk()

    private val issueRepositoryImpl = IssueRepositoryImpl(mockDagashiApi, mockIssueDatabase, mainCoroutineTestRule.dispatcher)

    @Test
    fun refresh() {
        mainCoroutineTestRule.runBlockingTest {
            val issues = ResponseGenerator.createIssueRootResponse().toModel()
            coEvery { mockDagashiApi.issues("path") } answers { issues }
            coEvery { mockIssueDatabase.saveIssue(issues) } answers {}

            issueRepositoryImpl.refresh("path")

            coVerify { mockDagashiApi.issues("path") }
            coVerify { mockIssueDatabase.saveIssue(issues) }
            confirmVerified(mockDagashiApi, mockIssueDatabase)
        }
    }

    @Test
    fun issue() {
        mainCoroutineTestRule.runBlockingTest {
            val db = EntityGenerator.createIssueWithLabelAndCommentOnStashes().map { it.toModel() }

            coEvery { mockIssueDatabase.issues(0) } answers { flowOf(db) }

            val mappedList = issueRepositoryImpl.issues(0).single()

            coVerify { mockIssueDatabase.issues(0) }

            MatcherAssert.assertThat(mappedList, CoreMatchers.equalTo(db))
        }
    }

    @Test
    fun issueByKeyword() {
        mainCoroutineTestRule.runBlockingTest {
            val db = EntityGenerator.createIssueWithLabelAndCommentOnStashes().map { it.toModel() }

            coEvery { mockIssueDatabase.issuesByKeyword("keyword") } answers { flowOf(db) }

            val mappedList = issueRepositoryImpl.issuesByKeyword("keyword").single()

            coVerify { mockIssueDatabase.issuesByKeyword("keyword") }

            MatcherAssert.assertThat(mappedList, CoreMatchers.equalTo(db))
        }
    }
}
