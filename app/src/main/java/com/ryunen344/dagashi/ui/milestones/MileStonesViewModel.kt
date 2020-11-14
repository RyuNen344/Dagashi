package com.ryunen344.dagashi.ui.milestones

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ryunen344.dagashi.data.repository.MileStoneRepository
import com.ryunen344.dagashi.model.MileStone
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.launch

class MileStonesViewModel @ViewModelInject constructor(private val mileStoneRepository: MileStoneRepository) : ViewModel() {

    private val _mileStones: ConflatedBroadcastChannel<List<MileStone>> = ConflatedBroadcastChannel()
    val mileStones: Flow<List<MileStone>>
        get() = _mileStones.asFlow()

    val isUpdated: Flow<Boolean>
        get() = combine(mileStones.drop(1), mileStones) { old, new -> old != new }

    init {
        viewModelScope.launch {
            _mileStones.offer(mileStoneRepository.mileStones())
        }
    }

    override fun onCleared() {
        _mileStones.cancel()
        super.onCleared()
    }
}
