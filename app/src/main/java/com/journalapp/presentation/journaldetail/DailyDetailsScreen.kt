package com.journalapp.presentation.journaldetail

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.journalapp.R
import com.journalapp.domain.model.JournalEntry
import com.journalapp.presentation.common.AppToolbar
import com.journalapp.presentation.common.AppToolbarText
import com.journalapp.presentation.common.EntryFormatter
import com.journalapp.presentation.common.ToolbarAction
import com.journalapp.presentation.common.components.CircularProgress
import com.journalapp.presentation.common.components.TagComponent
import com.journalapp.presentation.journallist.DailyEvent
import com.journalapp.presentation.journallist.components.shimmerBrush
import com.journalapp.presentation.ui.theme.spacingExtraSmall
import com.journalapp.presentation.ui.theme.spacingMedium
import com.journalapp.presentation.ui.theme.spacingSmall
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun DailyDetailsScreen(
    journalEntry: JournalEntry,
    onEvent: (DailyEvent) -> Unit,
    onScreenClose: () -> Unit,
    isLoading: Boolean,
    modifier: Modifier = Modifier
) {
    BackHandler(onBack = onScreenClose)

    var editMode by remember { mutableStateOf(false) }
    var text by remember(journalEntry.summary) { mutableStateOf(journalEntry.summary) }
    val photos = journalEntry.photos

    Scaffold(
        modifier = modifier,
        topBar = {
            AppToolbar(
                title = {
                    AppToolbarText(
                        text = stringResource(id = R.string.back),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface.copy(0.8f)
                    )
                },
                navIcon = {
                    ToolbarAction(
                        image = Icons.Filled.ArrowBack,
                        onClick = onScreenClose
                    )
                },
                actions = {
                    if (!editMode) {
                        ToolbarAction(
                            image = Icons.Filled.Edit,
                            onClick = {
                                editMode = !editMode
                            }
                        )
                    } else {
                        ToolbarAction(
                            image = Icons.Filled.Clear,
                            onClick = {
                                editMode = false
                                text = journalEntry.summary
                            }
                        )
                        ToolbarAction(
                            image = Icons.Filled.Done,
                            onClick = {
                                editMode = false
                                onEvent(DailyEvent.SaveEntry(journalEntry.copy(summary = text)))
                                text = journalEntry.summary
                            }
                        )
                    }
                    ToolbarAction(
                        image = Icons.Filled.Delete,
                        onClick = {
                            onEvent(DailyEvent.DeleteEntry(journalEntry))
                            onScreenClose()
                        }
                    )
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier.padding(paddingValues),
            contentAlignment = Alignment.TopCenter
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(spacingMedium),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                item {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start,
                        text = EntryFormatter.convertMillisecondsToDate(journalEntry.date),
                        color = MaterialTheme.colorScheme.outline,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(spacingSmall))
                }
                item {
                    if (!editMode) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Start,
                            text = journalEntry.summary,
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    } else {
                        TextField(
                            modifier = Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.colors(
                                focusedTextColor = MaterialTheme.colorScheme.onSurface
                            ),
                            maxLines = 50,
                            textStyle = MaterialTheme.typography.bodyLarge,
                            value = text,
                            onValueChange = {
                                text = it
                            }
                        )
                    }
                }
                item {
                    if (!photos.isNullOrEmpty()) {
                        Spacer(modifier = Modifier.height(spacingMedium))
                        MultimediaCarousel(photos = photos)
                    }
                }
                item {
                    if (!journalEntry.tags.isNullOrEmpty()) {
                        Spacer(modifier = Modifier.height(spacingMedium))
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(spacingSmall)
                        ) {
                            items(journalEntry.tags) { item ->
                                TagComponent(tag = item)
                            }
                        }
                    }
                }
            }
            if (isLoading) {
                CircularProgress()
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MultimediaCarousel(
    photos: List<String>
) {
    var showShimmer by remember { mutableStateOf(true) }
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f,
        pageCount = { photos.size }
    )
    LaunchedEffect(photos) {
        pagerState.scrollToPage(0)
    }
    val scope = rememberCoroutineScope()
    HorizontalPager(
        state = pagerState
    ) { index ->
        SubcomposeAsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .background(shimmerBrush(targetValue = 1300f, showShimmer = showShimmer))
                .clip(RoundedCornerShape(spacingSmall))
                .aspectRatio(1.7f),
            model = photos[index],
            contentDescription = "image",
            contentScale = ContentScale.Crop,
            onSuccess = { showShimmer = false },
            onError = { showShimmer = false }
        )
    }
    Spacer(modifier = Modifier.height(spacingSmall))
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(spacingSmall)
    ) {
        itemsIndexed(photos) { index, item ->
            SubcomposeAsyncImage(
                modifier = Modifier
                    .width(64.dp)
                    .background(shimmerBrush(targetValue = 1300f, showShimmer = showShimmer))
                    .clip(RoundedCornerShape(spacingExtraSmall))
                    .aspectRatio(1f)
                    .clickable {
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                model = item,
                contentDescription = "image",
                contentScale = ContentScale.Crop,
                onSuccess = { showShimmer = false },
                onError = { showShimmer = false }
            )
        }
    }
}
