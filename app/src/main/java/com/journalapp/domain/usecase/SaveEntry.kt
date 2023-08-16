package com.journalapp.domain.usecase

import com.journalapp.domain.model.JournalEntry
import com.journalapp.domain.model.Resource
import com.journalapp.domain.repository.JournalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SaveEntry @Inject constructor(
    private val repository: JournalRepository
) {
    suspend fun execute(entry: JournalEntry): Flow<Resource<JournalEntry>> = flow {
        try {
            emit(Resource.Loading())
            val hasChanged = repository.saveEntry(entry)
            if (hasChanged) emit(Resource.Success(entry)) else emit(Resource.Error(""))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: ""))
        }
    }
}
