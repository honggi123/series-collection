package com.example.series_collector.data.source.local.room

import androidx.room.TypeConverter
import java.util.Calendar

class Converter {
    @TypeConverter fun calendarToDatestamp(calendar: Calendar): Long = calendar.timeInMillis

    @TypeConverter fun datestampToCalendar(value: Long): Calendar =
        Calendar.getInstance().apply { timeInMillis = value }
}
