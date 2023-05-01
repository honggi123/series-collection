package com.example.series_collector.data

import androidx.room.*
import com.example.series_collector.data.Series
import java.util.*


@Entity(
    tableName = "my_series",
    foreignKeys = [
        ForeignKey(entity = Series::class, parentColumns = ["id"], childColumns = ["series_id"])
    ],
    indices = [Index("series_id")]
)
data class MySeries(
    @ColumnInfo(name = "series_id") val seriesId: String,

    @ColumnInfo(name = "put_date") val putDate: Calendar = Calendar.getInstance(),

    ) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var mySeriesId: Long = 0
}