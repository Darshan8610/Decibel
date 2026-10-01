package com.decibel.music.ui.component

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.decibel.music.expect.ui.PlatformBackdrop
import com.decibel.music.viewModel.SharedViewModel
import kotlin.reflect.KClass

@Composable
actual fun LiquidGlassAppBottomNavigationBar(
    startDestination: Any,
    navController: NavController,
    backdrop: PlatformBackdrop,
    viewModel: SharedViewModel,
    isScrolledToTop: Boolean,
    onOpenNowPlaying: () -> Unit,
    reloadDestinationIfNeeded: (KClass<*>) -> Unit
) {
}