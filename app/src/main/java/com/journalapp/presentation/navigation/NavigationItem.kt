package com.journalapp.presentation.navigation

import androidx.compose.runtime.Composable

data class NavigationItem(
    val name: String,
    val route: String,
    val selectedIcon: @Composable () -> Unit,
    val unselectedIcon: @Composable () -> Unit,
    val badgeCount: Int = 0
)