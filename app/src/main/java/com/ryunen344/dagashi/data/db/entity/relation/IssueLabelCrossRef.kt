package com.ryunen344.dagashi.data.db.entity.relation

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.ryunen344.dagashi.data.db.entity.IssueEntity
import com.ryunen344.dagashi.data.db.entity.LabelEntity

@Entity(
    primaryKeys = ["single_unique_id", "label_name"],
    foreignKeys = [
        ForeignKey(
            entity = IssueEntity::class,
            parentColumns = ["single_unique_id"],
            childColumns = ["single_unique_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = LabelEntity::class,
            parentColumns = ["name"],
            childColumns = ["label_name"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class IssueLabelCrossRef(
    @ColumnInfo(name = "single_unique_id")
    val singleUniqueId: String,
    @ColumnInfo(name = "label_name")
    val labelName: String
)
