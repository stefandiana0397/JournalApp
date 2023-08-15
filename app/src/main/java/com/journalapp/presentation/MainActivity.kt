package com.journalapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.journalapp.presentation.navigation.BottomNavigation
import com.journalapp.presentation.navigation.Navigation
import com.journalapp.presentation.navigation.Screen
import com.journalapp.presentation.ui.theme.JournalAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            JournalAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    Scaffold(
                        bottomBar = { if (showBottomBar(navController)) BottomNavigation(navController = navController) },
                        contentWindowInsets = WindowInsets(top = 0.dp)
                    ) {
                        Navigation(
                            navController = navController,
                            modifier = Modifier.padding(it)
                        )
                    }
                }
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
}

