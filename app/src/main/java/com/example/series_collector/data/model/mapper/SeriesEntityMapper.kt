package com.example.series_collector.data.model.mapper

import com.example.series_collector.data.api.PageInfo
import com.example.series_collector.data.model.Series
import com.example.series_collector.data.model.SeriesWithPageInfo
import com.example.series_collector.data.room.entity.SeriesEntity

fun List<SeriesEntity>.asDomain(): List<Series> {
    return this.map { seriesEntity ->
        Series(
            seriesId = seriesEntity.seriesId,
            name = seriesEntity.name,
            description = seriesEntity.description,
            channel = seriesEntity.channel,
            genre = seriesEntity.genre,
            thumbnail = seriesEntity.thumbnail
        )
    }
}

fun SeriesEntity.toSeriesWithPageInfo(
    pageInfo: PageInfo
): SeriesWithPageInfo {
    return SeriesWithPageInfo(
        name = name,
        description = description,
        channel = channel,
        haveCount = haveCount,
        genre = genre,
        thumbnail = thumbnail,
        pageInfo = pageInfo
    )
}
