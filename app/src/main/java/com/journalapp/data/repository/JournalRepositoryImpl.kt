package com.journalapp.data.repository

import com.journalapp.data.EntriesDataSource
import com.journalapp.domain.model.JournalEntry
import com.journalapp.domain.repository.JournalRepository
import kotlinx.coroutines.delay

class JournalRepositoryImpl(
    private val dataSource: EntriesDataSource
) : JournalRepository {
    override suspend fun getDailyEntries(): List<JournalEntry> {
        delay(2000)
        return dataSource.getEntriesData().sortedByDescending { it.date }
    }
}
