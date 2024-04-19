package com.example.data.model

import com.example.local.entity.SeriesEntity
import com.example.model.series.Series
import com.example.network.model.NetworkSeries
import java.util.Calendar
import java.util.Date
import java.util.TimeZone

fun Series.toSeriesEntity(): SeriesEntity {
    return SeriesEntity(
        id = this.id,
        name = this.name,
        description = this.description,
        channel = this.channel,
        genreIndex = this.genreIndex,
        thumbnail = this.thumbnailUrl ?: "",
        createdAt = this.createdAt ?: Calendar.getInstance(),
        updatedAt = this.updatedAt ?: Calendar.getInstance(),
    )
}

fun NetworkSeries.toSeries(): Series {
    return Series(
        id = this.id,
        name = this.name,
        description = this.description,
        channel = this.channel,
        genreIndex = this.genreIndex,
        createdAt = this.createdAt?.toDate()?.toCalendar(),
        updatedAt = this.updatedAt?.toDate()?.toCalendar(),
    )
}

fun Date.toCalendar(): Calendar {
    val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
    calendar.time = this
    return calendar
}