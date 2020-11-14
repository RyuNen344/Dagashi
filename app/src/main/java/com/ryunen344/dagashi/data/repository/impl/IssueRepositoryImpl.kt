package com.ryunen344.dagashi.data.repository.impl

import com.ryunen344.dagashi.data.api.DagashiApi
import com.ryunen344.dagashi.data.repository.IssueRepository
import com.ryunen344.dagashi.data.repository.mapper.IssueMapper
import com.ryunen344.dagashi.model.Issue
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class IssueRepositoryImpl @Inject constructor(private val dagashiApi: DagashiApi, private val dispatcher: CoroutineDispatcher) : IssueRepository {

    override val issues: Flow<List<Issue>> = emptyFlow()

    override suspend fun refresh() {
        // TODO: 2020/11/14
    }

    override suspend fun issue(path: String): List<Issue> {
        return withContext(dispatcher) {
            dagashiApi.issues(path).issues.nodes.map(IssueMapper::toModel)
        }
    }
}
