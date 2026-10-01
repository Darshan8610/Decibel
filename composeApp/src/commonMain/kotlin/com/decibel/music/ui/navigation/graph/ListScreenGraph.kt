package com.decibel.music.ui.navigation.graph

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.decibel.music.ui.navigation.destination.list.AlbumDestination
import com.decibel.music.ui.navigation.destination.list.ArtistDestination
import com.decibel.music.ui.navigation.destination.list.LocalPlaylistDestination
import com.decibel.music.ui.navigation.destination.list.MoreAlbumsDestination
import com.decibel.music.ui.navigation.destination.list.PlaylistDestination
import com.decibel.music.ui.navigation.destination.list.PodcastDestination
import com.decibel.music.ui.screen.library.LocalPlaylistScreen
import com.decibel.music.ui.screen.other.MoreAlbumsScreen
import com.decibel.music.ui.screen.other.PodcastScreen
import com.decibel.music.ui.skin.LocalSkinProvider
import com.decibel.music.ui.theme.ForceDarkContent

@ExperimentalMaterial3Api
@ExperimentalFoundationApi
fun NavGraphBuilder.listScreenGraph(
    innerPadding: PaddingValues,
    navController: NavController,
) {
    composable<AlbumDestination> { entry ->
        val data = entry.toRoute<AlbumDestination>()
        LocalSkinProvider.current.AlbumDetailScreen(
            browseId = data.browseId,
            navController = navController,
        )
    }
    composable<ArtistDestination> { entry ->
        val data = entry.toRoute<ArtistDestination>()
        LocalSkinProvider.current.ArtistDetailScreen(
            channelId = data.channelId,
            navController = navController,
        )
    }
    composable<LocalPlaylistDestination> { entry ->
        val data = entry.toRoute<LocalPlaylistDestination>()
        ForceDarkContent {
            LocalPlaylistScreen(
                id = data.id,
                navController = navController,
            )
        }
    }
    composable<MoreAlbumsDestination> { entry ->
        val data = entry.toRoute<MoreAlbumsDestination>()
        MoreAlbumsScreen(
            innerPadding = innerPadding,
            navController = navController,
            type = data.type,
            id = data.id,
        )
    }
    composable<PlaylistDestination> { entry ->
        val data = entry.toRoute<PlaylistDestination>()
        LocalSkinProvider.current.PlaylistDetailScreen(
            playlistId = data.playlistId,
            isYourYouTubePlaylist = data.isYourYouTubePlaylist,
            navController = navController,
        )
    }
    composable<PodcastDestination> { entry ->
        val data = entry.toRoute<PodcastDestination>()
        ForceDarkContent {
            PodcastScreen(
                podcastId = data.podcastId,
                navController = navController,
            )
        }
    }
}
