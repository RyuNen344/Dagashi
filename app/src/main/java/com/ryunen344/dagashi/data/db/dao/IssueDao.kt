package com.ryunen344.dagashi.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.ryunen344.dagashi.data.db.entity.CommentEntity
import com.ryunen344.dagashi.data.db.entity.IssueEntity
import com.ryunen344.dagashi.data.db.entity.LabelEntity
import com.ryunen344.dagashi.data.db.entity.combined.IssueWithLabelAndComment
import com.ryunen344.dagashi.data.db.entity.relation.IssueLabelCrossRef
import kotlinx.coroutines.flow.Flow

@Dao
abstract class IssueDao : BaseDao<IssueEntity>() {
    @Transaction
    @Query("SELECT * FROM issue WHERE number = :number ORDER BY id ASC")
    abstract fun select(number: Int): Flow<List<IssueWithLabelAndComment>>
}

@Dao
abstract class LabelDao : BaseDao<LabelEntity>()

@Dao
abstract class IssueLabelCrossRefDao : BaseDao<IssueLabelCrossRef>()

@Dao
abstract class CommentDao : BaseDao<CommentEntity>()
