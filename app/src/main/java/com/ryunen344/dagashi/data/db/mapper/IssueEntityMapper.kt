package com.ryunen344.dagashi.data.db.mapper

import com.ryunen344.dagashi.data.db.entity.CommentEntity
import com.ryunen344.dagashi.data.db.entity.IssueEntity
import com.ryunen344.dagashi.data.db.entity.LabelEntity
import com.ryunen344.dagashi.data.db.entity.StashedIssueEntity
import com.ryunen344.dagashi.data.db.entity.SummaryIssueEntity
import com.ryunen344.dagashi.data.db.entity.combined.IssueWithLabelAndCommentOnStash
import com.ryunen344.dagashi.model.Comment
import com.ryunen344.dagashi.model.Issue
import com.ryunen344.dagashi.model.Label
import com.ryunen344.dagashi.model.SummaryIssue

internal fun IssueWithLabelAndCommentOnStash.toModel(): Issue {
    TODO()
}

internal fun Issue.toEntity(): IssueEntity {
    TODO()
}

internal fun Issue.toStashedEntity(): StashedIssueEntity {
    TODO()
}

internal fun SummaryIssue.toEntity(): SummaryIssueEntity {
    TODO()
}

internal fun Label.toEntity(): LabelEntity {
    TODO()
}

internal fun Comment.toEntity(): CommentEntity {
    TODO()
}

