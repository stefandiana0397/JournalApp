package com.journalapp.domain.usecase

import com.journalapp.domain.model.JournalEntry
import com.journalapp.domain.repository.JournalRepository
import javax.inject.Inject

class DeleteEntry @Inject constructor(
    private val repository: JournalRepository
) {
    suspend fun execute(entry: JournalEntry) {
        repository.deleteEntry(entry)
    }
}
