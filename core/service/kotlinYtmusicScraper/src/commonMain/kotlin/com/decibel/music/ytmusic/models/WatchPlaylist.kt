package com.decibel.music.ytmusic.models

data class WatchPlaylist(
    val title: String?,
    val playlistId: String?,
    val thumbnails: List<Thumbnail>?,
)