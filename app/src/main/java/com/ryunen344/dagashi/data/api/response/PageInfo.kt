package com.ryunen344.dagashi.data.api.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PageInfo(
    @SerialName("startCursor")
    val startCursor: String?,
    @SerialName("endCursor")
    val endCursor: String?,
    @SerialName("hasPreviousPage")
    val hasPreviousPage: Boolean,
    @SerialName("hasNextPage")
    val hasNextPage: Boolean
)
