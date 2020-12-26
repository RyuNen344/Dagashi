package com.ryunen344.dagashi.util

import android.view.View
import androidx.annotation.CheckResult
import androidx.appcompat.widget.SearchView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.isActive
import ru.ldralighieri.corbind.corbindReceiveChannel
import ru.ldralighieri.corbind.safeOffer

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
    setOnQueryTextFocusChangeListener(listener(scope, events::offer))
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
): ReceiveChannel<SearchViewQueryTextFocusEvent> = corbindReceiveChannel(capacity) {
    safeOffer(SearchViewQueryTextFocusEvent(this@queryTextFocusChangeEvents, false))
    setOnQueryTextFocusChangeListener(listener(scope, ::safeOffer))
    invokeOnClose { setOnQueryTextFocusChangeListener(null) }
}

@CheckResult
fun SearchView.queryTextFocusChangeEvents(): Flow<SearchViewQueryTextFocusEvent> = channelFlow {
    offer(SearchViewQueryTextFocusEvent(this@queryTextFocusChangeEvents, false))
    setOnQueryTextFocusChangeListener(listener(this, ::offer))
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
