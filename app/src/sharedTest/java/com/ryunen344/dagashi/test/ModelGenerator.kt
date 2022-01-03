package com.ryunen344.dagashi.test

import com.ryunen344.dagashi.model.Author
import com.ryunen344.dagashi.model.Comment
import com.ryunen344.dagashi.model.Issue
import com.ryunen344.dagashi.model.Label
import java.time.OffsetDateTime

object ModelGenerator {

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
