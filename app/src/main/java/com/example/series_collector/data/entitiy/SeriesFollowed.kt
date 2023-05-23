package com.example.series_collector.data.entitiy

import androidx.room.*
import com.example.series_collector.data.entitiy.Series
import java.util.*


@Entity(
    tableName = "series_followed",
    foreignKeys = [
        ForeignKey(entity = Series::class, parentColumns = ["id"], childColumns = ["series_id"])
    ],
    indices = [Index("series_id")]
)
data class SeriesFollowed(
    @ColumnInfo(name = "series_id") val seriesId: String,

    @ColumnInfo(name = "put_date") val putDate: Calendar = Calendar.getInstance(),

    ) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var seriesFollowedId: Long = 0
}