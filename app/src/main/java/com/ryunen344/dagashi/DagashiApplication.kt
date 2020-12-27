package com.ryunen344.dagashi

import android.app.Application
import com.ryunen344.dagashi.initializer.core.AppInitializers
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class DagashiApplication : Application() {

    @Inject
    lateinit var appInitializers: AppInitializers

    override fun onCreate() {
        super.onCreate()
        appInitializers.initialize()
    }
}
