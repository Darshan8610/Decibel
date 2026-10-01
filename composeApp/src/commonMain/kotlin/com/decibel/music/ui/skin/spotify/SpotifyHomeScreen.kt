package com.decibel.music.ui.skin.spotify

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.decibel.music.common.Config
import com.decibel.music.domain.data.model.home.HomeItem
import com.decibel.music.domain.extension.now
import com.decibel.music.domain.mediaservice.handler.QueueData
import com.decibel.music.domain.utils.toTrack
import com.decibel.music.composeapp.generated.resources.Res
import com.decibel.music.composeapp.generated.resources.good_afternoon
import com.decibel.music.composeapp.generated.resources.good_evening
import com.decibel.music.composeapp.generated.resources.good_morning
import com.decibel.music.composeapp.generated.resources.good_night
import com.decibel.music.extension.isScrollingUp
import com.decibel.music.ui.component.EndOfPage
import com.decibel.music.ui.component.HomeShimmer
import com.decibel.music.ui.component.OfflineErrorState
import com.decibel.music.ui.icon.History
import com.decibel.music.ui.icon.Notifications
import com.decibel.music.ui.icon.Settings
import com.decibel.music.ui.icon.DecibelIcons
import com.decibel.music.ui.navigation.destination.home.NotificationDestination
import com.decibel.music.ui.navigation.destination.home.RecentlySongsDestination
import com.decibel.music.ui.navigation.destination.home.SettingsDestination
import com.decibel.music.ui.navigation.destination.list.AlbumDestination
import com.decibel.music.ui.navigation.destination.list.ArtistDestination
import com.decibel.music.ui.navigation.destination.list.PlaylistDestination
import com.decibel.music.ui.skin.SpotifyCard
import com.decibel.music.ui.skin.SpotifyDark
import com.decibel.music.ui.skin.SpotifyFilterChipsRow
import com.decibel.music.ui.skin.SpotifyTextSecondary
import com.decibel.music.ui.theme.typo
import com.decibel.music.viewModel.HomeViewModel
import com.decibel.music.viewModel.SharedViewModel
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpotifyHomeScreen(
    viewModel: HomeViewModel = koinViewModel(),
    sharedViewModel: SharedViewModel = koinInject(),
    navController: NavController,
    onScrolling: (onTop: Boolean) -> Unit = {},
) {
    val scrollState = rememberLazyListState()
    val isScrollingUp by scrollState.isScrollingUp()
    val homeData by viewModel.homeItemList.collectAsStateWithLifecycle()
    val loading by viewModel.loading.collectAsStateWithLifecycle()
    val params by viewModel.params.collectAsStateWithLifecycle()
    val pullToRefreshState = rememberPullToRefreshState()
    var isRefreshing by remember { mutableStateOf(false) }

    LaunchedEffect(scrollState) {
        snapshotFlow { scrollState.firstVisibleItemIndex }
            .collect {
                if (it <= 1) {
                    onScrolling(true)
                } else {
                    onScrolling(isScrollingUp)
                }
            }
    }

    PullToRefreshBox(
        state = pullToRefreshState,
        isRefreshing = isRefreshing,
        onRefresh = {
            isRefreshing = true
            viewModel.getHomeItemList(params)
            isRefreshing = false
        },
        modifier = Modifier.fillMaxSize().background(SpotifyDark),
    ) {
        Crossfade(targetState = loading, label = "SpotifyHomeShimmer") { isLoading ->
            if (!isLoading) {
                if (homeData.isEmpty()) {
                    OfflineErrorState(
                        onRetry = { viewModel.getHomeItemList(params) },
                        onOpenDownloaded = {},
                    )
                    return@Crossfade
                }

                LazyColumn(
                    state = scrollState,
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    contentPadding = PaddingValues(bottom = 90.dp),
                ) {
                    // 1. Spotify Top Bar & Greeting
                    item(key = "spotify_header") {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    Brush.verticalGradient(
                                        listOf(Color(0xFF2E3D30), SpotifyDark)
                                    )
                                )
                                .padding(horizontal = 16.dp)
                                .padding(top = 40.dp, bottom = 12.dp)
                        ) {
                            // Top action icons
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                val currentHour = now().hour
                                val greeting = when (currentHour) {
                                    in 5..11 -> stringResource(Res.string.good_morning)
                                    in 12..16 -> stringResource(Res.string.good_afternoon)
                                    in 17..21 -> stringResource(Res.string.good_evening)
                                    else -> stringResource(Res.string.good_night)
                                }
                                Text(
                                    text = greeting,
                                    style = typo().headlineMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White,
                                        fontSize = 24.sp,
                                    ),
                                )
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    IconButton(onClick = { navController.navigate(NotificationDestination) }) {
                                        Icon(DecibelIcons.Notifications, contentDescription = "Notifications", tint = Color.White, modifier = Modifier.size(24.dp))
                                    }
                                    IconButton(onClick = { navController.navigate(RecentlySongsDestination) }) {
                                        Icon(DecibelIcons.History, contentDescription = "History", tint = Color.White, modifier = Modifier.size(24.dp))
                                    }
                                    IconButton(onClick = { navController.navigate(SettingsDestination) }) {
                                        Icon(DecibelIcons.Settings, contentDescription = "Settings", tint = Color.White, modifier = Modifier.size(24.dp))
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))
                            // Spotify Filter Chips
                            SpotifyFilterChipsRow()

                            Spacer(modifier = Modifier.height(16.dp))

                            // 2. Spotify 2-Column Quick Access Grid (Top 6 Items)
                            val quickPicks = homeData.firstOrNull()?.contents?.filterNotNull()?.take(6) ?: emptyList()
                            if (quickPicks.isNotEmpty()) {
                                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                    quickPicks.chunked(2).forEach { rowPair ->
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                                        ) {
                                            rowPair.forEach { item ->
                                                SpotifyQuickAccessCard(
                                                    title = item.title,
                                                    thumbnailUrl = item.thumbnails?.lastOrNull()?.url,
                                                    modifier = Modifier.weight(1f),
                                                    onClick = {
                                                        val track = item.toTrack()
                                                        sharedViewModel.setQueueData(
                                                            QueueData.Data(
                                                                listTracks = arrayListOf(track),
                                                                firstPlayedTrack = track,
                                                                playlistId = "RDAMVM${item.videoId}",
                                                                playlistName = item.title,
                                                            )
                                                        )
                                                        sharedViewModel.loadMediaItem(track, Config.SONG_CLICK)
                                                    }
                                                )
                                            }
                                            if (rowPair.size == 1) {
                                                Spacer(modifier = Modifier.weight(1f))
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // 3. Spotify Content Carousels
                    itemsIndexed(
                        items = homeData,
                        key = { index, item -> item.id.ifEmpty { "${item.title}_$index" } },
                    ) { index, item ->
                        if (index == 0) return@itemsIndexed // Skip first as it was used in quick access
                        SpotifySectionCarousel(
                            section = item,
                            navController = navController,
                            sharedViewModel = sharedViewModel,
                        )
                    }

                    item(key = "end_of_page") {
                        EndOfPage()
                    }
                }
            } else {
                HomeShimmer()
            }
        }
    }
}

@Composable
fun SpotifyQuickAccessCard(
    title: String,
    thumbnailUrl: String?,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Card(
        modifier = modifier
            .height(56.dp)
            .clip(RoundedCornerShape(4.dp))
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(4.dp),
        colors = CardDefaults.cardColors(containerColor = SpotifyCard),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                model = thumbnailUrl,
                contentDescription = title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(56.dp),
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = title,
                style = typo().bodyMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 12.sp,
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f).padding(end = 8.dp),
            )
        }
    }
}

