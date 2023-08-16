package com.journalapp.presentation.journallistdetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.journalapp.presentation.journaldetail.DailyDetailsScreen
import com.journalapp.presentation.journallist.DailyEvent
import com.journalapp.presentation.journallist.DailyState
import com.journalapp.presentation.journallist.components.DailyListScreen
import com.journalapp.presentation.ui.theme.spacingExtraSmall

@Composable
fun DailyListDetailScreen(
    uiState: DailyState,
    onEvent: (DailyEvent) -> Unit,
    navigateTo: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(spacingExtraSmall)) {
        DailyListScreen(
            uiState = uiState,
            onEvent = onEvent,
            navigateTo = {},
            modifier = modifier.weight(1f)
        )
        DailyDetailsScreen(
            journalEntry = uiState.selectedEntry ?: uiState.entries.first(),
            onEvent = onEvent,
            onScreenClose = { },
            isLoading = uiState.isLoading,
            modifier = modifier.weight(2f)
        )
    }
}
