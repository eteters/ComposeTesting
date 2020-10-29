package com.example.composetesting.models

import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

/**
 * Factory class the creates commonly used date formats.
 *
 * @author berbschloe@quarkworks.co (Brandon Erbschloe)
 */
object DateFormats {

    object GMT {

        private fun dateFormat(pattern: String) = SimpleDateFormat(pattern, Locale.US).apply {
            timeZone = TimeZone.getTimeZone("GMT")
        }

        fun iso8601Full() = dateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")

        fun iso8601WithOffset() = dateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")

        fun yyyy_MM_dd() = dateFormat("yyyy-MM-dd")

        fun shortDate() = if (Locale.getDefault() == Locale.UK) dateFormat("d MMM") else dateFormat("MMM d")

        fun shortMonthCommaYear() = dateFormat("MMM, yyyy")

        fun shortMonthYear() = dateFormat("MMM yyyy")
    }

    object Local {

        private fun dateFormat(pattern: String) = SimpleDateFormat(pattern, Locale.US).apply {
            timeZone = TimeZone.getDefault()
        }

        fun dayOfWeek() = dateFormat("dd")

        fun shortDate() = if (Locale.getDefault() == Locale.UK) dateFormat("d MMM") else dateFormat("MMM d")

        fun hourAmPm() = dateFormat("h a")

        fun hourMinuteAmPm() = dateFormat("h:mma")

        fun shortMonth() = dateFormat("MM")

        fun mediumMonth() = dateFormat("MMM")

        fun monthYear() = dateFormat("MMMM yyyy")

        fun shortMonthYear() = dateFormat("MMM yyyy")

        fun year() = dateFormat("yyyy")

        fun shortYear() = dateFormat("yy")

        fun weekMonthYearTime() = if (Locale.getDefault() == Locale.UK) dateFormat("E, d MMM yyyy, h:ma") else dateFormat("E, MMM d yyyy, h:ma")

        fun shortMonthDay() = if (Locale.getDefault() == Locale.UK) dateFormat("d MMM，h:mm a ") else dateFormat("MMM d, h:mm a")

        fun longMonthDay() = if (Locale.getDefault() == Locale.UK) dateFormat("EEEE, d MMMM yyyy") else dateFormat("EEEE, MMMM d yyyy")
    }
}

/**
 * Parses a string into a date. Swallows the exception and returns null on failure.
 */
fun DateFormat.parseSafely(string: String): Date? = try { parse(string) } catch (ex: ParseException) { null }
