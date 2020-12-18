package com.ryunen344.dagashi.di

import com.ryunen344.dagashi.data.api.DagashiApi
import com.ryunen344.dagashi.data.db.interfaces.IssueDatabase
import com.ryunen344.dagashi.data.db.interfaces.MileStoneDatabase
import com.ryunen344.dagashi.data.preferences.SettingPreferences
import com.ryunen344.dagashi.data.repository.IssueRepository
import com.ryunen344.dagashi.data.repository.MileStoneRepository
import com.ryunen344.dagashi.data.repository.SettingRepository
import com.ryunen344.dagashi.data.repository.impl.IssueRepositoryImpl
import com.ryunen344.dagashi.data.repository.impl.MileStoneRepositoryImpl
import com.ryunen344.dagashi.data.repository.impl.SettingRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideMileStoneRepositoryImpl(
        dagashiApi: DagashiApi,
        mileStoneDatabase: MileStoneDatabase,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): MileStoneRepositoryImpl {
        return MileStoneRepositoryImpl(dagashiApi, mileStoneDatabase, ioDispatcher)
    }

    @Provides
    @Singleton
    fun provideIssueRepositoryImpl(
        dagashiApi: DagashiApi,
        issueDatabase: IssueDatabase,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): IssueRepositoryImpl {
        return IssueRepositoryImpl(dagashiApi, issueDatabase, ioDispatcher)
    }

    @Provides
    @Singleton
    fun provideSettingRepositoryImpl(
        settingPreferences: SettingPreferences
    ): SettingRepositoryImpl {
        return SettingRepositoryImpl(settingPreferences)
    }

    @Module
    @InstallIn(SingletonComponent::class)
    abstract class RepositoryModuleBinds {
        @Binds
        abstract fun bindMileStoneRepository(impl: MileStoneRepositoryImpl): MileStoneRepository

        @Binds
        abstract fun bindIssueRepository(impl: IssueRepositoryImpl): IssueRepository

        @Binds
        abstract fun bindSettingRepository(impl: SettingRepositoryImpl): SettingRepository
    }
}
