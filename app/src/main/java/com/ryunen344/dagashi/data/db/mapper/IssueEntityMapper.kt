package com.ryunen344.dagashi.data.db.mapper

import com.ryunen344.dagashi.data.db.entity.AuthorEntity
import com.ryunen344.dagashi.data.db.entity.CommentEntity
import com.ryunen344.dagashi.data.db.entity.IssueEntity
import com.ryunen344.dagashi.data.db.entity.LabelEntity
import com.ryunen344.dagashi.data.db.entity.StashedIssueEntity
import com.ryunen344.dagashi.data.db.entity.combined.IssueWithLabelAndCommentOnStash
import com.ryunen344.dagashi.model.Author
import com.ryunen344.dagashi.model.Comment
import com.ryunen344.dagashi.model.Issue
import com.ryunen344.dagashi.model.Label

internal fun IssueWithLabelAndCommentOnStash.toModel(): Issue {
    return Issue(
        singleUniqueId = issueWithLabelAndComment.issueEntity.singleUniqueId,
        url = issueWithLabelAndComment.issueEntity.url,
        title = issueWithLabelAndComment.issueEntity.title,
        body = issueWithLabelAndComment.issueEntity.body,
        labels = issueWithLabelAndComment.labels.map { it.toModel() },
        comments = issueWithLabelAndComment.comments.map { it.toModel() },
        isStashed = isStashed
    )
}

private fun LabelEntity.toModel(): Label {
    return Label(
        name = name,
        description = description,
        color = color
    )
}

private fun CommentEntity.toModel(): Comment {
    return Comment(
        id = id,
        singleUniqueId = singleUniqueId,
        body = body,
        publishedAt = publishedAt,
        author = author.toModel()
    )
}

private fun AuthorEntity.toModel(): Author {
    return Author(
        login = login,
        url = url,
        avatarUrl = avatarUrl
    )
}

internal fun Issue.toEntity(): IssueEntity {
    return IssueEntity(
        singleUniqueId = singleUniqueId,
        id = singleUniqueId.substringAfterLast("_").toInt(),
        number = singleUniqueId.substringBefore("_").toInt(),
        url = url,
        title = title,
        body = body,
    )
}

internal fun Issue.toStashedEntity(): StashedIssueEntity {
    return StashedIssueEntity(
        singleUniqueId = singleUniqueId
    )
}

internal fun Label.toEntity(): LabelEntity {
    return LabelEntity(
        name = name,
        description = description,
        color = color
    )
}

internal fun Comment.toEntity(): CommentEntity {
    return CommentEntity(
        id = id,
        singleUniqueId = singleUniqueId,
        body = body,
        publishedAt = publishedAt,
        author = author.toEntity()
    )
}

private fun Author.toEntity(): AuthorEntity {
    return AuthorEntity(
        login = login,
        url = url,
        avatarUrl = avatarUrl
    )
}

