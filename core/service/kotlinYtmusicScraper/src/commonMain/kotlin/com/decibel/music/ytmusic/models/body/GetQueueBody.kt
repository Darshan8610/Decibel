package com.decibel.music.ytmusic.models.body

import com.decibel.music.ytmusic.models.Context
import kotlinx.serialization.Serializable

@Serializable
data class GetQueueBody(
    val context: Context,
    val videoIds: List<String>?,
    val playlistId: String?,
)