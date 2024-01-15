package com.example.series_collector.data.model

data class Series(
    val seriesId: String,
    val name: String,
    val description: String,
    val channel: String,
    val genreIndex: Int,
    val thumbnail: String = ""
) : ListItem {
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