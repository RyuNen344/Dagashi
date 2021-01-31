package com.ryunen344.dagashi.data.repository

import com.ryunen344.dagashi.model.Issue
import kotlinx.coroutines.flow.Flow

interface IssueRepository {
    suspend fun refresh(path: String)
    suspend fun stashIssue(singleUniqueId: String)
    suspend fun unStashIssue(singleUniqueId: String)
    fun issue(number: Int): Flow<List<Issue>>
    fun issueOnStashed(): Flow<List<Issue>>
    fun issueByKeyword(keyword: String): Flow<List<Issue>>
}
