package com.decibel.music.ytmusic.pages

import com.decibel.music.ytmusic.models.YTItem

data class BrowseResult(
    val title: String?,
    val items: List<Item>,
) {
    data class Item(
        val title: String?,
        val items: List<YTItem>,
    )
}