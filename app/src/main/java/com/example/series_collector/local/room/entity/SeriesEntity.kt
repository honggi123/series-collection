package com.example.series_collector.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.series_collector.data.model.Series
import java.util.*

@Entity(tableName = "Series")
data class SeriesEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String = "",
    @ColumnInfo(name = "name")
    val name: String = "",
    @ColumnInfo(name = "description")
    val description: String = "",
    @ColumnInfo(name = "channel")
    val channel: String = "",
    @ColumnInfo(name = "have_count")
    val haveCount: Int = 0,
    @ColumnInfo(name = "genre")
    val genreIndex: Int = 0,
    @ColumnInfo(name = "thumbnail")
    val thumbnail: String? = "",
    @ColumnInfo(name = "createdAt")
    val createdAt: Calendar,
    @ColumnInfo(name = "updatedAt")
    val updatedAt: Calendar
)

fun List<SeriesEntity>.asDomain(): List<Series> {
    return this.map {
        Series(
            seriesId = it.id,
            name = it.name,
            description = it.description,
            channel = it.channel,
            genreIndex = it.genreIndex,
            thumbnail = it.thumbnail ?: ""
        )
    }
}

fun SeriesEntity.asDomain(): Series {
    return Series(
        seriesId = this.id,
        name = this.name,
        description = this.description,
        channel = this.channel,
        genreIndex = this.genreIndex,
        thumbnail = this.thumbnail ?: ""
    )
}