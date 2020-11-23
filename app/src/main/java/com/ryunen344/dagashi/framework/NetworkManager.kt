package com.ryunen344.dagashi.framework

import kotlinx.coroutines.flow.Flow

interface NetworkManager {
    val isConnected: Flow<Boolean>
}
