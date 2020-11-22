package com.ryunen344.dagashi.di

import android.content.Context
import com.ryunen344.dagashi.data.db.CacheDatabase
import com.ryunen344.dagashi.data.db.DagashiDatabase
import com.ryunen344.dagashi.data.db.interfaces.IssueDatabase
import com.ryunen344.dagashi.data.db.interfaces.MileStoneDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DbModule {
    @Provides
    @Singleton
    fun provideCacheDatabase(
        @ApplicationContext context: Context,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): CacheDatabase {
        return CacheDatabase.build(context, dispatcher)
    }

    @Provides
    @Singleton
    fun provideDagashiDatabase(cacheDatabase: CacheDatabase): DagashiDatabase {
        return DagashiDatabase(cacheDatabase)
    }

    @Module
    @InstallIn(ApplicationComponent::class)
    abstract class DbModuleBinds {
        @Binds
        abstract fun bindIssueDatabase(impl: DagashiDatabase): IssueDatabase

        @Binds
        abstract fun bindMileStoneDatabase(impl: DagashiDatabase): MileStoneDatabase
    }
}
