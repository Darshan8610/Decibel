package com.decibel.music.ui.skin.spotify

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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.decibel.music.common.Config
import com.decibel.music.domain.data.model.searchResult.albums.AlbumsResult
import com.decibel.music.domain.data.model.searchResult.artists.ArtistsResult
import com.decibel.music.domain.data.model.searchResult.playlists.PlaylistsResult
import com.decibel.music.domain.data.model.searchResult.songs.SongsResult
import com.decibel.music.domain.data.model.searchResult.videos.VideosResult
import com.decibel.music.domain.data.type.SearchResultType
import com.decibel.music.domain.mediaservice.handler.PlaylistType
import com.decibel.music.domain.mediaservice.handler.QueueData
import com.decibel.music.domain.utils.toTrack
import com.decibel.music.ui.component.SongFullWidthItems
import com.decibel.music.ui.icon.Close
import com.decibel.music.ui.icon.Search
import com.decibel.music.ui.icon.DecibelIcons
import com.decibel.music.ui.navigation.destination.home.MoodDestination
import com.decibel.music.ui.navigation.destination.list.AlbumDestination
import com.decibel.music.ui.navigation.destination.list.ArtistDestination
import com.decibel.music.ui.navigation.destination.list.PlaylistDestination
import com.decibel.music.ui.skin.SpotifyDark
import com.decibel.music.ui.skin.SpotifyGreenBright
import com.decibel.music.ui.theme.typo
import com.decibel.music.viewModel.SearchScreenUIState
import com.decibel.music.viewModel.SearchType
import com.decibel.music.viewModel.SearchViewModel
import com.decibel.music.viewModel.SharedViewModel
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

data class SpotifyBrowseCategory(
    val title: String,
    val color: Color,
    val imageUrl: String,
    val params: String = "",
)

/** Spotify search result filters shown as pills under the search field. */
enum class SpotifySearchFilter(val label: String) {
    ALL("All"),
    SONGS("Songs"),
    ARTISTS("Artists"),
    ALBUMS("Albums"),
    PLAYLISTS("Playlists"),
    PODCASTS("Podcasts"),
}

