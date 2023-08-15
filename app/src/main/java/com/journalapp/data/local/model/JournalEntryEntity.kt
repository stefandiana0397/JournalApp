package com.journalapp.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.journalapp.domain.model.JournalEntry
import com.journalapp.domain.model.Tag

@Entity(tableName = "journal")
data class JournalEntryEntity(
    @PrimaryKey
    val id: Long? = null,
    val date: Long,
    val summary: String,
    val photos: List<String>?,
    val tags: List<Tag>?
)

fun JournalEntryEntity.toExternal(): JournalEntry {
    return JournalEntry(date, summary, photos, tags)
}

fun List<JournalEntryEntity>.toExternal() = map(JournalEntryEntity::toExternal)

fun JournalEntry.toLocal(): JournalEntryEntity {
    return JournalEntryEntity(
        date = date,
        summary = summary,
        photos = photos,
        tags = tags
    )
}

fun List<JournalEntry>.toLocal() = map(JournalEntry::toLocal)
