package com.ryunen344.dagashi.initializer

import com.ryunen344.dagashi.BuildConfig
import com.ryunen344.dagashi.initializer.core.AppInitializer
import timber.log.Timber
import javax.inject.Inject

class TimberInitializer @Inject constructor() : AppInitializer {
    override fun initialize() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
