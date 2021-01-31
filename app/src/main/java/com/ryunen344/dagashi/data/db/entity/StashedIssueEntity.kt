package com.ryunen344.dagashi.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stashed_issue")
data class StashedIssueEntity(
    @PrimaryKey
    @ColumnInfo(name = "single_unique_id")
    val singleUniqueId: String
)
