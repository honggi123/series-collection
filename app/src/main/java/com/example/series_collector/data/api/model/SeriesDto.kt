package com.example.series_collector.data.api.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.series_collector.data.model.Series
import com.example.series_collector.data.room.entity.SeriesEntity
import com.google.firebase.firestore.DocumentId
import java.util.Calendar

data class SeriesDto(
    @DocumentId
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val channel: String = "",
    val haveCount: Int = 0,
    val genreIndex: Int = 0,
    val thumbnail: String? = ""
)

fun SeriesDto.asDomain(): Series {
    return Series(
        seriesId = this.id,
        name = this.name,
        description = this.description,
        channel = this.channel,
        genreIndex = this.genreIndex,
        thumbnail = this.thumbnail ?: ""
    )
}

fun SeriesDto.asEntity(): SeriesEntity {
    return SeriesEntity(
        id = this.id,
        name = this.name,
        description = this.description,
        channel = this.channel,
        genreIndex = this.genreIndex,
        thumbnail = this.thumbnail ?: ""
    )
}