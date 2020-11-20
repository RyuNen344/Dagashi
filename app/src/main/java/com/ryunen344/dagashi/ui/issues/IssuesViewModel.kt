package com.ryunen344.dagashi.ui.issues

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ryunen344.dagashi.data.repository.IssueRepository
import com.ryunen344.dagashi.di.DefaultDispatcher
import com.ryunen344.dagashi.model.Issue
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch

class IssuesViewModel @ViewModelInject constructor(
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    private val issueRepository: IssueRepository
) : ViewModel() {

    private val viewModelDefaultScope = CoroutineScope(viewModelScope.coroutineContext + defaultDispatcher)

    private val _issues: MutableSharedFlow<List<Issue>> =
        MutableSharedFlow(replay = 1, extraBufferCapacity = 0, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val issues: Flow<List<Issue>>
        get() = _issues

    val isUpdated: Flow<Unit>
        get() = issues
            .drop(1)
            .zip(issues) { new, old -> new != old }
            .filter { it }
            .map { Unit }
            .flowOn(defaultDispatcher)

    private val _openUrlModel: MutableSharedFlow<OpenUrlModel> = MutableSharedFlow()
    val openUrlModel: Flow<OpenUrlModel>
        get() = _openUrlModel

    fun refresh(path: String) {
        viewModelDefaultScope.launch {
            _issues.emit(issueRepository.issue(path))
        }
    }

    fun inputUrl(url: String) {
        viewModelDefaultScope.launch {
            _openUrlModel.emit(OpenUrlModel.WebView(url))
        }
    }

    override fun onCleared() {
        viewModelDefaultScope.cancel()
        super.onCleared()
    }

    sealed class OpenUrlModel {
        class WebView(val url: String) : OpenUrlModel()
        class ActionView(val url: String) : OpenUrlModel()
    }
}
