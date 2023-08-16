package com.journalapp.presentation.journallist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.journalapp.domain.model.JournalEntry
import com.journalapp.domain.model.Resource
import com.journalapp.domain.model.Tag
import com.journalapp.domain.usecase.DeleteEntry
import com.journalapp.domain.usecase.GetDailyGratitudeEntries
import com.journalapp.domain.usecase.SaveEntry
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.spyk
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DailyListViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `updateEntries success`() = runTest(testDispatcher) {
        val entry = JournalEntry(
            id = 1,
            date = 1686389015000,
            summary = "I’m grateful for my little brother and how he makes me laugh, even if he distracts me from completing my homework.",
            photos = listOf("https://live.staticflickr.com/65535/53097460011_e2c2d233cf_z.jpg"),
            tags = listOf(Tag("#coffe"))
        )

        val successData = listOf(entry)
        val successFlow: Flow<Resource<List<JournalEntry>>> = flowOf(Resource.Success(successData))
        val getDailyGratitudeEntries = mockk<GetDailyGratitudeEntries>()
        val deleteEntry = mockk<DeleteEntry>()
        val saveEntry = mockk<SaveEntry>()
        coEvery { getDailyGratitudeEntries.execute() } returns successFlow

        val viewModel = getTestedViewModel(getDailyGratitudeEntries, deleteEntry, saveEntry)
        viewModel.onEvent(DailyEvent.GetEntries)

        val currentState = viewModel.state.value
        assert(currentState.entries == successData)
        assertFalse { currentState.isLoading }
        assertNull(currentState.error)
        assertNull(currentState.selectedEntry)

        // Verify that the execute function was called
        coVerify { getDailyGratitudeEntries.execute() }
    }

    @Test
    fun `updateEntries error`() = runTest(testDispatcher) {
        val errorFlow: Flow<Resource<List<JournalEntry>>> = flowOf(
            Resource.Error("An error occurred.", null)
        )
        val getDailyGratitudeEntries = mockk<GetDailyGratitudeEntries>()
        val deleteEntry = mockk<DeleteEntry>()
        val saveEntry = mockk<SaveEntry>()
        coEvery { getDailyGratitudeEntries.execute() } returns errorFlow

        val viewModel = getTestedViewModel(getDailyGratitudeEntries, deleteEntry, saveEntry)
        viewModel.onEvent(DailyEvent.GetEntries)

        val currentState = viewModel.state.value
        assert(currentState.entries.isEmpty())
        assertFalse { currentState.isLoading }
        assertNotNull(currentState.error)
        assertNull(currentState.selectedEntry)

        // Verify that the execute function was called
        coEvery { getDailyGratitudeEntries.execute() }
    }

    @Test
    fun `updateEntries loading`() = runTest(testDispatcher) {
        val loadingFlow: Flow<Resource<List<JournalEntry>>> = flowOf(Resource.Loading(null))
        val getDailyGratitudeEntries = mockk<GetDailyGratitudeEntries>()
        val deleteEntry = mockk<DeleteEntry>()
        val saveEntry = mockk<SaveEntry>()
        coEvery { getDailyGratitudeEntries.execute() } returns loadingFlow

        val viewModel = getTestedViewModel(getDailyGratitudeEntries, deleteEntry, saveEntry)
        viewModel.onEvent(DailyEvent.GetEntries)

        val currentState = viewModel.state.value
        assertTrue(currentState.entries.isEmpty())
        assertTrue { currentState.isLoading }
        assertNull(currentState.error)
        assertNull(currentState.selectedEntry)

        // Verify that the execute function was called
        coEvery { getDailyGratitudeEntries.execute() }
    }

    private fun getTestedViewModel(
        getDailyGratitudeEntries: GetDailyGratitudeEntries,
        deleteEntry: DeleteEntry,
        saveEntry: SaveEntry
    ) = spyk(
        recordPrivateCalls = true,
        objToCopy = DailyListViewModel(getDailyGratitudeEntries, deleteEntry, saveEntry)
    )
}
