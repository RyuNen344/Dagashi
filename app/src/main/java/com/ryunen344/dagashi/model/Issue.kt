package com.ryunen344.dagashi.model

data class Issue(
    val singleUniqueId: String,
    val url: String,
    val title: String,
    val body: String,
    val labels: List<Label>,
    val comments: List<Comment>,
    val isStashed: Boolean
)
