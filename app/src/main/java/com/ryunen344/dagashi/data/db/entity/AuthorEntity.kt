package com.ryunen344.dagashi.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "author")
data class AuthorEntity(
    @ColumnInfo(name = "login")
    val login: String,
    @ColumnInfo(name = "url")
    val url: String,
    @ColumnInfo(name = "avatarUrl")
    val avatarUrl: String
)
