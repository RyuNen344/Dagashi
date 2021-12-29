package com.ryunen344.dagashi.data.db

import androidx.room.withTransaction
import com.ryunen344.dagashi.data.db.entity.relation.IssueLabelCrossRef
import com.ryunen344.dagashi.data.db.interfaces.IssueDatabase
import com.ryunen344.dagashi.data.db.interfaces.MileStoneDatabase
import com.ryunen344.dagashi.data.db.mapper.toEntity
import com.ryunen344.dagashi.data.db.mapper.toModel
import com.ryunen344.dagashi.data.db.mapper.toStashedEntity
import com.ryunen344.dagashi.model.Issue
import com.ryunen344.dagashi.model.MileStone
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DagashiDatabase @Inject constructor(
    private val cacheDatabase: CacheDatabase
) : MileStoneDatabase, IssueDatabase {

    override fun mileStones(): Flow<List<MileStone>> {
        return cacheDatabase.mileStoneDao.select().map { list -> list.map { it.toModel() } }
    }

    override suspend fun saveMileStones(mileStones: List<MileStone>) {
        cacheDatabase.withTransaction {
            cacheDatabase.mileStoneDao.insertOrUpdate(mileStones.map { it.toEntity() })
            cacheDatabase.summaryIssueDao.insertOrUpdate(mileStones.flatMap { it.issues }.map { it.toEntity() })
        }
    }

    override fun issues(number: Int): Flow<List<Issue>> {
        return cacheDatabase.issueDao.select(number).map { list -> list.map { it.toModel() } }
    }

    override fun stashedIssues(): Flow<List<Issue>> {
        return cacheDatabase.issueDao.stashed().map { list -> list.map { it.toModel() } }
    }

    override fun issuesByKeyword(keyword: String): Flow<List<Issue>> {
        return cacheDatabase.issueDao.search(keyword).map { list -> list.map { it.toModel() } }
    }

    override suspend fun saveIssue(issues: List<Issue>) {
        cacheDatabase.withTransaction {
            cacheDatabase.issueDao.insertOrUpdate(issues.map { it.toEntity() })
            cacheDatabase.labelDao.insertOrUpdate(
                issues
                    .flatMap { it.labels }
                    .distinct()
                    .map { it.toEntity() }
            )
            cacheDatabase.issueLabelCrossRefDao.insertOrUpdate(
                issues.flatMap { combined ->
                    combined.labels.map { label ->
                        IssueLabelCrossRef(
                            singleUniqueId = combined.singleUniqueId,
                            labelName = label.name
                        )
                    }
                }
            )
            cacheDatabase.commentDao.insertOrUpdate(issues.flatMap { it.comments }.map { it.toEntity() })
        }
    }

    override suspend fun stashIssue(issue: Issue) {
        cacheDatabase.stashedIssueDao.insertOrUpdate(issue.toStashedEntity())
    }

    override suspend fun unStashIssue(issue: Issue) {
        cacheDatabase.stashedIssueDao.delete(issue.toStashedEntity())
    }
}
