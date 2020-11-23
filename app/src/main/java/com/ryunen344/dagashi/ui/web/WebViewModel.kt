package com.ryunen344.dagashi.ui.web

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
) : ViewModel() {

    private val viewModelDefaultScope = CoroutineScope(viewModelScope.coroutineContext + defaultDispatcher)

    private val _backKeyEvent: MutableSharedFlow<Unit> = MutableSharedFlow()
    val backKeyEvent: Flow<Unit>
        get() = _backKeyEvent

    private val _progress: MutableSharedFlow<Int> = MutableSharedFlow()
    val progress: Flow<Int>
        get() = _progress.map { it % 100 }

    private val _webTitle: MutableSharedFlow<String?> = MutableSharedFlow()
    val webTitle: Flow<String>
        get() = _webTitle.map { it ?: "" }

    val webViewCacheMode: Flow<Int> =
        networkManger.isConnected
            .map { if (it) WebSettings.LOAD_DEFAULT else WebSettings.LOAD_CACHE_ELSE_NETWORK }
            .flowOn(defaultDispatcher)

    fun backKeyTapped() {
        viewModelDefaultScope.launch {
            _backKeyEvent.emit(Unit)
        }
    }

    fun progressChanged(progress: Int) {
        viewModelDefaultScope.launch {
            _progress.emit(progress)
        }
    }

    fun titleChanged(title: String?) {
        viewModelDefaultScope.launch {
            _webTitle.emit(title)
        }
    }

    override fun onCleared() {
        viewModelDefaultScope.cancel()
        super.onCleared()
    }
}
