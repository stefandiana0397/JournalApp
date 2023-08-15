package com.journalapp.data.local.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.journalapp.data.local.converter.StringListConverter
import com.journalapp.data.local.converter.TagConverter
import com.journalapp.data.local.dao.JournalDao
import com.journalapp.data.local.model.JournalEntryEntity

@Database(
    entities = [JournalEntryEntity::class],
    version = 1
)
@TypeConverters(StringListConverter::class, TagConverter::class)
abstract class LocalDatabase : RoomDatabase() {
    abstract val journalDao: JournalDao
}
