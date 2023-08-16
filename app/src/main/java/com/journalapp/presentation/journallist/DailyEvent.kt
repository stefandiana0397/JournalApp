package com.journalapp.presentation.journallist

import com.journalapp.domain.model.JournalEntry

sealed class DailyEvent {
    data class SelectEntry(val entry: JournalEntry) : DailyEvent()
    object GetEntries : DailyEvent()
    data class DeleteEntry(val entry: JournalEntry) : DailyEvent()
    data class SaveEntry(val entry: JournalEntry) : DailyEvent()
}
