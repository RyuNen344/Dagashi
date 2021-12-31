package com.ryunen344.dagashi.data.repository.impl

import com.ryunen344.dagashi.data.api.DagashiApi
import com.ryunen344.dagashi.test.MainCoroutineTestRule
import io.mockk.mockk
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class IssueRepositoryImplTest {

    @get:Rule
    val mainCoroutineTestRule = MainCoroutineTestRule()

    private val mockDagashiApi: DagashiApi = mockk()
//
//    private val mockIssueDatabase: IssueDatabase = mockk()
//
//    private val issueRepositoryImpl = IssueRepositoryImpl(mockDagashiApi, mockIssueDatabase, mainCoroutineTestRule.dispatcher)

    @Test
    fun addition_isCorrect() {
        mainCoroutineTestRule.runBlockingTest {
            Assert.assertEquals(4, 2 + 2)
        }
    }

//    @Test
//    fun refresh() {
//        mainCoroutineTestRule.runBlockingTest {
//            val issues = ResponseGenerator.createIssueRootResponse().toModel()
//            coEvery { mockDagashiApi.issues("path") } answers { issues }
//            coEvery { mockIssueDatabase.saveIssue(issues) } answers {}
//
//            issueRepositoryImpl.refresh("path")
//
//            coVerify { mockDagashiApi.issues("path") }
//            coVerify { mockIssueDatabase.saveIssue(issues) }
//            confirmVerified(mockDagashiApi, mockIssueDatabase)
//        }
//    }
//
//    @Test
//    fun issue() {
//        mainCoroutineTestRule.runBlockingTest {
//            val db = EntityGenerator.createIssueWithLabelAndCommentOnStashes().map { it.toModel() }
//
//            coEvery { mockIssueDatabase.issues(0) } answers { flowOf(db) }
//
//            val mappedList = issueRepositoryImpl.issues(0).single()
//
//            coVerify { mockIssueDatabase.issues(0) }
//
//            MatcherAssert.assertThat(mappedList, CoreMatchers.equalTo(db))
//        }
//    }
//
//    @Test
//    fun issueByKeyword() {
//        mainCoroutineTestRule.runBlockingTest {
//            val db = EntityGenerator.createIssueWithLabelAndCommentOnStashes().map { it.toModel() }
//
//            coEvery { mockIssueDatabase.issuesByKeyword("keyword") } answers { flowOf(db) }
//
//            val mappedList = issueRepositoryImpl.issuesByKeyword("keyword").single()
//
//            coVerify { mockIssueDatabase.issuesByKeyword("keyword") }
//
//            MatcherAssert.assertThat(mappedList, CoreMatchers.equalTo(db))
//        }
//    }
}
