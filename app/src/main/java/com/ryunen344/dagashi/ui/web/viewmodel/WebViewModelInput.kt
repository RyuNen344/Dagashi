package com.ryunen344.dagashi.ui.web.viewmodel

interface WebViewModelInput {
    fun backKeyTapped()
    fun progressChanged(progress: Int)
    fun titleChanged(title: String?)
}
