package com.journalapp.data.local.datasource

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.google.gson.Gson
import com.journalapp.data.local.converter.StringListConverter
import com.journalapp.data.local.converter.TagConverter
import com.journalapp.data.local.dao.JournalDao
import com.journalapp.data.local.model.JournalEntryEntity
import com.journalapp.data.util.GsonParser
import com.journalapp.domain.model.Tag
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LocalDatabaseTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var database: LocalDatabase

    private lateinit var journalDao: JournalDao

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context,
            LocalDatabase::class.java
        ).addTypeConverter(StringListConverter(GsonParser(Gson())))
            .addTypeConverter(TagConverter(GsonParser(Gson())))
            .allowMainThreadQueries().build()

        journalDao = database.journalDao
    }

    @After
    fun tearDown() {
        database.close()
        Dispatchers.resetMain()
    }

    @Test
    fun getJournalEntries() = runTest {
        val entry = JournalEntryEntity(
            id = 1,
            date = 1686389015000,
            summary = "I’m grateful for my little brother and how he makes me laugh, even if he distracts me from completing my homework.",
            photos = listOf("https://live.staticflickr.com/65535/53097460011_e2c2d233cf_z.jpg"),
            tags = listOf(Tag("#coffe"))
        )
        journalDao.insertJournalEntries(listOf(entry))

        val entries = journalDao.getJournalEntries()

        assertEquals(1, entries?.size)
        assertEquals(entry, entries?.first())
    }
}
