package com.example.composetesting.models

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun formatTimeRange(startDate: Date, endDate: Date): String {
    val startTime = DateFormats.Local.hourMinuteAmPm().format(startDate)
    val endTime = DateFormats.Local.hourMinuteAmPm().format(endDate)
    return String.format(Locale.getDefault(), "%s - %s", startTime, endTime)
}

fun getDaysAgo(daysAgo: Int): Date {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_YEAR, -daysAgo)

    return calendar.time
}

fun formatDateAndTimeRange(startDate: Date, endDate: Date): String {
    val starDateFormat = SimpleDateFormat("E, MMM yyyy, ${getHourMeridianDateFormatString(startDate)}", Locale.getDefault()).apply { timeZone = TimeZone.getDefault() }
    val startDateString = addOrdinalDay(starDateFormat, startDate)

    val endDateHoursFormatString = getHourMeridianDateFormatString(endDate)
    val endDateString = if (startDate.sameDay(endDate)) {
        SimpleDateFormat(endDateHoursFormatString, Locale.getDefault()).apply { timeZone = TimeZone.getDefault() }.format(endDate)
    } else {
        addOrdinalDay(SimpleDateFormat("E, MMM yyyy, $endDateHoursFormatString", Locale.getDefault()).apply { timeZone = TimeZone.getDefault() }, endDate)
    }

    return String.format(Locale.getDefault(), "%s - %s", startDateString, endDateString)
}

private fun addOrdinalDay(formatter: SimpleDateFormat, date: Date): String {
    val dateString = formatter.format(date)
    val cal = Calendar.getInstance()
    cal.time = date
    val day = cal.get(Calendar.DAY_OF_MONTH)
    val stringBuilder = StringBuilder(dateString)
    val insertIndex = findOrdinalDayInsertIndex(dateString)

    if (dateString.indexOf(",") != -1 && insertIndex != null) {
        stringBuilder.insert(insertIndex, " $day")
    }

    return stringBuilder.toString()
}

private fun findOrdinalDayInsertIndex(string: String): Int? {
    var spaceCount = 0
    string.forEachIndexed { index, c ->
        if (c.isWhitespace()) {
            spaceCount += 1
            if (Locale.getDefault() == Locale.UK && spaceCount == 1) return index
            if (spaceCount == 2) {
                return index
            }
        }
    }
    return null
}

fun Date.sameDay(date: Date): Boolean {
    val cal1 = Calendar.getInstance().apply { time = this@sameDay }
    val cal2 = Calendar.getInstance().apply { time = date }

    return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
        cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
}

private fun getHourMeridianDateFormatString(date: Date): String {
    val cal = Calendar.getInstance()
    cal.time = date
    val minutes = cal.get(Calendar.MINUTE)
    return if (minutes != 0) {
        "h:mma"
    } else {
        "ha"
    }
}