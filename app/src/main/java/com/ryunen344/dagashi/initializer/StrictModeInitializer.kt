package com.ryunen344.dagashi.initializer

import android.os.StrictMode
import com.ryunen344.dagashi.BuildConfig
import com.ryunen344.dagashi.initializer.core.AppInitializer
import javax.inject.Inject

class StrictModeInitializer @Inject constructor() : AppInitializer {
    override fun initialize() {
        if (BuildConfig.DEBUG) {
            StrictMode.setVmPolicy(
                StrictMode.VmPolicy.Builder()
                    .detectActivityLeaks()
                    .detectLeakedClosableObjects()
                    .detectLeakedRegistrationObjects()
                    .detectLeakedSqlLiteObjects()
                    .penaltyLog()
                    .build()
            )
        }
    }
}
