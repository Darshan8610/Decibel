package com.decibel.music.data.mediaservice

import com.decibel.music.domain.repository.AnalyticsRepository

actual fun createMediaServiceHandler(
    dataStoreManager: com.decibel.music.domain.manager.DataStoreManager,
    songRepository: com.decibel.music.domain.repository.SongRepository,
    streamRepository: com.decibel.music.domain.repository.StreamRepository,
    localPlaylistRepository: com.decibel.music.domain.repository.LocalPlaylistRepository,
    analyticsRepository: AnalyticsRepository,
    coroutineScope: kotlinx.coroutines.CoroutineScope,
): com.decibel.music.domain.mediaservice.handler.MediaPlayerHandler =
    JvmMediaPlayerHandlerImpl(
        dataStoreManager = dataStoreManager,
        songRepository = songRepository,
        streamRepository = streamRepository,
        localPlaylistRepository = localPlaylistRepository,
        analyticsRepository = analyticsRepository,
        coroutineScope = coroutineScope,
    )