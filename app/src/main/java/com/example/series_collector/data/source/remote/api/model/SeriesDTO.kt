package com.example.series_collector.data.source.remote.api.model

import com.example.series_collector.data.model.Series
import com.example.series_collector.data.source.local.room.entity.SeriesEntity
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.PropertyName
import java.util.Calendar

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

fun SeriesDTO.asDomain(): Series {
    return Series(
        seriesId = this.id,
        name = this.name,
        description = this.description,
        channel = this.channel,
        genreIndex = this.genreIndex
    )
}

fun SeriesDTO.asEntity(): SeriesEntity {
    val creatCalendar = Calendar.getInstance()
    val updateCalendar = Calendar.getInstance()
    creatCalendar.time = createdAt?.toDate()
    updateCalendar.time = updatedAt?.toDate()

    return SeriesEntity(
        id = this.id,
        name = this.name,
        description = this.description,
        channel = this.channel,
        genreIndex = this.genreIndex,
        createdAt = creatCalendar,
        updatedAt = updateCalendar
    )
}