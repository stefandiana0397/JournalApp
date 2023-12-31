package com.journalapp.data.repository

import android.util.Log
import com.journalapp.data.local.dao.JournalDao
import com.journalapp.data.local.model.toExternal
import com.journalapp.data.remote.EntriesDataSource
import com.journalapp.data.remote.dto.toLocal
import com.journalapp.domain.model.JournalEntry
import com.journalapp.domain.model.Resource
import com.journalapp.domain.model.ResponseType
import com.journalapp.domain.repository.JournalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class JournalRepositoryImpl @Inject constructor(
    private val dataSource: EntriesDataSource,
    private val journalDao: JournalDao
) : JournalRepository {

    private val TAG: String = JournalRepositoryImpl::class.java.simpleName
    override suspend fun getDailyEntries(): Flow<Resource<List<JournalEntry>>> = flow {

        val entries = journalDao.getJournalEntries()?.toExternal()?.sortedByDescending { it.date }
        emit(Resource.Loading(data = entries))

        try {
            val remoteEntries = dataSource.getEntriesData()
            journalDao.deleteAll()
            journalDao.insertJournalEntries(remoteEntries.toLocal())
        } catch (e: IOException) {
            Log.i(TAG, "Exception occurred while fetching the entries")
            emit(Resource.Error("An error occurred, couldn't reach server", entries))
        }

        val newEntries = journalDao.getJournalEntries()?.toExternal()?.sortedByDescending { it.date }
        emit(Resource.Success(newEntries))
    }.flowOn(Dispatchers.IO)

    override suspend fun loadDailyEntries(): Unit = withContext(Dispatchers.IO) {
        dataSource.loadData()
    }

    override suspend fun deleteEntry(entry: JournalEntry) = withContext(Dispatchers.IO) {
        when (dataSource.deleteEntryData(entry)) {
            ResponseType.SUCCESS -> {
                journalDao.deleteEntryById(entry.id)
            }
            else -> {}
        }
    }

    override suspend fun saveEntry(entry: JournalEntry): Boolean = withContext(Dispatchers.IO) {
        when (dataSource.saveEntryData(entry)) {
            ResponseType.SUCCESS -> { true}
            else -> { false}
        }
    }
}
