package com.journalapp.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.journalapp.domain.model.JournalEntry
import com.journalapp.domain.model.Tag
import com.journalapp.domain.model.TagDeserializer

class EntriesDataSource(private val context: Context) {

    fun getEntriesData(): List<JournalEntry> {
        val fileContents = context.assets.open(ENTRIES_FILE).bufferedReader().use { it.readText() }
        val gson = GsonBuilder()
            .registerTypeAdapter(Tag::class.java, TagDeserializer())
            .create()
        return gson.fromJson(fileContents, Array<JournalEntry>::class.java).toList()
    }

    companion object {
        const val ENTRIES_FILE = "daily.json"
    }
}