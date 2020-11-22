package com.ryunen344.dagashi.data.db.entity.combined

import androidx.room.Embedded
import androidx.room.Relation
import com.ryunen344.dagashi.data.db.entity.MileStoneEntity
import com.ryunen344.dagashi.data.db.entity.SummaryIssueEntity

data class MileStoneWithSummaryIssue(
    @Embedded
    val mileStoneEntity: MileStoneEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "mile_stone_id"
    )
    val issues: List<SummaryIssueEntity>,
)
