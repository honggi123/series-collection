package com.example.model.common

data class Tag(
    val type: TagType,
    val name: String?
)

enum class TagType {
    GENRE,
    CHANNEL,
    TOTAL_PAGE
}

