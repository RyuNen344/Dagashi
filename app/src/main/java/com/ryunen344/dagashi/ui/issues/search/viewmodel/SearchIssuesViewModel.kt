package com.ryunen344.dagashi.ui.issues.search.viewmodel

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
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import timber.log.Timber

class SearchIssuesViewModel @ViewModelInject constructor(
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    private val issueRepository: IssueRepository,
    private val settingRepository: SettingRepository
) : ViewModel(), SearchIssuesViewModelInput, SearchIssuesViewModelOutput {

    private val viewModelDefaultScope = CoroutineScope(viewModelScope.coroutineContext + defaultDispatcher)

    private val _issues: MutableSharedFlow<List<Issue>> =
        MutableSharedFlow(replay = 1, extraBufferCapacity = 0, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    override val issues: Flow<List<Issue>>
        get() = _issues

    private val openUrl: MutableSharedFlow<String> = MutableSharedFlow()
    override val openUrlModel: Flow<SearchIssuesViewModelOutput.OpenUrlModel>
        get() = combine(openUrl, settingRepository.isOpenInWebView().take(1)) { url, isOpen ->
            if (isOpen) SearchIssuesViewModelOutput.OpenUrlModel.WebView(url) else SearchIssuesViewModelOutput.OpenUrlModel.ChromeTabs(url)
        }

    override fun searchIssue(keyword: String) {
        viewModelDefaultScope.launch {
            runCatching {
                issueRepository.issueByKeyword(keyword)
            }.onSuccess {
                _issues.emitAll(it)
            }.onFailure {
                Timber.e(it)
            }
        }
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
