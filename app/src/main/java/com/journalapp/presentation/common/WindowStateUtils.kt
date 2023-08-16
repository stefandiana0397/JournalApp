package com.journalapp.presentation.common

import android.graphics.Rect
import androidx.window.layout.FoldingFeature
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

/**
 * Information about the posture of the device
 */
sealed interface DevicePosture {
    object NormalPosture : DevicePosture

    data class Separating(
        val hingePosition: Rect,
        var orientation: FoldingFeature.Orientation
    ) : DevicePosture
}

@OptIn(ExperimentalContracts::class)
fun isSeparating(foldFeature: FoldingFeature?): Boolean {
    contract { returns(true) implies (foldFeature != null) }
    return foldFeature?.state == FoldingFeature.State.FLAT && foldFeature.isSeparating
}

/**
 * Different type of navigation supported by app depending on size and state.
 */
enum class ReplyNavigationType {
    BOTTOM_NAVIGATION, NAVIGATION_RAIL
}

/**
 * Content shown depending on size and state of device.
 */
enum class ReplyContentType {
    LIST_ONLY, LIST_AND_DETAIL
}
