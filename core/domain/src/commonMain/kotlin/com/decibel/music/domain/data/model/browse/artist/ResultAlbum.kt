package com.decibel.music.domain.data.model.browse.artist

import com.decibel.music.domain.data.model.searchResult.songs.Thumbnail
import com.decibel.music.domain.data.type.HomeContentType

data class ResultAlbum(
    val browseId: String,
    val isExplicit: Boolean,
    val thumbnails: List<Thumbnail>,
    val title: String,
    val year: String,
) : HomeContentType