@Composable
fun SpotifySearchScreen(
    searchViewModel: SearchViewModel = koinViewModel(),
    navController: NavController,
) {
    val moodAndGenres by searchViewModel.moodAndGenres.collectAsStateWithLifecycle()
    val moodArtwork by searchViewModel.moodArtwork.collectAsStateWithLifecycle()
    val searchState by searchViewModel.searchScreenState.collectAsStateWithLifecycle()
    val searchUiState by searchViewModel.searchScreenUIState.collectAsStateWithLifecycle()
    val sharedViewModel: SharedViewModel = koinInject()
    var searchText by rememberSaveable { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf(SpotifySearchFilter.ALL) }
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    fun submitSearch(query: String) {
        val cleanQuery = query.trim()
        if (cleanQuery.isEmpty()) return
        searchViewModel.insertSearchHistory(cleanQuery)
        when (selectedFilter) {
            SpotifySearchFilter.ALL -> searchViewModel.searchAll(cleanQuery)
            SpotifySearchFilter.SONGS -> searchViewModel.searchSongs(cleanQuery)
            SpotifySearchFilter.ARTISTS -> searchViewModel.searchArtists(cleanQuery)
            SpotifySearchFilter.ALBUMS -> searchViewModel.searchAlbums(cleanQuery)
            SpotifySearchFilter.PLAYLISTS -> searchViewModel.searchPlaylists(cleanQuery)
            SpotifySearchFilter.PODCASTS -> searchViewModel.searchPodcast(cleanQuery)
        }
        focusManager.clearFocus()
    }

    val currentResults: List<SearchResultType> = when (selectedFilter) {
        SpotifySearchFilter.ALL -> searchState.searchAllResult
        SpotifySearchFilter.SONGS -> searchState.searchSongsResult
        SpotifySearchFilter.ARTISTS -> searchState.searchArtistsResult
        SpotifySearchFilter.ALBUMS -> searchState.searchAlbumsResult
        SpotifySearchFilter.PLAYLISTS -> searchState.searchPlaylistsResult
        SpotifySearchFilter.PODCASTS -> searchState.searchPodcastsResult
    }

    val categories = listOf(
        SpotifyBrowseCategory("Pop", Color(0xFF8D67AB), "https://picsum.photos/200/200?random=1", "ggMPOg1uX1BvcA%3D%3D"),
        SpotifyBrowseCategory("Hip-Hop", Color(0xFFBA5D07), "https://picsum.photos/200/200?random=2", "ggMPOg1uX0hpcC1Ib3A%3D"),
        SpotifyBrowseCategory("Rock", Color(0xFFE91429), "https://picsum.photos/200/200?random=3", "ggMPOg1uX1JvY2s%3D"),
        SpotifyBrowseCategory("Workout", Color(0xFF777777), "https://picsum.photos/200/200?random=4", "ggMPOg1uX1dvcmtvdXQ%3D"),
        SpotifyBrowseCategory("Chill", Color(0xFF509BF5), "https://picsum.photos/200/200?random=5", "ggMPOg1uX0NoaWxs"),
        SpotifyBrowseCategory("Party", Color(0xFFAF2896), "https://picsum.photos/200/200?random=6", "ggMPOg1uX1BhcnR5"),
        SpotifyBrowseCategory("Focus", Color(0xFF503750), "https://picsum.photos/200/200?random=7", "ggMPOg1uX0ZvY3Vz"),
        SpotifyBrowseCategory("Sleep", Color(0xFF1E3264), "https://picsum.photos/200/200?random=8", "ggMPOg1uX1NsZWVw"),
        SpotifyBrowseCategory("Charts", Color(0xFF8D67AB), "https://picsum.photos/200/200?random=9", "ggMPOg1uX0NoYXJ0cw%3D%3D"),
        SpotifyBrowseCategory("Discover", Color(0xFF006450), "https://picsum.photos/200/200?random=10", "ggMPOg1uX0Rpc2NvdmVy"),
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize().background(SpotifyDark),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 24.dp),
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
                        fontSize = 32.sp,
                    ),
                    modifier = Modifier.padding(bottom = 16.dp),
                )

                Card(
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    shape = RoundedCornerShape(500.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(DecibelIcons.Search, contentDescription = "Search", tint = Color(0xFF121212), modifier = Modifier.size(24.dp))
                        Spacer(modifier = Modifier.width(12.dp))
                        BasicTextField(
                            value = searchText,
                            onValueChange = {
                                searchText = it
                                if (it.isNotBlank()) searchViewModel.suggestQuery(it)
                            },
                            modifier = Modifier.weight(1f).focusRequester(focusRequester),
                            singleLine = true,
                            textStyle = typo().bodyMedium.copy(color = Color(0xFF121212), fontWeight = FontWeight.SemiBold, fontSize = 14.sp),
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                            keyboardActions = KeyboardActions(onSearch = { submitSearch(searchText) }),
                            decorationBox = { innerTextField ->
                                Box {
                                    if (searchText.isEmpty()) {
                                        Text("What do you want to listen to?", style = typo().bodyMedium.copy(color = Color(0xFF535353), fontWeight = FontWeight.SemiBold, fontSize = 14.sp))
                                    }
                                    innerTextField()
                                }
                            },
                        )
                        if (searchText.isNotEmpty()) {
                            IconButton(onClick = { searchText = "" }) {
                                Icon(DecibelIcons.Close, contentDescription = "Clear search", tint = Color(0xFF121212))
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Browse all",
                    style = typo().headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 18.sp,
                    ),
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Spotify search filter pills (All / Songs / Artists / Albums / Playlists / Podcasts)
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    items(SpotifySearchFilter.values()) { filter ->
                        val isSelected = filter == selectedFilter
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(500.dp))
                                .background(if (isSelected) SpotifyGreenBright else Color(0xFF2A2A2A))
                                .clickable {
                                    selectedFilter = filter
                                    val searchType = when (filter) {
                                        SpotifySearchFilter.ALL -> SearchType.ALL
                                        SpotifySearchFilter.SONGS -> SearchType.SONGS
                                        SpotifySearchFilter.ARTISTS -> SearchType.ARTISTS
                                        SpotifySearchFilter.ALBUMS -> SearchType.ALBUMS
                                        SpotifySearchFilter.PLAYLISTS -> SearchType.PLAYLISTS
                                        SpotifySearchFilter.PODCASTS -> SearchType.PODCASTS
                                    }
                                    searchViewModel.setSearchType(searchType)
                                    if (searchText.isNotBlank()) submitSearch(searchText)
                                }
                                .padding(horizontal = 14.dp, vertical = 7.dp),
                        ) {
                            Text(
                                text = filter.label,
                                style = typo().bodySmall.copy(
                                    color = if (isSelected) Color.Black else Color.White,
                                    fontSize = 13.sp,
                                ),
                                maxLines = 1,
                            )
                        }
                    }
                }
            }
        }

        if (searchText.isNotBlank() && (searchUiState is SearchScreenUIState.Success || searchUiState is SearchScreenUIState.Loading)) {
            item(span = { GridItemSpan(2) }) {
                when (searchUiState) {
                    SearchScreenUIState.Loading -> Text("Searching…", color = Color.White, modifier = Modifier.padding(12.dp))
                    SearchScreenUIState.Success -> Column {
                        currentResults.forEachIndexed { index, result ->
                            when (result) {
                                is SongsResult -> {
                                    val track = result.toTrack()
                                    SongFullWidthItems(
                                        track = track,
                                        index = index,
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
                                is VideosResult -> {
                                    val track = result.toTrack()
                                    SongFullWidthItems(
                                        track = track,
                                        index = index,
                                        isPlaying = false,
                                        onClickListener = {
                                            searchViewModel.setQueueData(QueueData.Data(arrayListOf(track), track, "RDAMVM${track.videoId}", searchText, PlaylistType.RADIO, null))
                                            searchViewModel.loadMediaItem(track, type = Config.SONG_CLICK)
                                        },
                                        onAddToQueue = { sharedViewModel.addListToQueue(arrayListOf(track)) },
                                        modifier = Modifier.fillMaxWidth(),
                                    )
                                }
                                is ArtistsResult -> Text(result.artist, color = Color.White, modifier = Modifier.fillMaxWidth().clickable { navController.navigate(ArtistDestination(result.browseId)) }.padding(16.dp))
                                is AlbumsResult -> Text(result.title, color = Color.White, modifier = Modifier.fillMaxWidth().clickable { navController.navigate(AlbumDestination(result.browseId)) }.padding(16.dp))
                                is PlaylistsResult -> Text(result.title, color = Color.White, modifier = Modifier.fillMaxWidth().clickable { navController.navigate(PlaylistDestination(result.browseId)) }.padding(16.dp))
                            }
                        }
                    }
                    else -> Unit
                }
            }
        }

        // 2-Column Angled Card Grid
        items(categories, key = { it.title }) { category ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.6f)
                    .clip(RoundedCornerShape(8.dp))
                    .clickable {
                        navController.navigate(MoodDestination(params = category.params))
                    },
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(containerColor = category.color),
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = category.title,
                        style = typo().titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontSize = 16.sp,
                        ),
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(12.dp),
                    )

                    // Angled rotated album artwork in bottom right corner
                    AsyncImage(
                        model = category.imageUrl,
                        contentDescription = category.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(68.dp)
                            .align(Alignment.BottomEnd)
                            .offset(x = 14.dp, y = 8.dp)
                            .rotate(25f)
                            .shadow(8.dp, RoundedCornerShape(4.dp))
                            .clip(RoundedCornerShape(4.dp)),
                    )
                }
            }
        }

        item(span = { GridItemSpan(2) }) {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}
