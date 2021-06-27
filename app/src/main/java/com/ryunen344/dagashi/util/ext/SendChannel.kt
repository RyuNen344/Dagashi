package com.ryunen344.dagashi.util.ext

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel

fun <E> SendChannel<E>.trySendResult(element: E): Boolean = trySend(element).isSuccess

inline fun <T> internalReceiveChannel(
    capacity: Int = Channel.RENDEZVOUS,
    block: Channel<T>.() -> Unit
): ReceiveChannel<T> {
    val channel = Channel<T>(capacity)
    channel.block()
    return channel
}

fun <T> SendChannel<T>.safeOffer(element: T) =
    !isClosedForSend && try {
        trySendResult(element)
    } catch (t: Throwable) {
        false
    }
