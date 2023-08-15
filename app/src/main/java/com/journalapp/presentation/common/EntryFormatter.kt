package com.journalapp.presentation.common

import com.journalapp.domain.model.JournalEntry
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.util.concurrent.TimeUnit

object EntryFormatter {
    fun convertMillisecondsToDate(milliseconds: Long): String {
        val instant = Instant.ofEpochMilli(milliseconds)
        val localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate()
        val formatter = DateTimeFormatter.ofPattern("MMMM dd yyyy", Locale.getDefault())
        return localDate.format(formatter)
    }

    fun getTimeFrame(timestamp: Long): TimeFrame {
        val currentTimeMillis = System.currentTimeMillis()
        val elapsedTimeMillis = currentTimeMillis - timestamp

        val elapsedDays = TimeUnit.MILLISECONDS.toDays(elapsedTimeMillis)
        val currentDate = LocalDate.now(ZoneId.systemDefault())
        val entryDate = LocalDateTime.ofInstant(
            Instant.ofEpochMilli(timestamp),
            ZoneId.systemDefault()
        ).toLocalDate()

        return when {
            elapsedDays < 7 -> TimeFrame.THIS_WEEK
            elapsedDays < 14 -> TimeFrame.LAST_WEEK
            entryDate.year == currentDate.year &&
                entryDate.monthValue == currentDate.monthValue - 1 -> TimeFrame.LAST_MONTH
            entryDate.year == currentDate.year -> TimeFrame.MORE_THAN_A_MONTH_AGO
            entryDate.year == currentDate.year - 1 -> TimeFrame.LAST_YEAR
            else -> TimeFrame.MORE_THAN_A_YEAR_AGO
        }
    }

    fun groupEntriesByTimeFrame(entries: List<JournalEntry>): Map<TimeFrame, List<JournalEntry>> {
        return entries.groupBy { getTimeFrame(it.date) }
    }
}
