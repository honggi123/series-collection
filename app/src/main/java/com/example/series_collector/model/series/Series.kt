package com.example.series_collector.model.series

import com.example.series_collector.model.category.CategoryListItem
import com.example.series_collector.model.category.ViewType
import com.example.series_collector.local.room.entity.SeriesEntity
import java.time.LocalDateTime
import java.util.Calendar

data class Series(
    val id: String,
    val name: String,
    val description: String,
    val channel: String,
    val genreIndex: Int,
    val thumbnailUrl: String? = "",
    val createdAt: Calendar?,
    val updatedAt: Calendar?
) : CategoryListItem {
    override val viewType: ViewType
        get() = ViewType.Series

    val genreType: GenreType?
        get() = GenreType.find(genreIndex)
}

fun Series.toSeriesEntity(): SeriesEntity {
    return SeriesEntity(
        id = this.id,
        name = this.name,
        description = this.description,
        channel = this.channel,
        genreIndex = this.genreIndex,
        thumbnail = this.thumbnailUrl ?: "",
        createdAt = this.createdAt ?: Calendar.getInstance(),
        updatedAt = this.updatedAt ?: Calendar.getInstance()
    )
}

enum class GenreType(val value: Int, val displayName: String) {
    FICTION(1, "픽션"),
    TRAVEL(2, "여행");

    companion object {
        fun find(value: Int): GenreType? =
            values().find { it.value == value }
    }
}


//fun List<Series>.asDomain(): List<SeriesEntity> {
//    return this.map {
//        Series(
//            id = it.id,
//            name = it.name,
//            description = it.description,
//            channel = it.channel,
//            genreIndex = it.genreIndex,
//            thumbnailUrl = it.thumbnailUrl ?: "",
//            createdAt = ,
//            updatedAt =
//        )
//    }
//}