package com.ryunen344.dagashi.ui.milestones.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ryunen344.dagashi.data.repository.MileStoneRepository
import com.ryunen344.dagashi.data.repository.SettingRepository
import com.ryunen344.dagashi.di.DefaultDispatcher
import com.ryunen344.dagashi.util.ext.isPassedDay
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
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
) : ViewModel(), MileStonesViewState, MileStonesViewEvent, MileStonesViewAction {

    private val viewModelDefaultScope = CoroutineScope(viewModelScope.coroutineContext + defaultDispatcher)

    private val _uiModel: MutableStateFlow<MileStonesViewState.UiModel> = MutableStateFlow(MileStonesViewState.UiModel.NonInitialized)
    override val uiModel: StateFlow<MileStonesViewState.UiModel>
        get() = _uiModel

    private val _isUpdated: MutableSharedFlow<Unit> = MutableSharedFlow()
    override val isUpdated: SharedFlow<Unit>
        get() = _isUpdated

    init {
        bindOutput()
        refresh()
    }

    private fun bindOutput() {
        mileStoneRepository
            .mileStones()
            .onEach {
                _uiModel.value = _uiModel.value.copy(mileStones = it)
            }.launchIn(viewModelDefaultScope)

        uiModel.filter { it.mileStones != null }
            .drop(1)
            .zip(uiModel.filter { it.mileStones != null }) { new, old -> new.mileStones != old.mileStones }
            .filter { it }
            .flowOn(defaultDispatcher)
            .onEach {
                _isUpdated.emit(Unit)
            }.launchIn(viewModelDefaultScope)
    }

    private fun refresh() {
        viewModelDefaultScope.launch {
            runCatching {
                val needsUpdate = settingRepository.mileStoneLastUpdateAt().firstOrNull().isPassedDay
                if (needsUpdate) {
                    _uiModel.value = _uiModel.value.copy(isRefreshing = true, error = null)
                    delay(300)
                    mileStoneRepository.refresh()
                    settingRepository.updateMileStoneLastUpdateAt(OffsetDateTime.now())
                    _uiModel.value = _uiModel.value.copy(isRefreshing = false, error = null)
                }
            }.onFailure {
                Timber.wtf(it)
                _uiModel.value = _uiModel.value.copy(isRefreshing = false, error = it)
            }
        }
    }
}
