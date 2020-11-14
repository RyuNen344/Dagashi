package com.ryunen344.dagashi.ui.milestones

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ryunen344.dagashi.data.repository.MileStoneRepository
import com.ryunen344.dagashi.model.MileStone
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch

class MileStonesViewModel @ViewModelInject constructor(private val mileStoneRepository: MileStoneRepository) : ViewModel() {

    private val _mileStones: ConflatedBroadcastChannel<List<MileStone>> = ConflatedBroadcastChannel()
    val mileStones: Flow<List<MileStone>>
        get() = _mileStones.asFlow()

    init {
        viewModelScope.launch {
            _mileStones.offer(mileStoneRepository.mileStones())
        }
    }
}
