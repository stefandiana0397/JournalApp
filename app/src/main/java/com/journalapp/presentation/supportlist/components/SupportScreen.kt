package com.journalapp.presentation.supportlist.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.journalapp.R
import com.journalapp.presentation.common.AppToolbar
import com.journalapp.presentation.common.AppToolbarText
import com.journalapp.presentation.common.ToolbarAction

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
        Box(modifier.padding(paddingValues)) {}
    }
}
