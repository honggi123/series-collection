package com.example.series_collector.remote.model

import com.example.series_collector.model.series.Series
import com.example.series_collector.local.room.entity.SeriesEntity
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import java.util.Calendar
import java.util.Date
import java.util.TimeZone

data class SeriesDTO(
    @DocumentId
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val channel: String = "",
    val haveCount: Int = 0,
    val genreIndex: Int = 0,
    val createdAt: Timestamp? = null,
    val updatedAt: Timestamp? = null
)

fun SeriesDTO.toSeries(): Series {
    return Series(
        id = this.id,
        name = this.name,
        description = this.description,
        channel = this.channel,
        genreIndex = this.genreIndex,
        createdAt = this.createdAt?.toDate()?.toCalendar(),
        updatedAt = this.updatedAt?.toDate()?.toCalendar()
    )
}

fun Date.toCalendar(): Calendar {
    val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
    calendar.time = this
    return calendar
}