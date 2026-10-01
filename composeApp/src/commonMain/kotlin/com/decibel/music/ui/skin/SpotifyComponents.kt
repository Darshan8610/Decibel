package com.decibel.music.ui.skin

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import coil3.compose.AsyncImage
import com.decibel.music.domain.mediaservice.handler.RepeatState
import com.decibel.music.extension.formatDuration
import com.decibel.music.expect.ui.PlatformBackdrop
import com.decibel.music.expect.openUrl
import com.decibel.music.expect.ui.PlatformCastButton
import com.decibel.music.ui.component.NowPlayingBottomSheet
import com.decibel.music.ui.component.QueueBottomSheet
import com.decibel.music.ui.component.liquidGlass
import com.decibel.music.ui.theme.LocalIsDarkTheme
import com.decibel.music.ui.icon.Add
import com.decibel.music.ui.icon.Check
import com.decibel.music.ui.icon.Favorite
import com.decibel.music.ui.icon.FavoriteBorder
import com.decibel.music.ui.icon.Home
import com.decibel.music.ui.icon.KeyboardArrowDown
import com.decibel.music.ui.icon.LibraryMusic
import com.decibel.music.ui.icon.MoreVert
import com.decibel.music.ui.icon.Pause
import com.decibel.music.ui.icon.PlayArrow
import com.decibel.music.ui.icon.QueueMusic
import com.decibel.music.ui.icon.Repeat
import com.decibel.music.ui.icon.RepeatOne
import com.decibel.music.ui.icon.Search
import com.decibel.music.ui.icon.Share
import com.decibel.music.ui.icon.Shuffle
import com.decibel.music.ui.icon.DecibelIcons
import com.decibel.music.ui.icon.SkipNext
import com.decibel.music.ui.icon.SkipPrevious
import com.decibel.music.ui.icon.VolumeUp
import com.decibel.music.ui.navigation.destination.home.HomeDestination
import com.decibel.music.ui.navigation.destination.library.LibraryDestination
import com.decibel.music.ui.navigation.destination.search.SearchDestination
import com.decibel.music.ui.theme.typo
import com.decibel.music.viewModel.SharedViewModel
import com.decibel.music.viewModel.UIEvent
import org.koin.compose.koinInject
import kotlin.reflect.KClass

// ==========================================
// 1. SPOTIFY BOTTOM NAVIGATION BAR (3 TABS)
// ==========================================
sealed class SpotifyNavTab(
    val title: String,
    val destination: Any,
    val routeClass: KClass<*>,
) {
    object Home : SpotifyNavTab("Home", HomeDestination, HomeDestination::class)
    object Search : SpotifyNavTab("Search", SearchDestination, SearchDestination::class)
    object Library : SpotifyNavTab("Your Library", LibraryDestination, LibraryDestination::class)
}

@Composable
fun SpotifyBottomNavigationBar(
    navController: NavController,
    reloadDestinationIfNeeded: (KClass<*>) -> Unit = {},
    backdrop: PlatformBackdrop? = null,
) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val tabs = listOf(SpotifyNavTab.Home, SpotifyNavTab.Search, SpotifyNavTab.Library)

    NavigationBar(
        windowInsets = WindowInsets(0, 0, 0, 0),
        containerColor = if (backdrop != null) Color.Transparent else Color(0xE6121212),
        tonalElevation = 0.dp,
        modifier = Modifier
            .height(72.dp)
            .then(backdrop?.let { Modifier.liquidGlass(it, RoundedCornerShape(18.dp)) } ?: Modifier),
    ) {
        tabs.forEach { tab ->
            val isSelected = remember(currentBackStackEntry, tab) {
                currentBackStackEntry?.destination?.hierarchy?.any { dest ->
                    runCatching { dest.hasRoute(tab.routeClass) }.getOrDefault(false)
                } == true
            }

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    if (isSelected) {
                        reloadDestinationIfNeeded(tab.routeClass)
                    } else {
                        navController.navigate(tab.destination) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    when (tab) {
                        SpotifyNavTab.Home -> Icon(DecibelIcons.Home, contentDescription = "Home", modifier = Modifier.size(24.dp))
                        SpotifyNavTab.Search -> Icon(DecibelIcons.Search, contentDescription = "Search", modifier = Modifier.size(24.dp))
                        SpotifyNavTab.Library -> Icon(DecibelIcons.LibraryMusic, contentDescription = "Library", modifier = Modifier.size(24.dp))
                    }
                },
                label = {
                    Text(
                        tab.title,
                        style = typo().bodySmall.copy(
                            fontSize = 11.sp,
                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                        ),
                        maxLines = 1,
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent,
                    selectedIconColor = Color.White,
                    unselectedIconColor = SpotifyTextSecondary,
                    selectedTextColor = Color.White,
                    unselectedTextColor = SpotifyTextSecondary,
                ),
            )
        }
    }
}

