package com.ryunen344.dagashi.test

import com.ryunen344.dagashi.data.db.entity.IssueEntity
import com.ryunen344.dagashi.data.db.entity.MileStoneEntity
import com.ryunen344.dagashi.data.db.entity.combined.IssueWithLabelAndComment
import com.ryunen344.dagashi.data.db.entity.combined.MileStoneWithSummaryIssue
import org.threeten.bp.OffsetDateTime

object EntityGenerator {

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

    @JvmStatic
    fun createIssueWithLabelAndComment(): IssueWithLabelAndComment {
        return IssueWithLabelAndComment(
            createIssue(),
            emptyList(),
            emptyList()
        )
    }

    @JvmStatic
    fun createIssueWithLabelAndComments(): List<IssueWithLabelAndComment> {
        return createIssues().map {
            IssueWithLabelAndComment(
                it,
                emptyList(),
                emptyList()
            )
        }
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

    @JvmStatic
    fun createMileStones(): List<MileStoneEntity> {
        val now = OffsetDateTime.now()
        return listOf(
            MileStoneEntity(
                "random_string_1",
                0,
                "https://www.google.co.jp/",
                "title",
                "description",
                now,
                "0-${now.year}-${now.monthValue}-${now.dayOfMonth}"
            ),
            MileStoneEntity(
                "random_string_2",
                1,
                "https://www.google.co.jp/",
                "title",
                "description",
                now,
                "1-${now.year}-${now.monthValue}-${now.dayOfMonth}"
            ),
            MileStoneEntity(
                "random_string_3",
                2,
                "https://www.google.co.jp/",
                "title",
                "description",
                now,
                "2-${now.year}-${now.monthValue}-${now.dayOfMonth}"
            ),
            MileStoneEntity(
                "random_string_4",
                3,
                "https://www.google.co.jp/",
                "title",
                "description",
                now,
                "3-${now.year}-${now.monthValue}-${now.dayOfMonth}"
            )
        )
    }

    @JvmStatic
    fun createMileStoneWithSummaryIssue(): MileStoneWithSummaryIssue {
        return MileStoneWithSummaryIssue(
            createMileStone(),
            emptyList()
        )
    }

    @JvmStatic
    fun createMileStoneWithSummaryIssues(): List<MileStoneWithSummaryIssue> {
        return createMileStones().map {
            MileStoneWithSummaryIssue(
                it,
                emptyList()
            )
        }
    }
    // endregion
}
