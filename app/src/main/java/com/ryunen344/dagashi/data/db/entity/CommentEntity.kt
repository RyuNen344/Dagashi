package com.ryunen344.dagashi.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import org.threeten.bp.OffsetDateTime

@Entity(
    tableName = "comment",
    primaryKeys = ["id", "single_unique_id"],
    foreignKeys = [ForeignKey(
        entity = IssueEntity::class,
        parentColumns = ["single_unique_id"],
        childColumns = ["single_unique_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class CommentEntity(
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "single_unique_id")
    val singleUniqueId: String,
    @ColumnInfo(name = "body")
    val body: String,
    @ColumnInfo(name = "publishedAt")
    val publishedAt: OffsetDateTime,
    @Embedded
    val author: AuthorEntity
)
