package com.decibel.music.spotify

import com.decibel.music.spotify.auth.SpotifyAuth
import com.decibel.music.spotify.model.response.spotify.CanvasResponse
import com.decibel.music.spotify.model.response.spotify.ClientTokenResponse
import com.decibel.music.spotify.model.response.spotify.PersonalTokenResponse
import com.decibel.music.spotify.model.response.spotify.SpotifyLyricsResponse
import com.decibel.music.spotify.model.response.spotify.search.SpotifySearchResponse
import io.ktor.client.call.body
import io.ktor.client.engine.ProxyBuilder
import io.ktor.client.engine.http
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withTimeout

private const val FETCH_PLAYLIST_TIMEOUT_MS = 30_000L
private const val REDIRECT_TIMEOUT_MS = 15_000L

class Spotify {
    private val spotifyClient = SpotifyClient()
    private val spotifyAuth = SpotifyAuth(spotifyClient)

    /**
     * Remove proxy for client
     */
    fun removeProxy() {
        spotifyClient.proxy = null
    }

    /**
     * Set the proxy for client
     */
    fun setProxy(
        isHttp: Boolean,
        host: String,
        port: Int,
    ) {
        val verifiedHost =
            if (!host.contains("http")) {
                "http://$host"
            } else {
                host
            }
        runCatching {
            if (isHttp) ProxyBuilder.http("$verifiedHost:$port") else ProxyBuilder.socks(verifiedHost, port)
        }.onSuccess {
            spotifyClient.proxy = it
        }.onFailure {
            it.printStackTrace()
        }
    }

    /**
     * Fetch the metadata and complete track list of a PUBLIC playlist without any Spotify login.
     *
     * Accepts shared web links (including `intl-` locale and `/embed/` paths, with or without
     * tracking parameters), a playlist link embedded in share text, `spotify:playlist:` URIs and
     * Spotify short links (`spotify.link`, `on.spotify.com`, `spoti.fi` — unwrapped via redirect).
     *
     * The HTML comes from Spotify's public embed endpoint and is parsed locally; no `sp_dc`
     * cookie or OAuth token is involved. Failures are reported as [Result.failure] carrying a
     * [SpotifyImportException] whose message is safe to show to the user.
     */
    suspend fun getPublicPlaylist(url: String): Result<SpotifyPublicPlaylist> =
        try {
            val playlistId = resolvePlaylistId(url)
            val response =
                withTimeout(FETCH_PLAYLIST_TIMEOUT_MS) {
                    spotifyClient.getPublicPlaylistEmbed(playlistId)
                }
            when (val code = response.status.value) {
                in 200..299 -> Result.success(SpotifyPublicPlaylistParser.parse(response.body()))
                404 -> Result.failure(SpotifyImportException("That playlist does not exist or is not public."))
                401, 403 -> Result.failure(SpotifyImportException("Spotify blocked this request. The playlist may be private."))
                else -> Result.failure(SpotifyImportException("Spotify returned HTTP $code. Try again in a moment."))
            }
        } catch (e: TimeoutCancellationException) {
            Result.failure(SpotifyImportException("Spotify took too long to respond. Check your connection and try again."))
        } catch (e: CancellationException) {
            throw e
        } catch (e: SpotifyImportException) {
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(SpotifyImportException("Could not reach Spotify (${e.message ?: "network error"}).", e))
        }

    /** Resolve whatever the user pasted into a concrete 22-character playlist id. */
    private suspend fun resolvePlaylistId(input: String): String {
        val trimmed = input.trim()
        SpotifyPlaylistUrl.extractPlaylistId(trimmed)?.let { return it }
        if (SpotifyPlaylistUrl.isShortLink(trimmed)) {
            val resolved =
                withTimeout(REDIRECT_TIMEOUT_MS) {
                    spotifyClient.resolveRedirect(trimmed)
                }
            SpotifyPlaylistUrl.extractPlaylistId(resolved.call.request.url.toString())?.let { return it }
            throw SpotifyImportException("That Spotify short link does not point to a playlist.")
        }
        throw SpotifyImportException("That does not look like a Spotify playlist link.")
    }

    suspend fun getPersonalToken(spdc: String) =
        runCatching {
            spotifyClient.getSpotifyLyricsToken(spdc).body<PersonalTokenResponse>()
        }

    /**
     * Get personal token using the more reliable TOTP-based method
     * This should be used when the standard method fails
     */
    suspend fun getPersonalTokenWithTotp(spdc: String) = spotifyAuth.refreshToken(spdc)

    suspend fun getClientToken() =
        runCatching {
            spotifyClient
                .getSpotifyClientToken()
                .body<ClientTokenResponse>()
        }

    suspend fun searchSpotifyTrack(
        query: String,
        authToken: String,
        clientToken: String,
    ) = runCatching {
        spotifyClient
            .searchSpotifyTrack(query, authToken, clientToken)
            .body<SpotifySearchResponse>()
    }

    suspend fun getSpotifyLyrics(
        trackId: String,
        token: String,
        clientToken: String,
    ) = runCatching {
        spotifyClient
            .getSpotifyLyrics(
                token = token,
                clientToken = clientToken,
                trackId,
            ).body<SpotifyLyricsResponse>()
    }

    suspend fun getSpotifyCanvas(
        trackId: String,
        token: String,
        clientToken: String,
    ) = runCatching {
        spotifyClient.getSpotifyCanvas(trackId, token, clientToken).body<CanvasResponse>()
    }
}