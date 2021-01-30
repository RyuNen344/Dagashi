package com.ryunen344.dagashi.data.repository.mapper

import com.ryunen344.dagashi.data.api.response.MileStoneIssueResponse
import com.ryunen344.dagashi.data.api.response.MileStoneIssuesResponse
import com.ryunen344.dagashi.data.api.response.MileStoneNodeResponse
import com.ryunen344.dagashi.data.db.entity.MileStoneEntity
import com.ryunen344.dagashi.data.db.entity.SummaryIssueEntity
import com.ryunen344.dagashi.data.db.entity.combined.MileStoneWithSummaryIssue
import com.ryunen344.dagashi.model.MileStone
import com.ryunen344.dagashi.model.SummaryIssue

object MileStoneMapper {
    @JvmStatic
    fun toEntity(response: MileStoneNodeResponse): MileStoneWithSummaryIssue {
        return MileStoneWithSummaryIssue(
            mileStoneEntity = MileStoneEntity(
                id = response.id,
                number = response.number,
                url = response.url,
                title = response.title,
                description = response.description,
                closedAt = response.closedAt,
                path = response.path
            ),
            issues = toEntity(
                mileStoneId = response.id,
                responses = response.issues
            )
        )
    }

    @JvmStatic
    private fun toEntity(mileStoneId: String, responses: MileStoneIssuesResponse): List<SummaryIssueEntity> {
        return responses.nodes.mapIndexed { index, response ->
            SummaryIssueEntity(
                id = index,
                mileStoneId = mileStoneId,
                title = response.title
            )
        }
    }

    @JvmStatic
    fun toModel(entity: MileStoneWithSummaryIssue): MileStone {
        return MileStone(
            id = entity.mileStoneEntity.id,
            number = entity.mileStoneEntity.number,
            url = entity.mileStoneEntity.url,
            title = entity.mileStoneEntity.title,
            description = entity.mileStoneEntity.description,
            closedAt = entity.mileStoneEntity.closedAt,
            issues = entity.issues.map(MileStoneMapper::toModel),
            path = entity.mileStoneEntity.path
        )
    }

    @JvmStatic
    private fun toModel(entity: SummaryIssueEntity): SummaryIssue {
        return SummaryIssue(
            entity.title
        )
    }

    @JvmStatic
    @Deprecated("Don't use except for remote test")
    fun toModel(node: MileStoneNodeResponse): MileStone {
        return MileStone(
            id = node.id,
            number = node.number,
            url = node.url,
            title = node.title,
            description = node.description,
            closedAt = node.closedAt,
            issues = node.issues.nodes.map(MileStoneMapper::toModel),
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
