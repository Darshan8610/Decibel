package com.decibel.music.ui.skin

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import com.decibel.music.domain.manager.DataStoreManager
import com.decibel.music.ui.theme.AppColors
import com.decibel.music.ui.theme.blackMoreOverlay
import com.decibel.music.ui.theme.overlay

enum class AppSkin(val id: String, val displayName: String) {
    DECIBEL_ORIGINALS(DataStoreManager.SKIN_DECIBEL_ORIGINALS, "Decibel Originals"),
    SPOTIFY(DataStoreManager.SKIN_SPOTIFY, "Spotify Skin"),
    APPLE_MUSIC(DataStoreManager.SKIN_APPLE_MUSIC, "Apple Music Skin");

    val provider: SkinUiProvider
        get() = getSkinProvider(this)

    companion object {
        fun fromId(id: String?): AppSkin =
            entries.find { it.id == id } ?: DECIBEL_ORIGINALS
    }
}

val LocalAppSkin = staticCompositionLocalOf { AppSkin.DECIBEL_ORIGINALS }

// ===== Spotify Brand Colors =====
val SpotifyGreen = Color(0xFF1DB954)
val SpotifyGreenBright = Color(0xFF1ED760)
val SpotifyDark = Color(0xFF121212)
val SpotifyCard = Color(0xFF181818)
val SpotifyCardHover = Color(0xFF282828)
val SpotifyElevated = Color(0xFF242424)
val SpotifyTextSecondary = Color(0xFFB3B3B3)
val SpotifyTextMuted = Color(0xFF727272)
val SpotifyLikedGradientStart = Color(0xFF450AF5)
val SpotifyLikedGradientEnd = Color(0xFFC4EFD9)

// ===== Apple Music Brand Colors =====
val AppleMusicPink = Color(0xFFFC3C44)
val AppleMusicPinkLight = Color(0xFFFA2D48)
val AppleMusicBackgroundDark = Color(0xFF000000)
val AppleMusicSurfaceDark = Color(0xFF1C1C1E)
val AppleMusicSurfaceElevatedDark = Color(0xFF2C2C2E)
val AppleMusicDividerDark = Color(0xFF38383A)
val AppleMusicTextSecondaryDark = Color(0xFF8E8E93)

// Material 3 ColorScheme for Spotify Skin
fun getSpotifyColorScheme(): ColorScheme =
    darkColorScheme(
        primary = SpotifyGreen,
        onPrimary = Color.Black,
        primaryContainer = SpotifyGreen.copy(alpha = 0.2f),
        onPrimaryContainer = SpotifyGreenBright,
        secondary = SpotifyGreen,
        onSecondary = Color.Black,
        background = SpotifyDark,
        onBackground = Color.White,
        surface = SpotifyDark,
        onSurface = Color.White,
        surfaceVariant = SpotifyCard,
        onSurfaceVariant = SpotifyTextSecondary,
        surfaceContainerLowest = Color.Black,
        surfaceContainerLow = SpotifyDark,
        surfaceContainer = SpotifyCard,
        surfaceContainerHigh = SpotifyElevated,
        surfaceContainerHighest = SpotifyCardHover,
        outline = Color(0xFF282828),
        outlineVariant = Color(0xFF3E3E3E),
    )

// Material 3 ColorScheme for Apple Music Skin
fun getAppleMusicColorScheme(): ColorScheme =
    darkColorScheme(
        primary = AppleMusicPink,
        onPrimary = Color.White,
        primaryContainer = AppleMusicPink.copy(alpha = 0.25f),
        onPrimaryContainer = AppleMusicPinkLight,
        secondary = AppleMusicPink,
        onSecondary = Color.White,
        background = AppleMusicBackgroundDark,
        onBackground = Color.White,
        surface = AppleMusicSurfaceDark,
        onSurface = Color.White,
        surfaceVariant = AppleMusicSurfaceElevatedDark,
        onSurfaceVariant = AppleMusicTextSecondaryDark,
        surfaceContainerLowest = Color.Black,
        surfaceContainerLow = AppleMusicBackgroundDark,
        surfaceContainer = AppleMusicSurfaceDark,
        surfaceContainerHigh = AppleMusicSurfaceElevatedDark,
        surfaceContainerHighest = Color(0xFF3A3A3C),
        outline = AppleMusicDividerDark,
        outlineVariant = Color(0xFF48484A),
    )

fun getSpotifyAppColors(): AppColors =
    AppColors(
        favorite = SpotifyGreen,
        lyricActive = SpotifyGreenBright,
        shimmerBackground = Color(0xFF181818),
        shimmerLine = Color(0xFF282828),
        overlay = overlay,
        overlayHeavy = blackMoreOverlay,
    )

fun getAppleMusicAppColors(): AppColors =
    AppColors(
        favorite = AppleMusicPink,
        lyricActive = AppleMusicPink,
        shimmerBackground = Color(0xFF1C1C1E),
        shimmerLine = Color(0xFF2C2C2E),
        overlay = overlay,
        overlayHeavy = blackMoreOverlay,
    )
