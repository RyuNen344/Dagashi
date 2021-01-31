package com.ryunen344.dagashi.data.db.entity.combined

import androidx.room.ColumnInfo
import androidx.room.Embedded

data class IssueWithLabelAndCommentOnStash(
    @Embedded
    val issueWithLabelAndComment: IssueWithLabelAndComment,

    @ColumnInfo(name = "is_stashed")
    val isStashed: Boolean
)
