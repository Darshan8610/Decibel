package com.decibel.music.ui.skin

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import com.decibel.music.expect.ui.PlatformCastButton
import com.decibel.music.ui.component.LyricsView
import com.decibel.music.ui.component.QueueBottomSheet
import com.decibel.music.ui.component.liquidGlass
import com.decibel.music.ui.theme.LocalIsDarkTheme
import com.decibel.music.ui.icon.ArrowForwardIos
import com.decibel.music.ui.icon.AutoGraph
import com.decibel.music.ui.icon.Favorite
import com.decibel.music.ui.icon.FavoriteBorder
import com.decibel.music.ui.icon.KeyboardArrowDown
import com.decibel.music.ui.icon.LibraryMusic
import com.decibel.music.ui.icon.Lyrics
import com.decibel.music.ui.icon.Pause
import com.decibel.music.ui.icon.PlayArrow
import com.decibel.music.ui.icon.QueueMusic
import com.decibel.music.ui.icon.Repeat
import com.decibel.music.ui.icon.RepeatOne
import com.decibel.music.ui.icon.Search
import com.decibel.music.ui.icon.Sensors
import com.decibel.music.ui.icon.Shuffle
import com.decibel.music.ui.icon.DecibelIcons
import com.decibel.music.ui.icon.SkipNext
import com.decibel.music.ui.icon.SkipPrevious
import com.decibel.music.ui.navigation.destination.home.HomeDestination
import com.decibel.music.ui.navigation.destination.home.MoodDestination
import com.decibel.music.ui.navigation.destination.library.LibraryDestination
import com.decibel.music.ui.navigation.destination.library.LibraryDynamicPlaylistDestination
import com.decibel.music.ui.navigation.destination.search.SearchDestination
import com.decibel.music.ui.screen.library.LibraryDynamicPlaylistType
import com.decibel.music.ui.theme.typo
import com.decibel.music.viewModel.SharedViewModel
import com.decibel.music.viewModel.UIEvent
import org.koin.compose.koinInject
import kotlin.reflect.KClass

// ===============================================
// 1. APPLE MUSIC BOTTOM NAVIGATION BAR (5 TABS)
// ===============================================
/**
 * Apple Music ships five tabs: Listen Now / Browse / Radio / Library / Search.
 * Every tab is wired to a *working* destination in this app:
 *  - Listen Now -> Home feed
 *  - Browse     -> genre browse page (Charts) — Apple Music Browse is genre driven
 *  - Radio      -> "Most Played" station-style dynamic playlist (no live radio surface exists)
 *  - Library    -> user library
 *  - Search     -> search + browse categories
 */
sealed class AppleMusicNavTab(
    val title: String,
    val destination: Any,
    val routeClass: KClass<*>,
) {
    object ListenNow : AppleMusicNavTab("Listen Now", HomeDestination, HomeDestination::class)

    object Browse : AppleMusicNavTab(
        "Browse",
        MoodDestination(params = APPLE_MUSIC_BROWSE_PARAMS),
        MoodDestination::class,
    )

    object Radio : AppleMusicNavTab(
        "Radio",
        LibraryDynamicPlaylistDestination(type = LibraryDynamicPlaylistType.MostPlayed.toStringParams()),
        LibraryDynamicPlaylistDestination::class,
    )

    object Library : AppleMusicNavTab("Library", LibraryDestination, LibraryDestination::class)

    object Search : AppleMusicNavTab("Search", SearchDestination, SearchDestination::class)

    companion object {
        /** Apple Music "Charts" browse page params (matches the Hit/Charts category tile). */
        const val APPLE_MUSIC_BROWSE_PARAMS = "ggMPOg1uX0NoYXJ0cw%3D%3D"
    }
}

