package com.ryunen344.dagashi.ui.milestones.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ryunen344.dagashi.data.repository.MileStoneRepository
import com.ryunen344.dagashi.data.repository.SettingRepository
import com.ryunen344.dagashi.di.DefaultDispatcher
import com.ryunen344.dagashi.model.MileStone
import com.ryunen344.dagashi.util.ext.isPassedDay
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.threeten.bp.OffsetDateTime
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MileStonesViewModel @Inject constructor(
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
