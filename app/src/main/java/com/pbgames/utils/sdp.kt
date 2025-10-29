package com.pbgames.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Matches Intuit SDP scaling exactly (based on smallest width).
 */
val Int.sdp: Dp
    @Composable
    get() {
        val configuration = LocalConfiguration.current
        val sw = minOf(configuration.screenWidthDp, configuration.screenHeightDp)
        val base = 360f // Intuit's baseline (values-sw360dp)
        val scale = sw / base
        return (this * scale).dp
    }

/**
 * Float version of SDP scaling.
 */
val Double.sdp: Dp
    @Composable
    get() {
        val configuration = LocalConfiguration.current
        val sw = minOf(configuration.screenWidthDp, configuration.screenHeightDp)
        val base = 360f
        val scale = sw / base
        return (this * scale).dp
    }
