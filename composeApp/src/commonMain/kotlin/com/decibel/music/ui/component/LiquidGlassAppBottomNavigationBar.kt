package com.decibel.music.ui.component

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.decibel.music.expect.ui.PlatformBackdrop
import com.decibel.music.ui.icon.Home
import com.decibel.music.ui.icon.LibraryMusic
import com.decibel.music.ui.icon.Search
import com.decibel.music.ui.icon.DecibelIcons
import com.decibel.music.ui.navigation.destination.home.HomeDestination
import com.decibel.music.ui.navigation.destination.library.LibraryDestination
import com.decibel.music.ui.navigation.destination.search.SearchDestination
import com.decibel.music.viewModel.SharedViewModel
import org.jetbrains.compose.resources.StringResource
import com.decibel.music.composeapp.generated.resources.Res
import com.decibel.music.composeapp.generated.resources.home
import com.decibel.music.composeapp.generated.resources.library
import com.decibel.music.composeapp.generated.resources.search
import kotlin.reflect.KClass

@Composable
expect fun LiquidGlassAppBottomNavigationBar(
    startDestination: Any = HomeDestination,
    navController: NavController,
    backdrop: PlatformBackdrop,
    viewModel: SharedViewModel,
    isScrolledToTop: Boolean = false,
    onOpenNowPlaying: () -> Unit = {},
    reloadDestinationIfNeeded: (KClass<*>) -> Unit = { _ -> },
)

sealed class BottomNavScreen(
    val ordinal: Int,
    val destination: Any,
    val title: StringResource,
    val icon: @Composable () -> Unit,
) {
    data object Home : BottomNavScreen(
        ordinal = 0,
        destination = HomeDestination,
        title = Res.string.home,
        icon = {
            Icon(
                DecibelIcons.Home,
                contentDescription = null,
            )
        },
    )

    data object Search : BottomNavScreen(
        ordinal = 1,
        destination = SearchDestination,
        title = Res.string.search,
        icon = {
            Icon(
                DecibelIcons.Search,
                contentDescription = null,
            )
        },
    )

    data object Library : BottomNavScreen(
        ordinal = 2,
        destination = LibraryDestination,
        title = Res.string.library,
        icon = {
            Icon(
                imageVector = DecibelIcons.LibraryMusic,
                contentDescription = null,
            )
        },
    )
}