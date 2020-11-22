package com.ryunen344.dagashi.data.repository.mapper

import com.ryunen344.dagashi.data.api.response.CommentNodeResponse
import com.ryunen344.dagashi.data.api.response.CommentsResponse
import com.ryunen344.dagashi.data.api.response.IssueNodeResponse
import com.ryunen344.dagashi.data.api.response.IssueRootResponse
import com.ryunen344.dagashi.data.api.response.LabelNodeResponse
import com.ryunen344.dagashi.data.api.response.LabelsResponse
import com.ryunen344.dagashi.data.db.entity.AuthorEntity
import com.ryunen344.dagashi.data.db.entity.CommentEntity
import com.ryunen344.dagashi.data.db.entity.IssueEntity
import com.ryunen344.dagashi.data.db.entity.LabelEntity
import com.ryunen344.dagashi.data.db.entity.combined.IssueWithLabelAndComment
import com.ryunen344.dagashi.model.Author
import com.ryunen344.dagashi.model.Comment
import com.ryunen344.dagashi.model.Issue
import com.ryunen344.dagashi.model.Label

object IssueMapper {
    @JvmStatic
    fun toEntity(response: IssueRootResponse): List<IssueWithLabelAndComment> {
        return response.issues.nodes.mapIndexed { index, issueNodeResponse ->
            IssueWithLabelAndComment(
                issueEntity = IssueEntity(
                    singleUniqueId = "${response.number}_${index}",
                    id = index,
                    number = response.number,
                    url = issueNodeResponse.url,
                    title = issueNodeResponse.title,
                    body = issueNodeResponse.body
                ),
                labels = toEntity(issueNodeResponse.labels),
                comments = toEntity("${response.number}_${index}", issueNodeResponse.comments)
            )
        }
    }

    @JvmStatic
    private fun toEntity(response: LabelsResponse): List<LabelEntity> {
        return response.nodes.map {
            LabelEntity(
                name = it.name,
                description = it.description,
                color = it.color
            )
        }
    }

    @JvmStatic
    private fun toEntity(singleUniqueId: String, response: CommentsResponse): List<CommentEntity> {
        return response.nodes.mapIndexed { index, node ->
            CommentEntity(
                id = index,
                singleUniqueId = singleUniqueId,
                body = node.body,
                publishedAt = node.publishedAt,
                author = AuthorEntity(
                    login = node.author.login,
                    url = node.author.url,
                    avatarUrl = node.author.avatarUrl
                )
            )
        }
    }

    @JvmStatic
    fun toModel(entity: IssueWithLabelAndComment): Issue {
        return Issue(
            url = entity.issueEntity.url,
            title = entity.issueEntity.title,
            body = entity.issueEntity.body,
            labels = entity.labels.map(::toModel),
            comments = entity.comments.map(::toModel)
        )
    }

    @JvmStatic
    fun toModel(entity: LabelEntity): Label {
        return Label(
            name = entity.name,
            description = entity.description,
            color = entity.color
        )
    }

    @JvmStatic
    fun toModel(entity: CommentEntity): Comment {
        return Comment(
            body = entity.body,
            publishedAt = entity.publishedAt,
            author = Author(
                login = entity.author.login,
                url = entity.author.url,
                avatarUrl = entity.author.avatarUrl
            )
        )
    }

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
            author = Author(
                login = node.author.login,
                url = node.author.url,
                avatarUrl = node.author.avatarUrl
            )
        )
    }
}
