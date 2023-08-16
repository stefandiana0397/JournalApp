package com.journalapp.presentation.journallist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.journalapp.R
import com.journalapp.domain.model.JournalEntry
import com.journalapp.domain.model.Resource
import com.journalapp.domain.usecase.DeleteEntry
import com.journalapp.domain.usecase.GetDailyGratitudeEntries
import com.journalapp.domain.usecase.SaveEntry
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class DailyListViewModel @Inject constructor(
    private val getDailyGratitudeEntries: GetDailyGratitudeEntries,
    private val deleteEntry: DeleteEntry,
    private val saveEntry: SaveEntry
) : ViewModel() {

    private val _state = MutableStateFlow(DailyState())
    val state = _state.asStateFlow()

    init {
        updateEntries()
    }

    fun onEvent(event: DailyEvent) {
        when (event) {
            is DailyEvent.GetEntries -> updateEntries()
            is DailyEvent.SelectEntry -> selectEntry(event.entry)
            is DailyEvent.DeleteEntry -> deleteEntry(event.entry)
            is DailyEvent.SaveEntry -> saveEntry(event.entry)
            else -> {}
        }
    }

    private fun updateEntries() {
        viewModelScope.launch {
            getDailyGratitudeEntries.execute().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                entries = result.data ?: emptyList(),
                                isLoading = false,
                                error = null
                            )
                        }
                    }
                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                entries = result.data ?: emptyList(),
                                isLoading = false,
                                error = R.string.error_loading_entries
                            )
                        }
                    }
                    is Resource.Loading -> {
                        _state.update {
                            it.copy(
                                entries = result.data ?: emptyList(),
                                isLoading = true,
                                error = null
                            )
                        }
                    }
                }
            }
        }
    }

    private fun selectEntry(entry: JournalEntry) {
        _state.update {
            it.copy(selectedEntry = entry)
        }
    }

    private fun deleteEntry(entry: JournalEntry) {
        viewModelScope.launch {
            deleteEntry.execute(entry)
        }
        updateEntries()
    }

    private fun saveEntry(entry: JournalEntry) {
        viewModelScope.launch {
            saveEntry.execute(entry).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = null,
                                selectedEntry = entry
                            )
                        }
                    }

                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = R.string.error_entry
                            )
                        }
                    }

                    is Resource.Loading -> {
                        _state.update {
                            it.copy(
                                isLoading = true,
                                error = null
                            )
                        }
                    }
                }
            }
        }
        updateEntries()
    }
}
