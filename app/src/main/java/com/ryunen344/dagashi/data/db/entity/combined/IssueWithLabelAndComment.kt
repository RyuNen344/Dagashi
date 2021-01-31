package com.ryunen344.dagashi.data.db.entity.combined

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.ryunen344.dagashi.data.db.entity.CommentEntity
import com.ryunen344.dagashi.data.db.entity.IssueEntity
import com.ryunen344.dagashi.data.db.entity.LabelEntity
import com.ryunen344.dagashi.data.db.entity.StashedIssueEntity
import com.ryunen344.dagashi.data.db.entity.relation.IssueLabelCrossRef

data class IssueWithLabelAndComment(
    @Embedded
    val issueEntity: IssueEntity,
    @Relation(
        entity = LabelEntity::class,
        parentColumn = "single_unique_id",
        entityColumn = "name",
        associateBy = Junction(
            value = IssueLabelCrossRef::class,
            parentColumn = "single_unique_id",
            entityColumn = "label_name"
        )
    )
    val labels: List<LabelEntity>,
    @Relation(
        parentColumn = "single_unique_id",
        entityColumn = "single_unique_id"
    )
    val comments: List<CommentEntity>,
)
