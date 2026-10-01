package com.decibel.music.ytmusic.models.body

import com.decibel.music.ytmusic.models.Context
import kotlinx.serialization.Serializable

@Serializable
data class SearchBody(
    val context: Context,
    val query: String?,
    val params: String?,
)