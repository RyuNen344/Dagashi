package com.ryunen344.dagashi.model

data class Label(
    val name: String,
    val description: String,
    val color: String
) {
    val labelIssueUrl: String
        get() = "https://github.com/AndroidDagashi/AndroidDagashi/issues?q=label%3A%22$name%22"
}
