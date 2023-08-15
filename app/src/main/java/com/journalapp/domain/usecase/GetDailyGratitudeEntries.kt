package com.journalapp.domain.usecase

import com.journalapp.domain.model.JournalEntry
import com.journalapp.domain.model.Resource
import com.journalapp.domain.repository.JournalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDailyGratitudeEntries @Inject constructor(
    private val repository: JournalRepository
) {

    suspend fun execute(): Flow<Resource<List<JournalEntry>>> {
        return repository.getDailyEntries()
    }
}
