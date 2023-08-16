package com.journalapp.presentation.supportlist.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import coil.compose.SubcomposeAsyncImage
import com.journalapp.R
import com.journalapp.presentation.common.AppToolbar
import com.journalapp.presentation.common.AppToolbarText
import com.journalapp.presentation.common.ReplyContentType
import com.journalapp.presentation.common.ToolbarAction
import com.journalapp.presentation.ui.theme.spacingExtraLarge
import com.journalapp.presentation.ui.theme.spacingExtraSmall
import com.journalapp.presentation.ui.theme.spacingMedium
import com.journalapp.presentation.ui.theme.spacingSmall

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SupportScreen(contentType: ReplyContentType, modifier: Modifier = Modifier) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        topBar = {
            AppToolbar(
                title = { AppToolbarText(text = stringResource(id = R.string.support_groups)) },
                scrollBehavior = scrollBehavior,
                actions = {
                    ToolbarAction(image = Icons.Filled.AccountCircle) {}
                }
            )
        }
    ) { paddingValues ->
        if (contentType == ReplyContentType.LIST_ONLY) {
            LazyColumn(
                modifier.padding(paddingValues).padding(
                    PaddingValues(
                        start = spacingMedium,
                        end = spacingMedium,
                        top = spacingExtraSmall,
                        bottom = spacingExtraLarge
                    )
                ),
                verticalArrangement = Arrangement.spacedBy(spacingExtraSmall),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    SupportContent()
                }
            }
        } else {
            LazyRow(
                modifier.padding(paddingValues).padding(
                    PaddingValues(
                        start = spacingExtraSmall,
                        end = spacingExtraSmall,
                        top = spacingExtraSmall,
                        bottom = spacingExtraLarge
                    )
                ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(spacingExtraSmall)
            ) {
                item {
                    SupportContent()
                }
            }
        }
    }
}

@Composable
fun SupportContent() {
    SubcomposeAsyncImage(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = spacingSmall)
            .clip(RoundedCornerShape(spacingExtraSmall))
            .aspectRatio(1f),
        model = R.mipmap.fire1,
        contentDescription = "image",
        contentScale = ContentScale.Crop
    )
    SubcomposeAsyncImage(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = spacingSmall)
            .clip(RoundedCornerShape(spacingExtraSmall))
            .aspectRatio(1f),
        model = R.mipmap.fire2,
        contentDescription = "image",
        contentScale = ContentScale.Crop
    )
}
