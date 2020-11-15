package com.ryunen344.dagashi.ui.issues

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ryunen344.dagashi.data.repository.IssueRepository
import com.ryunen344.dagashi.model.Issue
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch

class IssuesViewModel @ViewModelInject constructor(private val issueRepository: IssueRepository) : ViewModel() {

    private val _issues: ConflatedBroadcastChannel<List<Issue>> = ConflatedBroadcastChannel()
    val issues: Flow<List<Issue>>
        get() = _issues.asFlow()

    val isUpdated: Flow<Unit>
        get() = issues.drop(1).zip(issues) { old, new -> old != new }.filter { it }.map { Unit }

    private val _openUrlModel: BroadcastChannel<OpenUrlModel> = BroadcastChannel(Channel.BUFFERED)
    val openUrlModel: Flow<OpenUrlModel>
        get() = _openUrlModel.asFlow()

    fun refresh(path: String) {
        viewModelScope.launch {
            _issues.offer(issueRepository.issue(path))
        }
    }

    fun inputUrl(url: String) {
        _openUrlModel.offer(OpenUrlModel.WebView(url))
    }

    override fun onCleared() {
        _openUrlModel.cancel()
        _issues.cancel()
        super.onCleared()
    }

    sealed class OpenUrlModel {
        class WebView(val url: String) : OpenUrlModel()
        class ActionView(val url: String) : OpenUrlModel()
    }
}
