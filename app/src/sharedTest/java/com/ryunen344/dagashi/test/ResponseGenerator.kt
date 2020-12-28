package com.ryunen344.dagashi.test

import com.ryunen344.dagashi.data.api.response.MileStoneIssueResponse
import com.ryunen344.dagashi.data.api.response.MileStoneIssuesResponse
import com.ryunen344.dagashi.data.api.response.MileStoneNodeResponse
import com.ryunen344.dagashi.data.api.response.MileStonesResponse
import com.ryunen344.dagashi.data.api.response.MileStonesRootResponse
import com.ryunen344.dagashi.data.api.response.PageInfo
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.ZoneOffset

object ResponseGenerator {
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
