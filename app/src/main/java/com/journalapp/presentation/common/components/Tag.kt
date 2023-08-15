package com.journalapp.presentation.common.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.journalapp.domain.model.Tag
import com.journalapp.presentation.ui.theme.spacingSmall

@Composable
fun TagComponent(tag: Tag) =
    Box(
        modifier = Modifier.border(
            BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
            RoundedCornerShape(30)
        )
            .padding(spacingSmall)
    ) {
        Text(
            text = tag.name,
            color = MaterialTheme.colorScheme.outline,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium
        )
    }
