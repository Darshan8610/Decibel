package com.decibel.music.ui.skin

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.decibel.music.expect.ui.PlatformBackdrop
import com.decibel.music.ui.skin.applemusic.AppleMusicHomeScreen
import com.decibel.music.ui.skin.applemusic.AppleMusicLibraryScreen
import com.decibel.music.ui.skin.applemusic.AppleMusicSearchScreen
import com.decibel.music.viewModel.SharedViewModel
import kotlin.reflect.KClass

object AppleMusicSkinProvider : SkinUiProvider {
    override val skin: AppSkin = AppSkin.APPLE_MUSIC
    override val tokens: SkinTokens = SkinTokens(
        backgroundDark = 0xFF000000,
        surfaceDark = 0xFF1C1C1E,
        elevatedSurfaceDark = 0xFF2C2C2E,
        accent = 0xFFFC3C44,
        textPrimary = 0xFFFFFFFF,
        textSecondary = 0xFF8E8E93,
        cornerRadiusLarge = 14,
        cornerRadiusMedium = 12,
        cornerRadiusSmall = 8,
    )

    @Composable
    override fun HomeScreen(
        navController: NavController,
        onScrolling: (onTop: Boolean) -> Unit,
    ) {
        AppleMusicHomeScreen(
            navController = navController,
            onScrolling = onScrolling,
        )
    }

    @Composable
    override fun SearchScreen(
        navController: NavController,
    ) {
        AppleMusicSearchScreen(
            navController = navController,
        )
    }

    @Composable
    override fun LibraryScreen(
        innerPadding: PaddingValues,
        navController: NavController,
        onScrolling: (onTop: Boolean) -> Unit,
    ) {
        AppleMusicLibraryScreen(
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
        AppleMusicBottomNavigationBar(
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
        AppleMusicMiniPlayer(
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
        AppleMusicNowPlayingScreen(
            navController = navController,
            onDismiss = onDismiss,
        )
    }
}
