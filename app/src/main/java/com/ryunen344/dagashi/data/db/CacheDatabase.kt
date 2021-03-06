package com.ryunen344.dagashi.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ryunen344.dagashi.data.db.converter.OffsetDateTimeConverter
import com.ryunen344.dagashi.data.db.dao.CommentDao
import com.ryunen344.dagashi.data.db.dao.IssueDao
import com.ryunen344.dagashi.data.db.dao.IssueLabelCrossRefDao
import com.ryunen344.dagashi.data.db.dao.LabelDao
import com.ryunen344.dagashi.data.db.dao.MileStoneDao
import com.ryunen344.dagashi.data.db.dao.StashedIssueDao
import com.ryunen344.dagashi.data.db.dao.SummaryIssueDao
import com.ryunen344.dagashi.data.db.entity.CommentEntity
import com.ryunen344.dagashi.data.db.entity.IssueEntity
import com.ryunen344.dagashi.data.db.entity.IssueFts
import com.ryunen344.dagashi.data.db.entity.LabelEntity
import com.ryunen344.dagashi.data.db.entity.MileStoneEntity
import com.ryunen344.dagashi.data.db.entity.StashedIssueEntity
import com.ryunen344.dagashi.data.db.entity.SummaryIssueEntity
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
        IssueFts::class,
        StashedIssueEntity::class
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
    abstract val stashedIssueDao: StashedIssueDao
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
