package com.ryunen344.dagashi.di

import com.ryunen344.dagashi.repository.impl.FirstRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideFirstRepositoryImpl(): FirstRepositoryImpl {
        return FirstRepositoryImpl()
    }
}
