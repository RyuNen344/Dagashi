package com.ryunen344.dagashi.ui.issues.stash.viewmodel

import com.ryunen344.dagashi.model.Issue
import kotlinx.coroutines.flow.Flow

interface StashIssuesViewModelOutput {
    val issues: Flow<List<Issue>>
    val openUrlModel: Flow<OpenUrlModel>

    sealed class OpenUrlModel {
        class WebView(val url: String) : OpenUrlModel()
        class ChromeTabs(val url: String) : OpenUrlModel()
    }
}
