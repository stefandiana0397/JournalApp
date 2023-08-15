package com.journalapp.presentation.journallist.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.journalapp.domain.model.JournalEntry
import com.journalapp.presentation.common.EntryFormatter.convertMillisecondsToDate
import com.journalapp.presentation.common.components.TagComponent
import com.journalapp.presentation.ui.theme.spacingExtraSmall
import com.journalapp.presentation.ui.theme.spacingMedium
import com.journalapp.presentation.ui.theme.spacingSmall

@Composable
fun DailyListItem(
    journalEntry: JournalEntry,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.clickable { onClick() },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(spacingMedium),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start,
                text = convertMillisecondsToDate(journalEntry.date),
                color = MaterialTheme.colorScheme.outline,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(spacingExtraSmall))
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start,
                text = journalEntry.summary,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 30,
                overflow = TextOverflow.Ellipsis
            )
            if (!journalEntry.photos.isNullOrEmpty()) {
                Spacer(modifier = Modifier.height(spacingExtraSmall))
                var showShimmer by remember { mutableStateOf(true) }
                SubcomposeAsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(shimmerBrush(targetValue = 1300f, showShimmer = showShimmer))
                        .clip(RoundedCornerShape(spacingSmall))
                        .aspectRatio(2.2f),
                    model = journalEntry.photos.first(),
                    contentDescription = "image",
                    contentScale = ContentScale.Crop,
                    onSuccess = { showShimmer = false }
                )
            }
            if (!journalEntry.tags.isNullOrEmpty()) {
                Spacer(modifier = Modifier.height(spacingSmall))
                LazyRow {
                    itemsIndexed(journalEntry.tags) { index, item ->
                        TagComponent(tag = item)
                        if (index < journalEntry.tags.lastIndex) {
                            Spacer(modifier = Modifier.width(spacingSmall))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun shimmerBrush(showShimmer: Boolean = true, targetValue: Float = 1000f): Brush {
    return if (showShimmer) {
        val shimmerColors = listOf(
            Color.LightGray.copy(alpha = 0.6f),
            Color.LightGray.copy(alpha = 0.2f),
            Color.LightGray.copy(alpha = 0.6f)
        )

        val transition = rememberInfiniteTransition(label = "")
        val translateAnimation = transition.animateFloat(
            initialValue = 0f,
            targetValue = targetValue,
            animationSpec = infiniteRepeatable(
                animation = tween(800),
                repeatMode = RepeatMode.Reverse
            ),
            label = ""
        )
        Brush.linearGradient(
            colors = shimmerColors,
            start = Offset.Zero,
            end = Offset(x = translateAnimation.value, y = translateAnimation.value)
        )
    } else {
        Brush.linearGradient(
            colors = listOf(Color.Transparent, Color.Transparent),
            start = Offset.Zero,
            end = Offset.Zero
        )
    }
}
