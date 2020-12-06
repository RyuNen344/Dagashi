package com.ryunen344.dagashi.ui.web.viewmodel

import kotlinx.coroutines.flow.Flow

interface WebViewModelOutput {
    val backKeyEvent: Flow<Unit>
    val progress: Flow<Int>
    val webTitle: Flow<String>
    val webViewCacheMode: Flow<Int>
}
