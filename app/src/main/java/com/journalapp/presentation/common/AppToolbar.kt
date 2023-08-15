package com.journalapp.presentation.common

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppToolbar(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(
        containerColor = Color.Transparent,
        titleContentColor = MaterialTheme.colorScheme.onSurface,
        actionIconContentColor = MaterialTheme.colorScheme.onSurface
    ),
    title: @Composable (() -> Unit) = {},
    navIcon: @Composable (() -> Unit) = {},
    actions: @Composable RowScope.() -> Unit = {}
) {
    TopAppBar(
        modifier = modifier,
        title = title,
        colors = colors,
        navigationIcon = navIcon,
        actions = actions,
        scrollBehavior = scrollBehavior
    )
}

@Composable
fun AppToolbarText(
    text: String,
    style: TextStyle = MaterialTheme.typography.headlineLarge,
    color: Color = MaterialTheme.colorScheme.onSurface
) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Start,
        text = text,
        color = color,
        style = style
    )
}

@Composable
fun ToolbarAction(
    modifier: Modifier = Modifier,
    iconModifier: Modifier = Modifier,
    enabled: Boolean = true,
    image: ImageVector,
    onClick: () -> Unit
) {
    IconButton(
        modifier = modifier,
        enabled = enabled,
        onClick = onClick
    ) {
        Icon(
            modifier = iconModifier,
            imageVector = image,
            contentDescription = null
        )
    }
}
