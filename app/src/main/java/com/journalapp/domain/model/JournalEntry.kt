package com.journalapp.domain.model

data class JournalEntry(
    val id: Long,
    val date: Long,
    val summary: String,
    val photos: List<String>? = null,
    val tags: List<Tag>? = null
)
