package com.ryunen344.dagashi.test

import com.ryunen344.dagashi.data.db.entity.IssueEntity
import com.ryunen344.dagashi.data.db.entity.MileStoneEntity
import org.threeten.bp.OffsetDateTime

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
    fun createIssues(): List<IssueEntity> = listOf(
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

    // endregion
}
