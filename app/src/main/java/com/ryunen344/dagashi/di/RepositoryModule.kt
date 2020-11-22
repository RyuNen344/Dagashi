package com.ryunen344.dagashi.di

import com.ryunen344.dagashi.data.api.DagashiApi
import com.ryunen344.dagashi.data.db.interfaces.IssueDatabase
import com.ryunen344.dagashi.data.db.interfaces.MileStoneDatabase
import com.ryunen344.dagashi.data.repository.IssueRepository
import com.ryunen344.dagashi.data.repository.MileStoneRepository
import com.ryunen344.dagashi.data.repository.impl.IssueRepositoryImpl
import com.ryunen344.dagashi.data.repository.impl.MileStoneRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
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

    @Module
    @InstallIn(ApplicationComponent::class)
    abstract class RepositoryModuleBinds {
        @Binds
        abstract fun bindMileStoneRepository(impl: MileStoneRepositoryImpl): MileStoneRepository

        @Binds
        abstract fun bindIssueRepository(impl: IssueRepositoryImpl): IssueRepository
    }
}
