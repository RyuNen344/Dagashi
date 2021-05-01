package com.ryunen344.dagashi.ui.setting.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ryunen344.dagashi.data.repository.MileStoneRepository
import com.ryunen344.dagashi.data.repository.SettingRepository
import com.ryunen344.dagashi.di.DefaultDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    private val settingRepository: SettingRepository,
    private val mileStoneRepository: MileStoneRepository
) : ViewModel(), SettingViewModelInput, SettingViewModelOutput {

    private val viewModelDefaultScope = CoroutineScope(viewModelScope.coroutineContext + defaultDispatcher)

    private val _isOpenInWebView: MutableSharedFlow<Boolean> =
        MutableSharedFlow(replay = 1, extraBufferCapacity = 0, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    override val isOpenInWebView: Flow<Boolean>
        get() = _isOpenInWebView

    init {
        bindOutput()
    }

    private fun bindOutput() {
        settingRepository
            .isOpenInWebView()
            .onEach {
                _isOpenInWebView.emit(it)
            }.launchIn(viewModelDefaultScope)
    }

    override fun toggleSettingBrowser(isCurrentChecked: Boolean) {
        viewModelDefaultScope.launch {
            runCatching {
                settingRepository.updateIsOpenInWebView(!isCurrentChecked)
            }.onFailure {
                Timber.e(it)
            }
        }
    }

    override fun refreshMileStones() {
        viewModelDefaultScope.launch {
            runCatching {
                mileStoneRepository.refresh()
            }.onFailure {
                Timber.e(it)
            }
        }
    }

    override fun onCleared() {
        viewModelDefaultScope.cancel()
        super.onCleared()
    }
}
