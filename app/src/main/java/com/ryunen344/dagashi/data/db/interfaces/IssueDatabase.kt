package com.ryunen344.dagashi.data.db.interfaces

import com.ryunen344.dagashi.model.Issue
import kotlinx.coroutines.flow.Flow

interface IssueDatabase {
    fun issues(number: Int): Flow<List<Issue>>

    fun stashedIssues(): Flow<List<Issue>>

    fun issuesByKeyword(keyword: String): Flow<List<Issue>>

    suspend fun saveIssue(issues: List<Issue>)

    suspend fun stashIssue(issue: Issue)

    suspend fun unStashIssue(issue: Issue)
}
