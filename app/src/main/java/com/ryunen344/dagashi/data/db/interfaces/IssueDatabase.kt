package com.ryunen344.dagashi.data.db.interfaces

import com.ryunen344.dagashi.data.db.entity.combined.IssueWithLabelAndComment
import kotlinx.coroutines.flow.Flow

interface IssueDatabase {
    fun issueEntity(number: Int): Flow<List<IssueWithLabelAndComment>>

    suspend fun saveIssue(entity: List<IssueWithLabelAndComment>)
}
