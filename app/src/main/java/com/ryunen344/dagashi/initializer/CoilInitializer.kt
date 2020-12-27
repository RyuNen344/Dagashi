package com.ryunen344.dagashi.initializer

import android.content.Context
import coil.Coil
import coil.ImageLoader
import coil.util.CoilUtils
import com.ryunen344.dagashi.BuildConfig
import com.ryunen344.dagashi.initializer.core.AppInitializer
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Inject

class CoilInitializer @Inject constructor(@ApplicationContext private val applicationContext: Context) : AppInitializer {
    override fun initialize() {
        Coil.setImageLoader {
            ImageLoader.Builder(applicationContext)
                .crossfade(true)
                .crossfade(300)
                .okHttpClient {
                    buildHttpClient(applicationContext)
                }
                .build()
        }
    }

    private fun buildHttpClient(context: Context): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.cache(CoilUtils.createDefaultCache(context))
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.HEADERS
            })
        }
        return builder.build()
    }
}
