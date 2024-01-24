package com.example.model.series

data class Tag(
    val type: TagType,
    val name: String?
)

enum class TagType {
    GENRE,
    CHANNEL,
    TOTAL_PAGE
}

