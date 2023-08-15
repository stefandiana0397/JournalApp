package com.journalapp.domain.usecase

import com.journalapp.domain.repository.JournalRepository
import javax.inject.Inject

class LoadJournal @Inject constructor(
    private val repository: JournalRepository
) {
    suspend fun execute() {
        repository.loadDailyEntries()
    }
}
