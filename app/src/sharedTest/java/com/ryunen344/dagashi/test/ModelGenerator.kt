package com.ryunen344.dagashi.test

import android.util.Base64
import com.ryunen344.dagashi.data.db.entity.IssueEntity
import com.ryunen344.dagashi.data.db.entity.MileStoneEntity
import org.threeten.bp.OffsetDateTime
import kotlin.random.Random

object ModelGenerator {

    // region issue
    @JvmStatic
    fun createIssue(): IssueEntity {
        return IssueEntity(
            "987_0",
            0,
            987,
            "https://www.google.co.jp/",
            "title1",
            "body1"
        )
    }

    @JvmStatic
    fun createIssues(): List<IssueEntity> {
        return listOf(
            IssueEntity(
                "987_0",
                0,
                987,
                "https://www.google.co.jp/",
                "title1",
                "body1"
            ),
            IssueEntity(
                "987_1",
                1,
                987,
                "https://www.google.co.jp/",
                "title1",
                "body1"
            ),
            IssueEntity(
                "987_2",
                2,
                987,
                "https://www.google.co.jp/",
                "title1",
                "body1"
            ),
            IssueEntity(
                "987_3",
                3,
                987,
                "https://www.google.co.jp/",
                "title1",
                "body1"
            ),
            IssueEntity(
                "987_4",
                4,
                987,
                "https://www.google.co.jp/",
                "title1",
                "body1"
            )
        )
    }
    // endregion

    // region milestone
    @JvmStatic
    fun createMileStone(): MileStoneEntity {
        val now = OffsetDateTime.now()
        return MileStoneEntity(
            "random_string",
            0,
            "https://www.google.co.jp/",
            "title",
            "description",
            now,
            "0-${now.year}-${now.monthValue}-${now.dayOfMonth}"
        )
    }

    fun createMileStones(): List<MileStoneEntity> {
        val now = OffsetDateTime.now()
        return listOf(
            MileStoneEntity(
                Base64.encodeToString(Random.Default.nextBytes(32), 0),
                0,
                "https://www.google.co.jp/",
                "title",
                "description",
                now,
                "0-${now.year}-${now.monthValue}-${now.dayOfMonth}"
            ),
            MileStoneEntity(
                Base64.encodeToString(Random.Default.nextBytes(32), 0),
                1,
                "https://www.google.co.jp/",
                "title",
                "description",
                now,
                "1-${now.year}-${now.monthValue}-${now.dayOfMonth}"
            ),
            MileStoneEntity(
                Base64.encodeToString(Random.Default.nextBytes(32), 0),
                2,
                "https://www.google.co.jp/",
                "title",
                "description",
                now,
                "2-${now.year}-${now.monthValue}-${now.dayOfMonth}"
            ),
            MileStoneEntity(
                Base64.encodeToString(Random.Default.nextBytes(32), 0),
                3,
                "https://www.google.co.jp/",
                "title",
                "description",
                now,
                "3-${now.year}-${now.monthValue}-${now.dayOfMonth}"
            )
        )
    }

    // endregion
}
