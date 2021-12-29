package com.ryunen344.dagashi.data.repository

import com.ryunen344.dagashi.model.Issue
import kotlinx.coroutines.flow.Flow

interface IssueRepository {
    suspend fun refresh(path: String)
    suspend fun stashIssue(issue: Issue)
    suspend fun unStashIssue(issue: Issue)
    fun issues(number: Int): Flow<List<Issue>>
    fun stashedIssues(): Flow<List<Issue>>
    fun issuesByKeyword(keyword: String): Flow<List<Issue>>
}
