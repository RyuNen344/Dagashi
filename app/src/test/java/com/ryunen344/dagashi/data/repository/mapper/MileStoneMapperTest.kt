package com.ryunen344.dagashi.data.repository.mapper

import com.ryunen344.dagashi.test.EntityGenerator
import com.ryunen344.dagashi.test.ResponseGenerator
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Test

class MileStoneMapperTest {

    @Test
    fun toEntity() {
        val mileStonesNodeResponse = ResponseGenerator.createSinglePageMileStonesRootResponse().milestones.nodes.first()
        val result = MileStoneMapper.toEntity(mileStonesNodeResponse)

        MatcherAssert.assertThat(result.mileStoneEntity.id, CoreMatchers.equalTo(mileStonesNodeResponse.id))
        MatcherAssert.assertThat(result.mileStoneEntity.number, CoreMatchers.equalTo(mileStonesNodeResponse.number))
        MatcherAssert.assertThat(result.mileStoneEntity.url, CoreMatchers.equalTo(mileStonesNodeResponse.url))
        MatcherAssert.assertThat(result.mileStoneEntity.title, CoreMatchers.equalTo(mileStonesNodeResponse.title))
        MatcherAssert.assertThat(result.mileStoneEntity.description, CoreMatchers.equalTo(mileStonesNodeResponse.description))
        MatcherAssert.assertThat(result.mileStoneEntity.closedAt, CoreMatchers.equalTo(mileStonesNodeResponse.closedAt))
        MatcherAssert.assertThat(result.mileStoneEntity.path, CoreMatchers.equalTo(mileStonesNodeResponse.path))

        MatcherAssert.assertThat(result.issues.size, CoreMatchers.equalTo(mileStonesNodeResponse.issues.totalCount))
        MatcherAssert.assertThat(result.issues.size, CoreMatchers.equalTo(mileStonesNodeResponse.issues.nodes.size))
        MatcherAssert.assertThat(result.issues.first().id, CoreMatchers.equalTo(0))
        MatcherAssert.assertThat(result.issues.first().mileStoneId, CoreMatchers.equalTo(mileStonesNodeResponse.id))
        MatcherAssert.assertThat(result.issues.first().title, CoreMatchers.equalTo(mileStonesNodeResponse.issues.nodes.first().title))
    }

    @Test
    fun toModel() {
        val mileStoneSummaryIssueEntity = EntityGenerator.createMileStoneWithSummaryIssue()
        val result = MileStoneMapper.toModel(mileStoneSummaryIssueEntity)

        MatcherAssert.assertThat(result.id, CoreMatchers.equalTo(mileStoneSummaryIssueEntity.mileStoneEntity.id))
        MatcherAssert.assertThat(result.number, CoreMatchers.equalTo(mileStoneSummaryIssueEntity.mileStoneEntity.number))
        MatcherAssert.assertThat(result.url, CoreMatchers.equalTo(mileStoneSummaryIssueEntity.mileStoneEntity.url))
        MatcherAssert.assertThat(result.title, CoreMatchers.equalTo(mileStoneSummaryIssueEntity.mileStoneEntity.title))
        MatcherAssert.assertThat(result.description, CoreMatchers.equalTo(mileStoneSummaryIssueEntity.mileStoneEntity.description))
        MatcherAssert.assertThat(result.closedAt, CoreMatchers.equalTo(mileStoneSummaryIssueEntity.mileStoneEntity.closedAt))
        MatcherAssert.assertThat(result.issues.size, CoreMatchers.equalTo(mileStoneSummaryIssueEntity.issues.size))
        MatcherAssert.assertThat(0, CoreMatchers.equalTo(mileStoneSummaryIssueEntity.issues.first().id))
        MatcherAssert.assertThat(result.id, CoreMatchers.equalTo(mileStoneSummaryIssueEntity.issues.first().mileStoneId))
        MatcherAssert.assertThat(result.issues.first().title, CoreMatchers.equalTo(mileStoneSummaryIssueEntity.issues.first().title))
        MatcherAssert.assertThat(result.path, CoreMatchers.equalTo(mileStoneSummaryIssueEntity.mileStoneEntity.path))
    }
}
