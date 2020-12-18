package com.ryunen344.dagashi.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ryunen344.dagashi.data.db.converter.OffsetDateTimeConverter
import com.ryunen344.dagashi.data.db.dao.*
import com.ryunen344.dagashi.data.db.entity.*
import com.ryunen344.dagashi.data.db.entity.relation.IssueLabelCrossRef
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.asExecutor

@Database(
    entities = [
        IssueEntity::class,
        LabelEntity::class,
        CommentEntity::class,
        MileStoneEntity::class,
        SummaryIssueEntity::class,
        IssueLabelCrossRef::class,
        IssueFts::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    value = [
        OffsetDateTimeConverter::class
    ]
)
abstract class CacheDatabase : RoomDatabase() {
    abstract val issueDao: IssueDao
    abstract val labelDao: LabelDao
    abstract val issueLabelCrossRefDao: IssueLabelCrossRefDao
    abstract val commentDao: CommentDao
    abstract val mileStoneDao: MileStoneDao
    abstract val summaryIssueDao: SummaryIssueDao

    companion object {

        private const val DATABASE_NAME = "dagashi.db"

        fun build(context: Context, dispatcher: CoroutineDispatcher): CacheDatabase {
            return Room.databaseBuilder(context, CacheDatabase::class.java, DATABASE_NAME)
                .setQueryExecutor(dispatcher.asExecutor())
                .setTransactionExecutor(dispatcher.asExecutor())
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}
