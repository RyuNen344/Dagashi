package com.ryunen344.dagashi.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.ryunen344.dagashi.data.db.entity.MileStoneEntity
import com.ryunen344.dagashi.data.db.entity.SummaryIssueEntity
import com.ryunen344.dagashi.data.db.entity.combined.MileStoneWithSummaryIssue
import kotlinx.coroutines.flow.Flow

@Dao
interface MileStoneDao {
    @Transaction
    @Query("SELECT * FROM mile_stone ORDER BY number DESC")
    fun select(): Flow<List<MileStoneWithSummaryIssue>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMileStone(entity: List<MileStoneEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSummaryIssue(entity: List<SummaryIssueEntity>)
}
