package com.journalapp.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.journalapp.presentation.ui.theme.spacingLarge

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigationRail(
    items: List<NavigationItem>,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    NavigationRail(
        modifier = modifier
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.inverseOnSurface)
    ) {
        Column(
            modifier = Modifier.fillMaxHeight().padding(bottom = spacingLarge),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(spacingLarge, Alignment.Bottom)
        ) {
            items.forEachIndexed { index, item ->
                val selectedItem =
                    backStackEntry.value?.destination?.hierarchy?.any { it.route == item.route } == true
                NavigationRailItem(
                    selected = selectedItem,
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
                    colors = NavigationRailItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.error,
                        selectedTextColor = MaterialTheme.colorScheme.error
                    ),
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
}
