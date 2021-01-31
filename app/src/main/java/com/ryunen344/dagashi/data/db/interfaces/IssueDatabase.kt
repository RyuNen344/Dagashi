package com.ryunen344.dagashi.data.db.interfaces

import com.ryunen344.dagashi.data.db.entity.StashedIssueEntity
import com.ryunen344.dagashi.data.db.entity.combined.IssueWithLabelAndComment
import com.ryunen344.dagashi.data.db.entity.combined.IssueWithLabelAndCommentOnStash
import kotlinx.coroutines.flow.Flow

interface IssueDatabase {
    fun issueEntity(number: Int): Flow<List<IssueWithLabelAndCommentOnStash>>

    fun issueEntityByKeyword(keyword: String): Flow<List<IssueWithLabelAndCommentOnStash>>

    suspend fun saveIssue(entity: List<IssueWithLabelAndComment>)

    suspend fun stashIssue(stashedIssueEntity: StashedIssueEntity)

    suspend fun unStashIssue(stashedIssueEntity: StashedIssueEntity)
}
