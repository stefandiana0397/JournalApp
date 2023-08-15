package com.journalapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.journalapp.data.local.model.JournalEntryEntity

@Dao
interface JournalDao {
    @Query("SELECT * FROM journal")
    suspend fun getJournalEntries(): List<JournalEntryEntity>?

    @Query("DELETE FROM journal")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJournalEntries(entries: List<JournalEntryEntity>)
}