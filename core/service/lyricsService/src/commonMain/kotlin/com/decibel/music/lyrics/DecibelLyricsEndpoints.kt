package com.decibel.music.lyrics

import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@OptIn(ExperimentalEncodingApi::class)
internal object DecibelLyricsEndpoints {
    // Obfuscated endpoints and internal keys to prevent direct repository/source scraping
    private val BASE_URL_B64 = "aHR0cHM6Ly9hcGktbHlyaWNzLnNpbXBtdXNpYy5vcmcvdjEv"
    private val BASE_URL_ALT_B64 = "aHR0cHM6Ly9hcGktbHlyaWNzLnNpbXBtdXNpYy5vcmcvdjE="
    private val HMAC_KEY_B64 = "c2ltcG11c2ljLWx5cmljcw=="

    val BASE_URL: String by lazy {
        Base64.decode(BASE_URL_B64).decodeToString()
    }

    val BASE_URL_ALT: String by lazy {
        Base64.decode(BASE_URL_ALT_B64).decodeToString()
    }

    val HMAC_KEY: String by lazy {
        Base64.decode(HMAC_KEY_B64).decodeToString()
    }
}
