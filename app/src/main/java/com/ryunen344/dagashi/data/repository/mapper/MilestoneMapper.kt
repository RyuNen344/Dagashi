package com.ryunen344.dagashi.data.repository.mapper

import com.ryunen344.dagashi.data.api.response.MileStoneIssueResponse
import com.ryunen344.dagashi.data.api.response.MileStoneNodeResponse
import com.ryunen344.dagashi.model.MileStone
import com.ryunen344.dagashi.model.SummaryIssue

object MilestoneMapper {
    @JvmStatic
    fun toModel(node: MileStoneNodeResponse): MileStone {
        return MileStone(
            id = node.id,
            number = node.number,
            url = node.url,
            title = node.title,
            description = node.description,
            closedAt = node.closedAt,
            issues = node.issues.nodes.map(MilestoneMapper::toModel),
            path = node.path
        )
    }

    @JvmStatic
    private fun toModel(node: MileStoneIssueResponse): SummaryIssue {
        return SummaryIssue(
            title = node.title
        )
    }
}
