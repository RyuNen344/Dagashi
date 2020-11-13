package com.ryunen344.dagashi.di

import android.app.Application
import android.content.Context
import com.ryunen344.dagashi.DagashiApplication
import com.ryunen344.dagashi.repository.impl.FirstRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object AppModule {
    @Provides
    @JvmStatic
    @Singleton
    fun provideContext(application: DagashiApplication): Context {
        return application.applicationContext
    }

    @Provides
    @JvmStatic
    @Singleton
    fun provideApplication(application: DagashiApplication): Application {
        return application
    }

    @Provides
    @JvmStatic
    @Singleton
    fun provideFirstRepositoryImpl(): FirstRepositoryImpl {
        return FirstRepositoryImpl()
    }
}
