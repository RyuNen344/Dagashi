package com.ryunen344.dagashi.data.repository.impl

import com.ryunen344.dagashi.data.api.DagashiApi
import com.ryunen344.dagashi.data.db.interfaces.IssueDatabase
import com.ryunen344.dagashi.data.repository.IssueRepository
import com.ryunen344.dagashi.data.repository.mapper.IssueMapper
import com.ryunen344.dagashi.data.repository.mapper.IssueMapper.toModel
import com.ryunen344.dagashi.di.IoDispatcher
import com.ryunen344.dagashi.model.Issue
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
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
            issueDatabase.saveIssue(IssueMapper.toEntity(response))
        }
    }

    override fun issue(number: Int): Flow<List<Issue>> {
        return issueDatabase.issueEntity(number)
            .map { it.map(::toModel) }
            .flowOn(dispatcher)
    }

    private suspend fun issueFromApi(path: String): List<Issue> {
        return withContext(dispatcher) {
            dagashiApi.issues(path).issues.nodes.map(IssueMapper::toModel)
        }
    }
}
