package com.decibel.music.ui.skin.applemusic

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.decibel.music.extension.isScrollingUp
import com.decibel.music.ui.icon.ArrowForwardIos
import com.decibel.music.ui.icon.KeyboardArrowDown
import com.decibel.music.ui.icon.DecibelIcons
import com.decibel.music.ui.navigation.destination.library.LibraryDynamicPlaylistDestination
import com.decibel.music.ui.navigation.destination.list.LocalPlaylistDestination
import com.decibel.music.ui.screen.library.LibraryDynamicPlaylistType
import com.decibel.music.ui.skin.AppleMusicPink
import com.decibel.music.ui.skin.AppleMusicSurfaceElevatedDark
import com.decibel.music.ui.skin.AppleMusicTextSecondaryDark
import com.decibel.music.ui.theme.typo
import com.decibel.music.viewModel.LibraryViewModel
import com.decibel.music.viewModel.SharedViewModel
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

data class AppleMusicMenuItem(
    val title: String,
    val onClick: () -> Unit,
)

@Composable
fun AppleMusicLibraryScreen(
    viewModel: LibraryViewModel = koinViewModel(),
    sharedViewModel: SharedViewModel = koinInject(),
    innerPadding: PaddingValues,
    navController: NavController,
    onScrolling: (onTop: Boolean) -> Unit = {},
) {
    val scrollState = rememberLazyListState()
    val isScrollingUp by scrollState.isScrollingUp()

    val localPlaylists by viewModel.yourLocalPlaylist.collectAsStateWithLifecycle()
    val recentlyAdded by viewModel.recentlyAdded.collectAsStateWithLifecycle()
    var isEditing by rememberSaveable { mutableStateOf(false) }
    var newestFirst by rememberSaveable { mutableStateOf(true) }
    var selectedPlaylistIds by rememberSaveable { mutableStateOf(setOf<Long>()) }

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

    val menuItems = listOf(
        AppleMusicMenuItem("Playlists") {
            navController.navigate(LibraryDynamicPlaylistDestination(type = LibraryDynamicPlaylistType.Followed.toStringParams()))
        },
        AppleMusicMenuItem("Artists") {
            navController.navigate(LibraryDynamicPlaylistDestination(type = LibraryDynamicPlaylistType.TopArtists.toStringParams()))
        },
        AppleMusicMenuItem("Albums") {
            navController.navigate(LibraryDynamicPlaylistDestination(type = LibraryDynamicPlaylistType.TopAlbums.toStringParams()))
        },
        AppleMusicMenuItem("Songs") {
            navController.navigate(LibraryDynamicPlaylistDestination(type = LibraryDynamicPlaylistType.Favorite.toStringParams()))
        },
        AppleMusicMenuItem("Made for You") {
            navController.navigate(LibraryDynamicPlaylistDestination(type = LibraryDynamicPlaylistType.TopTracks.toStringParams()))
        },
        AppleMusicMenuItem("Downloaded") {
            navController.navigate(LibraryDynamicPlaylistDestination(type = LibraryDynamicPlaylistType.Downloaded.toStringParams()))
        },
    )

    LazyColumn(
        state = scrollState,
        modifier = Modifier.fillMaxSize().background(Color.Black),
        contentPadding = PaddingValues(bottom = 90.dp),
    ) {
        // 1. Apple Music Large Title & Edit Button
        item(key = "apple_library_header") {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(top = 44.dp, bottom = 12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "Library",
                        style = typo().headlineLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontSize = 34.sp,
                        ),
                    )
                    Text(
                        text = if (isEditing) "Done" else "Edit",
                        style = typo().bodyLarge.copy(
                            color = AppleMusicPink,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 17.sp,
                        ),
                        modifier = Modifier.clickable {
                            isEditing = !isEditing
                            if (!isEditing) selectedPlaylistIds = emptySet()
                        },
                    )
                }
            }
        }

        // 2. Cupertino Table View Disclosure Rows
        items(menuItems, key = { it.title }) { item ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = item.onClick)
                    .padding(horizontal = 20.dp),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = item.title,
                        style = typo().bodyLarge.copy(
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Normal,
                        ),
                    )
                    Icon(
                        imageVector = DecibelIcons.ArrowForwardIos,
                        contentDescription = "Navigate",
                        tint = Color(0xFF545458),
                        modifier = Modifier.size(14.dp),
                    )
                }
                HorizontalDivider(
                    color = Color(0xFF2C2C2E),
                    thickness = 0.5.dp,
                )
            }
        }

        // 3. "Recently Added" Section Header (Apple Music pairs it with a sort pill)
        item(key = "recently_added_header") {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(top = 28.dp, bottom = 14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Recently Added",
                    style = typo().headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 22.sp,
                    ),
                )
                // Apple Music sort control pill ("Recently Added ⌄")
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(500.dp))
                        .background(AppleMusicSurfaceElevatedDark)
                        .clickable { newestFirst = !newestFirst }
                        .padding(horizontal = 12.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = if (newestFirst) "Recently Added" else "Oldest First",
                        style = typo().bodySmall.copy(
                            color = AppleMusicPink,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 13.sp,
                        ),
                    )
                    Spacer(modifier = Modifier.size(4.dp))
                    Icon(
                        imageVector = DecibelIcons.KeyboardArrowDown,
                        contentDescription = "Sort options",
                        tint = AppleMusicPink,
                        modifier = Modifier.size(16.dp),
                    )
                }
            }
        }

        // 4. Recently Added 2-Column Grid
        val playlists = localPlaylists.data.orEmpty().let { if (newestFirst) it else it.reversed() }
        val chunkedPlaylists = playlists.chunked(2)
        items(chunkedPlaylists, key = { chunk -> chunk.firstOrNull()?.id ?: 0 }) { pair ->
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                pair.forEach { playlist ->
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                if (isEditing) {
                                    selectedPlaylistIds = if (playlist.id in selectedPlaylistIds) selectedPlaylistIds - playlist.id else selectedPlaylistIds + playlist.id
                                } else {
                                    navController.navigate(LocalPlaylistDestination(playlist.id))
                                }
                            },
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (playlist.id in selectedPlaylistIds) AppleMusicPink.copy(alpha = 0.25f) else AppleMusicSurfaceElevatedDark),
                        ) {
                            AsyncImage(
                                model = playlist.thumbnail,
                                contentDescription = playlist.title,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize(),
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = playlist.title,
                            style = typo().bodyMedium.copy(
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White,
                                fontSize = 14.sp,
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )

                        Text(
                            text = "Decibel Playlist",
                            style = typo().bodySmall.copy(
                                color = AppleMusicTextSecondaryDark,
                                fontSize = 12.sp,
                            ),
                        )
                    }
                }
                if (pair.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}
