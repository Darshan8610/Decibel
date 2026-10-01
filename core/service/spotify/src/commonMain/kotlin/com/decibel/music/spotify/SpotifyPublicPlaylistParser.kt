package com.decibel.music.spotify

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

data class SpotifyPublicPlaylist(
    val id: String,
    val name: String,
    val imageUrl: String?,
    val tracks: List<SpotifyPublicTrack>,
)

data class SpotifyPublicTrack(
    val spotifyTrackId: String,
    val title: String,
    val artists: String,
    val durationMs: Int,
)

/**
 * Extracts playlist metadata and the ordered track list from the HTML that Spotify serves
 * for `https://open.spotify.com/embed/playlist/<id>`.
 *
 * The page embeds its data as a `__NEXT_DATA__` JSON blob whose exact nesting has changed
 * over time, so parsing is deliberately defensive:
 *  1. try the known path (`props.pageProps.state.data.entity`), then
 *  2. fall back to a bounded search for any object that looks like a playlist
 *     (i.e. carries a `trackList` array).
 *
 * All field access is best-effort — a malformed or partially changed payload fails with a
 * user-readable [SpotifyImportException] instead of a NullPointerException.
 */
internal object SpotifyPublicPlaylistParser {
    private val nextDataRegex =
        Regex(
            """<script[^>]*\bid=["']__NEXT_DATA__["'][^>]*>(.*?)</script>""",
            setOf(RegexOption.DOT_MATCHES_ALL, RegexOption.IGNORE_CASE),
        )
    private val json = Json { ignoreUnknownKeys = true }
    private const val MAX_FALLBACK_DEPTH = 12

    fun parse(html: String): SpotifyPublicPlaylist {
        val payload =
            nextDataRegex.find(html)?.groupValues?.getOrNull(1)
                ?: throw SpotifyImportException("Spotify did not return playlist data. The playlist may be private or unavailable.")
        val root =
            try {
                json.parseToJsonElement(payload)
            } catch (e: Exception) {
                throw SpotifyImportException("Spotify sent playlist data that Decibel could not read.", e)
            }
        val entity =
            findPlaylistEntity(root)
                ?: throw SpotifyImportException("This link does not point to a public Spotify playlist.")
        val tracks = readTracks(entity)
        if (tracks.isEmpty()) {
            throw SpotifyImportException("This playlist has no playable tracks to import.")
        }
        return SpotifyPublicPlaylist(
            id = entity.string("id").orEmpty(),
            name = entity.string("name")?.trim().orEmpty().ifEmpty { "Spotify playlist" },
            imageUrl = readImageUrl(entity),
            tracks = tracks,
        )
    }

    /** Known path first, then a depth-limited search for any playlist-shaped object. */
    private fun findPlaylistEntity(root: JsonElement): JsonObject? {
        knownEntityPath(root)?.takeIf(::looksLikePlaylist)?.let { return it }
        val queue = ArrayDeque<Pair<JsonElement, Int>>()
        queue.addLast(root to 0)
        while (queue.isNotEmpty()) {
            val (node, depth) = queue.removeFirst()
            when (node) {
                is JsonObject -> {
                    if (looksLikePlaylist(node)) return node
                    if (depth < MAX_FALLBACK_DEPTH) node.values.forEach { queue.addLast(it to depth + 1) }
                }
                is JsonArray -> {
                    if (depth < MAX_FALLBACK_DEPTH) node.forEach { queue.addLast(it to depth + 1) }
                }
                else -> Unit
            }
        }
        return null
    }

    private fun knownEntityPath(root: JsonElement): JsonObject? {
        var node: JsonElement = root
        for (key in listOf("props", "pageProps", "state", "data", "entity")) {
            node = (node as? JsonObject)?.get(key) ?: return null
        }
        return node as? JsonObject
    }

    private fun looksLikePlaylist(obj: JsonObject): Boolean {
        if (obj["trackList"] !is JsonArray) return false
        val type = obj.string("type") ?: return true
        return type == "playlist"
    }

    private fun readTracks(entity: JsonObject): List<SpotifyPublicTrack> =
        (entity["trackList"] as? JsonArray).orEmpty().mapNotNull { element ->
            val item = element as? JsonObject ?: return@mapNotNull null
            val entityType = item.string("entityType")
            if (entityType != null && entityType != "track") return@mapNotNull null
            val uri = item.string("uri")
            val trackId =
                when {
                    uri.isNullOrEmpty() -> return@mapNotNull null
                    uri.startsWith("spotify:episode:") -> return@mapNotNull null
                    uri.startsWith("spotify:track:") -> uri.removePrefix("spotify:track:")
                    else -> uri
                }
            val title = item.string("title")?.trim().orEmpty()
            if (title.isEmpty()) return@mapNotNull null
            SpotifyPublicTrack(
                spotifyTrackId = trackId,
                title = title,
                artists = item.string("subtitle")?.trim().orEmpty(),
                durationMs = item["duration"].asIntOrNull() ?: 0,
            )
        }

    private fun readImageUrl(entity: JsonObject): String? {
        val coverArt = entity["coverArt"] as? JsonObject
        val fromCover =
            (coverArt?.get("sources") as? JsonArray)
                ?.firstOrNull()
                ?.let { it as? JsonObject }
                ?.string("url")
        if (fromCover != null) return fromCover
        return (entity["images"] as? JsonArray)
            ?.firstOrNull()
            ?.let { it as? JsonObject }
            ?.string("url")
    }

    private fun JsonObject.string(key: String): String? {
        val primitive = this[key] as? JsonPrimitive ?: return null
        return primitive.content.takeIf { !it.equals("null", ignoreCase = true) }
    }

    private fun JsonElement?.asIntOrNull(): Int? {
        val primitive = this as? JsonPrimitive ?: return null
        return primitive.content.toIntOrNull() ?: primitive.content.toDoubleOrNull()?.toInt()
    }
}
