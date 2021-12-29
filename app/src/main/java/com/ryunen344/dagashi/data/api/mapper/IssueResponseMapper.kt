package com.ryunen344.dagashi.data.api.mapper

import com.ryunen344.dagashi.data.api.response.AuthorResponse
import com.ryunen344.dagashi.data.api.response.CommentsResponse
import com.ryunen344.dagashi.data.api.response.IssueNodeResponse
import com.ryunen344.dagashi.data.api.response.IssueRootResponse
import com.ryunen344.dagashi.data.api.response.LabelsResponse
import com.ryunen344.dagashi.model.Author
import com.ryunen344.dagashi.model.Comment
import com.ryunen344.dagashi.model.Issue
import com.ryunen344.dagashi.model.Label

internal fun IssueRootResponse.toModel(): List<Issue> {
    return issues.nodes.mapIndexed { index, response ->
        response.toModel(idPrefix = number, id = index)
    }
}

private fun IssueNodeResponse.toModel(idPrefix: Int, id: Int): Issue {
    val singleUniqueId = "${idPrefix}_$id"
    return Issue(
        singleUniqueId = singleUniqueId,
        url = url,
        title = title,
        body = body,
        labels = labels.toModel(),
        comments = comments.toModel(singleUniqueId),
        isStashed = false
    )
}

private fun LabelsResponse.toModel(): List<Label> {
    return nodes.map {
        Label(
            name = it.name,
            description = it.description,
            color = it.color
        )
    }
}

private fun CommentsResponse.toModel(singleUniqueId: String): List<Comment> {
    return nodes.mapIndexed { index, node ->
        Comment(
            id = index,
            singleUniqueId = singleUniqueId,
            body = node.body,
            publishedAt = node.publishedAt,
            author = node.author.toModel()
        )
    }
}

private fun AuthorResponse.toModel(): Author {
    return Author(
        login = login,
        url = url,
        avatarUrl = avatarUrl
    )
}
