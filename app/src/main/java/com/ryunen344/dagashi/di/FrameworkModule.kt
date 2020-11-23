package com.ryunen344.dagashi.di

import android.content.Context
import com.ryunen344.dagashi.framework.NetworkManager
import com.ryunen344.dagashi.framework.impl.NetworkManagerImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object FrameworkModule {
    @Provides
    @Singleton
    fun provideNetworkManagerImpl(
        @ApplicationContext context: Context
    ): NetworkManagerImpl {
        return NetworkManagerImpl(context)
    }

    @Module
    @InstallIn(ApplicationComponent::class)
    abstract class FrameworkModuleBinds {
        @Binds
        abstract fun bindNetworkManager(impl: NetworkManagerImpl): NetworkManager
    }
}
