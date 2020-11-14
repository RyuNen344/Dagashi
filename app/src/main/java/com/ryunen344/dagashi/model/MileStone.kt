package com.ryunen344.dagashi.model

data class MileStone(
    val id: String,
    val number: Int,
    val url: String,
    val title: String,
    val description: String,
    val closedAt: String,
    val issues: List<SummaryIssue>,
    val path: String
)
