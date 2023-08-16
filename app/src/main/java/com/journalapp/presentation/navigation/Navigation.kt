package com.journalapp.presentation.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.journalapp.R
import com.journalapp.presentation.common.ReplyContentType
import com.journalapp.presentation.common.ReplyNavigationType
import com.journalapp.presentation.journaldetail.DailyDetailsScreen
import com.journalapp.presentation.journallist.DailyListViewModel
import com.journalapp.presentation.journallist.components.DailyListScreen
import com.journalapp.presentation.journallistdetail.DailyListDetailScreen
import com.journalapp.presentation.supportlist.components.SupportScreen

@Composable
fun NavigationWrapperUI(
    navigationType: ReplyNavigationType,
    contentType: ReplyContentType
) {
    val navController = rememberNavController()
    val supportScreen = NavigationItem(
        stringResource(id = R.string.support_groups),
        Screen.SupportScreen.route,
        { Icon(Icons.Filled.Favorite, contentDescription = stringResource(id = R.string.support_groups)) },
        { Icon(Icons.Outlined.Favorite, contentDescription = stringResource(id = R.string.support_groups)) },
        badgeCount = 1
    )
    val dailyList = NavigationItem(
        stringResource(id = R.string.daily_gratitude),
        Screen.DailyListScreen.route,
        { Icon(Icons.Filled.DateRange, contentDescription = stringResource(id = R.string.daily_gratitude)) },
        { Icon(Icons.Outlined.DateRange, contentDescription = stringResource(id = R.string.daily_gratitude)) }
    )
    val dailyListDetails = NavigationItem(
        stringResource(id = R.string.daily_gratitude),
        Screen.DailyListDetailsScreen.route,
        { Icon(Icons.Filled.DateRange, contentDescription = stringResource(id = R.string.daily_gratitude)) },
        { Icon(Icons.Outlined.DateRange, contentDescription = stringResource(id = R.string.daily_gratitude)) }
    )
    val navigationItems = listOf(supportScreen, if (contentType == ReplyContentType.LIST_AND_DETAIL) dailyListDetails else dailyList)
    UpdateScreen(navController, contentType)

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            bottomBar = {
                AnimatedVisibility(
                    visible = navigationType == ReplyNavigationType.BOTTOM_NAVIGATION &&
                        showBottomBar(navController)
                ) {
                    BottomNavigationBar(navigationItems, navController)
                }
            },
            contentWindowInsets = WindowInsets(top = 0.dp)
        ) {
            Row {
                AnimatedVisibility(visible = navigationType == ReplyNavigationType.NAVIGATION_RAIL) {
                    AppNavigationRail(navigationItems, navController)
                }
                Navigation(
                    navController = navController,
                    contentType = contentType,
                    modifier = Modifier.padding(it)
                )
            }
        }
    }
}

@Composable
fun Navigation(
    navController: NavHostController,
    contentType: ReplyContentType,
    modifier: Modifier = Modifier,
) {
    val dailyViewModel = hiltViewModel<DailyListViewModel>()
    val state by dailyViewModel.state.collectAsStateWithLifecycle()

    NavHost(navController = navController, startDestination = Screen.SupportScreen.route) {
        composable(route = Screen.SupportScreen.route) {
            SupportScreen(contentType)
        }
        composable(route = Screen.DailyListScreen.route) {
            DailyListScreen(
                uiState = state,
                onEvent = dailyViewModel::onEvent,
                navigateTo = { route ->
                    navController.navigate(route)
                }
            )
        }
        composable(route = Screen.DailyDetailsScreen.route) {
            state.selectedEntry?.let { entry ->
                DailyDetailsScreen(
                    journalEntry = entry,
                    isLoading = state.isLoading,
                    onEvent = dailyViewModel::onEvent,
                    onScreenClose = { navController.navigateUp() }
                )
            }
        }
        composable(route = Screen.DailyListDetailsScreen.route) {
            DailyListDetailScreen(
                uiState = state,
                onEvent = dailyViewModel::onEvent,
                navigateTo = { route ->
                    navController.navigate(route)
                }
            )
        }
    }
}

@Composable
fun showBottomBar(navController: NavHostController): Boolean {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return when (navBackStackEntry?.destination?.route) {
        Screen.DailyDetailsScreen.route -> false
        else -> true
    }
}

@Composable
fun UpdateScreen(navController: NavHostController, contentType: ReplyContentType) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    if (contentType == ReplyContentType.LIST_AND_DETAIL &&
        (navBackStackEntry?.destination?.route == Screen.DailyDetailsScreen.route || navBackStackEntry?.destination?.route == Screen.DailyListScreen.route)) {
        navController.navigate(Screen.DailyListDetailsScreen.route)
    } else if (contentType == ReplyContentType.LIST_ONLY &&
        (navBackStackEntry?.destination?.route == Screen.DailyListDetailsScreen.route) ) {
        navController.navigate(Screen.DailyListScreen.route)
    }
}