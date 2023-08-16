package com.journalapp.data.remote

import android.content.Context
import com.google.gson.GsonBuilder
import com.journalapp.data.remote.dto.JournalEntryDto
import com.journalapp.data.remote.dto.toDto
import com.journalapp.domain.model.JournalEntry
import com.journalapp.domain.model.ResponseType
import com.journalapp.domain.model.Tag
import com.journalapp.domain.model.TagDeserializer
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlinx.coroutines.delay
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class EntriesDataSource(private val context: Context) {

    // A mutex is used to ensure that reads and writes are thread-safe.
    private val accessMutex = Mutex()

    private var journalList: List<JournalEntryDto> = listOf()

    suspend fun loadData(): List<JournalEntryDto> = accessMutex.withLock {
        val fileContents = context.assets.open(ENTRIES_FILE).bufferedReader().use { it.readText() }
        val gson = GsonBuilder()
            .registerTypeAdapter(Tag::class.java, TagDeserializer())
            .create()
        journalList = gson.fromJson(fileContents, Array<JournalEntryDto>::class.java).toList()
        return journalList
    }

    suspend fun getEntriesData(): List<JournalEntryDto> = accessMutex.withLock {
        delay(SERVICE_LATENCY_IN_MILLIS)
        return journalList
    }

    suspend fun saveEntriesData(newList: List<JournalEntryDto>) = accessMutex.withLock {
        delay(SERVICE_LATENCY_IN_MILLIS)
        journalList = newList
    }

    suspend fun deleteEntryData(entry: JournalEntry): ResponseType = accessMutex.withLock {
        delay(SERVICE_LATENCY_IN_MILLIS)
        suspendCoroutine { continuation ->
            val entryDto = journalList.find { it.id == entry.id }
            if (entryDto != null) {
                journalList = journalList.toMutableList().apply { remove(entryDto) }
                continuation.resume(ResponseType.SUCCESS)
            } else {
                continuation.resume(ResponseType.NOT_FOUND)
            }
        }
    }

    suspend fun saveEntryData(newEntry: JournalEntry): ResponseType = accessMutex.withLock {
        delay(SERVICE_LATENCY_IN_MILLIS)
        suspendCoroutine { continuation ->
            val updatedList = journalList.map { entry ->
                if (entry.id == newEntry.id) { newEntry.toDto() } else { entry }
            }
            if (updatedList != journalList) {
                // Update took place
                journalList = updatedList
                continuation.resume(ResponseType.SUCCESS)
            } else {
                continuation.resume(ResponseType.NOT_FOUND)
            }
        }
    }

    companion object {
        const val ENTRIES_FILE = "daily.json"
        private const val SERVICE_LATENCY_IN_MILLIS = 1000L
    }
}
