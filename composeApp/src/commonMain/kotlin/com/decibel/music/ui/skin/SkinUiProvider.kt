package com.decibel.music.ui.skin

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.decibel.music.expect.ui.PlatformBackdrop
import com.decibel.music.viewModel.SharedViewModel
import kotlin.reflect.KClass
import org.koin.compose.koinInject

data class SkinTokens(
    val backgroundDark: Long,
    val surfaceDark: Long,
    val elevatedSurfaceDark: Long,
    val accent: Long,
    val textPrimary: Long,
    val textSecondary: Long,
    val cornerRadiusLarge: Int,
    val cornerRadiusMedium: Int,
    val cornerRadiusSmall: Int,
)

interface SkinUiProvider {
    val skin: AppSkin
    val tokens: SkinTokens

    @Composable
    fun HomeScreen(
        navController: NavController,
        onScrolling: (onTop: Boolean) -> Unit,
    )

    @Composable
    fun SearchScreen(
        navController: NavController,
    )

    @Composable
    fun LibraryScreen(
        innerPadding: PaddingValues,
        navController: NavController,
        onScrolling: (onTop: Boolean) -> Unit,
    )

    @Composable
    fun AlbumDetailScreen(
        browseId: String,
        navController: NavController,
    )

    @Composable
    fun PlaylistDetailScreen(
        playlistId: String,
        isYourYouTubePlaylist: Boolean,
        navController: NavController,
    )

    @Composable
    fun ArtistDetailScreen(
        channelId: String,
        navController: NavController,
    )

    @Composable
    fun BottomNavigationBar(
        navController: NavController,
        reloadDestinationIfNeeded: (KClass<*>) -> Unit,
        backdrop: PlatformBackdrop? = null,
    )

    @Composable
    fun MiniPlayer(
        modifier: Modifier,
        backdrop: PlatformBackdrop? = null,
        onClose: (() -> Unit)? = null,
        onClick: () -> Unit,
        sharedViewModel: SharedViewModel = koinInject(),
    )

    @Composable
    fun NowPlayingScreen(
        navController: NavController,
        onDismiss: () -> Unit,
    )
}

val LocalSkinProvider = staticCompositionLocalOf<SkinUiProvider> {
    DecibelOriginalsSkinProvider
}

fun getSkinProvider(skin: AppSkin): SkinUiProvider = when (skin) {
    AppSkin.DECIBEL_ORIGINALS -> DecibelOriginalsSkinProvider
    AppSkin.SPOTIFY -> SpotifySkinProvider
    AppSkin.APPLE_MUSIC -> AppleMusicSkinProvider
}
