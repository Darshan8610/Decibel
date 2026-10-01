package com.decibel.music.ui.skin

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.decibel.music.expect.ui.PlatformBackdrop
import com.decibel.music.ui.skin.spotify.SpotifyHomeScreen
import com.decibel.music.ui.skin.spotify.SpotifyLibraryScreen
import com.decibel.music.ui.skin.spotify.SpotifySearchScreen
import com.decibel.music.viewModel.SharedViewModel
import kotlin.reflect.KClass

object SpotifySkinProvider : SkinUiProvider {
    override val skin: AppSkin = AppSkin.SPOTIFY
    override val tokens: SkinTokens = SkinTokens(
        backgroundDark = 0xFF121212,
        surfaceDark = 0xFF181818,
        elevatedSurfaceDark = 0xFF242424,
        accent = 0xFF1DB954,
        textPrimary = 0xFFFFFFFF,
        textSecondary = 0xFFB3B3B3,
        cornerRadiusLarge = 8,
        cornerRadiusMedium = 8,
        cornerRadiusSmall = 4,
    )

    @Composable
    override fun HomeScreen(
        navController: NavController,
        onScrolling: (onTop: Boolean) -> Unit,
    ) {
        SpotifyHomeScreen(
            navController = navController,
            onScrolling = onScrolling,
        )
    }

    @Composable
    override fun SearchScreen(
        navController: NavController,
    ) {
        SpotifySearchScreen(
            navController = navController,
        )
    }

    @Composable
    override fun LibraryScreen(
        innerPadding: PaddingValues,
        navController: NavController,
        onScrolling: (onTop: Boolean) -> Unit,
    ) {
        SpotifyLibraryScreen(
            innerPadding = innerPadding,
            navController = navController,
            onScrolling = onScrolling,
        )
    }

    @Composable
    override fun AlbumDetailScreen(
        browseId: String,
        navController: NavController,
    ) {
        DecibelOriginalsSkinProvider.AlbumDetailScreen(
            browseId = browseId,
            navController = navController,
        )
    }

    @Composable
    override fun PlaylistDetailScreen(
        playlistId: String,
        isYourYouTubePlaylist: Boolean,
        navController: NavController,
    ) {
        DecibelOriginalsSkinProvider.PlaylistDetailScreen(
            playlistId = playlistId,
            isYourYouTubePlaylist = isYourYouTubePlaylist,
            navController = navController,
        )
    }

    @Composable
    override fun ArtistDetailScreen(
        channelId: String,
        navController: NavController,
    ) {
        DecibelOriginalsSkinProvider.ArtistDetailScreen(
            channelId = channelId,
            navController = navController,
        )
    }

    @Composable
    override fun BottomNavigationBar(
        navController: NavController,
        reloadDestinationIfNeeded: (KClass<*>) -> Unit,
        backdrop: PlatformBackdrop?,
    ) {
        SpotifyBottomNavigationBar(
            navController = navController,
            reloadDestinationIfNeeded = reloadDestinationIfNeeded,
            backdrop = backdrop,
        )
    }

    @Composable
    override fun MiniPlayer(
        modifier: Modifier,
        backdrop: PlatformBackdrop?,
        onClose: (() -> Unit)?,
        onClick: () -> Unit,
        sharedViewModel: SharedViewModel,
    ) {
        SpotifyMiniPlayer(
            modifier = modifier,
            backdrop = backdrop,
            onClick = onClick,
        )
    }

    @Composable
    override fun NowPlayingScreen(
        navController: NavController,
        onDismiss: () -> Unit,
    ) {
        SpotifyNowPlayingScreen(
            navController = navController,
            onDismiss = onDismiss,
        )
    }
}
