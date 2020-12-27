package com.ryunen344.dagashi.ui.issues.path.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ryunen344.dagashi.data.repository.IssueRepository
import com.ryunen344.dagashi.data.repository.SettingRepository
import com.ryunen344.dagashi.di.DefaultDispatcher
import com.ryunen344.dagashi.model.Issue
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import timber.log.Timber

class PathIssuesViewModel @AssistedInject constructor(
    @Assisted private val number: Int,
    @Assisted private val path: String,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    private val issueRepository: IssueRepository,
    private val settingRepository: SettingRepository
) : ViewModel(), PathIssuesViewModelInput, PathIssuesViewModelOutput {

    @AssistedInject.Factory
    interface AssistedFactory {
        fun create(number: Int, path: String): PathIssuesViewModel
    }

    private val viewModelDefaultScope = CoroutineScope(viewModelScope.coroutineContext + defaultDispatcher)

    private val _issues: MutableSharedFlow<List<Issue>> =
        MutableSharedFlow(replay = 1, extraBufferCapacity = 0, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    override val issues: Flow<List<Issue>>
        get() = _issues

    private val _isUpdated: Channel<Unit> = Channel(capacity = Channel.BUFFERED)
    override val isUpdated: Flow<Unit>
        get() = _isUpdated.receiveAsFlow()

    private val openUrl: MutableSharedFlow<String> = MutableSharedFlow()
    override val openUrlModel: Flow<PathIssuesViewModelOutput.OpenUrlModel>
        get() = combine(openUrl, settingRepository.isOpenInWebView().take(1)) { url, isOpen ->
            if (isOpen) PathIssuesViewModelOutput.OpenUrlModel.WebView(url) else PathIssuesViewModelOutput.OpenUrlModel.ChromeTabs(url)
        }

    init {
        bindOutput()
        viewModelDefaultScope.launch {
            runCatching {
                issueRepository.refresh(path)
            }.onFailure {
                Timber.e(it)
            }
        }
    }

    private fun bindOutput() {
        issues
            .drop(1)
            .zip(issues) { new, old -> new != old }
            .filter { it }
            .flowOn(defaultDispatcher)
            .onEach {
                _isUpdated.send(Unit)
            }.launchIn(viewModelDefaultScope)

        issueRepository
            .issue(number)
            .onEach {
                _issues.emit(it)
            }.launchIn(viewModelDefaultScope)
    }

    override fun inputUrl(url: String) {
        viewModelDefaultScope.launch {
            openUrl.emit(url)
        }
    }

    override fun onCleared() {
        viewModelDefaultScope.cancel()
        super.onCleared()
    }
}