@Composable
fun AppleMusicBottomNavigationBar(
    navController: NavController,
    reloadDestinationIfNeeded: (KClass<*>) -> Unit = {},
    backdrop: PlatformBackdrop? = null,
) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val tabs = listOf(
        AppleMusicNavTab.ListenNow,
        AppleMusicNavTab.Browse,
        AppleMusicNavTab.Radio,
        AppleMusicNavTab.Library,
        AppleMusicNavTab.Search,
    )

    Column {
        // Hairline divider above the tab bar (Apple Music always shows a separator)
        HorizontalDivider(
            thickness = 0.5.dp,
            color = Color(0xFF38383A),
        )

        NavigationBar(
            windowInsets = WindowInsets(0, 0, 0, 0),
            containerColor = if (backdrop != null) Color.Transparent else Color(0xEE1C1C1E),
            tonalElevation = 0.dp,
            modifier = Modifier
                .height(64.dp)
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
                            AppleMusicNavTab.ListenNow -> Icon(DecibelIcons.PlayArrow, contentDescription = "Listen Now", modifier = Modifier.size(24.dp))
                            AppleMusicNavTab.Browse -> Icon(DecibelIcons.AutoGraph, contentDescription = "Browse", modifier = Modifier.size(24.dp))
                            AppleMusicNavTab.Radio -> Icon(DecibelIcons.Sensors, contentDescription = "Radio", modifier = Modifier.size(24.dp))
                            AppleMusicNavTab.Library -> Icon(DecibelIcons.LibraryMusic, contentDescription = "Library", modifier = Modifier.size(24.dp))
                            AppleMusicNavTab.Search -> Icon(DecibelIcons.Search, contentDescription = "Search", modifier = Modifier.size(24.dp))
                        }
                    },
                    label = {
                        Text(
                            tab.title,
                            style = typo().bodySmall.copy(
                                fontSize = 10.sp,
                                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                            ),
                            maxLines = 1,
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = Color.Transparent,
                        selectedIconColor = AppleMusicPink,
                        unselectedIconColor = AppleMusicTextSecondaryDark,
                        selectedTextColor = AppleMusicPink,
                        unselectedTextColor = AppleMusicTextSecondaryDark,
                    ),
                )
            }
        }
    }
}

// ==========================================
// 2. APPLE MUSIC MINI PLAYER
// ==========================================
@Composable
fun AppleMusicMiniPlayer(
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
    val progress = timelineState.current
    val duration = timelineState.total

    var dragOffset by remember { mutableFloatStateOf(0f) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .then(backdrop?.let { Modifier.liquidGlass(it, RoundedCornerShape(12.dp)) } ?: Modifier.clip(RoundedCornerShape(12.dp)))
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
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = if (backdrop != null) Color.Transparent else AppleMusicSurfaceDark),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // Square Artwork (Apple Music: rounded ~6dp corners, fills mini player height)
                AsyncImage(
                    model = songEntity.thumbnails,
                    contentDescription = songEntity.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(6.dp)),
                )

                Spacer(modifier = Modifier.width(10.dp))

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
                            fontSize = 14.sp,
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.basicMarquee(),
                    )
                    Text(
                        text = songEntity.artistName?.joinToString(", ") ?: "",
                        style = typo().bodySmall.copy(
                            color = AppleMusicTextSecondaryDark,
                            fontSize = 12.sp,
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }

                PlatformCastButton(modifier = Modifier.size(40.dp), tint = AppleMusicTextSecondaryDark)

                // Play / Pause Button (Apple Music: prominent, right side)
                IconButton(
                    onClick = {
                        viewModel.onUIEvent(UIEvent.PlayPause)
                    },
                    modifier = Modifier.size(40.dp),
                ) {
                    Icon(
                        imageVector = if (isPlaying) DecibelIcons.Pause else DecibelIcons.PlayArrow,
                        contentDescription = if (isPlaying) "Pause" else "Play",
                        tint = Color.White,
                        modifier = Modifier.size(30.dp),
                    )
                }

                // Next Track Button
                IconButton(
                    onClick = { viewModel.onUIEvent(UIEvent.Next) },
                    modifier = Modifier.size(40.dp),
                ) {
                    Icon(
                        imageVector = DecibelIcons.SkipNext,
                        contentDescription = "Next",
                        tint = AppleMusicTextSecondaryDark,
                        modifier = Modifier.size(28.dp),
                    )
                }
            }

            // Full-width pink-ish progress line at very bottom (real Apple Music)
            val progressFraction = if (duration > 0) (progress.toFloat() / duration.toFloat()).coerceIn(0f, 1f) else 0f
            LinearProgressIndicator(
                progress = { progressFraction },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.5.dp),
                color = AppleMusicPink,
                trackColor = Color(0x4DFFFFFF),
                strokeCap = StrokeCap.Butt,
                drawStopIndicator = {},
            )
        }
    }
}

