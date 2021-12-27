package com.ryunen344.dagashi.di

import com.ryunen344.dagashi.initializer.CoilInitializer
import com.ryunen344.dagashi.initializer.EmojiInitializer
import com.ryunen344.dagashi.initializer.StrictModeInitializer
import com.ryunen344.dagashi.initializer.TimberInitializer
import com.ryunen344.dagashi.initializer.core.AppInitializer
import com.ryunen344.dagashi.initializer.core.AppInitializers
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideAppInitializers(initializers: Set<@JvmSuppressWildcards AppInitializer>): AppInitializers {
        return AppInitializers(initializers)
    }

    @Module
    @InstallIn(SingletonComponent::class)
    abstract class AppModuleBinds {
        @Binds
        @IntoSet
        abstract fun bindCoilInitializer(impl: CoilInitializer): AppInitializer

        @Binds
        @IntoSet
        abstract fun bindEmojiInitializer(impl: EmojiInitializer): AppInitializer

        @Binds
        @IntoSet
        abstract fun bindStrictModeInitializer(impl: StrictModeInitializer): AppInitializer

        @Binds
        @IntoSet
        abstract fun bindTimberInitializer(impl: TimberInitializer): AppInitializer
    }
}
