package com.ryunen344.dagashi.data.db.interfaces

import com.ryunen344.dagashi.data.db.entity.combined.MileStoneWithSummaryIssue
import kotlinx.coroutines.flow.Flow

interface MileStoneDatabase {
    fun mileStoneEntity(): Flow<List<MileStoneWithSummaryIssue>>

    suspend fun saveMileStone(entity: List<MileStoneWithSummaryIssue>)
}
