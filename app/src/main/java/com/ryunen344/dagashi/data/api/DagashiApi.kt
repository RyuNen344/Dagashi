package com.ryunen344.dagashi.data.api

import com.ryunen344.dagashi.data.api.response.IssueRootResponse
import com.ryunen344.dagashi.data.api.response.MileStonesRootResponse

interface DagashiApi {
    suspend fun milestones(previousEndCursor: String? = null): MileStonesRootResponse
    suspend fun issues(path: String): IssueRootResponse
}

