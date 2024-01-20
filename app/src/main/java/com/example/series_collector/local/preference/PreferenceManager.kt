package com.example.series_collector.local.preference

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Calendar
import javax.inject.Inject

class PreferenceManager @Inject constructor(
    @ApplicationContext context: Context,
) {

    companion object {
        private const val PREFERENCE_NAME = "preference_name"
        private const val LAST_UPDATE_DATE = "last_update_date"
    }

    private fun getPreference(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    private val prefs by lazy { getPreference(context) }
    private val editor by lazy { prefs.edit() }
    private fun putLong(key: String, data: Long) {
        editor.putLong(key, data)
        editor.apply()
    }

    private fun getLong(key: String, defValue: Long = 0): Long? {
        return prefs.getLong(key, defValue)
    }

    fun putLastUpdateDate(lastUpdateDate: Calendar) {
        putLong(LAST_UPDATE_DATE, lastUpdateDate.timeInMillis)
    }

    fun getLastUpdateDate(): Calendar? {
        val calendar = Calendar.getInstance()
        val date = getLong(LAST_UPDATE_DATE, 0)

        if (date == 0L) {
            return null
        }

        calendar.timeInMillis = date!!

        return calendar
    }

}