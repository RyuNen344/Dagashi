package com.ryunen344.dagashi.data.repository

import com.ryunen344.dagashi.model.MileStone
import kotlinx.coroutines.flow.Flow

interface MileStoneRepository {
    val mileStones: Flow<MileStone>
    suspend fun refresh()
    suspend fun mileStones(): List<MileStone>
}
