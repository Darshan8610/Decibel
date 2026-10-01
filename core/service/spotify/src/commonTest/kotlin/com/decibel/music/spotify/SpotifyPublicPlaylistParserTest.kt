package com.decibel.music.spotify

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class SpotifyPublicPlaylistParserTest {
    /**
     * Payload shaped exactly like the live `__NEXT_DATA__` of
     * https://open.spotify.com/embed/playlist/3pZ1uxm19nwBXmgJKGFVNf
     * (field names, extra keys, trailing space in the name, episode entry, string duration).
     */
    private val realisticEntity =
        """{"props":{"pageProps":{"state":{"data":{"entity":{"id":"3pZ1uxm19nwBXmgJKGFVNf","uri":"spotify:playlist:3pZ1uxm19nwBXmgJKGFVNf","type":"playlist","name":"Somewhere peaceful ","coverArt":{"sources":[{"url":"https://i.scdn.co/image/abc640","width":640,"height":640}]},"trackList":[{"uri":"spotify:track:4qW3BbQAwZsrnu8a3ZRdyT","uid":"635761ef0dce3fcf","title":"Self Aware","subtitle":"Temper City","isExplicit":false,"isNineteenPlus":false,"contentRatings":{"labels":[]},"duration":180740,"isPlayable":true,"playabilityReason":"PLAYABLE","audioPreview":{"format":"MP3_96","url":"https://p.scdn.co/mp3-preview/x"},"entityType":"track"},{"uri":"spotify:episode:4xEpisodeIdAbc123","title":"Podcast Ep","subtitle":"Host","duration":300000,"entityType":"episode"},{"uri":"spotify:track:secondTrackIdAbc123","title":"Second","subtitle":"Artist Two","duration":"201500"}]}}}}}}"""

    private fun html(payload: String) = """<html><head><script id="__NEXT_DATA__" type="application/json" nonce="n">$payload</script></head></html>"""

    @Test
    fun parsesRealisticPayloadWithEpisodesAndStringDurations() {
        val playlist = SpotifyPublicPlaylistParser.parse(html(realisticEntity))

        assertEquals("3pZ1uxm19nwBXmgJKGFVNf", playlist.id)
        assertEquals("Somewhere peaceful", playlist.name)
        assertEquals("https://i.scdn.co/image/abc640", playlist.imageUrl)
        assertEquals(listOf("4qW3BbQAwZsrnu8a3ZRdyT", "secondTrackIdAbc123"), playlist.tracks.map { it.spotifyTrackId })
        assertEquals(180740, playlist.tracks[0].durationMs)
        assertEquals(201500, playlist.tracks[1].durationMs)
        assertEquals("Self Aware" to "Temper City", playlist.tracks[0].title to playlist.tracks[0].artists)
    }

    @Test
    fun acceptsScriptTagWithDifferentAttributeOrder() {
        val html = """<html><script type="application/json" id="__NEXT_DATA__">$realisticEntity</script></html>"""
        assertEquals("Somewhere peaceful", SpotifyPublicPlaylistParser.parse(html).name)
    }

    @Test
    fun fallsBackToSearchingForPlaylistShapeAtUnknownNesting() {
        val payload =
            """{"props":{"pageProps":{"deep":{"nested":{"entity":{"type":"playlist","name":"  Deep Mix  ","trackList":[{"uri":"spotify:track:abcdef1234567890abcd","title":"Song","subtitle":"Artist","duration":100,"entityType":"track"}]}}}}}}"""
        val playlist = SpotifyPublicPlaylistParser.parse(html(payload))
        assertEquals("Deep Mix", playlist.name)
        assertEquals(1, playlist.tracks.size)
    }

    @Test
    fun failsClearlyWhenNextDataMissing() {
        val error = assertFailsWith<SpotifyImportException> { SpotifyPublicPlaylistParser.parse("<html><body>no data</body></html>") }
        assertTrue(error.message!!.contains("did not return playlist data"))
    }

    @Test
    fun failsClearlyWhenPayloadIsNotJson() {
        val error = assertFailsWith<SpotifyImportException> { SpotifyPublicPlaylistParser.parse(html("not-json{")) }
        assertTrue(error.message!!.contains("could not read"))
    }

    @Test
    fun failsClearlyWhenEntityIsNotAPlaylist() {
        val payload = """{"props":{"pageProps":{"state":{"data":{"entity":{"type":"track","name":"Single Song"}}}}}}"""
        val error = assertFailsWith<SpotifyImportException> { SpotifyPublicPlaylistParser.parse(html(payload)) }
        assertTrue(error.message!!.contains("does not point"))
    }

    @Test
    fun failsClearlyWhenTrackListEmpty() {
        val payload = """{"props":{"pageProps":{"state":{"data":{"entity":{"type":"playlist","name":"Empty","trackList":[]}}}}}}"""
        val error = assertFailsWith<SpotifyImportException> { SpotifyPublicPlaylistParser.parse(html(payload)) }
        assertTrue(error.message!!.contains("no playable tracks"))
    }

    @Test
    fun skipsEntriesWithoutTitleAndDefaultsNameWhenMissing() {
        val payload =
            """{"props":{"pageProps":{"state":{"data":{"entity":{"type":"playlist","trackList":[{"uri":"spotify:track:abc123def456ghi78jklm","entityType":"track"}]}}}}}}"""
        val error = assertFailsWith<SpotifyImportException> { SpotifyPublicPlaylistParser.parse(html(payload)) }
        assertTrue(error.message!!.contains("no playable tracks"))
    }

    @Test
    fun toleratesMissingCoverArtAndUnknownFields() {
        val payload =
            """{"props":{"pageProps":{"state":{"data":{"entity":{"type":"playlist","name":"Bare","somethingNew":42,"trackList":[{"uri":"spotify:track:abcdef1234567890abcdexf","title":"T","subtitle":"A","entityType":"track","brandNewKey":{"a":1}}]}}}}}}"""
        val playlist = SpotifyPublicPlaylistParser.parse(html(payload))
        assertEquals(null, playlist.imageUrl)
        assertEquals("Bare", playlist.name)
    }
}
