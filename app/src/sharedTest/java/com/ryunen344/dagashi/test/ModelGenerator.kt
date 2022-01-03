package com.ryunen344.dagashi.test

import com.ryunen344.dagashi.model.Author
import com.ryunen344.dagashi.model.Comment
import com.ryunen344.dagashi.model.Issue
import com.ryunen344.dagashi.model.Label
import com.ryunen344.dagashi.model.MileStone
import com.ryunen344.dagashi.model.SummaryIssue
import java.time.OffsetDateTime

object ModelGenerator {

    @JvmStatic
    fun createMileStone(): MileStone {
        return MileStone(
            id = "MI_kwDOB4jhdM4Acryj",
            number = 205,
            url = "https://github.com/AndroidDagashi/AndroidDagashi/milestone/205",
            title = "205 2022-01-02",
            description = "公式のAndroidチャンネルで2021年に最も見られた動画、ViewModelイベントの扱い方、2022年1月におけるAndroid開発状況、など",
            closedAt = OffsetDateTime.parse("2022-01-02T10:25:22Z"),
            issues = listOf(
                SummaryIssue(
                    id = 0,
                    mileStoneId = "MI_kwDOB4jhdM4Acryj",
                    title = "公式のAndroidチャンネルで2021年に最も見られた動画21本",
                ),
                SummaryIssue(
                    id = 1,
                    mileStoneId = "MI_kwDOB4jhdM4Acryj",
                    title = "ViewModelイベントの扱い方",
                ),
                SummaryIssue(
                    id = 2,
                    mileStoneId = "MI_kwDOB4jhdM4Acryj",
                    title = "2022年1月におけるAndroid開発状況",
                ),
                SummaryIssue(
                    id = 3,
                    mileStoneId = "MI_kwDOB4jhdM4Acryj",
                    title = "STORES 決済 Androidアプリの設計改善の歴史",
                )
            ),
            path = "205-2022-01-02"
        )
    }

    @JvmStatic
    fun createIssue(): Issue {
        return Issue(
            singleUniqueId = "204_0",
            url = "https://github.com/AndroidDagashi/AndroidDagashi/issues/2224",
            title = "Coroutines 1.6.0",
            body = "https://blog.jetbrains.com/kotlin/2021/12/introducing-kotlinx-coroutines-1-6-0/\r\nhttps://github.com/Kotlin/kotlinx.coroutines/releases/tag/1.6.0\r\n\r\nKotlin 1.6対応、testライブラリの変更、Kotlin/Nativeの新しいメモリ管理対応、などの変更が入っています",
            labels = listOf(
                Label(
                    name = "Kotlin",
                    description = "",
                    color = "EC953C"
                )
            ),
            comments = listOf(
                Comment(
                    id = 0,
                    singleUniqueId = "204_0",
                    body = "reddit の反応はこちら\r\nhttps://www.reddit.com/r/androiddev/comments/rgdamo/rebuilding_our_guide_to_app_architecture/",
                    publishedAt = OffsetDateTime.parse("2021-12-18T14:17:45Z"),
                    author = Author(
                        login = "RyuNen344",
                        url = "https://github.com/RyuNen344",
                        avatarUrl = "https://avatars.githubusercontent.com/u/32740480?v=4"
                    )
                )
            ),
            isStashed = false
        )
    }
}
