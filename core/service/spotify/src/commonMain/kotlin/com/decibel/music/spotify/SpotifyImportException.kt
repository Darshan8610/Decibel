package com.decibel.music.spotify

/**
 * Signals a Spotify import failure with a message that is safe to display to the user.
 *
 * Every layer of the import pipeline throws this instead of low-level exceptions so the
 * UI never has to surface raw implementation details (NullPointerException, parse errors, …).
 */
class SpotifyImportException(
    message: String,
    cause: Throwable? = null,
) : RuntimeException(message, cause)
