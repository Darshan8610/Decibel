package com.decibel.music.spotify

/**
 * Parses Spotify playlist references in every shape a user can paste them:
 *
 * - shared web links with tracking params (`https://open.spotify.com/playlist/<id>?si=…`)
 * - locale-prefixed links (`https://open.spotify.com/intl-de/playlist/<id>`)
 * - embed links (`https://open.spotify.com/embed/playlist/<id>`)
 * - `spotify:playlist:<id>` URIs
 * - a playlist link embedded in surrounding share text
 *
 * Short links (`spotify.link`, `on.spotify.com`, `spoti.fi`) cannot be parsed directly —
 * they must be followed over HTTP first; see [isShortLink] and `Spotify.resolvePlaylistId`.
 */
object SpotifyPlaylistUrl {
    private val base62Id = Regex("^[A-Za-z0-9]{22}$")
    private val shortLinkHosts = setOf("spotify.link", "on.spotify.com", "spoti.fi")
    private val anyUrl = Regex("""https?://[^/?#]+""", RegexOption.IGNORE_CASE)
    private val shortLinkUrl = Regex("""https?://([^/?#]+)""", RegexOption.IGNORE_CASE)
    private val playlistPath =
        Regex("""https?://[^/?#]+/(?:[^/?#]+/)*playlist/([A-Za-z0-9]{22})""", RegexOption.IGNORE_CASE)
    private val playlistUri = Regex("""spotify:playlist:([A-Za-z0-9]{22})""", RegexOption.IGNORE_CASE)

    /** Returns the 22-character playlist id referenced by [value], or null when there is none. */
    fun extractPlaylistId(value: String): String? {
        val trimmed = value.trim()
        if (trimmed.isEmpty()) return null
        // Direct URI form (inside text or standalone).
        playlistUri.find(trimmed)?.let { return it.groupValues[1] }
        // HTTP(S) form: the host must be a Spotify host before the path is considered,
        // so foreign URLs like example.com/playlist/<22 chars> are rejected.
        if (!anyUrl.containsMatchIn(trimmed)) return null
        val host = shortLinkUrl.find(trimmed)?.groupValues?.get(1)?.lowercase()?.removePrefix("www.") ?: return null
        if (!host.endsWith("spotify.com")) return null
        return playlistPath.find(trimmed)?.groupValues?.get(1)
    }

    /** True when [value] is an HTTP(S) Spotify short link that has to be resolved via redirect. */
    fun isShortLink(value: String): Boolean {
        val host = shortLinkUrl.find(value.trim())?.groupValues?.get(1)?.lowercase()?.removePrefix("www.") ?: return false
        return host in shortLinkHosts
    }
}
