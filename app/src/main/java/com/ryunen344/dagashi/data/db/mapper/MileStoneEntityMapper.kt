package com.ryunen344.dagashi.data.db.mapper

import com.ryunen344.dagashi.data.db.entity.MileStoneEntity
import com.ryunen344.dagashi.data.db.entity.SummaryIssueEntity
import com.ryunen344.dagashi.data.db.entity.combined.MileStoneWithSummaryIssue
import com.ryunen344.dagashi.model.MileStone
import com.ryunen344.dagashi.model.SummaryIssue

internal fun MileStoneWithSummaryIssue.toModel(): MileStone {
    return MileStone(
        id = mileStoneEntity.id,
        number = mileStoneEntity.number,
        url = mileStoneEntity.url,
        title = mileStoneEntity.title,
        description = mileStoneEntity.description,
        closedAt = mileStoneEntity.closedAt,
        issues = issues.map { it.toModel() },
        path = mileStoneEntity.path
    )
}

private fun SummaryIssueEntity.toModel(): SummaryIssue {
    return SummaryIssue(
        id = id,
        mileStoneId = mileStoneId,
        title = title,
    )
}

internal fun MileStone.toEntity(): MileStoneEntity {
    return MileStoneEntity(
        id = id,
        number = number,
        url = url,
        title = title,
        description = description,
        closedAt = closedAt,
        path = path
    )
}

internal fun SummaryIssue.toEntity(): SummaryIssueEntity {
    return SummaryIssueEntity(
        id = id,
        mileStoneId = mileStoneId,
        title = title
    )
}
