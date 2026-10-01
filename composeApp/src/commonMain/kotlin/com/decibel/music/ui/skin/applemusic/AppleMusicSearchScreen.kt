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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.IconButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.decibel.music.common.Config
import com.decibel.music.domain.data.model.searchResult.songs.SongsResult
import com.decibel.music.domain.mediaservice.handler.PlaylistType
import com.decibel.music.domain.mediaservice.handler.QueueData
import com.decibel.music.domain.utils.toTrack
import com.decibel.music.ui.component.SongFullWidthItems
import com.decibel.music.viewModel.SearchScreenUIState
import com.decibel.music.viewModel.SearchViewModel
import com.decibel.music.viewModel.SharedViewModel
import org.koin.compose.koinInject
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.decibel.music.ui.icon.Search
import com.decibel.music.ui.icon.DecibelIcons
import com.decibel.music.ui.navigation.destination.home.MoodDestination
import com.decibel.music.ui.skin.AppleMusicPink
import com.decibel.music.ui.skin.AppleMusicSurfaceDark
import com.decibel.music.ui.skin.AppleMusicTextSecondaryDark
import com.decibel.music.ui.theme.typo
import org.koin.compose.viewmodel.koinViewModel

data class AppleMusicCategory(
    val title: String,
    val imageUrl: String,
    val params: String = "",
)

@Composable
fun AppleMusicSearchScreen(
    searchViewModel: SearchViewModel = koinViewModel(),
    navController: NavController,
) {
    val searchState by searchViewModel.searchScreenState.collectAsStateWithLifecycle()
    val searchUiState by searchViewModel.searchScreenUIState.collectAsStateWithLifecycle()
    val sharedViewModel: SharedViewModel = koinInject()
    var searchText by rememberSaveable { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    fun submitSearch(query: String) {
        val cleanQuery = query.trim()
        if (cleanQuery.isEmpty()) return
        searchViewModel.insertSearchHistory(cleanQuery)
        searchViewModel.searchSongs(cleanQuery)
        focusManager.clearFocus()
    }

    val categories = listOf(
        AppleMusicCategory("Spatial Audio", "https://picsum.photos/400/250?random=11", "ggMPOg1uX1BvcA%3D%3D"),
        AppleMusicCategory("Hits", "https://picsum.photos/400/250?random=12", "ggMPOg1uX0NoYXJ0cw%3D%3D"),
        AppleMusicCategory("Pop", "https://picsum.photos/400/250?random=13", "ggMPOg1uX1BvcA%3D%3D"),
        AppleMusicCategory("Hip-Hop / R&B", "https://picsum.photos/400/250?random=14", "ggMPOg1uX0hpcC1Ib3A%3D"),
        AppleMusicCategory("Dance & Electronic", "https://picsum.photos/400/250?random=15", "ggMPOg1uX1BhcnR5"),
        AppleMusicCategory("Chill", "https://picsum.photos/400/250?random=16", "ggMPOg1uX0NoaWxs"),
        AppleMusicCategory("Rock", "https://picsum.photos/400/250?random=17", "ggMPOg1uX1JvY2s%3D"),
        AppleMusicCategory("Workout", "https://picsum.photos/400/250?random=18", "ggMPOg1uX1dvcmtvdXQ%3D"),
        AppleMusicCategory("Wellness", "https://picsum.photos/400/250?random=19", "ggMPOg1uX0ZvY3Vz"),
        AppleMusicCategory("Classical", "https://picsum.photos/400/250?random=20", "ggMPOg1uX1NsZWVw"),
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize().background(Color.Black),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        item(span = { GridItemSpan(2) }) {
            Column(modifier = Modifier.fillMaxWidth().padding(top = 20.dp, bottom = 12.dp)) {
                Text(
                    text = "Search",
                    style = typo().headlineLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 34.sp,
                    ),
                    modifier = Modifier.padding(bottom = 16.dp),
                )

                Card(
                    modifier = Modifier.fillMaxWidth().height(44.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(containerColor = AppleMusicSurfaceDark),
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize().padding(horizontal = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(DecibelIcons.Search, contentDescription = "Search", tint = AppleMusicTextSecondaryDark, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.size(8.dp))
                        BasicTextField(
                            value = searchText,
                            onValueChange = {
                                searchText = it
                                if (it.isNotBlank()) searchViewModel.suggestQuery(it)
                            },
                            modifier = Modifier.weight(1f).focusRequester(focusRequester),
                            singleLine = true,
                            textStyle = typo().bodyMedium.copy(color = Color.White, fontSize = 16.sp),
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                            keyboardActions = KeyboardActions(onSearch = { submitSearch(searchText) }),
                            decorationBox = { innerTextField ->
                                Box {
                                    if (searchText.isEmpty()) {
                                        Text("Artists, Songs, Lyrics, and More", style = typo().bodyMedium.copy(color = AppleMusicTextSecondaryDark, fontSize = 16.sp))
                                    }
                                    innerTextField()
                                }
                            },
                        )
                        if (searchText.isNotEmpty()) {
                            IconButton(onClick = { searchText = "" }) {
                                Text("×", color = AppleMusicTextSecondaryDark, fontSize = 20.sp)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Browse Categories",
                    style = typo().headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 20.sp,
                    ),
                )
            }
        }

        if (searchText.isNotBlank() && searchUiState is SearchScreenUIState.Loading) {
            item(span = { GridItemSpan(2) }) {
                Text("Searching…", color = Color.White, modifier = Modifier.padding(16.dp))
            }
        }
        if (searchText.isNotBlank() && searchUiState is SearchScreenUIState.Success) {
            item(span = { GridItemSpan(2) }) {
                LazyColumn(modifier = Modifier.fillMaxWidth().height(520.dp)) {
                    items(searchState.searchSongsResult, key = { it.videoId }) { song ->
                        val track = song.toTrack()
                        SongFullWidthItems(
                            track = track,
                            isPlaying = false,
                            onClickListener = {
                                searchViewModel.setQueueData(
                                    QueueData.Data(
                                        listTracks = arrayListOf(track),
                                        firstPlayedTrack = track,
                                        playlistId = "RDAMVM${track.videoId}",
                                        playlistName = searchText,
                                        playlistType = PlaylistType.RADIO,
                                        continuation = null,
                                    ),
                                )
                                searchViewModel.loadMediaItem(track, type = Config.SONG_CLICK)
                            },
                            onAddToQueue = { sharedViewModel.addListToQueue(arrayListOf(track)) },
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }
                }
            }
        }

        // 2-Column Category Grid (Apple Music: square tiles with dark gradient + bold label)
        items(categories, key = { it.title }) { category ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(10.dp))
                    .clickable {
                        navController.navigate(MoodDestination(params = category.params))
                    },
                shape = RoundedCornerShape(10.dp),
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    AsyncImage(
                        model = category.imageUrl,
                        contentDescription = category.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize(),
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    listOf(Color(0x22000000), Color(0x99000000), Color(0xE6000000))
                                )
                            )
                    )
                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(12.dp),
                    ) {
                        Text(
                            text = category.title,
                            style = typo().titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                fontSize = 16.sp,
                            ),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                        )
                        Text(
                            text = "Category",
                            style = typo().bodySmall.copy(
                                color = AppleMusicTextSecondaryDark,
                                fontSize = 12.sp,
                            ),
                        )
                    }
                }
            }
        }

        item(span = { GridItemSpan(2) }) {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}
