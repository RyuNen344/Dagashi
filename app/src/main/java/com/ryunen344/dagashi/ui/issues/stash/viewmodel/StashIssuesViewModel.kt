package com.ryunen344.dagashi.ui.issues.stash.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ryunen344.dagashi.data.repository.IssueRepository
import com.ryunen344.dagashi.data.repository.SettingRepository
import com.ryunen344.dagashi.di.DefaultDispatcher
import com.ryunen344.dagashi.model.Issue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StashIssuesViewModel @Inject constructor(
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    private val issueRepository: IssueRepository,
    private val settingRepository: SettingRepository
) : ViewModel(), StashIssuesViewModelInput, StashIssuesViewModelOutput {

    private val viewModelDefaultScope = CoroutineScope(viewModelScope.coroutineContext + defaultDispatcher)

    private val _issues: MutableSharedFlow<List<Issue>> =
        MutableSharedFlow(replay = 1, extraBufferCapacity = 0, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    override val issues: Flow<List<Issue>>
        get() = _issues

    private val openUrl: MutableSharedFlow<String> = MutableSharedFlow()
    override val openUrlModel: Flow<StashIssuesViewModelOutput.OpenUrlModel>
        get() = combine(openUrl, settingRepository.isOpenInWebView().take(1)) { url, isOpen ->
            if (isOpen) StashIssuesViewModelOutput.OpenUrlModel.WebView(url) else StashIssuesViewModelOutput.OpenUrlModel.ChromeTabs(url)
        }

    init {
        bindOutput()
    }

    private fun bindOutput() {
        issueRepository
            .issueOnStashed()
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

    override fun onCleared() {
        viewModelDefaultScope.cancel()
        super.onCleared()
    }
}
