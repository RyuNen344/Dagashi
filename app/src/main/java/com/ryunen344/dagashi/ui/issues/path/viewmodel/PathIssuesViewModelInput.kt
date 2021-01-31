package com.ryunen344.dagashi.ui.issues.path.viewmodel

import com.ryunen344.dagashi.model.Issue

interface PathIssuesViewModelInput {
    fun inputUrl(url: String)
    fun toggleStash(issue: Issue)
}
