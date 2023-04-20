package com.example.series_collector.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.DocumentId
import java.util.Calendar

@Entity(tableName = "Series")
data class Series(
    @PrimaryKey
    @DocumentId
    @ColumnInfo(name = "id")
    val SeriesId: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "have_count")
    val have_count: Int,
    @ColumnInfo(name = "genre")
    val genre: Int,
){
    @ColumnInfo(name = "last_update_date")
    var lastUpdateDate: Calendar = Calendar.getInstance()
}
