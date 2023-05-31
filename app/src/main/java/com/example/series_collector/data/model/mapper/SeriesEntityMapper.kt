package com.example.series_collector.data.model.mapper

import com.example.series_collector.data.api.PageInfo
import com.example.series_collector.data.model.Series
import com.example.series_collector.data.model.SeriesWithPageInfo
import com.example.series_collector.data.room.entity.SeriesEntity

fun SeriesEntity.asDomain(): Series{
    return Series(
        seriesId = this.seriesId,
        name = this.name,
        description = this.description,
        channel = this.channel,
        genre = this.genre,
        thumbnail = this.thumbnail
    )
}

fun List<SeriesEntity>.asDomain(): List<Series> {
    return this.map { seriesEntity ->
        seriesEntity.asDomain()
    }
}

fun SeriesEntity.toSeriesWithPageInfo(
    pageInfo: PageInfo
): SeriesWithPageInfo {
    return SeriesWithPageInfo(
        series = this.asDomain(),
        pageInfo = pageInfo
    )
}