// ==========================================
// 2. SPOTIFY MINI PLAYER
// ==========================================
@Composable
fun SpotifyMiniPlayer(
    modifier: Modifier = Modifier,
    backdrop: PlatformBackdrop? = null,
    viewModel: SharedViewModel = koinInject(),
    onClick: () -> Unit,
) {
    val nowPlayingData by viewModel.nowPlayingState.collectAsStateWithLifecycle()
    val controllerState by viewModel.controllerState.collectAsStateWithLifecycle()
    val timelineState by viewModel.timeline.collectAsStateWithLifecycle()

    val songEntity = nowPlayingData?.songEntity ?: return
    val isPlaying = controllerState.isPlaying
    val isLiked = controllerState.isLiked
    val progress = timelineState.current
    val duration = timelineState.total

    var dragOffset by remember { mutableFloatStateOf(0f) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .then(backdrop?.let { Modifier.liquidGlass(it, RoundedCornerShape(8.dp)) } ?: Modifier.clip(RoundedCornerShape(8.dp)))
            .pointerInput(Unit) {
                detectHorizontalDragGestures(
                    onDragEnd = {
                        if (dragOffset > 100f) {
                            viewModel.onUIEvent(UIEvent.Previous)
                        } else if (dragOffset < -100f) {
                            viewModel.onUIEvent(UIEvent.Next)
                        }
                        dragOffset = 0f
                    },
                    onHorizontalDrag = { _, dragAmount ->
                        dragOffset += dragAmount
                    }
                )
            }
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = if (backdrop != null) Color.Transparent else SpotifyCard),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // Square Artwork (Spotify Style: 4.dp rounded corners)
                AsyncImage(
                    model = songEntity.thumbnails,
                    contentDescription = songEntity.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(4.dp)),
                )

                Spacer(modifier = Modifier.width(8.dp))

                // Track Title & Artist
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = songEntity.title,
                        style = typo().bodyMedium.copy(
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 13.sp,
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.basicMarquee(),
                    )
                    Text(
                        text = songEntity.artistName?.joinToString(", ") ?: "",
                        style = typo().bodySmall.copy(
                            color = SpotifyTextSecondary,
                            fontSize = 11.sp,
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }

                // Devices icon (Spotify uses speaker/devices, opens connect picker)
                PlatformCastButton(modifier = Modifier.size(32.dp), tint = SpotifyTextSecondary)

                // Play / Pause button
                IconButton(
                    onClick = { viewModel.onUIEvent(UIEvent.PlayPause) },
                    modifier = Modifier.size(40.dp),
                ) {
                    Icon(
                        imageVector = if (isPlaying) DecibelIcons.Pause else DecibelIcons.PlayArrow,
                        contentDescription = if (isPlaying) "Pause" else "Play",
                        tint = Color.White,
                        modifier = Modifier.size(30.dp),
                    )
                }
            }

            // Full-width white-ish progress line at very bottom (real Spotify: white on dark card)
            val progressFraction = if (duration > 0) (progress.toFloat() / duration.toFloat()).coerceIn(0f, 1f) else 0f
            LinearProgressIndicator(
                progress = { progressFraction },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.5.dp),
                color = Color(0xFFFFFFFF),
                trackColor = Color(0x4DFFFFFF),
                strokeCap = StrokeCap.Butt,
                drawStopIndicator = {},
            )
        }
    }
}

