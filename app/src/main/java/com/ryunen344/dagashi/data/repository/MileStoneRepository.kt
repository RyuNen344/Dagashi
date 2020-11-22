package com.ryunen344.dagashi.data.repository

import com.ryunen344.dagashi.model.MileStone
import kotlinx.coroutines.flow.Flow

interface MileStoneRepository {
    suspend fun refresh()
    fun mileStones(): Flow<List<MileStone>>
}
