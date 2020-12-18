package com.ryunen344.dagashi.di

import android.content.Context
import com.ryunen344.dagashi.data.preferences.SettingPreferences
import com.ryunen344.dagashi.data.preferences.impl.SettingPreferencesImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PreferencesModule {
    @Provides
    @Singleton
    fun provideSettingPreferencesImpl(
        @ApplicationContext context: Context
    ): SettingPreferencesImpl {
        return SettingPreferencesImpl(context)
    }

    @Module
    @InstallIn(SingletonComponent::class)
    abstract class PreferencesModuleBinds {
        @Binds
        abstract fun bindSettingPreferences(impl: SettingPreferencesImpl): SettingPreferences
    }
}
