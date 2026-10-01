package com.decibel.music.ui.skin.applemusic

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
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import com.decibel.music.domain.data.type.PlaylistType
import com.decibel.music.domain.mediaservice.handler.QueueData
import com.decibel.music.domain.utils.toTrack
import com.decibel.music.extension.isScrollingUp
import com.decibel.music.ui.component.EndOfPage
import com.decibel.music.ui.component.HomeShimmer
import com.decibel.music.ui.component.OfflineErrorState
import com.decibel.music.ui.icon.ArrowForwardIos
import com.decibel.music.ui.icon.DecibelIcons
import com.decibel.music.ui.navigation.destination.home.SettingsDestination
import com.decibel.music.ui.navigation.destination.list.AlbumDestination
import com.decibel.music.ui.navigation.destination.list.ArtistDestination
import com.decibel.music.ui.navigation.destination.list.PlaylistDestination
import com.decibel.music.ui.skin.AppleMusicPink
import com.decibel.music.ui.skin.AppleMusicSurfaceElevatedDark
import com.decibel.music.ui.skin.AppleMusicTextSecondaryDark
import com.decibel.music.ui.theme.typo
import com.decibel.music.viewModel.HomeViewModel
import com.decibel.music.viewModel.SharedViewModel
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppleMusicHomeScreen(
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
        modifier = Modifier.fillMaxSize().background(Color.Black),
    ) {
        Crossfade(targetState = loading, label = "AppleMusicHomeShimmer") { isLoading ->
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
                    contentPadding = PaddingValues(bottom = 90.dp),
                    verticalArrangement = Arrangement.spacedBy(26.dp),
                ) {
                    // 1. Apple Music iOS Large Title Header
                    item(key = "apple_header") {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp)
                                .padding(top = 44.dp),
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text(
                                    text = "Listen Now",
                                    style = typo().headlineLarge.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White,
                                        fontSize = 34.sp,
                                    ),
                                )
                                Box(
                                    modifier = Modifier
                                        .size(34.dp)
                                        .clip(CircleShape)
                                        .background(AppleMusicSurfaceElevatedDark)
                                        .clickable { navController.navigate(SettingsDestination) },
                                    contentAlignment = Alignment.Center,
                                ) {
                                    Text("D", color = AppleMusicPink, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                }
                            }
                            HorizontalDivider(
                                color = Color(0xFF2C2C2E),
                                thickness = 0.5.dp,
                                modifier = Modifier.padding(top = 12.dp),
                            )
                        }
                    }

                    // 2. Top Picks / Hero Banner (First Section)
                    val heroItem = homeData.firstOrNull()
                    if (heroItem != null && heroItem.contents.isNotEmpty()) {
                        item(key = "apple_hero") {
                            val firstContent = heroItem.contents.firstOrNull()
                            if (firstContent != null) {
                                Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                                    Text(
                                        text = "FEATURED RELEASE",
                                        style = typo().labelSmall.copy(
                                            color = AppleMusicPink,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 11.sp,
                                            letterSpacing = 0.5.sp,
                                        ),
                                    )
                                    Text(
                                        text = firstContent.title,
                                        style = typo().headlineMedium.copy(
                                            color = Color.White,
                                            fontWeight = FontWeight.SemiBold,
                                            fontSize = 22.sp,
                                        ),
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                    )
                                    val artistStr = firstContent.artists?.joinToString(", ") { it.name } ?: ""
                                    if (artistStr.isNotEmpty()) {
                                        Text(
                                            text = artistStr,
                                            style = typo().bodyMedium.copy(
                                                color = AppleMusicTextSecondaryDark,
                                                fontSize = 15.sp,
                                            ),
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(10.dp))

                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .aspectRatio(1.7f)
                                            .clip(RoundedCornerShape(12.dp))
                                            .clickable {
                                                if (firstContent.videoId != null) {
                                                    val track = firstContent.toTrack()
                                                    sharedViewModel.setQueueData(
                                                        QueueData.Data(
                                                            listTracks = arrayListOf(track),
                                                            firstPlayedTrack = track,
                                                            playlistId = "RDAMVM${firstContent.videoId}",
                                                            playlistName = firstContent.title,
                                                        )
                                                    )
                                                    sharedViewModel.loadMediaItem(track, Config.SONG_CLICK)
                                                }
                                            },
                                        shape = RoundedCornerShape(12.dp),
                                    ) {
                                        Box(modifier = Modifier.fillMaxSize()) {
                                            AsyncImage(
                                                model = firstContent.thumbnails?.lastOrNull()?.url,
                                                contentDescription = firstContent.title,
                                                contentScale = ContentScale.Crop,
                                                modifier = Modifier.fillMaxSize(),
                                            )
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .background(
                                                        Brush.verticalGradient(
                                                            listOf(Color.Transparent, Color(0xCC000000))
                                                        )
                                                    )
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // 3. Apple Music Squircle Carousels
                    itemsIndexed(
                        items = homeData,
                        key = { index, item -> item.id.ifEmpty { "${item.title}_$index" } },
                    ) { index, section ->
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 20.dp, vertical = 6.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text(
                                    text = section.title,
                                    style = typo().headlineSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White,
                                        fontSize = 20.sp,
                                    ),
                                )
                                Icon(
                                    DecibelIcons.ArrowForwardIos,
                                    contentDescription = "More",
                                    tint = AppleMusicTextSecondaryDark,
                                    modifier = Modifier.size(14.dp),
                                )
                            }

                            val contents = section.contents.filterNotNull()
                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(16.dp),
                                contentPadding = PaddingValues(horizontal = 20.dp),
                            ) {
                                items(contents, key = { it.videoId ?: it.browseId ?: it.playlistId ?: it.title }) { content ->
                                    val isArtist = content.browseId?.startsWith("UC") == true
                                    Column(
                                        modifier = Modifier
                                            .width(160.dp)
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
                                        AsyncImage(
                                            model = content.thumbnails?.lastOrNull()?.url,
                                            contentDescription = content.title,
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier
                                                .size(160.dp)
                                                .clip(if (isArtist) CircleShape else RoundedCornerShape(12.dp))
                                                .shadow(6.dp, if (isArtist) CircleShape else RoundedCornerShape(12.dp)),
                                        )

                                        Spacer(modifier = Modifier.height(8.dp))

                                        Text(
                                            text = content.title,
                                            style = typo().bodyMedium.copy(
                                                fontWeight = FontWeight.SemiBold,
                                                color = Color.White,
                                                fontSize = 14.sp,
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
                                                    color = AppleMusicTextSecondaryDark,
                                                    fontSize = 12.sp,
                                                ),
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis,
                                            )
                                        }
                                    }
                                }
                            }
                        }
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
