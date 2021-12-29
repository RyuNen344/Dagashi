package com.ryunen344.dagashi.ui.issues.path.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ryunen344.dagashi.data.repository.IssueRepository
import com.ryunen344.dagashi.data.repository.SettingRepository
import com.ryunen344.dagashi.di.DefaultDispatcher
import com.ryunen344.dagashi.model.Issue
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

class PathIssuesViewModel @AssistedInject constructor(
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    private val issueRepository: IssueRepository,
    private val settingRepository: SettingRepository,
    @Assisted private val number: Int,
    @Assisted private val path: String,
) : ViewModel(), PathIssuesViewModelInput, PathIssuesViewModelOutput {

    @AssistedFactory
    interface ViewModelFactory {
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
            .zip(issues) { new, old -> new.map { it.copy(isStashed = false) } != old.map { it.copy(isStashed = false) } }
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

    override fun toggleStash(issue: Issue) {
        viewModelDefaultScope.launch {
            if (issue.isStashed) {
                issueRepository.unStashIssue(issue.singleUniqueId)
            } else {
                issueRepository.stashIssue(issue.singleUniqueId)
            }
        }
    }

    companion object {
        fun provideFactory(
            assistedFactory: ViewModelFactory,
            number: Int,
            path: String
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return assistedFactory.create(number, path) as T
            }
        }
    }
}
