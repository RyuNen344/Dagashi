package com.ryunen344.dagashi.data.api.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MileStonesRootResponse(
    @SerialName("name")
    val name: String,
    @SerialName("url")
    val url: String,
    @SerialName("milestones")
    val milestones: MilestonesResponse
)

@Serializable
data class MilestonesResponse(
    @SerialName("totalCount")
    val totalCount: Int,
    @SerialName("nodes")
    val nodes: List<MileStoneNodeResponse>,
    @SerialName("pageInfo")
    val pageInfo: PageInfo
)

@Serializable
data class MileStoneNodeResponse(
    @SerialName("id")
    val id: String,
    @SerialName("number")
    val number: Int,
    @SerialName("url")
    val url: String,
    @SerialName("title")
    val title: String,
    @SerialName("description")
    val description: String,
    @SerialName("closedAt")
    val closedAt: String,
    @SerialName("issues")
    val issues: MileStoneIssuesResponse,
    @SerialName("path")
    val path: String
)

@Serializable
data class MileStoneIssuesResponse(
    @SerialName("totalCount")
    val totalCount: Int,
    @SerialName("nodes")
    val nodes: List<MileStoneIssueResponse>
)

@Serializable
data class MileStoneIssueResponse(
    @SerialName("title")
    val title: String
)
