package com.ryunen344.dagashi.data.repository.impl

import com.ryunen344.dagashi.data.api.DagashiApi
import com.ryunen344.dagashi.data.repository.MileStoneRepository
import com.ryunen344.dagashi.data.repository.mapper.MilestoneMapper
import com.ryunen344.dagashi.model.MileStone
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MileStoneRepositoryImpl @Inject constructor(
    private val dagashiApi: DagashiApi,
    private val dispatcher: CoroutineDispatcher
) : MileStoneRepository {

    override val mileStones: Flow<MileStone> = emptyFlow()

    override suspend fun refresh() {
        // TODO: 2020/11/14
    }

    override suspend fun mileStones(): List<MileStone> {
        return withContext(dispatcher) {
            dagashiApi.milestones().milestones.nodes.map(MilestoneMapper::toModel)
        }
    }
}