// ==========================================
// 3. APPLE MUSIC NOW PLAYING SCREEN
// ==========================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppleMusicNowPlayingScreen(
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
    var showLyrics by remember { mutableStateOf(false) }

    if (showQueue) {
        QueueBottomSheet(onDismiss = { showQueue = false }, sharedViewModel = viewModel)
    }
    if (showLyrics) {
        androidx.compose.material3.BasicAlertDialog(onDismissRequest = { showLyrics = false }) {
            androidx.compose.material3.Surface(
                modifier = Modifier.fillMaxWidth().height(520.dp),
                shape = RoundedCornerShape(20.dp),
                color = AppleMusicSurfaceDark,
            ) {
                val lyrics = nowPlayingScreenData.lyricsData
                if (lyrics != null) {
                    LyricsView(
                        lyricsData = lyrics,
                        timeLine = viewModel.timeline,
                        onLineClick = { viewModel.onUIEvent(UIEvent.UpdateProgress(it)) },
                        backgroundColor = AppleMusicSurfaceDark,
                    )
                } else {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Lyrics are loading or unavailable", color = AppleMusicTextSecondaryDark)
                    }
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFF2A1518),
                        Color(0xFF140D0F),
                        AppleMusicBackgroundDark,
                    )
                )
            )
            .padding(horizontal = 24.dp)
            .padding(top = 28.dp, bottom = 28.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // iOS Grabber / Top Bar
            Box(
                modifier = Modifier
                    .width(36.dp)
                    .height(5.dp)
                    .clip(CircleShape)
                    .background(Color(0x66FFFFFF))
                    .clickable(onClick = onDismiss)
            )

            Spacer(modifier = Modifier.height(16.dp))

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
                Text(
                    text = nowPlayingScreenData.playlistName.ifEmpty { "Apple Music" },
                    style = typo().bodyMedium.copy(
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.sp,
                    ),
                    maxLines = 1,
                )
                IconButton(onClick = { viewModel.onUIEvent(UIEvent.ToggleLike) }) {
                    Icon(
                        imageVector = if (isLiked) DecibelIcons.Favorite else DecibelIcons.FavoriteBorder,
                        contentDescription = "Like",
                        tint = if (isLiked) AppleMusicPink else Color.White,
                        modifier = Modifier.size(24.dp),
                    )
                }
            }

            Spacer(modifier = Modifier.weight(0.3f))

            // Large Rounded Album Art (Apple Music: 20.dp radius, soft shadow)
            AsyncImage(
                model = songEntity.thumbnails,
                contentDescription = songEntity.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth(0.92f)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(20.dp))
                    .shadow(24.dp, RoundedCornerShape(20.dp)),
            )

            Spacer(modifier = Modifier.weight(0.3f))

            // Title and Artist
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    text = songEntity.title,
                    style = typo().titleLarge.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.basicMarquee(),
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = songEntity.artistName?.joinToString(", ") ?: "",
                    style = typo().bodyLarge.copy(
                        color = AppleMusicTextSecondaryDark,
                        fontSize = 18.sp,
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // iOS-style Scrubber
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
                    inactiveTrackColor = Color(0xFF38383A),
                ),
                modifier = Modifier.fillMaxWidth(),
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                val currentPosition = if (isSeeking) (seekPosition * duration).toLong() else progress
                Text(
                    text = formatDuration(currentPosition),
                    style = typo().bodySmall.copy(color = AppleMusicTextSecondaryDark, fontSize = 12.sp),
                )
                Text(
                    text = "-${formatDuration((duration - currentPosition).coerceAtLeast(0))}",
                    style = typo().bodySmall.copy(color = AppleMusicTextSecondaryDark, fontSize = 12.sp),
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Apple Music Transport Controls
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(onClick = { viewModel.onUIEvent(UIEvent.Previous) }, modifier = Modifier.size(48.dp)) {
                    Icon(
                        DecibelIcons.SkipPrevious,
                        contentDescription = "Previous",
                        tint = Color.White,
                        modifier = Modifier.size(38.dp),
                    )
                }

                // Apple Style Play/Pause
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clickable {
                            viewModel.onUIEvent(UIEvent.PlayPause)
                        },
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = if (isPlaying) DecibelIcons.Pause else DecibelIcons.PlayArrow,
                        contentDescription = if (isPlaying) "Pause" else "Play",
                        tint = Color.White,
                        modifier = Modifier.size(48.dp),
                    )
                }

                IconButton(onClick = { viewModel.onUIEvent(UIEvent.Next) }, modifier = Modifier.size(48.dp)) {
                    Icon(
                        DecibelIcons.SkipNext,
                        contentDescription = "Next",
                        tint = Color.White,
                        modifier = Modifier.size(38.dp),
                    )
                }
            }

            Spacer(modifier = Modifier.weight(0.3f))

            // Bottom Toolbar: Synced Lyrics Quote Bubble, Shuffle, Repeat, Queue
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(onClick = { showLyrics = true }) {
                    Icon(
                        DecibelIcons.Lyrics,
                        contentDescription = "Lyrics",
                        tint = AppleMusicTextSecondaryDark,
                        modifier = Modifier.size(24.dp),
                    )
                }

                IconButton(onClick = { viewModel.onUIEvent(UIEvent.Shuffle) }) {
                    Icon(
                        DecibelIcons.Shuffle,
                        contentDescription = "Shuffle",
                        tint = if (isShuffle) AppleMusicPink else AppleMusicTextSecondaryDark,
                        modifier = Modifier.size(24.dp),
                    )
                }

                IconButton(onClick = { viewModel.onUIEvent(UIEvent.Repeat) }) {
                    Icon(
                        imageVector = when (repeatMode) {
                            is RepeatState.One -> DecibelIcons.RepeatOne
                            else -> DecibelIcons.Repeat
                        },
                        contentDescription = "Repeat",
                        tint = if (repeatMode !is RepeatState.None) AppleMusicPink else AppleMusicTextSecondaryDark,
                        modifier = Modifier.size(24.dp),
                    )
                }

                IconButton(onClick = { showQueue = true }) {
                    Icon(
                        DecibelIcons.QueueMusic,
                        contentDescription = "Queue",
                        tint = AppleMusicTextSecondaryDark,
                        modifier = Modifier.size(24.dp),
                    )
                }
            }
        }
    }
}

// ==========================================
// 4. APPLE MUSIC LIBRARY SECTIONS
// ==========================================
@Composable
fun AppleMusicLibraryRow(
    title: String,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 14.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = title,
                style = typo().titleMedium.copy(
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                ),
            )
            Icon(
                DecibelIcons.ArrowForwardIos,
                contentDescription = null,
                tint = Color(0x66FFFFFF),
                modifier = Modifier.size(16.dp),
            )
        }
        Spacer(modifier = Modifier.height(14.dp))
        HorizontalDivider(color = AppleMusicDividerDark)
    }
}
