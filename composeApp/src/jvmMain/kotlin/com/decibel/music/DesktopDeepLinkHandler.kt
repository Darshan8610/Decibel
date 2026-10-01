package com.decibel.music

import com.eygraber.uri.Uri
import com.decibel.music.domain.data.model.intent.GenericIntent
import com.decibel.music.logger.Logger
import java.io.File

/**
 * Singleton to handle deep link URIs on Desktop.
 * Caches URI if app UI is not ready yet, delivers immediately if listener is set.
 *
 * Also provides file-based IPC for single-instance deep link forwarding:
 * when a second instance launches with a URI, it writes the URI to a temp file,
 * and the first instance reads it on restore.
 *
 * Supported URI patterns:
 * - decibel://open-app?url=<encoded_url>  (redirected from website)
 * - decibel://watch?v=VIDEO_ID            (direct scheme)
 * - decibel://playlist?list=PLAYLIST_ID   (direct scheme)
 * - decibel://channel/CHANNEL_ID          (direct scheme)
 * - decibel://album?id=ALBUM_ID           (direct scheme)
 * - https://decibel.app/app/...            (web URL passed via args)
 */
object DesktopDeepLinkHandler {
    private const val TAG = "DesktopDeepLinkHandler"

    private val pendingUriFile: File by lazy {
        File(System.getProperty("java.io.tmpdir"), "decibel_pending_deeplink.txt")
    }

    private var cached: String? = null

    var listener: ((GenericIntent) -> Unit)? = null
        set(value) {
            field = value
            if (value != null) {
                cached?.let { uri ->
                    Logger.d(TAG, "Delivering cached URI: $uri")
                    value.invoke(parseToIntent(uri))
                    cached = null
                }
            }
        }

    fun onNewUri(uri: String) {
        Logger.d(TAG, "Received URI: $uri")
        val intent = parseToIntent(uri)
        val currentListener = listener
        if (currentListener != null) {
            currentListener.invoke(intent)
            cached = null
        } else {
            Logger.d(TAG, "Listener not ready, caching URI: $uri")
            cached = uri
        }
    }

    /**
     * Write URI to a temp file so the running (first) instance can pick it up.
     * Called by the second instance before it exits.
     */
    fun writePendingUri(uri: String) {
        try {
            pendingUriFile.writeText(uri)
            Logger.d(TAG, "Wrote pending URI to file: $uri")
        } catch (e: Exception) {
            Logger.e(TAG, "Failed to write pending URI: ${e.message}")
        }
    }

    /**
     * Read and consume the pending URI file written by a second instance.
     * Called by the first instance when it receives a restore request.
     */
    fun consumePendingUri() {
        try {
            if (pendingUriFile.exists()) {
                val uri = pendingUriFile.readText().trim()
                pendingUriFile.delete()
                if (uri.isNotEmpty()) {
                    Logger.d(TAG, "Consumed pending URI from file: $uri")
                    onNewUri(uri)
                }
            }
        } catch (e: Exception) {
            Logger.e(TAG, "Failed to read pending URI: ${e.message}")
        }
    }

    /**
     * Converts a raw URI string into a [GenericIntent] that App.kt can process.
     *
     * Conversion rules:
     * 1. decibel://open-app?url=<encoded_url>
     *    -> Extract the `url` param and use it as intent data
     *
     * 2. decibel://watch?v=xxx, decibel://playlist?list=xxx, etc.
     *    -> Convert to https://decibel.app/app/watch?v=xxx format
     *      so App.kt handles it uniformly via the decibel.app branch
     *
     * 3. https://decibel.app/app/... or YouTube URLs
     *    -> Pass through as-is
     */
    private fun parseToIntent(uri: String): GenericIntent {
        val parsed = Uri.parse(uri)

        val actualUri = when {
            // decibel://open-app?url=<encoded_url>
            (parsed.scheme == "decibel" || parsed.scheme == "decibel") && parsed.host == "open-app" -> {
                val urlParam = parsed.getQueryParameter("url")
                if (urlParam != null) {
                    Logger.d(TAG, "Extracted URL from open-app: $urlParam")
                    Uri.parse(urlParam)
                } else {
                    // decibel://open-app without params -> just open the app, no navigation
                    Logger.d(TAG, "open-app without URL param, just opening app")
                    null
                }
            }

            // decibel://watch?v=xxx -> https://decibel.app/app/watch?v=xxx
            // decibel://playlist?list=xxx -> https://decibel.app/app/playlist?list=xxx
            // decibel://channel/UCxxx -> https://decibel.app/app/channel/UCxxx
            // decibel://album?id=xxx -> https://decibel.app/app/album?id=xxx
            (parsed.scheme == "decibel" || parsed.scheme == "decibel") && parsed.host != null -> {
                val host = parsed.host!!
                val query = parsed.query?.let { "?$it" } ?: ""
                val pathSuffix = parsed.pathSegments.joinToString("/").let {
                    if (it.isNotEmpty()) "/$it" else ""
                }
                val convertedUrl = "https://decibel.app/app/$host$pathSuffix$query"
                Logger.d(TAG, "Converted decibel:// to: $convertedUrl")
                Uri.parse(convertedUrl)
            }

            // wordbyword://lastfm-auth?token=xxx -> pass through untouched. It must NOT be rewritten
            // to decibel.app like the branch above does: App.kt matches on this exact scheme to
            // read the Last.fm request token.
            parsed.scheme == "wordbyword" -> parsed

            // https://decibel.app/app/... or YouTube URLs -> pass through
            else -> parsed
        }

        return if (actualUri != null) {
            GenericIntent(
                action = "android.intent.action.VIEW",
                data = actualUri,
            )
        } else {
            // No data -> just triggers app restore, no navigation
            GenericIntent(action = "android.intent.action.VIEW")
        }
    }
}

