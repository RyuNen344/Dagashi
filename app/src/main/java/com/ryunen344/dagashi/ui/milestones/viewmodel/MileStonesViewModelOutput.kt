package com.ryunen344.dagashi.ui.milestones.viewmodel

import com.ryunen344.dagashi.model.MileStone
import kotlinx.coroutines.flow.Flow

interface MileStonesViewModelOutput {
    val mileStones: Flow<List<MileStone>>
    val isUpdated: Flow<Unit>
}
