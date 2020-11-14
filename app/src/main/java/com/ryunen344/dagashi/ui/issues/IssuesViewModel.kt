package com.ryunen344.dagashi.ui.issues

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ryunen344.dagashi.data.repository.IssueRepository
import com.ryunen344.dagashi.model.Issue
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch

class IssuesViewModel @ViewModelInject constructor(private val issueRepository: IssueRepository) : ViewModel() {

    private val _issues: ConflatedBroadcastChannel<List<Issue>> = ConflatedBroadcastChannel()
    val issues: Flow<List<Issue>>
        get() = _issues.asFlow()

    val isUpdated: Flow<Boolean>
        get() = issues.drop(1).zip(issues) { old, new -> old != new }

    fun refresh(path: String) {
        viewModelScope.launch {
            _issues.offer(issueRepository.issue(path))
        }
    }

    override fun onCleared() {
        _issues.cancel()
        super.onCleared()
    }
}
