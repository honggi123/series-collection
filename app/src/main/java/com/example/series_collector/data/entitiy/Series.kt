package com.example.series_collector.data.entitiy

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp
import java.util.*

@Entity(tableName = "Series")
data class Series(
    @PrimaryKey
    @DocumentId
    @ColumnInfo(name = "id")
    val seriesId: String = "",
    @ColumnInfo(name = "name")
    val name: String = "",
    @ColumnInfo(name = "description")
    val description: String = "",
    @ColumnInfo(name = "channel")
    val channel: String = "",
    @ColumnInfo(name = "have_count")
    val haveCount: Int = 0,
    @ColumnInfo(name = "genre")
    val genre: Int = 0,
    @ColumnInfo(name = "thumbnail")
    val thumbnail: String = ""
){
    @ColumnInfo(name = "last_update_date")
    var lastUpdateDate: Calendar = Calendar.getInstance()
}
