package com.ryunen344.dagashi.ui.web

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map

class WebViewModel @ViewModelInject constructor() : ViewModel() {

    private val _backKeyEvent: BroadcastChannel<Unit> = BroadcastChannel(Channel.BUFFERED)
    val backKeyEvent: Flow<Unit>
        get() = _backKeyEvent.asFlow()

    private val _progress: BroadcastChannel<Int> = BroadcastChannel(Channel.BUFFERED)
    val progress: Flow<Int>
        get() = _progress.asFlow().map { it % 100 }

    private val _webTitle: BroadcastChannel<String?> = BroadcastChannel(Channel.BUFFERED)
    val webTitle: Flow<String>
        get() = _webTitle.asFlow().map { it ?: "" }

    fun backKeyTapped() {
        _backKeyEvent.offer(Unit)
    }

    fun progressChanged(progress: Int) {
        _progress.offer(progress)
    }

    fun titleChanged(title: String?) {
        _webTitle.offer(title)
    }
}
