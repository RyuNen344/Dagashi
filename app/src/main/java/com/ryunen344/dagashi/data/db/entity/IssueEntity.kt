package com.ryunen344.dagashi.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "issue")
data class IssueEntity(
    @PrimaryKey
    @ColumnInfo(name = "single_unique_id")
    val singleUniqueId: String,
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "number")
    val number: Int,
    @ColumnInfo(name = "url")
    val url: String,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "body")
    val body: String,
)