@Composable
fun SpotifySectionCarousel(
    section: HomeItem,
    navController: NavController,
    sharedViewModel: SharedViewModel,
) {
    Column(modifier = Modifier.fillMaxWidth().padding(top = 4.dp)) {
        Text(
            text = section.title,
            style = typo().headlineSmall.copy(
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 20.sp,
            ),
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
        )

        val contents = section.contents.filterNotNull()
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
        ) {
            items(
                items = contents,
                key = { it.videoId ?: it.browseId ?: it.playlistId ?: it.title },
            ) { content ->
                val isArtist = content.browseId?.startsWith("UC") == true
                Column(
                    modifier = Modifier
                        .width(if (isArtist) 120.dp else 148.dp)
                        .clickable {
                            if (content.videoId != null) {
                                val track = content.toTrack()
                                sharedViewModel.setQueueData(
                                    QueueData.Data(
                                        listTracks = arrayListOf(track),
                                        firstPlayedTrack = track,
                                        playlistId = "RDAMVM${content.videoId}",
                                        playlistName = content.title,
                                    )
                                )
                                sharedViewModel.loadMediaItem(track, Config.SONG_CLICK)
                            } else if (content.playlistId != null) {
                                navController.navigate(PlaylistDestination(playlistId = content.playlistId!!))
                            } else if (content.browseId != null) {
                                if (isArtist) {
                                    navController.navigate(ArtistDestination(channelId = content.browseId!!))
                                } else {
                                    navController.navigate(AlbumDestination(browseId = content.browseId!!))
                                }
                            }
                        },
                ) {
                    Box {
                        AsyncImage(
                            model = content.thumbnails?.lastOrNull()?.url,
                            contentDescription = content.title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(if (isArtist) 120.dp else 148.dp)
                                .clip(if (isArtist) CircleShape else RoundedCornerShape(8.dp))
                                .shadow(if (isArtist) 0.dp else 4.dp, RoundedCornerShape(8.dp)),
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = content.title,
                        style = typo().bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White,
                            fontSize = 13.sp,
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.basicMarquee(),
                    )

                    val subtitle = content.artists?.joinToString(", ") { it.name } ?: content.description ?: ""
                    if (subtitle.isNotEmpty()) {
                        Text(
                            text = subtitle,
                            style = typo().bodySmall.copy(
                                color = SpotifyTextSecondary,
                                fontSize = 11.sp,
                            ),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }
            }
        }
    }
}
