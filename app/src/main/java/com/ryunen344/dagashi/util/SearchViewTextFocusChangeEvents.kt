package com.ryunen344.dagashi.util

import android.view.View
import androidx.annotation.CheckResult
import androidx.appcompat.widget.SearchView
import com.ryunen344.dagashi.util.ext.internalReceiveChannel
import com.ryunen344.dagashi.util.ext.safeOffer
import com.ryunen344.dagashi.util.ext.trySendResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.isActive

data class SearchViewQueryTextFocusEvent(
    val view: SearchView,
    val hasFocus: Boolean
)

fun SearchView.queryTextFocusChangeEvents(
    scope: CoroutineScope,
    capacity: Int = Channel.RENDEZVOUS,
    action: suspend (SearchViewQueryTextFocusEvent) -> Unit
) {
    val events = scope.actor<SearchViewQueryTextFocusEvent>(Dispatchers.Main.immediate, capacity) {
        for (event in channel) action(event)
    }
    setOnQueryTextFocusChangeListener(listener(scope, events::trySendResult))
    events.invokeOnClose { setOnQueryTextFocusChangeListener(null) }
}

suspend fun SearchView.queryTextFocusChangeEvents(
    capacity: Int = Channel.RENDEZVOUS,
    action: suspend (SearchViewQueryTextFocusEvent) -> Unit
) = coroutineScope {
    queryTextFocusChangeEvents(this, capacity, action)
}

@CheckResult
fun SearchView.queryTextFocusChangeEvents(
    scope: CoroutineScope,
    capacity: Int = Channel.RENDEZVOUS
): ReceiveChannel<SearchViewQueryTextFocusEvent> = internalReceiveChannel(capacity) {
    safeOffer(SearchViewQueryTextFocusEvent(this@queryTextFocusChangeEvents, false))
    setOnQueryTextFocusChangeListener(listener(scope, ::safeOffer))
    invokeOnClose { setOnQueryTextFocusChangeListener(null) }
}

@CheckResult
fun SearchView.queryTextFocusChangeEvents(): Flow<SearchViewQueryTextFocusEvent> = channelFlow {
    trySend(SearchViewQueryTextFocusEvent(this@queryTextFocusChangeEvents, false))
    setOnQueryTextFocusChangeListener(listener(this) { trySend(it).isSuccess })
    awaitClose { setOnQueryTextFocusChangeListener(null) }
}

@CheckResult
private fun listener(
    scope: CoroutineScope,
    emitter: (SearchViewQueryTextFocusEvent) -> Boolean
) = View.OnFocusChangeListener { v, hasFocus ->
    if (scope.isActive) {
        emitter(SearchViewQueryTextFocusEvent(v as SearchView, hasFocus))
    }
}
