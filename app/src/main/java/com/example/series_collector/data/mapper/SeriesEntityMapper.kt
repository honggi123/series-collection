package com.example.series_collector.data.mapper

import com.example.series_collector.data.model.Series
import com.example.series_collector.data.room.entity.SeriesEntity

fun SeriesEntity.asDomain(): Series{
    return Series(
        seriesId = this.id,
        name = this.name,
        description = this.description,
        channel = this.channel,
        genreIndex = this.genreIndex,
        thumbnail = this.thumbnail ?: ""
    )
}

fun List<SeriesEntity>.asDomain(): List<Series> {
    return this.map { seriesEntity ->
        seriesEntity.asDomain()
    }
}

