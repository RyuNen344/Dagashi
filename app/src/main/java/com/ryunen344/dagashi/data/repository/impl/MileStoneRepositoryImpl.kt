package com.ryunen344.dagashi.data.repository.impl

import com.ryunen344.dagashi.data.api.DagashiApi
import com.ryunen344.dagashi.data.db.interfaces.MileStoneDatabase
import com.ryunen344.dagashi.data.repository.MileStoneRepository
import com.ryunen344.dagashi.data.repository.mapper.MileStoneMapper
import com.ryunen344.dagashi.data.repository.mapper.MileStoneMapper.toModel
import com.ryunen344.dagashi.di.IoDispatcher
import com.ryunen344.dagashi.model.MileStone
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MileStoneRepositoryImpl @Inject constructor(
    private val dagashiApi: DagashiApi,
    private val mileStoneDatabase: MileStoneDatabase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : MileStoneRepository {

    override suspend fun refresh() {
        withContext(dispatcher) {
            val response = dagashiApi.milestones().milestones.nodes.map(MileStoneMapper::toEntity)
            mileStoneDatabase.saveMileStone(response)
        }
    }

    override fun mileStones(): Flow<List<MileStone>> {
        return mileStoneDatabase.mileStoneEntity()
            .map { it.map(::toModel) }
            .flowOn(dispatcher)
    }

    private suspend fun mileStonesFromApi(): List<MileStone> {
        return withContext(dispatcher) {
            dagashiApi.milestones().milestones.nodes.map(MileStoneMapper::toModel)
        }
    }
}
