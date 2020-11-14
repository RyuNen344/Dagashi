package com.ryunen344.dagashi.model

import org.threeten.bp.OffsetDateTime

data class Comment(
    val body: String,
    val publishedAt: OffsetDateTime,
    val author: Author
)
