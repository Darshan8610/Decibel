package com.decibel.music.domain.data.model.mood.genre

import com.decibel.music.domain.data.model.searchResult.songs.Thumbnail
import com.decibel.music.domain.data.type.HomeContentType

data class Content(
    val playlistBrowseId: String,
    val thumbnail: List<Thumbnail>?,
    val title: Title,
) : HomeContentType