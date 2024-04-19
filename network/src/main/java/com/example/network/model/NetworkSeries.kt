package com.example.network.model


import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

data class NetworkSeries(
    @DocumentId
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val channel: String = "",
    val haveCount: Int = 0,
    val genreIndex: Int = 0,
    val createdAt: Timestamp? = null,
    val updatedAt: Timestamp? = null,
)

