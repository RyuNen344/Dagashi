package com.ryunen344.dagashi.data.repository.mapper

import com.ryunen344.dagashi.test.EntityGenerator
import com.ryunen344.dagashi.test.ResponseGenerator
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Test

class IssueMapperTest {

    @Test
    fun toIssueWithLabelAndComment() {
        val issueRootResponse = ResponseGenerator.createIssueRootResponse()
        val result = IssueMapper.toEntity(issueRootResponse).first()

        MatcherAssert.assertThat(result.issueEntity.singleUniqueId, CoreMatchers.equalTo("${issueRootResponse.number}_0"))
        MatcherAssert.assertThat(result.issueEntity.id, CoreMatchers.equalTo(0))
        MatcherAssert.assertThat(result.issueEntity.number, CoreMatchers.equalTo(issueRootResponse.number))
        MatcherAssert.assertThat(result.issueEntity.url, CoreMatchers.equalTo(issueRootResponse.issues.nodes.first().url))
        MatcherAssert.assertThat(result.issueEntity.title, CoreMatchers.equalTo(issueRootResponse.issues.nodes.first().title))
        MatcherAssert.assertThat(result.issueEntity.body, CoreMatchers.equalTo(issueRootResponse.issues.nodes.first().body))

        MatcherAssert.assertThat(result.labels.first().name, CoreMatchers.equalTo(issueRootResponse.issues.nodes.first().labels.nodes.first().name))
        MatcherAssert.assertThat(result.labels.first().description, CoreMatchers.equalTo(issueRootResponse.issues.nodes.first().labels.nodes.first().description))
        MatcherAssert.assertThat(result.labels.first().color, CoreMatchers.equalTo(issueRootResponse.issues.nodes.first().labels.nodes.first().color))

        MatcherAssert.assertThat(result.comments.first().id, CoreMatchers.equalTo(0))
        MatcherAssert.assertThat(result.comments.first().singleUniqueId, CoreMatchers.equalTo("${issueRootResponse.number}_0"))
        MatcherAssert.assertThat(result.comments.first().body, CoreMatchers.equalTo(issueRootResponse.issues.nodes.first().comments.nodes.first().body))
        MatcherAssert.assertThat(result.comments.first().publishedAt, CoreMatchers.equalTo(issueRootResponse.issues.nodes.first().comments.nodes.first().publishedAt))

        MatcherAssert.assertThat(result.comments.first().author.login, CoreMatchers.equalTo(issueRootResponse.issues.nodes.first().comments.nodes.first().author.login))
        MatcherAssert.assertThat(result.comments.first().author.url, CoreMatchers.equalTo(issueRootResponse.issues.nodes.first().comments.nodes.first().author.url))
        MatcherAssert.assertThat(result.comments.first().author.avatarUrl, CoreMatchers.equalTo(issueRootResponse.issues.nodes.first().comments.nodes.first().author.avatarUrl))
    }

    @Test
    fun toIssue() {
        val response = EntityGenerator.createIssueWithLabelAndComment()
        val result = IssueMapper.toModel(response)

        MatcherAssert.assertThat(result.url, CoreMatchers.equalTo(response.issueEntity.url))
        MatcherAssert.assertThat(result.title, CoreMatchers.equalTo(response.issueEntity.title))
        MatcherAssert.assertThat(result.body, CoreMatchers.equalTo(response.issueEntity.body))

        MatcherAssert.assertThat(result.labels.first().name, CoreMatchers.equalTo(response.labels.first().name))
        MatcherAssert.assertThat(result.labels.first().description, CoreMatchers.equalTo(response.labels.first().description))
        MatcherAssert.assertThat(result.labels.first().color, CoreMatchers.equalTo(response.labels.first().color))

        MatcherAssert.assertThat(result.comments.first().body, CoreMatchers.equalTo(response.comments.first().body))
        MatcherAssert.assertThat(result.comments.first().publishedAt, CoreMatchers.equalTo(response.comments.first().publishedAt))
        MatcherAssert.assertThat(result.comments.first().author.login, CoreMatchers.equalTo(response.comments.first().author.login))
        MatcherAssert.assertThat(result.comments.first().author.url, CoreMatchers.equalTo(response.comments.first().author.url))
        MatcherAssert.assertThat(result.comments.first().author.avatarUrl, CoreMatchers.equalTo(response.comments.first().author.avatarUrl))
    }
}
