package com.decibel.music.ytmusic.models.body

import com.decibel.music.ytmusic.models.Context
import kotlinx.serialization.Serializable

@Serializable
data class GetSearchSuggestionsBody(
    val context: Context,
    val input: String,
)