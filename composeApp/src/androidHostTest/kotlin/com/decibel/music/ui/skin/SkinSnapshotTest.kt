package com.decibel.music.ui.skin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.github.takahirom.roborazzi.captureRoboImage
import com.decibel.music.ui.component.EndOfPage
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [34], qualifiers = RobolectricDeviceQualifiers.Pixel7)
class SkinSnapshotTest {

    @Test
    fun testPageEndHasNoWatermark() {
        captureRoboImage("build/outputs/roborazzi/page_end_without_watermark.png") {
            EndOfPage()
        }
    }

    @Test
    fun testSpotifySkinIndicator() {
        captureRoboImage("build/outputs/roborazzi/spotify_indicator.png") {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(Color(0xFF1DB954)),
                contentAlignment = Alignment.Center,
            ) {
                Text("Spotify", color = Color.White)
            }
        }
    }

    @Test
    fun testAppleMusicSkinIndicator() {
        captureRoboImage("build/outputs/roborazzi/apple_music_indicator.png") {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(Color(0xFFFC3C44)),
                contentAlignment = Alignment.Center,
            ) {
                Text("Apple Music", color = Color.White)
            }
        }
    }

    @Test
    fun testSpotifyFilterChips() {
        captureRoboImage("build/outputs/roborazzi/spotify_chips.png") {
            SpotifyFilterChipsRow(selectedCategory = "Music")
        }
    }

    @Test
    fun testSpotifyFilterChipsAllSelectedIncludesAudiobooks() {
        captureRoboImage("build/outputs/roborazzi/spotify_chips_all.png") {
            Column(modifier = Modifier.background(Color(0xFF121212))) {
                SpotifyFilterChipsRow(selectedCategory = "All")
                SpotifyFilterChipsRow(selectedCategory = "Audiobooks")
            }
        }
    }

    @Test
    fun testAppleMusicLibraryRows() {
        captureRoboImage("build/outputs/roborazzi/apple_music_library_rows.png") {
            Column(modifier = Modifier.background(Color.Black)) {
                AppleMusicLibraryRow(title = "Playlists", onClick = {})
                AppleMusicLibraryRow(title = "Artists", onClick = {})
                AppleMusicLibraryRow(title = "Albums", onClick = {})
            }
        }
    }

    @Test
    fun testAppleMusicLibraryRow() {
        captureRoboImage("build/outputs/roborazzi/apple_music_row.png") {
            AppleMusicLibraryRow(title = "Playlists", onClick = {})
        }
    }

    @Test
    fun testSpotifyTokensPalette() {
        captureRoboImage("build/outputs/roborazzi/spotify_palette.png") {
            val tokens = SpotifySkinProvider.tokens
            Column(modifier = Modifier.background(Color(tokens.backgroundDark)).padding(16.dp)) {
                Box(
                    modifier = Modifier
                        .size(width = 200.dp, height = 40.dp)
                        .background(Color(tokens.surfaceDark)),
                    contentAlignment = Alignment.Center,
                ) {
                    Text("Surface Dark", color = Color(tokens.textPrimary))
                }
                Box(
                    modifier = Modifier
                        .size(width = 200.dp, height = 40.dp)
                        .background(Color(tokens.accent)),
                    contentAlignment = Alignment.Center,
                ) {
                    Text("Accent", color = Color.Black)
                }
            }
        }
    }

    @Test
    fun testAppleMusicTokensPalette() {
        captureRoboImage("build/outputs/roborazzi/apple_music_palette.png") {
            val tokens = AppleMusicSkinProvider.tokens
            Column(modifier = Modifier.background(Color(tokens.backgroundDark)).padding(16.dp)) {
                Box(
                    modifier = Modifier
                        .size(width = 200.dp, height = 40.dp)
                        .background(Color(tokens.surfaceDark)),
                    contentAlignment = Alignment.Center,
                ) {
                    Text("Surface Dark", color = Color(tokens.textPrimary))
                }
                Box(
                    modifier = Modifier
                        .size(width = 200.dp, height = 40.dp)
                        .background(Color(tokens.accent)),
                    contentAlignment = Alignment.Center,
                ) {
                    Text("Accent", color = Color.White)
                }
            }
        }
    }

    @Test
    fun testDecibelTokensPalette() {
        captureRoboImage("build/outputs/roborazzi/decibel_palette.png") {
            val tokens = DecibelOriginalsSkinProvider.tokens
            Column(modifier = Modifier.background(Color(tokens.backgroundDark)).padding(16.dp)) {
                Box(
                    modifier = Modifier
                        .size(width = 200.dp, height = 40.dp)
                        .background(Color(tokens.surfaceDark)),
                    contentAlignment = Alignment.Center,
                ) {
                    Text("Surface Dark", color = Color(tokens.textPrimary))
                }
                Box(
                    modifier = Modifier
                        .size(width = 200.dp, height = 40.dp)
                        .background(Color(tokens.accent)),
                    contentAlignment = Alignment.Center,
                ) {
                    Text("Accent", color = Color.White)
                }
            }
        }
    }
}
