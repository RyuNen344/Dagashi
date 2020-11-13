package com.ryunen344.dagashi

import android.app.Application
import com.ryunen344.dagashi.di.AppComponent
import com.ryunen344.dagashi.di.DaggerAppComponent
import timber.log.Timber

class DagashiApplication : Application() {

    val appComponent: AppComponent by lazy {
        initializeComponent()
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }

    private fun initializeComponent(): AppComponent {
        return DaggerAppComponent.factory().create(this)
    }
}
