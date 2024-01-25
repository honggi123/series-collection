package com.example.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Calendar

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

fun List<SeriesEntity>.toSeriesList(): List<com.example.model.series.Series> {
    return this.map {
        com.example.model.series.Series(
            id = it.id,
            name = it.name,
            description = it.description,
            channel = it.channel,
            genreIndex = it.genreIndex,
            thumbnailUrl = it.thumbnail ?: "",
            createdAt = it.createdAt,
            updatedAt = it.updatedAt
        )
    }
}

fun SeriesEntity.toSeries(): com.example.model.series.Series {
    return com.example.model.series.Series(
        id = this.id,
        name = this.name,
        description = this.description,
        channel = this.channel,
        genreIndex = this.genreIndex,
        thumbnailUrl = this.thumbnail ?: "",
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )
}