package com.ryunen344.dagashi.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.ryunen344.dagashi.data.db.entity.CommentEntity
import com.ryunen344.dagashi.data.db.entity.IssueEntity
import com.ryunen344.dagashi.data.db.entity.LabelEntity
import com.ryunen344.dagashi.data.db.entity.combined.IssueWithLabelAndComment
import com.ryunen344.dagashi.data.db.entity.relation.IssueLabelCrossRef
import kotlinx.coroutines.flow.Flow

@Dao
interface IssueDao {
    @Transaction
    @Query("SELECT * FROM issue WHERE number = :number ORDER BY id ASC")
    fun select(number: Int): Flow<List<IssueWithLabelAndComment>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIssue(entity: List<IssueEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLabel(entity: List<LabelEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIssueAndLabel(entity: List<IssueLabelCrossRef>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComment(entity: List<CommentEntity>)
}
