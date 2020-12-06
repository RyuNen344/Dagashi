package com.ryunen344.dagashi.ui.web.viewmodel

import android.webkit.WebSettings
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ryunen344.dagashi.di.DefaultDispatcher
import com.ryunen344.dagashi.framework.NetworkManager
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class WebViewModel @ViewModelInject constructor(
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    networkManger: NetworkManager
) : ViewModel(), WebViewModelInput, WebViewModelOutput {

    private val viewModelDefaultScope = CoroutineScope(viewModelScope.coroutineContext + defaultDispatcher)

    private val _backKeyEvent: MutableSharedFlow<Unit> = MutableSharedFlow()
    override val backKeyEvent: Flow<Unit>
        get() = _backKeyEvent

    private val _progress: MutableSharedFlow<Int> = MutableSharedFlow()
    override val progress: Flow<Int>
        get() = _progress.map { it % 100 }

    private val _webTitle: MutableSharedFlow<String?> = MutableSharedFlow()
    override val webTitle: Flow<String>
        get() = _webTitle.map { it ?: "" }

    override val webViewCacheMode: Flow<Int> =
        networkManger.isConnected
            .map { if (it) WebSettings.LOAD_DEFAULT else WebSettings.LOAD_CACHE_ELSE_NETWORK }
            .flowOn(defaultDispatcher)

    override fun backKeyTapped() {
        viewModelDefaultScope.launch {
            _backKeyEvent.emit(Unit)
        }
    }

    override fun progressChanged(progress: Int) {
        viewModelDefaultScope.launch {
            _progress.emit(progress)
        }
    }

    override fun titleChanged(title: String?) {
        viewModelDefaultScope.launch {
            _webTitle.emit(title)
        }
    }

    override fun onCleared() {
        viewModelDefaultScope.cancel()
        super.onCleared()
    }
}
