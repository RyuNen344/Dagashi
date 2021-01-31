package com.ryunen344.dagashi.ui.issues.search.viewmodel

import com.ryunen344.dagashi.model.Issue

interface SearchIssuesViewModelInput {
    fun searchIssue(keyword: String)
    fun inputUrl(url: String)
    fun toggleStash(issue: Issue)
}
