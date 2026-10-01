package com.decibel.music.domain.data.model.browse.artist

import com.decibel.music.domain.data.model.searchResult.songs.Thumbnail
import com.decibel.music.domain.data.type.HomeContentType

data class ResultSingle(
    val browseId: String,
    val thumbnails: List<Thumbnail>,
    val title: String,
    val year: String,
) : HomeContentType