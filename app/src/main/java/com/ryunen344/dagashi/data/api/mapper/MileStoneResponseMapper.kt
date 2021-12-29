package com.ryunen344.dagashi.data.api.mapper

import com.ryunen344.dagashi.data.api.response.MileStoneIssueResponse
import com.ryunen344.dagashi.data.api.response.MileStoneIssuesResponse
import com.ryunen344.dagashi.data.api.response.MileStoneNodeResponse
import com.ryunen344.dagashi.data.api.response.MileStonesRootResponse
import com.ryunen344.dagashi.model.MileStone
import com.ryunen344.dagashi.model.SummaryIssue

internal fun MileStonesRootResponse.toModel(): List<MileStone> {
    return milestones.nodes.map { it.toModel() }
}

private fun MileStoneNodeResponse.toModel(): MileStone {
    return MileStone(
        id = id,
        number = number,
        url = url,
        title = title,
        description = description,
        closedAt = closedAt,
        issues = issues.toModel(mileStoneId = id),
        path = path
    )
}

private fun MileStoneIssuesResponse.toModel(mileStoneId: String): List<SummaryIssue> {
    return nodes.mapIndexed { index, response ->
        response.toModel(id = index, mileStoneId = mileStoneId)
    }
}

private fun MileStoneIssueResponse.toModel(id: Int, mileStoneId: String): SummaryIssue {
    return SummaryIssue(
        id = id,
        mileStoneId = mileStoneId,
        title = title
    )
}
