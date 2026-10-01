package com.decibel.music.domain.data.model.home.chart

import com.decibel.music.domain.data.model.browse.artist.ResultPlaylist

data class ChartItemPlaylist(
    val title: String,
    val playlists: List<ResultPlaylist>,
)