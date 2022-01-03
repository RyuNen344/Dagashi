package com.ryunen344.dagashi.test

import com.ryunen344.dagashi.data.api.response.MileStoneIssueResponse
import com.ryunen344.dagashi.data.api.response.MileStoneIssuesResponse
import com.ryunen344.dagashi.data.api.response.MileStoneNodeResponse
import com.ryunen344.dagashi.data.api.response.MileStonesResponse
import com.ryunen344.dagashi.data.api.response.MileStonesRootResponse
import com.ryunen344.dagashi.data.api.response.PageInfo
import java.time.OffsetDateTime

object ResponseGenerator {
    // region multiple milestone
    @JvmStatic
    fun createMultiplePageMileStonesRootResponse(): MileStonesRootResponse {
        return MileStonesRootResponse(
            name = "AndroidDagashi",
            url = "https://github.com/AndroidDagashi/AndroidDagashi",
            milestones = createMultiplePageMileStonesResponse()
        )
    }

    @JvmStatic
    fun createMultiplePageMileStonesResponse(): MileStonesResponse {
        val nodes = createMultiplePageMileStoneNodeResponse()
        return MileStonesResponse(
            totalCount = nodes.size,
            nodes = nodes,
            pageInfo = createHasNextPageInfo()
        )
    }

    @JvmStatic
    fun createMultiplePageMileStoneNodeResponse(): List<MileStoneNodeResponse> {
        return listOf(
            MileStoneNodeResponse(
                id = "MDk6TWlsZXN0b25lNjEwNzMyNw==",
                number = 147,
                url = "https://github.com/AndroidDagashi/AndroidDagashi/milestone/147",
                title = "147 2020-11-22",
                description = "ConstraintLayout 2.1.0 alpha 1, Coroutine FlowのAndroid公式ドキュメント, FlowMarbles, 2021年8月から新規アプリはAppBundleが必須に, Kotlin 1.4.20, など",
                closedAt = OffsetDateTime.parse("2020-11-22T10:26:20Z"),
                issues = createMultiplePageMileStoneIssuesResponse(),
                path = "147-2020-11-22"
            )
        )
    }

    @JvmStatic
    fun createMultiplePageMileStoneIssuesResponse(): MileStoneIssuesResponse {
        val nodes = createMultiplePageMileStoneIssueResponses()
        return MileStoneIssuesResponse(
            totalCount = nodes.size,
            nodes = nodes
        )
    }

    @JvmStatic
    fun createMultiplePageMileStoneIssueResponses(): List<MileStoneIssueResponse> {
        return listOf(
            MileStoneIssueResponse(
                title = "ConstraintLayout 2.1.0 alpha 1"
            ),
            MileStoneIssueResponse(
                title = "Coroutine FlowのAndroid公式ドキュメント"
            ),
            MileStoneIssueResponse(
                title = "2021年8月より新規アプリはAppBundleが必須に"
            ),
            MileStoneIssueResponse(
                title = "Dagger 2.30"
            ),
            MileStoneIssueResponse(
                title = "Kotlin 1.4.20"
            ),
            MileStoneIssueResponse(
                title = "Kotlin Coroutiensの各オペレータの処理を可視化するサイト"
            ),
            MileStoneIssueResponse(
                title = "2020年のクックパッドAndroidアプリのアーキテクチャ事情"
            ),
            MileStoneIssueResponse(
                title = "バックグラウンド位置情報にアクセスするアプリの審査を円滑に進めるためのヒント"
            ),
            MileStoneIssueResponse(
                title = "Trello AndroidアプリのGitブランチ運用"
            ),
        )
    }
    // endregion

    // region single milestone
    @JvmStatic
    fun createSinglePageMileStonesRootResponse(): MileStonesRootResponse {
        return MileStonesRootResponse(
            name = "AndroidDagashi",
            url = "https://github.com/AndroidDagashi/AndroidDagashi",
            milestones = createSinglePageMileStonesResponse()
        )
    }

    @JvmStatic
    fun createSinglePageMileStonesResponse(): MileStonesResponse {
        val nodes = createSinglePageMileStoneNodeResponse()
        return MileStonesResponse(
            totalCount = nodes.size,
            nodes = nodes,
            pageInfo = createSinglePageInfo()
        )
    }

    @JvmStatic
    fun createSinglePageMileStoneNodeResponse(): List<MileStoneNodeResponse> {
        return listOf(
            MileStoneNodeResponse(
                id = "MDk6TWlsZXN0b25lNjA5ODIzNQ==",
                number = 146,
                url = "https://github.com/AndroidDagashi/AndroidDagashi/milestone/146",
                title = "146 2020-11-15",
                description = "App Bundle Q&A、Now in Android エピソード29、App Bundleのテスト、Android Studio 4.1.1、実践的なDagger Hilt + Kotlin解説、など",
                closedAt = OffsetDateTime.parse("2020-11-15T10:21:58Z"),
                issues = createSinglePageMileStoneIssuesResponse(),
                path = "146-2020-11-15"
            )
        )
    }

    @JvmStatic
    fun createSinglePageMileStoneIssuesResponse(): MileStoneIssuesResponse {
        val nodes = createSinglePageMileStoneIssueResponses()
        return MileStoneIssuesResponse(
            totalCount = nodes.size,
            nodes = nodes
        )
    }

    @JvmStatic
    fun createSinglePageMileStoneIssueResponses(): List<MileStoneIssueResponse> {
        return listOf(
            MileStoneIssueResponse(
                title = "App Bundle Q&A"
            ),
            MileStoneIssueResponse(
                title = "Now in Android エピソード29"
            ),
            MileStoneIssueResponse(
                title = "PyTorchがAndroidのNNAPIに対応"
            ),
            MileStoneIssueResponse(
                title = "AOSPのビルドシステムがBazelに移行"
            ),
            MileStoneIssueResponse(
                title = "App Bundleのテスト"
            ),
            MileStoneIssueResponse(
                title = "Android Studio 4.1.1"
            ),
            MileStoneIssueResponse(
                title = "実践的なDagger Hilt + Kotlin解説"
            ),
            MileStoneIssueResponse(
                title = "Android Studioをリモートで実行する"
            ),
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
    // endregion
}
