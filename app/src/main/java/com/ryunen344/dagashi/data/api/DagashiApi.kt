package com.ryunen344.dagashi.data.api

import com.ryunen344.dagashi.data.api.response.IssueRootResponse
import com.ryunen344.dagashi.data.api.response.MileStonesRootResponse

interface DagashiApi {
    suspend fun milestones(): MileStonesRootResponse
    suspend fun milestones(previousEndCursor: String): MileStonesRootResponse
    suspend fun issues(path: String): IssueRootResponse
}
