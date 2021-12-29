package com.ryunen344.dagashi.data.api

import com.ryunen344.dagashi.model.Issue
import com.ryunen344.dagashi.model.MileStone

interface DagashiApi {
    suspend fun milestones(): List<MileStone>
    suspend fun issues(path: String): List<Issue>
}
