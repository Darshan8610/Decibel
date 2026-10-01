package com.decibel.music.data.mediaservice

actual fun createMediaServiceHandler(
    dataStoreManager: com.decibel.music.domain.manager.DataStoreManager,
    songRepository: com.decibel.music.domain.repository.SongRepository,
    streamRepository: com.decibel.music.domain.repository.StreamRepository,
    localPlaylistRepository: com.decibel.music.domain.repository.LocalPlaylistRepository,
    analyticsRepository: com.decibel.music.domain.repository.AnalyticsRepository,
    coroutineScope: kotlinx.coroutines.CoroutineScope,
): com.decibel.music.domain.mediaservice.handler.MediaPlayerHandler {
    TODO("Not yet implemented")
}