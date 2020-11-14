package com.ryunen344.dagashi.di

import com.ryunen344.dagashi.data.repository.FirstRepository
import com.ryunen344.dagashi.data.repository.impl.FirstRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class AppModuleBinds {

    @Binds
    abstract fun bindFirstRepository(impl: FirstRepositoryImpl): FirstRepository
}
