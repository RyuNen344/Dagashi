package com.ryunen344.dagashi.data.repository.mapper

import com.ryunen344.dagashi.data.api.response.AuthorResponse
import com.ryunen344.dagashi.data.api.response.CommentNodeResponse
import com.ryunen344.dagashi.data.api.response.IssueNodeResponse
import com.ryunen344.dagashi.data.api.response.LabelNodeResponse
import com.ryunen344.dagashi.model.Author
import com.ryunen344.dagashi.model.Comment
import com.ryunen344.dagashi.model.Issue
import com.ryunen344.dagashi.model.Label

object IssueMapper {
    @JvmStatic
    fun toModel(node: IssueNodeResponse): Issue {
        return Issue(
            url = node.url,
            title = node.title,
            body = node.body,
            labels = node.labels.nodes.map(::toModel),
            comments = node.comments.nodes.map(::toModel)
        )
    }

    @JvmStatic
    fun toModel(node: LabelNodeResponse): Label {
        return Label(
            name = node.name,
            description = node.description,
            color = node.color
        )
    }

    @JvmStatic
    fun toModel(node: CommentNodeResponse): Comment {
        return Comment(
            body = node.body,
            publishedAt = node.publishedAt,
            author = toModel(node.author)
        )
    }

    @JvmStatic
    fun toModel(node: AuthorResponse): Author {
        return Author(
            login = node.login,
            url = node.url,
            avatarUrl = node.avatarUrl
        )
    }
}
