package com.journalapp.presentation.journallist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.journalapp.R
import com.journalapp.domain.model.JournalEntry
import com.journalapp.domain.model.Resource
import com.journalapp.domain.usecase.GetDailyGratitudeEntries
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DailyListViewModel @Inject constructor(
    private val getDailyGratitudeEntries: GetDailyGratitudeEntries
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
                                error = null,
                                selectedEntry = null
                            )
                        }
                    }
                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                entries = result.data ?: emptyList(),
                                isLoading = false,
                                error = R.string.error_loading_entries,
                                selectedEntry = null
                            )
                        }
                    }
                    is Resource.Loading -> {
                        _state.update {
                            it.copy(
                                entries = result.data ?: emptyList(),
                                isLoading = true,
                                error = null,
                                selectedEntry = null
                            )
                        }
                    }
                }
            }
        }
    }

    private fun selectEntry(entry: JournalEntry) {
        _state.update {
            it.copy(
                selectedEntry = entry
            )
        }
    }
}
