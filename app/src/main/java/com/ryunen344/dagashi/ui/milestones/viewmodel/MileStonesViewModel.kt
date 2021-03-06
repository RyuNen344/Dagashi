package com.ryunen344.dagashi.ui.milestones.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ryunen344.dagashi.data.repository.MileStoneRepository
import com.ryunen344.dagashi.data.repository.SettingRepository
import com.ryunen344.dagashi.di.DefaultDispatcher
import com.ryunen344.dagashi.model.MileStone
import com.ryunen344.dagashi.util.ext.isPassedDay
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import org.threeten.bp.OffsetDateTime
import timber.log.Timber

class MileStonesViewModel @ViewModelInject constructor(
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    private val mileStoneRepository: MileStoneRepository,
    private val settingRepository: SettingRepository
) : ViewModel(), MileStonesViewModelInput, MileStonesViewModelOutput {

    private val viewModelDefaultScope = CoroutineScope(viewModelScope.coroutineContext + defaultDispatcher)

    private val _mileStones: MutableSharedFlow<List<MileStone>> =
        MutableSharedFlow(replay = 1, extraBufferCapacity = 0, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    override val mileStones: Flow<List<MileStone>>
        get() = _mileStones

    private val _isUpdated: Channel<Unit> = Channel(capacity = Channel.BUFFERED)
    override val isUpdated: Flow<Unit>
        get() = _isUpdated.receiveAsFlow()

    init {
        bindOutput()
        viewModelDefaultScope.launch {
            runCatching {
                val needsUpdate = settingRepository.mileStoneLastUpdateAt().firstOrNull().isPassedDay
                if (needsUpdate) {
                    mileStoneRepository.refresh()
                    settingRepository.updateMileStoneLastUpdateAt(OffsetDateTime.now())
                }
            }.onFailure {
                Timber.wtf(it)
            }
        }
    }

    private fun bindOutput() {
        mileStoneRepository
            .mileStones()
            .onEach {
                _mileStones.emit(it)
            }.launchIn(viewModelDefaultScope)
        mileStones
            .drop(1)
            .zip(mileStones) { new, old -> new != old }
            .filter { it }
            .map { Unit }
            .flowOn(defaultDispatcher)
            .onEach {
                _isUpdated.send(Unit)
            }.launchIn(viewModelDefaultScope)
    }

    override fun onCleared() {
        viewModelDefaultScope.cancel()
        super.onCleared()
    }
}
