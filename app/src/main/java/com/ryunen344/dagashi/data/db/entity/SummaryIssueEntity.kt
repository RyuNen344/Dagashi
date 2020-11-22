package com.ryunen344.dagashi.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "summary_issue",
    primaryKeys = ["id", "mile_stone_id"],
    foreignKeys = [ForeignKey(
        entity = MileStoneEntity::class,
        parentColumns = ["id"],
        childColumns = ["mile_stone_id"]
    )]
)
data class SummaryIssueEntity(
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "mile_stone_id")
    val mileStoneId: String,
    @ColumnInfo(name = "title")
    val title: String
)
