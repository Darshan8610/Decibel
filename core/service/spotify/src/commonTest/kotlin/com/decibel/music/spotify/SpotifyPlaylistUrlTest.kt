package com.decibel.music.spotify

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class SpotifyPlaylistUrlTest {
    private val id = "3pZ1uxm19nwBXmgJKGFVNf"

    @Test
    fun extractsPlainShareUrl() {
        assertEquals(id, SpotifyPlaylistUrl.extractPlaylistId("https://open.spotify.com/playlist/$id"))
    }

    @Test
    fun extractsShareUrlWithTrackingParams() {
        val url =
            "https://open.spotify.com/playlist/$id?si=864mrN4oS0eEI94_kURWxg&utm_source=copy-link&pi=KmxBNPyBTCe1D"
        assertEquals(id, SpotifyPlaylistUrl.extractPlaylistId(url))
    }

    @Test
    fun extractsIntlLocalePath() {
        assertEquals(id, SpotifyPlaylistUrl.extractPlaylistId("https://open.spotify.com/intl-de/playlist/$id"))
        assertEquals(id, SpotifyPlaylistUrl.extractPlaylistId("https://open.spotify.com/intl-pt/playlist/$id?si=xx"))
    }

    @Test
    fun extractsEmbedPath() {
        assertEquals(id, SpotifyPlaylistUrl.extractPlaylistId("https://open.spotify.com/embed/playlist/$id"))
    }

    @Test
    fun extractsUri() {
        assertEquals(id, SpotifyPlaylistUrl.extractPlaylistId("spotify:playlist:$id"))
        assertEquals(id, SpotifyPlaylistUrl.extractPlaylistId("spotify:playlist:$id?si=abc"))
    }

    @Test
    fun extractsHttpAndUppercaseScheme() {
        assertEquals(id, SpotifyPlaylistUrl.extractPlaylistId("HTTP://open.spotify.com/playlist/$id"))
        assertEquals(id, SpotifyPlaylistUrl.extractPlaylistId("http://open.spotify.com/playlist/$id"))
    }

    @Test
    fun extractsLinkEmbeddedInShareText() {
        val text = "check out my playlist https://open.spotify.com/playlist/$id?si=zz great tunes"
        assertEquals(id, SpotifyPlaylistUrl.extractPlaylistId(text))
    }

    @Test
    fun rejectsNonPlaylistSpotifyPaths() {
        assertNull(SpotifyPlaylistUrl.extractPlaylistId("https://open.spotify.com/track/$id"))
        assertNull(SpotifyPlaylistUrl.extractPlaylistId("https://open.spotify.com/artist/$id"))
        assertNull(SpotifyPlaylistUrl.extractPlaylistId("https://open.spotify.com/album/$id"))
    }

    @Test
    fun rejectsForeignHostsAndJunk() {
        assertNull(SpotifyPlaylistUrl.extractPlaylistId("https://example.com/playlist/$id"))
        assertNull(SpotifyPlaylistUrl.extractPlaylistId("https://youtube.com/playlist?list=PL123"))
        assertNull(SpotifyPlaylistUrl.extractPlaylistId("not a link at all"))
        assertNull(SpotifyPlaylistUrl.extractPlaylistId(""))
        assertNull(SpotifyPlaylistUrl.extractPlaylistId("https://open.spotify.com/playlist/tooshort"))
    }

    @Test
    fun detectsShortLinks() {
        assertTrue(SpotifyPlaylistUrl.isShortLink("https://spotify.link/abc123"))
        assertTrue(SpotifyPlaylistUrl.isShortLink("https://on.spotify.com/short/xyz"))
        assertTrue(SpotifyPlaylistUrl.isShortLink("https://spoti.fi/3abcDEF"))
        assertFalse(SpotifyPlaylistUrl.isShortLink("https://open.spotify.com/playlist/$id"))
        assertFalse(SpotifyPlaylistUrl.isShortLink("just a name"))
    }
}
