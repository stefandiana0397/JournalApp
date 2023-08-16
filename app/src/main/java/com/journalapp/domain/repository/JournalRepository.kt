package com.journalapp.domain.repository

import com.journalapp.domain.model.JournalEntry
import com.journalapp.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface JournalRepository {

    suspend fun getDailyEntries(): Flow<Resource<List<JournalEntry>>>
    suspend fun loadDailyEntries()
    suspend fun deleteEntry(entry: JournalEntry)
    suspend fun saveEntry(entry: JournalEntry): Boolean

}