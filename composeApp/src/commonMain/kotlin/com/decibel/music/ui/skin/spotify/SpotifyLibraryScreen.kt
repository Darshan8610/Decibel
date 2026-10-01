package com.decibel.music.ui.skin.spotify

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.decibel.music.domain.data.entities.LocalPlaylistEntity
import com.decibel.music.domain.utils.LocalResource
import com.decibel.music.extension.isScrollingUp
import com.decibel.music.ui.icon.Add
import com.decibel.music.ui.icon.Favorite
import com.decibel.music.ui.icon.PushPin
import com.decibel.music.ui.icon.Search
import com.decibel.music.ui.icon.DecibelIcons
import com.decibel.music.ui.icon.Sort
import com.decibel.music.ui.navigation.destination.library.LibraryDynamicPlaylistDestination
import com.decibel.music.ui.navigation.destination.list.LocalPlaylistDestination
import com.decibel.music.ui.screen.library.LibraryDynamicPlaylistType
import com.decibel.music.ui.skin.SpotifyCard
import com.decibel.music.ui.skin.SpotifyDark
import com.decibel.music.ui.skin.SpotifyGreenBright
import com.decibel.music.ui.skin.SpotifyTextSecondary
import com.decibel.music.ui.theme.typo
import com.decibel.music.viewModel.LibraryViewModel
import com.decibel.music.viewModel.SharedViewModel
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

enum class SpotifyLibraryFilter(val label: String) {
    PLAYLISTS("Playlists"),
    ARTISTS("Artists"),
    ALBUMS("Albums"),
    DOWNLOADED("Downloaded"),
}

