package com.journalapp.presentation.navigation

sealed class Screen(val route: String) {
    object DailyListScreen : Screen("daily_list")
    object DailyDetailsScreen : Screen("daily_details")
    object SupportScreen : Screen("support_list")
}