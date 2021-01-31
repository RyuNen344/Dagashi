package com.ryunen344.dagashi.ui.issues.stash.viewmodel

import com.ryunen344.dagashi.model.Issue

interface StashIssuesViewModelInput {
    fun inputUrl(url: String)
    fun toggleStash(issue: Issue)
}
