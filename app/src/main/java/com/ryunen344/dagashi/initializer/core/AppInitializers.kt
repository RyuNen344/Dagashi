package com.ryunen344.dagashi.initializer.core

import javax.inject.Inject

class AppInitializers @Inject constructor(private val initializers: Set<@JvmSuppressWildcards AppInitializer>) {
    fun initialize() {
        initializers.forEach { it.initialize() }
    }
}
