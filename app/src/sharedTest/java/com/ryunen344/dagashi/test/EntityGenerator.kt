package com.ryunen344.dagashi.test

import com.ryunen344.dagashi.data.db.entity.AuthorEntity
import com.ryunen344.dagashi.data.db.entity.CommentEntity
import com.ryunen344.dagashi.data.db.entity.IssueEntity
import com.ryunen344.dagashi.data.db.entity.LabelEntity
import com.ryunen344.dagashi.data.db.entity.MileStoneEntity
import com.ryunen344.dagashi.data.db.entity.SummaryIssueEntity
import com.ryunen344.dagashi.data.db.entity.combined.IssueWithLabelAndComment
import com.ryunen344.dagashi.data.db.entity.combined.IssueWithLabelAndCommentOnStash
import com.ryunen344.dagashi.data.db.entity.combined.MileStoneWithSummaryIssue
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.ZoneOffset

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
    fun createLabel(): LabelEntity {
        return LabelEntity(
            name = "name",
            description = "description",
            color = "FFFFFF"
        )
    }

    @JvmStatic
    fun createComment(): CommentEntity {
        return CommentEntity(
            id = 0,
            singleUniqueId = "single_unique_id",
            body = "body",
            publishedAt = OffsetDateTime.of(2020, 12, 30, 12, 45, 0, 0, ZoneOffset.UTC),
            author = createAuthor()
        )
    }

    @JvmStatic
    fun createAuthor(): AuthorEntity {
        return AuthorEntity(
            login = "RyuNen344",
            url = "https://github.com/RyuNen344",
            avatarUrl = "https://avatars2.githubusercontent.com/u/32740480?v=4"
        )
    }

    @JvmStatic
    fun createIssueWithLabelAndComment(): IssueWithLabelAndComment {
        return IssueWithLabelAndComment(
            createIssue(),
            listOf(createLabel()),
            listOf(createComment())
        )
    }

    @JvmStatic
    fun createIssueWithLabelAndComments(): List<IssueWithLabelAndComment> {
        return createIssues().map {
            IssueWithLabelAndComment(
                it,
                listOf(createLabel()),
                listOf(createComment())
            )
        }
    }

    @JvmStatic
    fun createIssueWithLabelAndCommentOnStash(): IssueWithLabelAndCommentOnStash {
        return IssueWithLabelAndCommentOnStash(
            createIssueWithLabelAndComment(),
            false
        )
    }

    @JvmStatic
    fun createIssueWithLabelAndCommentOnStashes(): List<IssueWithLabelAndCommentOnStash> {
        return createIssueWithLabelAndComments().map {
            IssueWithLabelAndCommentOnStash(
                it,
                false
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
        val mileStoneEntity = createMileStone()
        return MileStoneWithSummaryIssue(
            mileStoneEntity,
            listOf(createSummaryIssue(mileStoneEntity))
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

    @JvmStatic
    fun createSummaryIssue(mileStoneEntity: MileStoneEntity): SummaryIssueEntity {
        return SummaryIssueEntity(
            0,
            mileStoneEntity.id,
            "summary_issue_title"
        )
    }
    // endregion
}
