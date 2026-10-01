package com.decibel.music.spotify

/**
 * Builds a catalog search query from a Spotify track's title and artist text.
 *
 * Spotify titles often carry release annotations such as `- From "Movie"` or
 * `(From "Album")` that hurt catalog matching, so those are stripped first and
 * whitespace is normalised.
 */
object SpotifyTrackQuery {
    private val fromParenthetical = Regex("""\s*\(\s*From\s*"[^"]*"\s*\)""")
    private val fromSuffix = Regex("""\s*-\s*From\s*"[^"]*"\s*""")
    private val extraWhitespace = Regex("""\s{2,}""")

    fun build(
        title: String,
        artists: String,
    ): String {
        val cleanedTitle =
            title
                .replace(fromParenthetical, "")
                .replace(fromSuffix, "")
        return listOf(cleanedTitle, artists)
            .map { extraWhitespace.replace(it, " ").trim() }
            .filter(String::isNotBlank)
            .joinToString(" ")
    }
}
