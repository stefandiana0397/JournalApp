package com.journalapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.journalapp.domain.usecase.LoadJournal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val loadJournal: LoadJournal
) : ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            loadJournal.execute()
        }
    }

    companion object {

        fun factory(
            loadJournal: LoadJournal
        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                    return MainViewModel(loadJournal) as T
                }
            }
        }
    }
}