package com.decibel.music.domain.data.model.metadata

import kotlinx.serialization.Serializable

@Serializable
data class Lyrics(
    val error: Boolean = false,
    val lines: List<Line>?,
    val syncType: String?,
    val decibelLyrics: DecibelLyrics? = null,
)

@Serializable
data class DecibelLyrics(
    val id: String,
    val vote: Int,
)