package com.ryunen344.dagashi.di

import com.ryunen344.dagashi.repository.FirstRepository
import com.ryunen344.dagashi.repository.impl.FirstRepositoryImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class AppModuleBinds {

    @Binds
    @Singleton
    abstract fun bindFirstRepository(impl: FirstRepositoryImpl): FirstRepository
}
