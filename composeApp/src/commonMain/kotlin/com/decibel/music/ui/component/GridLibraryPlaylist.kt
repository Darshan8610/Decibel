package com.decibel.music.ui.component

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.MarqueeAnimationMode
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.decibel.music.domain.data.entities.AlbumEntity
import com.decibel.music.domain.data.entities.LocalPlaylistEntity
import com.decibel.music.domain.data.entities.PlaylistEntity
import com.decibel.music.domain.data.entities.PodcastsEntity
import com.decibel.music.domain.data.model.searchResult.playlists.PlaylistsResult
import com.decibel.music.domain.data.type.ChartItem
import com.decibel.music.domain.data.type.PlaylistType
import com.decibel.music.domain.utils.LocalResource
import com.decibel.music.logger.Logger
import com.decibel.music.extension.angledGradientBackground
import com.decibel.music.extension.isScrollingUp
import com.decibel.music.ui.icon.Add
import com.decibel.music.ui.icon.DecibelIcons
import com.decibel.music.ui.navigation.destination.list.AlbumDestination
import com.decibel.music.ui.navigation.destination.list.LocalPlaylistDestination
import com.decibel.music.ui.navigation.destination.list.PlaylistDestination
import com.decibel.music.ui.navigation.destination.list.PodcastDestination
import com.decibel.music.ui.theme.seed
import com.decibel.music.ui.theme.typo
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import com.decibel.music.composeapp.generated.resources.Res
import com.decibel.music.composeapp.generated.resources.create

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal inline fun <reified T> GridLibraryPlaylist(
    navController: NavController,
    contentPadding: PaddingValues,
    data: LocalResource<List<T>>,
    emptyText: StringResource,
    noinline onScrolling: (onTop: Boolean) -> Unit = { _ -> },
    noinline createNewPlaylist: (() -> Unit)? = null,
    noinline onReload: () -> Unit,
) {
    Logger.w("GridLibraryPlaylist", "Generic Type: ${T::class.simpleName}")
    val state = rememberLazyGridState()
    val isScrollingUp by state.isScrollingUp()

    LaunchedEffect(state) {
        snapshotFlow { state.firstVisibleItemIndex }
            .collect {
                if (it <= 1) {
                    onScrolling.invoke(true)
                } else {
                    onScrolling.invoke(isScrollingUp)
                }
            }
    }
    val pullToRefreshState = rememberPullToRefreshState()
    PullToRefreshBox(
        modifier = Modifier.fillMaxSize(),
        state = pullToRefreshState,
        onRefresh = onReload,
        isRefreshing = data is LocalResource.Loading,
        indicator = {
            PullToRefreshDefaults.Indicator(
                state = pullToRefreshState,
                isRefreshing = data is LocalResource.Loading,
                modifier =
                    Modifier
                        .align(Alignment.TopCenter)
                        .padding(
                            top = contentPadding.calculateTopPadding(),
                        ),
                containerColor = PullToRefreshDefaults.indicatorContainerColor,
                color = PullToRefreshDefaults.indicatorColor,
                maxDistance = PullToRefreshDefaults.PositionalThreshold,
            )
        },
    ) {
        Crossfade(targetState = data) { data ->
            val list = (data as? LocalResource.Success)?.data ?: emptyList()
            if ((data is LocalResource.Success && list.isNotEmpty()) || createNewPlaylist != null) {
                LazyVerticalGrid(
                    columns = GridCells.FixedSize(size = 132.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    contentPadding = contentPadding,
                    state = state,
                ) {
                    if (createNewPlaylist != null) {
                        item(key = "create_new_playlist") {
                            Box(
                                modifier =
                                    Modifier.clickable {
                                        createNewPlaylist()
                                    },
                            ) {
                                Column(
                                    modifier =
                                        Modifier
                                            .padding(10.dp),
                                ) {
                                    Box(
                                        Modifier
                                            .size(132.dp)
                                            .aspectRatio(1f)
                                            .clip(RoundedCornerShape(10.dp))
                                            .angledGradientBackground(
                                                colors =
                                                    listOf(
                                                        seed,
                                                        Color.White.copy(alpha = 0.8f),
                                                    ),
                                                degrees = 45f,
                                            ),
                                        Alignment.Center,
                                    ) {
                                        Icon(
                                            modifier = Modifier.size(84.dp),
                                            imageVector = DecibelIcons.Add,
                                            tint = Color.White,
                                            contentDescription = null,
                                        )
                                    }
                                    Text(
                                        text = stringResource(Res.string.create),
                                        style = typo().titleSmall,
                                        // Sits on the page background, not on the tile, so it has
                                        // to follow the theme — hard-coded white vanished in light
                                        // theme while every other tile label stayed readable.
                                        color = MaterialTheme.colorScheme.onBackground,
                                        maxLines = 1,
                                        modifier =
                                            Modifier
                                                .width(132.dp)
                                                .wrapContentHeight(align = Alignment.CenterVertically)
                                                .padding(top = 8.dp)
                                                .basicMarquee(
                                                    iterations = Int.MAX_VALUE,
                                                    animationMode = MarqueeAnimationMode.Immediately,
                                                ).focusable(),
                                    )
                                }
                            }
                        }
                    }
                    itemsIndexed(
                        items = list,
                        key = { index, item ->
                            when (item) {
                                is ChartItem -> "chart_${item.ytPlaylistId}"
                                is LocalPlaylistEntity -> "local_${item.id}"
                                is PlaylistsResult -> "playlist_${item.browseId}"
                                is AlbumEntity -> "album_${item.browseId}"
                                is PlaylistEntity -> "playlist_${item.id}"
                                is PodcastsEntity -> "podcast_${item.podcastId}"
                                else -> "playlist_item_$index"
                            }
                        },
                    ) { _, item ->
                        if (item !is PlaylistType) {
                            return@itemsIndexed
                        }
                        HomeItemContentPlaylist(
                            onClick = {
                                when (item) {
                                    is ChartItem -> {
                                        navController.navigate(
                                            PlaylistDestination(
                                                playlistId = item.ytPlaylistId,
                                                isYourYouTubePlaylist = false,
                                            ),
                                        )
                                    }

                                    is LocalPlaylistEntity -> {
                                        navController.navigate(
                                            LocalPlaylistDestination(
                                                item.id,
                                            ),
                                        )
                                    }

                                    is PlaylistsResult -> {
                                        navController.navigate(
                                            PlaylistDestination(
                                                item.browseId,
                                                isYourYouTubePlaylist = true,
                                            ),
                                        )
                                    }

                                    is AlbumEntity -> {
                                        navController.navigate(
                                            AlbumDestination(
                                                item.browseId,
                                            ),
                                        )
                                    }

                                    is PlaylistEntity -> {
                                        navController.navigate(
                                            PlaylistDestination(
                                                item.id,
                                            ),
                                        )
                                    }

                                    is PodcastsEntity -> {
                                        navController.navigate(
                                            PodcastDestination(
                                                podcastId = item.podcastId,
                                            ),
                                        )
                                    }
                                }
                            },
                            data = item,
                            thumbSize = 132.dp,
                        )
                    }



                    item(span = { GridItemSpan(maxLineSpan) }) {
                        EndOfPage()
                    }
                }
            } else if (data is LocalResource.Loading) {
                CenterLoadingBox(
                    Modifier.fillMaxSize(),
                )
            } else {
                Box(
                    Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = stringResource(emptyText),
                        style = typo().bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        }
    }
}