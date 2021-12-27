package com.ryunen344.dagashi.model

import java.time.OffsetDateTime

data class Comment(
    val body: String,
    val publishedAt: OffsetDateTime,
    val author: Author
)
