package com.ryunen344.dagashi.data.repository.impl

import com.ryunen344.dagashi.data.api.DagashiApi
import com.ryunen344.dagashi.data.db.interfaces.MileStoneDatabase
import com.ryunen344.dagashi.data.repository.MileStoneRepository
import com.ryunen344.dagashi.di.IoDispatcher
import com.ryunen344.dagashi.model.MileStone
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MileStoneRepositoryImpl @Inject constructor(
    private val dagashiApi: DagashiApi,
    private val mileStoneDatabase: MileStoneDatabase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : MileStoneRepository {

    override suspend fun refresh() {
        withContext(dispatcher) {
            val response = dagashiApi.milestones()
            mileStoneDatabase.saveMileStones(response)
        }
    }

    override fun mileStones(): Flow<List<MileStone>> {
        return mileStoneDatabase.mileStones()
            .flowOn(dispatcher)
    }
}
