package com.journalapp.presentation.supportlist.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import com.journalapp.presentation.common.ToolbarAction
import com.journalapp.presentation.ui.theme.spacingExtraLarge
import com.journalapp.presentation.ui.theme.spacingExtraSmall
import com.journalapp.presentation.ui.theme.spacingLarge
import com.journalapp.presentation.ui.theme.spacingMedium
import com.journalapp.presentation.ui.theme.spacingSmall

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SupportScreen(modifier: Modifier = Modifier) {
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
        LazyColumn(
            modifier.fillMaxSize().padding(paddingValues).padding(
                PaddingValues(
                    start = spacingLarge,
                    end = spacingLarge,
                    top = spacingExtraSmall,
                    bottom = spacingExtraLarge
                )
            ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                SubcomposeAsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = spacingMedium)
                        .clip(RoundedCornerShape(spacingExtraSmall))
                        .aspectRatio(1f),
                    model = R.mipmap.fire1,
                    contentDescription = "image",
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(spacingSmall))
                SubcomposeAsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = spacingMedium)
                        .clip(RoundedCornerShape(spacingExtraSmall))
                        .aspectRatio(1f),
                    model = R.mipmap.fire2,
                    contentDescription = "image",
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}
