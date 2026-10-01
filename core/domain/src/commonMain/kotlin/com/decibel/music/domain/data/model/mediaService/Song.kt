package com.decibel.music.domain.data.model.mediaService

import com.decibel.music.domain.data.model.searchResult.songs.Album
import com.decibel.music.domain.data.model.searchResult.songs.Artist
import com.decibel.music.domain.data.model.searchResult.songs.Thumbnail

data class Song(
    val title: String?,
    val artists: List<Artist>?,
    val duration: Long,
    val lyrics: Any,
    val album: Album,
    val videoId: String,
    val thumbnail: Thumbnail?,
    val isLocal: Boolean,
)