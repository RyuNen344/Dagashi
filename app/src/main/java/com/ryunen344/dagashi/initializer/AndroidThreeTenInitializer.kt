package com.ryunen344.dagashi.initializer

import android.content.Context
import com.jakewharton.threetenabp.AndroidThreeTen
import com.ryunen344.dagashi.initializer.core.AppInitializer
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AndroidThreeTenInitializer @Inject constructor(@ApplicationContext private val applicationContext: Context) : AppInitializer {
    override fun initialize() {
        AndroidThreeTen.init(applicationContext)
    }
}
