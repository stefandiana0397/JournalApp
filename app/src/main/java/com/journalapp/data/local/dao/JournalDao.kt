package com.journalapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.journalapp.data.local.model.JournalEntryEntity
import com.journalapp.domain.model.Tag

@Dao
interface JournalDao {
    @Query("SELECT * FROM journal")
    suspend fun getJournalEntries(): List<JournalEntryEntity>?

    @Query("DELETE FROM journal")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJournalEntries(entries: List<JournalEntryEntity>)

    @Query(
        "DELETE FROM journal WHERE date = :entryDate AND summary = :entrySummary AND photos = :entryPhotos AND tags = :entryTags"
    )
    suspend fun deleteEntryByProperties(
        entryDate: Long,
        entrySummary: String,
        entryPhotos: List<String>?,
        entryTags: List<Tag>?
    )
}
