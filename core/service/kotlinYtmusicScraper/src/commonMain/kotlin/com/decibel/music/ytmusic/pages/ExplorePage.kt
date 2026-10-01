package com.decibel.music.ytmusic.pages

import com.decibel.music.ytmusic.models.AlbumItem
import com.decibel.music.ytmusic.models.VideoItem

data class ExplorePage(
    val released: List<AlbumItem>,
    val musicVideo: List<VideoItem>,
)