@Composable
fun SpotifyLibraryScreen(
    viewModel: LibraryViewModel = koinViewModel(),
    sharedViewModel: SharedViewModel = koinInject(),
    innerPadding: PaddingValues,
    navController: NavController,
    onScrolling: (onTop: Boolean) -> Unit = {},
) {
    val scrollState = rememberLazyListState()
    val isScrollingUp by scrollState.isScrollingUp()
    var selectedFilter by remember { mutableStateOf<SpotifyLibraryFilter?>(null) }
    var searchQuery by rememberSaveable { mutableStateOf("") }
    var showSearch by rememberSaveable { mutableStateOf(false) }
    var showCreatePlaylist by rememberSaveable { mutableStateOf(false) }
    var showSortMenu by remember { mutableStateOf(false) }
    var newestFirst by rememberSaveable { mutableStateOf(true) }

    val localPlaylists by viewModel.yourLocalPlaylist.collectAsStateWithLifecycle()
    val favorites by viewModel.favoritePlaylist.collectAsStateWithLifecycle()
    val downloaded by viewModel.downloadedPlaylist.collectAsStateWithLifecycle()

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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SpotifyDark)
            .padding(top = 28.dp),
    ) {
        // 1. Spotify Your Library Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF535353)),
                    contentAlignment = Alignment.Center,
                ) {
                    Text("D", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Your Library",
                    style = typo().headlineLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 24.sp,
                    ),
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = {
                    showSearch = !showSearch
                    if (!showSearch) searchQuery = ""
                }) {
                    Icon(DecibelIcons.Search, contentDescription = "Search Library", tint = Color.White, modifier = Modifier.size(24.dp))
                }
                IconButton(onClick = { showCreatePlaylist = true }) {
                    Icon(DecibelIcons.Add, contentDescription = "Add Playlist", tint = Color.White, modifier = Modifier.size(26.dp))
                }
            }
        }

        if (showSearch) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                singleLine = true,
                label = { Text("Search your library") },
            )
        }

        // 2. Spotify Pill Filter Chips (Spotify uses dark pills; green only when active)
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(SpotifyLibraryFilter.values()) { filter ->
                val isSelected = selectedFilter == filter
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(500.dp))
                        .background(if (isSelected) SpotifyGreenBright else Color(0xFF2A2A2A))
                        .clickable {
                            selectedFilter = if (isSelected) null else filter
                            when (filter) {
                                SpotifyLibraryFilter.PLAYLISTS -> viewModel.getLocalPlaylist()
                                SpotifyLibraryFilter.ARTISTS -> navController.navigate(LibraryDynamicPlaylistDestination(type = LibraryDynamicPlaylistType.TopArtists.toStringParams()))
                                SpotifyLibraryFilter.ALBUMS -> navController.navigate(LibraryDynamicPlaylistDestination(type = LibraryDynamicPlaylistType.TopAlbums.toStringParams()))
                                SpotifyLibraryFilter.DOWNLOADED -> navController.navigate(LibraryDynamicPlaylistDestination(type = LibraryDynamicPlaylistType.Downloaded.toStringParams()))
                            }
                        }
                        .padding(horizontal = 14.dp, vertical = 7.dp),
                ) {
                    Text(
                        text = filter.label,
                        style = typo().bodySmall.copy(
                            fontWeight = FontWeight.Normal,
                            color = if (isSelected) Color.Black else Color.White,
                            fontSize = 13.sp,
                        ),
                        maxLines = 1,
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // 3. Sort & List Content
        LazyColumn(
            state = scrollState,
            contentPadding = PaddingValues(bottom = 90.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            item(key = "sort_row") {
                Box(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 6.dp)) {
                    Row(
                        modifier = Modifier.clickable { showSortMenu = true },
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(DecibelIcons.Sort, contentDescription = "Sort", tint = SpotifyTextSecondary, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (newestFirst) "Most recent" else "Oldest first",
                            style = typo().bodySmall.copy(color = SpotifyTextSecondary, fontWeight = FontWeight.SemiBold, fontSize = 12.sp),
                        )
                    }
                    DropdownMenu(expanded = showSortMenu, onDismissRequest = { showSortMenu = false }) {
                        DropdownMenuItem(text = { Text("Most recent") }, onClick = { newestFirst = true; showSortMenu = false })
                        DropdownMenuItem(text = { Text("Oldest first") }, onClick = { newestFirst = false; showSortMenu = false })
                    }
                }
            }

            // Pinned Liked Songs row (always first, like real Spotify)
            item(key = "liked_songs") {
                SpotifyLikedSongsRow(
                    navController = navController,
                    localPlaylists = localPlaylists,
                )
            }

            // Local Playlists
            val playlists = localPlaylists.data.orEmpty()
                .filter { it.title.contains(searchQuery, ignoreCase = true) }
                .let { values -> if (newestFirst) values else values.reversed() }
            items(playlists, key = { "local_${it.id}" }) { playlist ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate(LocalPlaylistDestination(playlist.id))
                        }
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(SpotifyCard),
                        contentAlignment = Alignment.Center,
                    ) {
                        AsyncImage(
                            model = playlist.thumbnail,
                            contentDescription = playlist.title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize(),
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = playlist.title,
                            style = typo().bodyMedium.copy(
                                fontWeight = FontWeight.Normal,
                                color = Color.White,
                                fontSize = 15.sp,
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                        Text(
                            text = "Playlist • Decibel",
                            style = typo().bodySmall.copy(
                                color = SpotifyTextSecondary,
                                fontSize = 13.sp,
                            ),
                        )
                    }
                }
            }
        }
    }

    if (showCreatePlaylist) {
        var title by rememberSaveable { mutableStateOf("") }
        AlertDialog(
            onDismissRequest = { showCreatePlaylist = false },
            title = { Text("Create playlist") },
            text = {
                OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Playlist name") }, singleLine = true)
            },
            confirmButton = {
                TextButton(
                    enabled = title.isNotBlank(),
                    onClick = {
                        viewModel.createPlaylist(title.trim())
                        viewModel.getLocalPlaylist()
                        showCreatePlaylist = false
                    },
                ) { Text("Create") }
            },
            dismissButton = { TextButton(onClick = { showCreatePlaylist = false }) { Text("Cancel") } },
        )
    }
}

@Composable
private fun SpotifyLikedSongsRow(
    navController: NavController,
    localPlaylists: LocalResource<List<LocalPlaylistEntity>>,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                val fav = localPlaylists.data?.firstOrNull()
                if (fav != null) navController.navigate(LocalPlaylistDestination(fav.id))
            }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Brush.linearGradient(listOf(Color(0xFF450AF5), Color(0xFFC4EFD9)))),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                DecibelIcons.Favorite,
                contentDescription = "Liked Songs",
                tint = Color.White,
                modifier = Modifier.size(24.dp),
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "Liked Songs",
                style = typo().bodyMedium.copy(
                    fontWeight = FontWeight.Normal,
                    color = Color.White,
                    fontSize = 15.sp,
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    DecibelIcons.PushPin,
                    contentDescription = "Pinned",
                    tint = SpotifyGreenBright,
                    modifier = Modifier.size(12.dp),
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Playlist • Liked",
                    style = typo().bodySmall.copy(
                        color = SpotifyTextSecondary,
                        fontSize = 13.sp,
                    ),
                )
            }
        }
    }
}
