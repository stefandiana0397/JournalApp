package com.journalapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.journalapp.presentation.MainViewModel
import com.journalapp.presentation.journaldetail.DailyDetailsScreen
import com.journalapp.presentation.journallist.DailyListViewModel
import com.journalapp.presentation.journallist.components.DailyListScreen
import com.journalapp.presentation.supportlist.components.SupportScreen

@Composable
fun Navigation(
    navController: NavHostController,
    mainViewModel: MainViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val dailyViewModel = hiltViewModel<DailyListViewModel>()
    val state by dailyViewModel.state.collectAsStateWithLifecycle()

    NavHost(navController = navController, startDestination = Screen.SupportScreen.route) {
        composable(route = Screen.SupportScreen.route) {
            SupportScreen()
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
                    onEvent = dailyViewModel::onEvent,
                    onScreenClose = { navController.navigateUp() }
                )
            }
        }
    }
}
