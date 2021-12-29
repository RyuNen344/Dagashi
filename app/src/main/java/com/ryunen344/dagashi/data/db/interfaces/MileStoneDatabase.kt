package com.ryunen344.dagashi.data.db.interfaces

import com.ryunen344.dagashi.model.MileStone
import kotlinx.coroutines.flow.Flow

interface MileStoneDatabase {
    fun mileStones(): Flow<List<MileStone>>

    suspend fun saveMileStones(mileStones: List<MileStone>)
}
