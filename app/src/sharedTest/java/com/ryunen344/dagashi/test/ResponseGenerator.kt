package com.ryunen344.dagashi.test

import com.ryunen344.dagashi.data.api.response.AuthorResponse
import com.ryunen344.dagashi.data.api.response.CommentNodeResponse
import com.ryunen344.dagashi.data.api.response.CommentsResponse
import com.ryunen344.dagashi.data.api.response.IssueNodeResponse
import com.ryunen344.dagashi.data.api.response.IssueRootResponse
import com.ryunen344.dagashi.data.api.response.IssuesResponse
import com.ryunen344.dagashi.data.api.response.LabelNodeResponse
import com.ryunen344.dagashi.data.api.response.LabelsResponse
import com.ryunen344.dagashi.data.api.response.MileStoneIssueResponse
import com.ryunen344.dagashi.data.api.response.MileStoneIssuesResponse
import com.ryunen344.dagashi.data.api.response.MileStoneNodeResponse
import com.ryunen344.dagashi.data.api.response.MileStonesResponse
import com.ryunen344.dagashi.data.api.response.MileStonesRootResponse
import com.ryunen344.dagashi.data.api.response.PageInfo
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.ZoneOffset

object ResponseGenerator {

    // region issue
    @JvmStatic
    fun createIssueRootResponse(): IssueRootResponse {
        return IssueRootResponse(
            id = "id",
            number = 0,
            url = "https://github.com/RyuNen344",
            title = "title",
            description = "description",
            closedAt = OffsetDateTime.of(2020, 12, 20, 6, 30, 0, 0, ZoneOffset.UTC),
            issues = createIssueResponse()
        )
    }

    @JvmStatic
    fun createIssueResponse(): IssuesResponse {
        return IssuesResponse(
            totalCount = 1,
            pageInfo = createSinglePageInfo(),
            nodes = listOf(createIssueNodeResponse())
        )
    }

    @JvmStatic
    fun createIssueNodeResponse(): IssueNodeResponse {
        return IssueNodeResponse(
            url = "https://github.com/RyuNen344",
            title = "title",
            body = "body",
            labels = createLabelsResponse(),
            comments = createCommentsResponse()
        )
    }

    @JvmStatic
    fun createLabelsResponse(): LabelsResponse {
        return LabelsResponse(
            nodes = listOf(createLabelNodeResponse())
        )
    }

    @JvmStatic
    fun createLabelNodeResponse(): LabelNodeResponse {
        return LabelNodeResponse(
            name = "name",
            description = "description",
            color = "FFFFFF"
        )
    }

    @JvmStatic
    fun createCommentsResponse(): CommentsResponse {
        return CommentsResponse(
            totalCount = 1,
            pageInfo = createSinglePageInfo(),
            nodes = listOf(createCommentNodeResponse())
        )
    }

    @JvmStatic
    fun createCommentNodeResponse(): CommentNodeResponse {
        return CommentNodeResponse(
            body = "body",
            publishedAt = OffsetDateTime.of(2020, 12, 22, 6, 30, 0, 0, ZoneOffset.UTC),
            author = createAuthorResponse()
        )
    }

    @JvmStatic
    fun createAuthorResponse(): AuthorResponse {
        return AuthorResponse(
            login = "RyuNen344",
            url = "https://github.com/RyuNen344",
            avatarUrl = "https://avatars2.githubusercontent.com/u/32740480?v=4"
        )
    }
    // endregion

    // region milestone
    @JvmStatic
    fun createSinglePageMileStonesRootResponse(): MileStonesRootResponse {
        return MileStonesRootResponse(
            name = "name",
            url = "https://github.com/RyuNen344",
            milestones = createSinglePageMileStonesResponse()
        )
    }

    @JvmStatic
    fun createSinglePageMileStonesResponse(): MileStonesResponse {
        return MileStonesResponse(
            totalCount = 1,
            nodes = listOf(createSinglePageMileStoneNodeResponse()),
            pageInfo = createSinglePageInfo()
        )
    }

    @JvmStatic
    fun createSinglePageMileStoneNodeResponse(): MileStoneNodeResponse {
        return MileStoneNodeResponse(
            id = "id_0",
            number = 0,
            url = "https://github.com/RyuNen344",
            title = "title",
            description = "description",
            closedAt = OffsetDateTime.of(2020, 12, 20, 6, 30, 0, 0, ZoneOffset.UTC),
            issues = createMileStoneIssuesResponse(),
            path = "path"
        )
    }

    @JvmStatic
    fun createMultiplePageMileStonesRootResponse(): MileStonesRootResponse {
        return MileStonesRootResponse(
            name = "name",
            url = "https://github.com/RyuNen344",
            milestones = createMultiplePageMileStonesResponse()
        )
    }

    @JvmStatic
    fun createMultiplePageMileStonesResponse(): MileStonesResponse {
        return MileStonesResponse(
            totalCount = 1,
            nodes = listOf(createMultiplePageMileStoneNodeResponse()),
            pageInfo = createHasNextPageInfo()
        )
    }

    @JvmStatic
    fun createMultiplePageMileStoneNodeResponse(): MileStoneNodeResponse {
        return MileStoneNodeResponse(
            id = "id_1",
            number = 0,
            url = "https://github.com/RyuNen344",
            title = "title",
            description = "description",
            closedAt = OffsetDateTime.of(2020, 12, 20, 6, 30, 0, 0, ZoneOffset.UTC),
            issues = createMileStoneIssuesResponse(),
            path = "path"
        )
    }

    @JvmStatic
    fun createMileStoneIssuesResponse(): MileStoneIssuesResponse {
        return MileStoneIssuesResponse(
            totalCount = 1,
            nodes = listOf(createMileStoneIssueResponse())
        )
    }

    @JvmStatic
    fun createMileStoneIssueResponse(): MileStoneIssueResponse {
        return MileStoneIssueResponse(
            title = "title"
        )
    }
    // endregion

    // region pageInfo
    @JvmStatic
    fun createSinglePageInfo(): PageInfo {
        return PageInfo(
            startCursor = "startCursor",
            endCursor = "endCursor",
            hasNextPage = false,
            hasPreviousPage = false
        )
    }

    @JvmStatic
    fun createHasNextPageInfo(): PageInfo {
        return PageInfo(
            startCursor = "startCursor",
            endCursor = "endCursor",
            hasNextPage = true,
            hasPreviousPage = false
        )
    }

    @JvmStatic
    fun createHasPreviousPageInfo(): PageInfo {
        return PageInfo(
            startCursor = "startCursor",
            endCursor = "endCursor",
            hasNextPage = false,
            hasPreviousPage = true
        )
    }
    // endregion
}
