package com.ryunen344.dagashi.data.api.response

import com.ryunen344.dagashi.data.api.serializer.OffsetDateTimeSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime

@Serializable
data class IssueRootResponse(
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
    @Serializable(with = OffsetDateTimeSerializer::class)
    @SerialName("closedAt")
    val closedAt: OffsetDateTime,
    @SerialName("issues")
    val issues: IssuesResponse
)

@Serializable
data class IssuesResponse(
    @SerialName("totalCount")
    val totalCount: Int,
    @SerialName("pageInfo")
    val pageInfo: PageInfo,
    @SerialName("nodes")
    val nodes: List<IssueNodeResponse>
)

@Serializable
data class IssueNodeResponse(
    @SerialName("url")
    val url: String,
    @SerialName("title")
    val title: String,
    @SerialName("body")
    val body: String,
    @SerialName("labels")
    val labels: LabelsResponse,
    @SerialName("comments")
    val comments: CommentsResponse
)

@Serializable
data class LabelsResponse(
    @SerialName("nodes")
    val nodes: List<LabelNodeResponse>
)

@Serializable
data class LabelNodeResponse(
    @SerialName("name")
    val name: String,
    @SerialName("description")
    val description: String,
    @SerialName("color")
    val color: String
)

@Serializable
data class CommentsResponse(
    @SerialName("totalCount")
    val totalCount: Int,
    @SerialName("pageInfo")
    val pageInfo: PageInfo,
    @SerialName("nodes")
    val nodes: List<CommentNodeResponse>
)

@Serializable
data class CommentNodeResponse(
    @SerialName("body")
    val body: String,
    @Serializable(with = OffsetDateTimeSerializer::class)
    @SerialName("publishedAt")
    val publishedAt: OffsetDateTime,
    @SerialName("author")
    val author: AuthorResponse
)

@Serializable
data class AuthorResponse(
    @SerialName("login")
    val login: String,
    @SerialName("url")
    val url: String,
    @SerialName("avatarUrl")
    val avatarUrl: String,
)
