package com.jdvq.android_mvvm_app.domain.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(
    tableName = "relations",
    foreignKeys = [
        ForeignKey(
            entity = ObjectEntity::class,
            parentColumns = ["id"],
            childColumns = ["parentId"]
        ),
        ForeignKey(entity = ObjectEntity::class, parentColumns = ["id"], childColumns = ["childId"])
    ]
)
data class RelationEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val parentId: Long,
    val childId: Long
) : Parcelable
