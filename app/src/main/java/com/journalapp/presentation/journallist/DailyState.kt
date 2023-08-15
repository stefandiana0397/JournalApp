package com.journalapp.presentation.journallist

import com.journalapp.domain.model.JournalEntry

data class DailyState(
    val entries: List<JournalEntry> = emptyList(),
    val isLoading: Boolean = false,
    val error: Int? = null,
    val selectedEntry: JournalEntry? = null
)
