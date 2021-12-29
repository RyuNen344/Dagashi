package com.ryunen344.dagashi.data.repository.impl

import com.ryunen344.dagashi.data.api.DagashiApi
import com.ryunen344.dagashi.data.db.interfaces.IssueDatabase
import com.ryunen344.dagashi.data.repository.IssueRepository
import com.ryunen344.dagashi.di.IoDispatcher
import com.ryunen344.dagashi.model.Issue
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class IssueRepositoryImpl @Inject constructor(
    private val dagashiApi: DagashiApi,
    private val issueDatabase: IssueDatabase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : IssueRepository {

    override suspend fun refresh(path: String) {
        withContext(dispatcher) {
            val response = dagashiApi.issues(path)
            issueDatabase.saveIssue(response)
        }
    }

    override suspend fun stashIssue(issue: Issue) {
        withContext(dispatcher) {
            issueDatabase.stashIssue(issue)
        }
    }

    override suspend fun unStashIssue(issue: Issue) {
        withContext(dispatcher) {
            issueDatabase.unStashIssue(issue)
        }
    }

    override fun issues(number: Int): Flow<List<Issue>> {
        return issueDatabase.issues(number)
            .flowOn(dispatcher)
    }

    override fun stashedIssues(): Flow<List<Issue>> {
        return issueDatabase.stashedIssues()
            .flowOn(dispatcher)
    }

    override fun issuesByKeyword(keyword: String): Flow<List<Issue>> {
        return issueDatabase.issuesByKeyword(keyword)
            .flowOn(dispatcher)
    }

    @Deprecated("Don't use except for remote test")
    private suspend fun issueFromApi(path: String): List<Issue> {
        return withContext(dispatcher) {
            dagashiApi.issues(path)
        }
    }
}
