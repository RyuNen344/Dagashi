package com.ryunen344.dagashi.data.db.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.ryunen344.dagashi.data.db.CacheDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class DaoTestRule : TestWatcher() {
    lateinit var db: CacheDatabase

    override fun starting(description: Description?) {
        super.starting(description)
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room
            .inMemoryDatabaseBuilder(context, CacheDatabase::class.java)
            .setQueryExecutor(Dispatchers.Default.asExecutor())
            .setTransactionExecutor(Dispatchers.Default.asExecutor())
            .build()
    }

    override fun finished(description: Description?) {
        super.finished(description)
        db.close()
    }
}
