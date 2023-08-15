package com.journalapp.domain.usecase

import android.util.Log
import com.journalapp.domain.model.JournalEntry
import com.journalapp.domain.model.Resource
import com.journalapp.domain.repository.JournalRepository
import java.io.IOException
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetDailyGratitudeEntries @Inject constructor(
    private val repository: JournalRepository
) {
    private val TAG: String = GetDailyGratitudeEntries::class.java.simpleName

    suspend fun execute(): Flow<Resource<List<JournalEntry>>> = flow {
        try {
            emit(Resource.Loading())
            val entries = repository.getDailyEntries()
            emit(Resource.Success(entries))
        } catch (e: IOException) {
            Log.i(TAG, "Exception occurred while fetching the entries")
            emit(Resource.Error(e.localizedMessage ?: "Couldn't reach server"))
        }
    }.flowOn(Dispatchers.IO)
}
