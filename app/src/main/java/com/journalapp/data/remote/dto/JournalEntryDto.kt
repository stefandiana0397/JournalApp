package com.journalapp.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.journalapp.data.local.model.JournalEntryEntity
import com.journalapp.domain.model.JournalEntry
import com.journalapp.domain.model.Tag

data class JournalEntryDto(
    @SerializedName("entry_date")
    val date: Long,
    @SerializedName("entry_summary")
    val summary: String,
    @SerializedName("entry_images")
    val photos: List<String>?,
    @SerializedName("entry_tags")
    val tags: List<Tag>?
)

fun JournalEntryDto.toExternal(): JournalEntry {
    return JournalEntry(date, summary, photos, tags)
}

fun List<JournalEntryDto>.toExternal() = map(JournalEntryDto::toExternal)

fun JournalEntryDto.toLocal(): JournalEntryEntity {
    return JournalEntryEntity(
        date = date,
        summary = summary,
        photos = photos,
        tags = tags
    )
}

fun List<JournalEntryDto>.toLocal() = map(JournalEntryDto::toLocal)
