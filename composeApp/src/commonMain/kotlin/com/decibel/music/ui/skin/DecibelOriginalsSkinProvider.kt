package com.decibel.music.ui.skin

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.decibel.music.expect.ui.PlatformBackdrop
import com.decibel.music.expect.ui.rememberBackdrop
import com.decibel.music.ui.component.AppBottomNavigationBar
import com.decibel.music.ui.screen.MiniPlayer as DecibelMiniPlayer
import com.decibel.music.ui.screen.home.HomeScreen as DecibelHomeScreen
import com.decibel.music.ui.screen.library.LibraryScreen as DecibelLibraryScreen
import com.decibel.music.ui.screen.other.SearchScreen as DecibelSearchScreen
import com.decibel.music.ui.screen.player.NowPlayingScreen as DecibelNowPlayingScreen
import com.decibel.music.ui.screen.other.AlbumScreen as DecibelAlbumScreen
import com.decibel.music.ui.screen.other.ArtistScreen as DecibelArtistScreen
import com.decibel.music.ui.screen.other.PlaylistScreen as DecibelPlaylistScreen
import com.decibel.music.ui.theme.ForceDarkContent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import com.decibel.music.viewModel.SharedViewModel
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import kotlin.reflect.KClass

object DecibelOriginalsSkinProvider : SkinUiProvider {
    override val skin: AppSkin = AppSkin.DECIBEL_ORIGINALS
    override val tokens: SkinTokens = SkinTokens(
        backgroundDark = 0xFF000000,
        surfaceDark = 0xFF121212,
        elevatedSurfaceDark = 0xFF1E1E1E,
        accent = 0xFFFF4081,
        textPrimary = 0xFFFFFFFF,
        textSecondary = 0xFFB0B0B0,
        cornerRadiusLarge = 16,
        cornerRadiusMedium = 12,
        cornerRadiusSmall = 8,
    )

    @OptIn(
        ExperimentalFoundationApi::class,
        ExperimentalMaterial3Api::class,
        ExperimentalHazeMaterialsApi::class,
    )
    @Composable
    override fun HomeScreen(
        navController: NavController,
        onScrolling: (onTop: Boolean) -> Unit,
    ) {
        DecibelHomeScreen(
            navController = navController,
            onScrolling = onScrolling,
        )
    }

    @OptIn(
        ExperimentalFoundationApi::class,
        ExperimentalMaterial3Api::class,
        ExperimentalHazeMaterialsApi::class,
    )
    @Composable
    override fun SearchScreen(
        navController: NavController,
    ) {
        DecibelSearchScreen(
            navController = navController,
        )
    }

    @OptIn(
        ExperimentalFoundationApi::class,
        ExperimentalMaterial3Api::class,
        ExperimentalHazeMaterialsApi::class,
    )
    @Composable
    override fun LibraryScreen(
        innerPadding: PaddingValues,
        navController: NavController,
        onScrolling: (onTop: Boolean) -> Unit,
    ) {
        DecibelLibraryScreen(
            innerPadding = innerPadding,
            navController = navController,
            onScrolling = onScrolling,
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun AlbumDetailScreen(
        browseId: String,
        navController: NavController,
    ) {
        ForceDarkContent {
            DecibelAlbumScreen(
                browseId = browseId,
                navController = navController,
            )
        }
    }

    @OptIn(
        ExperimentalCoroutinesApi::class,
        ExperimentalMaterial3Api::class,
        ExperimentalHazeMaterialsApi::class,
    )
    @Composable
    override fun PlaylistDetailScreen(
        playlistId: String,
        isYourYouTubePlaylist: Boolean,
        navController: NavController,
    ) {
        ForceDarkContent {
            DecibelPlaylistScreen(
                playlistId = playlistId,
                isYourYouTubePlaylist = isYourYouTubePlaylist,
                navController = navController,
            )
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun ArtistDetailScreen(
        channelId: String,
        navController: NavController,
    ) {
        ForceDarkContent {
            DecibelArtistScreen(
                channelId = channelId,
                navController = navController,
            )
        }
    }

    @Composable
    override fun BottomNavigationBar(
        navController: NavController,
        reloadDestinationIfNeeded: (KClass<*>) -> Unit,
        backdrop: PlatformBackdrop?,
    ) {
        AppBottomNavigationBar(
            navController = navController,
            reloadDestinationIfNeeded = reloadDestinationIfNeeded,
        )
    }

    @OptIn(
        ExperimentalFoundationApi::class,
        ExperimentalMaterial3Api::class,
        ExperimentalHazeMaterialsApi::class,
    )
    @Composable
    override fun MiniPlayer(
        modifier: Modifier,
        backdrop: PlatformBackdrop?,
        onClose: (() -> Unit)?,
        onClick: () -> Unit,
        sharedViewModel: SharedViewModel,
    ) {
        val resolvedBackdrop = backdrop ?: rememberBackdrop(Color.Black)
        val resolvedOnClose: () -> Unit =
            onClose ?: {
                sharedViewModel.stopPlayer()
                sharedViewModel.isServiceRunning = false
            }
        DecibelMiniPlayer(
            modifier = modifier,
            backdrop = resolvedBackdrop,
            sharedViewModel = sharedViewModel,
            onClose = resolvedOnClose,
            onClick = onClick,
        )
    }

    @OptIn(
        ExperimentalFoundationApi::class,
        ExperimentalMaterial3Api::class,
        ExperimentalHazeMaterialsApi::class,
    )
    @Composable
    override fun NowPlayingScreen(
        navController: NavController,
        onDismiss: () -> Unit,
    ) {
        DecibelNowPlayingScreen(
            navController = navController,
            onDismiss = onDismiss,
        )
    }
}
