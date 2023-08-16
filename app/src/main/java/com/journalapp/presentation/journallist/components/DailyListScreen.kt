package com.journalapp.presentation.journallist.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.journalapp.R
import com.journalapp.presentation.common.AppToolbar
import com.journalapp.presentation.common.AppToolbarText
import com.journalapp.presentation.common.EntryFormatter.groupEntriesByTimeFrame
import com.journalapp.presentation.common.TimeFrame
import com.journalapp.presentation.common.ToolbarAction
import com.journalapp.presentation.journallist.DailyEvent
import com.journalapp.presentation.journallist.DailyState
import com.journalapp.presentation.navigation.Screen
import com.journalapp.presentation.ui.theme.JournalAppTheme
import com.journalapp.presentation.ui.theme.spacingExtraExtraLarge
import com.journalapp.presentation.ui.theme.spacingExtraLarge
import com.journalapp.presentation.ui.theme.spacingLarge
import com.journalapp.presentation.ui.theme.spacingMedium

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun DailyListScreen(
    uiState: DailyState,
    onEvent: (DailyEvent) -> Unit,
    navigateTo: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val pullRefreshState = rememberPullRefreshState(
        uiState.isLoading,
        { onEvent(DailyEvent.GetEntries) }
    )
    Scaffold(
        modifier = modifier,
        topBar = {
            AppToolbar(
                title = { AppToolbarText(text = stringResource(id = R.string.daily_gratitude)) },
                scrollBehavior = scrollBehavior,
                actions = {
                    ToolbarAction(image = Icons.Filled.Home) {
                    }
                    ToolbarAction(image = Icons.Filled.Add) {
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(Modifier.fillMaxSize().padding(paddingValues).pullRefresh(pullRefreshState)) {
            val groupedEntries = groupEntriesByTimeFrame(uiState.entries)
            LazyColumn(
                modifier = Modifier.padding(bottom = spacingLarge)
                    .nestedScroll(scrollBehavior.nestedScrollConnection),
                contentPadding = PaddingValues(
                    start = spacingMedium,
                    end = spacingMedium,
                    top = spacingMedium,
                    bottom = spacingExtraLarge
                ),
                verticalArrangement = Arrangement.spacedBy(spacingMedium)
            ) {
                groupedEntries.forEach { (timeFrame, entries) ->
                    item {
                        TimeFrameSeparator(timeFrame = timeFrame)
                    }
                    items(entries) { entry ->
                        DailyListItem(
                            journalEntry = entry,
                            onClick = {
                                // Handle click
                                onEvent(DailyEvent.SelectEntry(entry))
                                navigateTo(Screen.DailyDetailsScreen.route)
                            }
                        )
                    }
                }
                if (groupedEntries.isEmpty()) {
                    item {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Start,
                            text = "No data",
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
            PullRefreshIndicator(
                uiState.isLoading,
                pullRefreshState,
                Modifier.align(Alignment.TopCenter).padding(top = spacingExtraExtraLarge)
            )
        }
    }
}

@Composable
fun TimeFrameSeparator(timeFrame: TimeFrame) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Start,
        text = stringResource(id = timeFrame.id),
        color = MaterialTheme.colorScheme.outline,
        style = MaterialTheme.typography.bodyLarge
    )
}

@Preview
@Composable
fun DailyListScreenPreview() {
    JournalAppTheme {
        DailyListScreen(DailyState(), {}, {})
    }
}
