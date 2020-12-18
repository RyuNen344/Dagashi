package com.ryunen344.dagashi.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.PrimaryKey

@Fts4(contentEntity = IssueEntity::class)
@Entity
data class IssueFts(
    @PrimaryKey
    @ColumnInfo(name = "rowid")
    val rowid: Int,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "body")
    val body: String
)
