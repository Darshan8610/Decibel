package com.decibel.music.ui.skin

import java.io.File
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * Regression guard for the Decibel Originals StackOverflowError.
 *
 * Each override used to call an unqualified composable with the same name
 * (e.g. `HomeScreen(...)` inside `override fun HomeScreen`), which Kotlin
 * resolves to the member itself — infinite self-recursion that only shows
 * up at runtime as `StackOverflowError` on the Home/Content route.
 *
 * The provider must delegate through import aliases (DecibelHomeScreen,
 * DecibelSearchScreen, ...) so the call target is unambiguous. This is a
 * static source check because composing the real screens in a unit test
 * would require NavController + Koin + DataStore.
 */
class SkinDelegationTest {

    private fun providerSource(): String {
        val candidates = listOf(
            // Gradle module dir when run via :composeApp:jvmTest
            File("composeApp/src/commonMain/kotlin/com/decibel/music/ui/skin/DecibelOriginalsSkinProvider.kt"),
            File("src/commonMain/kotlin/com/decibel/music/ui/skin/DecibelOriginalsSkinProvider.kt"),
            File("D:/new decibel/composeApp/src/commonMain/kotlin/com/decibel/music/ui/skin/DecibelOriginalsSkinProvider.kt"),
        )
        val file = candidates.firstOrNull { it.isFile }
            ?: error("DecibelOriginalsSkinProvider.kt not found from ${File(".").absolutePath}")
        return file.readText()
    }

    @Test
    fun decibelProviderDelegatesToAliasedScreens() {
        val src = providerSource()
        assertTrue(src.contains("DecibelHomeScreen("), "HomeScreen must call DecibelHomeScreen")
        assertTrue(src.contains("DecibelSearchScreen("), "SearchScreen must call DecibelSearchScreen")
        assertTrue(src.contains("DecibelLibraryScreen("), "LibraryScreen must call DecibelLibraryScreen")
        assertTrue(src.contains("DecibelAlbumScreen("), "AlbumDetailScreen must call DecibelAlbumScreen")
        assertTrue(src.contains("DecibelPlaylistScreen("), "PlaylistDetailScreen must call DecibelPlaylistScreen")
        assertTrue(src.contains("DecibelArtistScreen("), "ArtistDetailScreen must call DecibelArtistScreen")
        assertTrue(src.contains("DecibelMiniPlayer("), "MiniPlayer must call DecibelMiniPlayer")
        assertTrue(src.contains("DecibelNowPlayingScreen("), "NowPlayingScreen must call DecibelNowPlayingScreen")
    }

    @Test
    fun decibelProviderHasNoUnqualifiedSelfCalls() {
        val lines = providerSource().lines()
        // Collect the member function bodies so import lines don't trip the check.
        val body = lines.dropWhile { !it.contains("object DecibelOriginalsSkinProvider") }.joinToString("\n")
        val bareCall = Regex("""(?m)^\s*(HomeScreen|SearchScreen|LibraryScreen|AlbumDetailScreen|PlaylistDetailScreen|ArtistDetailScreen|MiniPlayer|NowPlayingScreen)\(""")
        val hits = bareCall.findAll(body).map { it.groupValues[1] }.toList()
        assertTrue(
            hits.isEmpty(),
            "Unqualified self-recursive call(s) would StackOverflow at runtime: $hits. " +
                "Use the Decibel* import alias instead.",
        )
    }

    @Test
    fun interfaceKeepsBackwardCompatibleMiniPlayerDefaults() {
        val root = sequenceOf(
            File("composeApp/src/commonMain/kotlin/com/decibel/music/ui/skin/SkinUiProvider.kt"),
            File("src/commonMain/kotlin/com/decibel/music/ui/skin/SkinUiProvider.kt"),
            File("D:/new decibel/composeApp/src/commonMain/kotlin/com/decibel/music/ui/skin/SkinUiProvider.kt"),
        ).firstOrNull { it.isFile } ?: error("SkinUiProvider.kt not found")
        val src = root.readText()
        assertTrue(src.contains("backdrop: PlatformBackdrop? = null"), "MiniPlayer.backdrop must keep a default")
        assertTrue(src.contains("onClose: (() -> Unit)? = null"), "MiniPlayer.onClose must keep a default")
        assertFalse(src.contains("TODO"), "SkinUiProvider should not contain TODOs")
    }
}
