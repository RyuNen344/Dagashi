package com.ryunen344.dagashi.ui.issues

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ryunen344.dagashi.data.repository.IssueRepository
import com.ryunen344.dagashi.data.repository.SettingRepository
import com.ryunen344.dagashi.di.DefaultDispatcher
import com.ryunen344.dagashi.model.Issue
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.produceIn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.random.Random

class IssuesViewModel @ViewModelInject constructor(
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    private val issueRepository: IssueRepository,
    private val settingRepository: SettingRepository
) : ViewModel() {

    private val viewModelDefaultScope = CoroutineScope(viewModelScope.coroutineContext + defaultDispatcher)

    private val _issues: MutableSharedFlow<List<Issue>> =
        MutableSharedFlow(replay = 1, extraBufferCapacity = 0, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val issues: Flow<List<Issue>>
        get() = _issues

    private val _isUpdated: Channel<Unit> = Channel(capacity = Channel.BUFFERED)
    val isUpdated: Flow<Unit>
        get() = _isUpdated.receiveAsFlow()

    private val openUrl: MutableSharedFlow<String> = MutableSharedFlow()
    val openUrlModel: Flow<OpenUrlModel>
        get() = combine(openUrl, settingRepository.isOpenInWebView().take(1)) { url, isOpen ->
            if (isOpen) OpenUrlModel.WebView(url) else OpenUrlModel.ChromeTabs(url)
        }

    init {
        issues
            .drop(1)
            .zip(issues) { new, old -> new != old }
            .filter { it }
            .map { Unit }
            .flowOn(defaultDispatcher)
            .onEach {
                _isUpdated.send(Unit)
            }.launchIn(viewModelDefaultScope)
    }

    fun refresh(number: Int, path: String) {
        issueRepository.issue(number).onEach {
            _issues.emit(it)
        }.launchIn(viewModelDefaultScope)
        viewModelDefaultScope.launch {
            runCatching {
                issueRepository.refresh(path)
            }.onFailure {
                Timber.e(it)
            }
        }
    }

    fun inputUrl(url: String) {
        viewModelDefaultScope.launch {
            openUrl.emit(url)
        }
    }

    override fun onCleared() {
        viewModelDefaultScope.cancel()
        super.onCleared()
    }

    sealed class OpenUrlModel {
        class WebView(val url: String) : OpenUrlModel()
        class ChromeTabs(val url: String) : OpenUrlModel()
    }
}
