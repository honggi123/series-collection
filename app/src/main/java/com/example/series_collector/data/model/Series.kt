package com.example.series_collector.data.model

data class Series(
    val seriesId: String,
    val name: String,
    val description: String,
    val channel: String,
    val genre: Int,
    val thumbnail: String
) : ListItem {
    override val viewType: ViewType
        get() = ViewType.Series

    val genreType: GenreType?
        get() = GenreType.find(genre)
}

enum class GenreType(val value: Int) {
    FICTION(1),
    TRAVEL(2);

    companion object {
        fun find(value: Int): GenreType? =
            values().find { it.value == value }
    }
}