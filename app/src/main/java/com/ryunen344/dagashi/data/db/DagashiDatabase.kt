package com.ryunen344.dagashi.data.db

import androidx.room.withTransaction
import com.ryunen344.dagashi.data.db.entity.StashedIssueEntity
import com.ryunen344.dagashi.data.db.entity.combined.IssueWithLabelAndComment
import com.ryunen344.dagashi.data.db.entity.combined.IssueWithLabelAndCommentOnStash
import com.ryunen344.dagashi.data.db.entity.combined.MileStoneWithSummaryIssue
import com.ryunen344.dagashi.data.db.entity.relation.IssueLabelCrossRef
import com.ryunen344.dagashi.data.db.interfaces.IssueDatabase
import com.ryunen344.dagashi.data.db.interfaces.MileStoneDatabase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DagashiDatabase @Inject constructor(
    private val cacheDatabase: CacheDatabase
) : MileStoneDatabase, IssueDatabase {

    override fun mileStoneEntity(): Flow<List<MileStoneWithSummaryIssue>> {
        return cacheDatabase.mileStoneDao.select()
    }

    override suspend fun saveMileStone(entity: List<MileStoneWithSummaryIssue>) {
        cacheDatabase.withTransaction {
            cacheDatabase.mileStoneDao.insertOrUpdate(entity.map { it.mileStoneEntity })
            cacheDatabase.summaryIssueDao.insertOrUpdate(entity.flatMap { it.issues })
        }
    }

    override fun issueEntity(number: Int): Flow<List<IssueWithLabelAndCommentOnStash>> {
        return cacheDatabase.issueDao.select(number)
    }

    override fun issueOnStashed(): Flow<List<IssueWithLabelAndCommentOnStash>> {
        return cacheDatabase.issueDao.stashed()
    }

    override fun issueEntityByKeyword(keyword: String): Flow<List<IssueWithLabelAndCommentOnStash>> {
        return cacheDatabase.issueDao.search(keyword)
    }

    override suspend fun saveIssue(entity: List<IssueWithLabelAndComment>) {
        cacheDatabase.withTransaction {
            cacheDatabase.issueDao.insertOrUpdate(entity.map { it.issueEntity })
            cacheDatabase.labelDao.insertOrUpdate(entity.flatMap { it.labels }.distinct())
            cacheDatabase.issueLabelCrossRefDao.insertOrUpdate(
                entity.flatMap { combined ->
                    combined.labels.map { label ->
                        IssueLabelCrossRef(
                            singleUniqueId = combined.issueEntity.singleUniqueId,
                            labelName = label.name
                        )
                    }
                }
            )
            cacheDatabase.commentDao.insertOrUpdate(entity.flatMap { it.comments })
        }
    }

    override suspend fun stashIssue(stashedIssueEntity: StashedIssueEntity) {
        cacheDatabase.stashedIssueDao.insertOrUpdate(stashedIssueEntity)
    }

    override suspend fun unStashIssue(stashedIssueEntity: StashedIssueEntity) {
        cacheDatabase.stashedIssueDao.delete(stashedIssueEntity)
    }
}
