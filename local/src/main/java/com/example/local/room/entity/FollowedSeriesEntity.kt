package com.example.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Calendar


@Entity(
    tableName = "series_followed",
    foreignKeys = [
        ForeignKey(entity = SeriesEntity::class, parentColumns = ["id"], childColumns = ["series_id"])
    ],
    indices = [Index("series_id")]
)
data class FollowingSeriesEntity(
    @ColumnInfo(name = "series_id") val seriesId: String,

    @ColumnInfo(name = "put_date") val putDate: Calendar = Calendar.getInstance()
    ) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var seriesFollowedId: Long = 0
}