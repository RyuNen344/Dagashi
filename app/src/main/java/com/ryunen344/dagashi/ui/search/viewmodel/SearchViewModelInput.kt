package com.ryunen344.dagashi.ui.search.viewmodel

interface SearchViewModelInput {
    fun searchIssue(keyword: String)
    fun inputUrl(url: String)
}
