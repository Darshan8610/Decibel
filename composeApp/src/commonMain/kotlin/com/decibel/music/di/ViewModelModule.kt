package com.decibel.music.di

import com.decibel.music.viewModel.AlbumViewModel
import com.decibel.music.viewModel.AnalyticsViewModel
import com.decibel.music.viewModel.ArtistViewModel
import com.decibel.music.viewModel.HomeViewModel
import com.decibel.music.viewModel.ImportViewModel
import com.decibel.music.viewModel.LibraryDynamicPlaylistViewModel
import com.decibel.music.viewModel.LibraryViewModel
import com.decibel.music.viewModel.LocalPlaylistViewModel
import com.decibel.music.viewModel.LogInViewModel
import com.decibel.music.viewModel.MoodViewModel
import com.decibel.music.viewModel.MoreAlbumsViewModel
import com.decibel.music.viewModel.NotificationViewModel
import com.decibel.music.viewModel.NowPlayingBottomSheetViewModel
import com.decibel.music.viewModel.PlaylistViewModel
import com.decibel.music.viewModel.PodcastViewModel
import com.decibel.music.viewModel.RecentlySongsViewModel
import com.decibel.music.viewModel.SearchViewModel
import com.decibel.music.viewModel.SettingsViewModel
import com.decibel.music.viewModel.SharedViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule =
    module {
        single {
            SharedViewModel(
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
            )
        }
        single {
            SearchViewModel(
                get(),
                get(),
                get(),
            )
        }
        viewModel {
            NowPlayingBottomSheetViewModel(
                get(),
                get(),
                get(),
                get(),
            )
        }
        viewModel {
            LibraryViewModel(
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get<com.decibel.music.domain.repository.SearchRepository>(),
                get<com.decibel.music.spotify.Spotify>(),
            )
        }
        viewModel {
            LibraryDynamicPlaylistViewModel(
                get(),
                get(),
            )
        }
        viewModel {
            ImportViewModel(
                get(),
            )
        }
        viewModel {
            AlbumViewModel(
                get(),
                get(),
            )
        }
        viewModel {
            HomeViewModel(
                get(),
                get(),
            )
        }
        viewModel {
            SettingsViewModel(
                get(),
                get(),
                get(),
                get(),
                get(),
            )
        }
        viewModel {
            ArtistViewModel(
                get(),
                get(),
                get(),
            )
        }
        viewModel {
            PlaylistViewModel(
                get(),
                get(),
                get(),
            )
        }
        viewModel {
            LogInViewModel(
                get(),
            )
        }
        viewModel {
            PodcastViewModel(
                get(),
            )
        }
        viewModel {
            MoreAlbumsViewModel(
                get(),
            )
        }
        viewModel {
            RecentlySongsViewModel(
                get(),
            )
        }
        viewModel {
            LocalPlaylistViewModel(
                get(),
                get(),
                get(),
            )
        }
        viewModel {
            NotificationViewModel(
                get(),
            )
        }
        viewModel {
            MoodViewModel(
                get(),
                get(),
            )
        }
        viewModel {
            AnalyticsViewModel(
                get(),
                get(),
                get(),
                get(),
                get(),
            )
        }
    }