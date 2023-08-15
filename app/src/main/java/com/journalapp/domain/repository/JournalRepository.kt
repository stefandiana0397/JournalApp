package com.journalapp.domain.repository

import com.journalapp.domain.model.JournalEntry

interface JournalRepository {

    suspend fun getDailyEntries(): List<JournalEntry>
}