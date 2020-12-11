package com.ryunen344.dagashi.ui.issues.viewmodel

interface IssuesViewModelInput {
    fun refresh(number: Int, path: String)
    fun inputUrl(url: String)
}
