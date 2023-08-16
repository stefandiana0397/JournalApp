package com.journalapp.presentation

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.journalapp.presentation.common.DevicePosture
import com.journalapp.presentation.common.ReplyContentType
import com.journalapp.presentation.common.ReplyNavigationType
import com.journalapp.presentation.navigation.NavigationWrapperUI

@Composable
fun MainScreen(
    windowSize: WindowWidthSizeClass,
    foldingDevicePosture: DevicePosture,
    mainViewModel: MainViewModel = hiltViewModel()
) {
    val navigationType: ReplyNavigationType
    val contentType: ReplyContentType

    when (windowSize) {
        WindowWidthSizeClass.Compact -> {
            navigationType = ReplyNavigationType.BOTTOM_NAVIGATION
            contentType = ReplyContentType.LIST_ONLY
        }
        WindowWidthSizeClass.Medium -> {
            navigationType = ReplyNavigationType.NAVIGATION_RAIL
            contentType = if (foldingDevicePosture != DevicePosture.NormalPosture) {
                ReplyContentType.LIST_AND_DETAIL
            } else {
                ReplyContentType.LIST_ONLY
            }
        }
        WindowWidthSizeClass.Expanded -> {
            navigationType = ReplyNavigationType.NAVIGATION_RAIL
            contentType = ReplyContentType.LIST_AND_DETAIL
        }
        else -> {
            navigationType = ReplyNavigationType.BOTTOM_NAVIGATION
            contentType = ReplyContentType.LIST_ONLY
        }
    }
    NavigationWrapperUI(navigationType, contentType)
}
