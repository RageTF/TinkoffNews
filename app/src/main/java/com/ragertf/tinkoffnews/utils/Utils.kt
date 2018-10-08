package com.ragertf.tinkoffnews.utils

import android.support.annotation.StringRes
import com.ragertf.tinkoffnews.R
import com.ragertf.tinkoffnews.data.dto.Date
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.getOrSet

val moscowTimeZone: TimeZone = TimeZone.getTimeZone("Europe/Moscow")
val dateTimeDateFormat = ThreadLocal<SimpleDateFormat>()

fun Date?.toCalendar(sourceTimeZone: TimeZone = moscowTimeZone, targetTimeZone: TimeZone = TimeZone.getDefault()): Calendar = let {
    if (it != null) {
        val calendar = Calendar.getInstance(sourceTimeZone)
        calendar.timeInMillis = it.milliseconds
        calendar.timeZone = targetTimeZone
        calendar.timeInMillis
        calendar
    } else {
        Calendar.getInstance(targetTimeZone)
    }
}

fun Calendar.toDateTimeFormat(targetTimeZone: TimeZone = TimeZone.getDefault()): String {
    val formatter = dateTimeDateFormat.getOrSet {
        SimpleDateFormat("HH:mm dd.MM.yyyy", Locale.US)
    }
    formatter.timeZone = targetTimeZone
    return formatter.format(time)
}

@StringRes
fun getMonth(month: Int): Int = when (month) {
    0 -> R.string.m0
    1 -> R.string.m1
    2 -> R.string.m2
    3 -> R.string.m3
    4 -> R.string.m4
    5 -> R.string.m5
    6 -> R.string.m6
    7 -> R.string.m7
    8 -> R.string.m8
    9 -> R.string.m9
    10 -> R.string.m10
    11 -> R.string.m11
    else -> -1
}
