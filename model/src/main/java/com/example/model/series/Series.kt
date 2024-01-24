package com.example.model.series

import com.example.model.category.CategoryListItem
import com.example.model.category.ViewType
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