package com.ryunen344.dagashi.ui.issues.search.viewmodel

interface SearchIssuesViewModelInput {
    fun searchIssue(keyword: String)
    fun inputUrl(url: String)
}
