package com.journalapp.presentation.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.journalapp.R
import com.journalapp.presentation.ui.theme.spacingMedium
import com.journalapp.presentation.ui.theme.spacingSmall

@Composable
fun BottomNavigation(navController: NavController) {
    val bottomNavigationItems = listOf(
        BottomNavigationItem(
            stringResource(id = R.string.support_groups),
            Screen.SupportScreen.route,
            { Icon(Icons.Filled.Favorite, contentDescription = stringResource(id = R.string.support_groups)) },
            { Icon(Icons.Outlined.Favorite, contentDescription = stringResource(id = R.string.support_groups)) },
            badgeCount = 1
        ),
        BottomNavigationItem(
            stringResource(id = R.string.daily_gratitude),
            Screen.DailyListScreen.route,
            { Icon(Icons.Filled.DateRange, contentDescription = stringResource(id = R.string.daily_gratitude)) },
            { Icon(Icons.Outlined.DateRange, contentDescription = stringResource(id = R.string.daily_gratitude)) }
        ),
    )

    BottomNavigationBar(
        items = bottomNavigationItems,
        navController = navController,
        modifier = Modifier.fillMaxWidth()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigationBar(
    items: List<BottomNavigationItem>,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    NavigationBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
        tonalElevation = spacingMedium
    ) {
        items.forEachIndexed { index, item ->
            val selectedItem = backStackEntry.value?.destination?.hierarchy?.any { it.route == item.route } == true
            NavigationBarItem(
                icon = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (item.badgeCount > 0) {
                            BadgedBox(
                                badge = { Badge { Text(text = item.badgeCount.toString()) } }
                            ) {
                                if (selectedItem) item.selectedIcon() else item.unselectedIcon()
                            }
                        } else {
                            if (selectedItem) item.selectedIcon() else item.unselectedIcon()
                        }
                    }
                },
                label = { Text(text = item.name) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.error,
                    selectedTextColor = MaterialTheme.colorScheme.error
                ),
                selected = selectedItem,
                onClick = {
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let { screenRoute ->
                            popUpTo(screenRoute) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}
