package com.ryunen344.dagashi.data.db

import androidx.room.withTransaction
import com.ryunen344.dagashi.data.db.entity.combined.IssueWithLabelAndComment
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
            cacheDatabase.mileStoneDao.insertMileStone(entity.map { it.mileStoneEntity })
            cacheDatabase.mileStoneDao.insertSummaryIssue(entity.flatMap { it.issues })
        }
    }

    override fun issueEntity(number: Int): Flow<List<IssueWithLabelAndComment>> {
        return cacheDatabase.issueDao.select(number)
    }

    override suspend fun saveIssue(entity: List<IssueWithLabelAndComment>) {
        cacheDatabase.withTransaction {
            cacheDatabase.issueDao.insertIssue(entity.map { it.issueEntity })
            cacheDatabase.issueDao.insertLabel(entity.flatMap { it.labels }.distinct())
            cacheDatabase.issueDao.insertIssueAndLabel(
                entity.flatMap { combined ->
                    combined.labels.map { label ->
                        IssueLabelCrossRef(
                            singleUniqueId = combined.issueEntity.singleUniqueId,
                            labelName = label.name
                        )
                    }
                }
            )
            cacheDatabase.issueDao.insertComment(entity.flatMap { it.comments })
        }
    }
}
