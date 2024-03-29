package com.ryunen344.dagashi.model

import java.time.OffsetDateTime

data class MileStone(
    val id: String,
    val number: Int,
    val url: String,
    val title: String,
    val description: String,
    val closedAt: OffsetDateTime,
    val issues: List<SummaryIssue>,
    val path: String
)
