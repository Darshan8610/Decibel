package com.decibel.music.domain.data.model.home

import com.decibel.music.domain.data.model.searchResult.songs.Thumbnail

data class HomeItem(
    val contents: List<Content?>,
    val title: String,
    val subtitle: String? = null,
    val thumbnail: List<Thumbnail>? = null,
    val channelId: String? = null,
) {
    val id: String
        get() = channelId.orEmpty()
}