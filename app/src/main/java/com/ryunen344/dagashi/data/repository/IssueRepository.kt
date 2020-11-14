package com.ryunen344.dagashi.data.repository

import com.ryunen344.dagashi.model.Issue
import kotlinx.coroutines.flow.Flow

interface IssueRepository {
    val issues: Flow<List<Issue>>
    suspend fun refresh()
    suspend fun issue(path: String): List<Issue>
}
