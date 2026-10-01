package com.decibel.music.ytmusic.pages

import com.decibel.music.ytmusic.models.SongItem

data class PlaylistContinuationPage(
    val songs: List<SongItem>,
    val continuation: String?,
)