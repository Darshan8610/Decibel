package com.decibel.music.data.mediaservice

import com.decibel.music.domain.manager.DataStoreManager
import com.decibel.music.domain.mediaservice.handler.MediaPlayerHandler
import com.decibel.music.domain.repository.AnalyticsRepository
import com.decibel.music.domain.repository.LocalPlaylistRepository
import com.decibel.music.domain.repository.SongRepository
import com.decibel.music.domain.repository.StreamRepository
import kotlinx.coroutines.CoroutineScope

expect fun createMediaServiceHandler(
    dataStoreManager: DataStoreManager,
    songRepository: SongRepository,
    streamRepository: StreamRepository,
    localPlaylistRepository: LocalPlaylistRepository,
    analyticsRepository: AnalyticsRepository,
    coroutineScope: CoroutineScope,
): MediaPlayerHandler