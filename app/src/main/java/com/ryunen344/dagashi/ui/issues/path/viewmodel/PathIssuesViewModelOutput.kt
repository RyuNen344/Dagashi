package com.ryunen344.dagashi.ui.issues.path.viewmodel

import com.ryunen344.dagashi.model.Issue
import kotlinx.coroutines.flow.Flow

interface PathIssuesViewModelOutput {
    val issues: Flow<List<Issue>>
    val isUpdated: Flow<Unit>
    val openUrlModel: Flow<OpenUrlModel>

    sealed class OpenUrlModel {
        class WebView(val url: String) : OpenUrlModel()
        class ChromeTabs(val url: String) : OpenUrlModel()
    }
}


