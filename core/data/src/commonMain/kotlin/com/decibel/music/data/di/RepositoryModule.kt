package com.decibel.music.data.di

import com.decibel.music.common.Config.SERVICE_SCOPE
import com.decibel.music.data.io.fileDir
import com.decibel.music.data.repository.AccountRepositoryImpl
import com.decibel.music.data.repository.AlbumRepositoryImpl
import com.decibel.music.data.repository.AnalyticsRepositoryImpl
import com.decibel.music.data.repository.ArtistRepositoryImpl
import com.decibel.music.data.repository.CommonRepositoryImpl
import com.decibel.music.data.repository.HomeRepositoryImpl
import com.decibel.music.data.repository.ImportRepositoryImpl
import com.decibel.music.data.repository.LocalPlaylistRepositoryImpl
import com.decibel.music.data.repository.LyricsCanvasRepositoryImpl
import com.decibel.music.data.repository.PlaylistRepositoryImpl
import com.decibel.music.data.repository.PodcastRepositoryImpl
import com.decibel.music.data.repository.SearchRepositoryImpl
import com.decibel.music.data.repository.SongRepositoryImpl
import com.decibel.music.data.repository.StreamRepositoryImpl
import com.decibel.music.data.repository.UpdateRepositoryImpl
import com.decibel.music.domain.repository.AccountRepository
import com.decibel.music.domain.repository.AlbumRepository
import com.decibel.music.domain.repository.AnalyticsRepository
import com.decibel.music.domain.repository.ArtistRepository
import com.decibel.music.domain.repository.CommonRepository
import com.decibel.music.domain.repository.HomeRepository
import com.decibel.music.domain.repository.ImportRepository
import com.decibel.music.domain.repository.LocalPlaylistRepository
import com.decibel.music.domain.repository.LyricsCanvasRepository
import com.decibel.music.domain.repository.PlaylistRepository
import com.decibel.music.domain.repository.PodcastRepository
import com.decibel.music.domain.repository.SearchRepository
import com.decibel.music.domain.repository.SongRepository
import com.decibel.music.domain.repository.StreamRepository
import com.decibel.music.domain.repository.UpdateRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module

val repositoryModule =
    module {
        single<AccountRepository>(createdAtStart = true) {
            AccountRepositoryImpl(get(), get())
        }

        single<AlbumRepository>(createdAtStart = true) {
            AlbumRepositoryImpl(get(), get())
        }

        single<ArtistRepository>(createdAtStart = true) {
            ArtistRepositoryImpl(get(), get())
        }

        single<CommonRepository>(createdAtStart = true) {
            CommonRepositoryImpl(get(named(SERVICE_SCOPE)), get(), get(), get(), get(), get()).apply {
                this.init("${fileDir()}/ytdlp-cookie.txt", get())
            }
        }

        single<HomeRepository>(createdAtStart = true) {
            HomeRepositoryImpl(get(), get())
        }

        single<ImportRepository>(createdAtStart = true) {
            ImportRepositoryImpl(get())
        }

        single<LocalPlaylistRepository>(createdAtStart = true) {
            LocalPlaylistRepositoryImpl(get(), get())
        }

        single<LyricsCanvasRepository>(createdAtStart = true) {
            LyricsCanvasRepositoryImpl(get(), get(), get(), get(), get())
        }

        single<PlaylistRepository>(createdAtStart = true) {
            PlaylistRepositoryImpl(get(), get(), get())
        }

        single<PodcastRepository>(createdAtStart = true) {
            PodcastRepositoryImpl(get(), get())
        }

        single<SearchRepository>(createdAtStart = true) {
            SearchRepositoryImpl(get(), get())
        }

        single<SongRepository>(createdAtStart = true) {
            SongRepositoryImpl(get(), get(), get())
        }

        single<StreamRepository>(createdAtStart = true) {
            StreamRepositoryImpl(get(), get())
        }

        single<UpdateRepository>(createdAtStart = true) {
            UpdateRepositoryImpl(get())
        }

        single<AnalyticsRepository>(createdAtStart = true) {
            AnalyticsRepositoryImpl(get())
        }
    }