// ==========================================
// 3. SPOTIFY NOW PLAYING SCREEN
// ==========================================
@Composable
fun SpotifyNowPlayingScreen(
    navController: NavController,
    viewModel: SharedViewModel = koinInject(),
    onDismiss: () -> Unit,
) {
    val nowPlayingData by viewModel.nowPlayingState.collectAsStateWithLifecycle()
    val nowPlayingScreenData by viewModel.nowPlayingScreenData.collectAsStateWithLifecycle()
    val controllerState by viewModel.controllerState.collectAsStateWithLifecycle()
    val timelineState by viewModel.timeline.collectAsStateWithLifecycle()

    val songEntity = nowPlayingData?.songEntity ?: return
    val isPlaying = controllerState.isPlaying
    val isLiked = controllerState.isLiked
    val isShuffle = controllerState.isShuffle
    val repeatMode = controllerState.repeatState
    val progress = timelineState.current
    val duration = timelineState.total

    var isSeeking by remember { mutableStateOf(false) }
    var seekPosition by remember { mutableFloatStateOf(0f) }
    var showQueue by remember { mutableStateOf(false) }
    var showMore by remember { mutableStateOf(false) }

    if (showQueue) {
        QueueBottomSheet(onDismiss = { showQueue = false }, sharedViewModel = viewModel)
    }
    if (showMore) {
        NowPlayingBottomSheet(
            onDismiss = { showMore = false },
            navController = navController,
            song = songEntity,
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFF3D2C2C),
                        Color(0xFF262626),
                        SpotifyDark,
                        Color.Black,
                    )
                )
            )
            .padding(horizontal = 24.dp)
            .padding(top = 48.dp, bottom = 32.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // Top Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(onClick = onDismiss) {
                    Icon(
                        DecibelIcons.KeyboardArrowDown,
                        contentDescription = "Dismiss",
                        tint = Color.White,
                        modifier = Modifier.size(28.dp),
                    )
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        "PLAYING FROM PLAYLIST",
                        style = typo().bodySmall.copy(
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = SpotifyTextSecondary,
                            letterSpacing = 1.sp,
                        ),
                    )
                    Text(
                        nowPlayingScreenData.playlistName.ifEmpty { "Decibel" },
                        style = typo().bodyMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontSize = 12.sp,
                        ),
                        maxLines = 1,
                    )
                }
                IconButton(onClick = { showMore = true }) {
                    Icon(
                        DecibelIcons.MoreVert,
                        contentDescription = "More",
                        tint = Color.White,
                    )
                }
            }

            Spacer(modifier = Modifier.weight(0.4f))

            // Large Square Artwork (Spotify Spec: Square with rounded corners)
            AsyncImage(
                model = songEntity.thumbnails,
                contentDescription = songEntity.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(8.dp))
                    .shadow(16.dp, RoundedCornerShape(8.dp)),
            )

            Spacer(modifier = Modifier.weight(0.4f))

            // Title, Artist and Plus Icon Row (real Spotify uses + circle, not heart)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = songEntity.title,
                        style = typo().titleLarge.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp,
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.basicMarquee(),
                    )
                    Text(
                        text = songEntity.artistName?.joinToString(", ") ?: "",
                        style = typo().bodyMedium.copy(
                            color = SpotifyTextSecondary,
                            fontSize = 15.sp,
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }

                IconButton(onClick = { viewModel.onUIEvent(UIEvent.ToggleLike) }) {
                    Icon(
                        imageVector = if (isLiked) DecibelIcons.Check else DecibelIcons.Add,
                        contentDescription = if (isLiked) "Added" else "Add",
                        tint = if (isLiked) SpotifyGreenBright else SpotifyTextSecondary,
                        modifier = Modifier.size(26.dp),
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Slim seekbar (real Spotify: thin white bar with white knob only while scrubbing)
            val sliderValue = if (isSeeking) seekPosition else if (duration > 0) (progress.toFloat() / duration.toFloat()) else 0f
            Slider(
                value = sliderValue.coerceIn(0f, 1f),
                onValueChange = {
                    isSeeking = true
                    seekPosition = it
                },
                onValueChangeFinished = {
                    isSeeking = false
                    viewModel.onUIEvent(UIEvent.UpdateProgress(seekPosition))
                },
                colors = SliderDefaults.colors(
                    thumbColor = Color.White,
                    activeTrackColor = Color.White,
                    inactiveTrackColor = Color(0xFF535353),
                ),
                track = { sliderState ->
                    SliderDefaults.Track(
                        sliderState = sliderState,
                        modifier = Modifier.height(4.dp),
                        colors = SliderDefaults.colors(
                            activeTrackColor = Color.White,
                            inactiveTrackColor = Color(0xFF535353),
                        ),
                        drawStopIndicator = null,
                        thumbTrackGapSize = 0.dp,
                    )
                },
                thumb = {
                    if (isSeeking) {
                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .clip(CircleShape)
                                .background(Color.White)
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                val currentPosition = if (isSeeking) (seekPosition * duration).toLong() else progress
                Text(
                    text = formatDuration(currentPosition),
                    style = typo().bodySmall.copy(color = SpotifyTextSecondary, fontSize = 11.sp),
                )
                Text(
                    text = formatDuration(duration),
                    style = typo().bodySmall.copy(color = SpotifyTextSecondary, fontSize = 11.sp),
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Transport Controls: Shuffle, Prev, Giant Play/Pause, Next, Repeat
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(onClick = { viewModel.onUIEvent(UIEvent.Shuffle) }) {
                    Icon(
                        DecibelIcons.Shuffle,
                        contentDescription = "Shuffle",
                        tint = if (isShuffle) SpotifyGreenBright else SpotifyTextSecondary,
                        modifier = Modifier.size(24.dp),
                    )
                }

                IconButton(onClick = { viewModel.onUIEvent(UIEvent.Previous) }) {
                    Icon(
                        DecibelIcons.SkipPrevious,
                        contentDescription = "Previous",
                        tint = Color.White,
                        modifier = Modifier.size(34.dp),
                    )
                }

                // Giant Circular Play/Pause Button (Spotify signature)
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .clickable {
                            viewModel.onUIEvent(UIEvent.PlayPause)
                        },
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = if (isPlaying) DecibelIcons.Pause else DecibelIcons.PlayArrow,
                        contentDescription = if (isPlaying) "Pause" else "Play",
                        tint = Color.Black,
                        modifier = Modifier.size(32.dp),
                    )
                }

                IconButton(onClick = { viewModel.onUIEvent(UIEvent.Next) }) {
                    Icon(
                        DecibelIcons.SkipNext,
                        contentDescription = "Next",
                        tint = Color.White,
                        modifier = Modifier.size(34.dp),
                    )
                }

                IconButton(onClick = { viewModel.onUIEvent(UIEvent.Repeat) }) {
                    Icon(
                        imageVector = when (repeatMode) {
                            is RepeatState.One -> DecibelIcons.RepeatOne
                            else -> DecibelIcons.Repeat
                        },
                        contentDescription = "Repeat",
                        tint = if (repeatMode !is RepeatState.None) SpotifyGreenBright else SpotifyTextSecondary,
                        modifier = Modifier.size(24.dp),
                    )
                }
            }

            Spacer(modifier = Modifier.weight(0.3f))

            // Bottom row: devices + lyrics/mic shortcut, share, queue (real Spotify order)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    PlatformCastButton(modifier = Modifier.size(40.dp), tint = SpotifyTextSecondary)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = { openUrl("decibel://watch?v=${songEntity.videoId}") }) {
                        Icon(
                            DecibelIcons.Share,
                            contentDescription = "Share",
                            tint = SpotifyTextSecondary,
                            modifier = Modifier.size(22.dp),
                        )
                    }
                    IconButton(onClick = { showQueue = true }) {
                        Icon(
                            DecibelIcons.QueueMusic,
                            contentDescription = "Queue",
                            tint = SpotifyTextSecondary,
                            modifier = Modifier.size(24.dp),
                        )
                    }
                }
            }
        }
    }
}

// ==========================================
// 4. SPOTIFY HOME FEED GREETING & PILLS
// ==========================================
@Composable
fun SpotifyFilterChipsRow(
    selectedCategory: String = "All",
    onCategorySelected: (String) -> Unit = {},
) {
    // Real Spotify top filter: All / Music / Podcasts (+ Audiobooks on some accounts)
    val categories = listOf("All", "Music", "Podcasts", "Audiobooks")
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(categories, key = { it }) { cat ->
            val isSelected = cat == selectedCategory
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(if (isSelected) SpotifyGreenBright else Color(0xFF2A2A2A))
                    .clickable { onCategorySelected(cat) }
                    .padding(horizontal = 14.dp, vertical = 8.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = cat,
                    style = typo().bodyMedium.copy(
                        color = if (isSelected) Color.Black else Color.White,
                        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                        fontSize = 13.sp,
                    ),
                    maxLines = 1,
                )
            }
        }
    }
}
