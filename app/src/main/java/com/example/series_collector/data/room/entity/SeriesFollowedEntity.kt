package com.example.series_collector.data.room.entity

import androidx.room.*
import java.util.*


@Entity(
    tableName = "series_followed",
    foreignKeys = [
        ForeignKey(entity = SeriesEntity::class, parentColumns = ["id"], childColumns = ["series_id"])
    ],
    indices = [Index("series_id")]
)
data class SeriesFollowedEntity(
    @ColumnInfo(name = "series_id") val seriesId: String,

    @ColumnInfo(name = "put_date") val putDate: Calendar = Calendar.getInstance(),

    ) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var seriesFollowedId: Long = 0
}