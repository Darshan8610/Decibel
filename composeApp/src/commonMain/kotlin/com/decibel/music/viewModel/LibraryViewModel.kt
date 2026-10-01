package com.decibel.music.viewModel

import androidx.lifecycle.viewModelScope
import com.decibel.music.common.Config
import com.decibel.music.common.LibraryChipType
import com.decibel.music.domain.data.entities.AlbumEntity
import com.decibel.music.domain.data.entities.LocalPlaylistEntity
import com.decibel.music.domain.data.entities.PlaylistEntity
import com.decibel.music.domain.data.entities.SongEntity
import com.decibel.music.domain.data.model.searchResult.playlists.PlaylistsResult
import com.decibel.music.domain.data.type.ChartItem
import com.decibel.music.domain.data.type.PlaylistType
import com.decibel.music.domain.data.type.RecentlyType
import com.decibel.music.domain.manager.DataStoreManager
import com.decibel.music.domain.repository.AlbumRepository
import com.decibel.music.domain.repository.CommonRepository
import com.decibel.music.domain.repository.LocalPlaylistRepository
import com.decibel.music.domain.repository.PlaylistRepository
import com.decibel.music.domain.repository.PodcastRepository
import com.decibel.music.domain.repository.SongRepository
import com.decibel.music.domain.repository.SearchRepository
import com.decibel.music.spotify.Spotify
import com.decibel.music.spotify.SpotifyPublicTrack
import com.decibel.music.spotify.SpotifyTrackQuery
import com.decibel.music.domain.data.model.searchResult.songs.SongsResult
import com.decibel.music.domain.utils.LocalResource
import com.decibel.music.domain.utils.Resource
import com.decibel.music.domain.utils.isRadioPlaylistId
import com.decibel.music.domain.utils.toListId
import com.decibel.music.domain.utils.toListName
import com.decibel.music.viewModel.base.BaseViewModel
import kotlin.math.abs
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.sync.withPermit
import kotlinx.datetime.LocalDateTime
import com.decibel.music.composeapp.generated.resources.Res
import com.decibel.music.composeapp.generated.resources.added_local_playlist
import com.decibel.music.composeapp.generated.resources.import_failed
import com.decibel.music.composeapp.generated.resources.import_no_matches
import com.decibel.music.composeapp.generated.resources.import_spotify_success
import com.decibel.music.composeapp.generated.resources.youtube_liked_music

/** UI state for the Spotify playlist import flow, exposed by [LibraryViewModel.spotifyImportState]. */
sealed interface SpotifyImportState {
    data object Idle : SpotifyImportState

    /** Playlist metadata/track list is being fetched from Spotify. */
    data class Fetching(val url: String) : SpotifyImportState

    /** Catalog matching in progress: [matched] of [total] tracks processed. */
    data class Matching(val matched: Int, val total: Int) : SpotifyImportState

    /** Matched tracks are being written to the local database. */
    data object Saving : SpotifyImportState

    /** Finished: [imported] of [total] tracks were saved into [playlistName]. */
    data class Success(
        val playlistName: String,
        val imported: Int,
        val total: Int,
    ) : SpotifyImportState

    /** Finished with an error; [message] is user-readable. */
    data class Failure(val message: String) : SpotifyImportState
}

/** Upper bound on concurrent catalog searches while importing a playlist. */
private const val SPOTIFY_IMPORT_CONCURRENCY = 4

class LibraryViewModel(
    private val dataStoreManager: DataStoreManager,
    private val songRepository: SongRepository,
    private val commonRepository: CommonRepository,
    private val playlistRepository: PlaylistRepository,
    private val localPlaylistRepository: LocalPlaylistRepository,
    private val albumRepository: AlbumRepository,
    private val podcastRepository: PodcastRepository,
    private val searchRepository: SearchRepository,
    private val spotify: Spotify,
) : BaseViewModel() {
    private val _currentScreen: MutableStateFlow<LibraryChipType> = MutableStateFlow(LibraryChipType.YOUR_LIBRARY)
    val currentScreen: StateFlow<LibraryChipType> get() = _currentScreen.asStateFlow()
    private val _recentlyAdded: MutableStateFlow<LocalResource<List<RecentlyType>>> =
        MutableStateFlow(LocalResource.Loading())
    val recentlyAdded: StateFlow<LocalResource<List<RecentlyType>>> get() = _recentlyAdded.asStateFlow()

    private val _yourLocalPlaylist: MutableStateFlow<LocalResource<List<LocalPlaylistEntity>>> =
        MutableStateFlow(LocalResource.Loading())
    val yourLocalPlaylist: StateFlow<LocalResource<List<LocalPlaylistEntity>>> get() = _yourLocalPlaylist.asStateFlow()

    private val _youTubePlaylist: MutableStateFlow<LocalResource<List<PlaylistsResult>>> =
        MutableStateFlow(LocalResource.Loading())
    val youTubePlaylist: StateFlow<LocalResource<List<PlaylistsResult>>> get() = _youTubePlaylist.asStateFlow()

    private val _youTubeMixForYou: MutableStateFlow<LocalResource<List<PlaylistsResult>>> =
        MutableStateFlow(LocalResource.Loading())
    val youTubeMixForYou: StateFlow<LocalResource<List<PlaylistsResult>>> get() = _youTubeMixForYou.asStateFlow()

    private val _favoritePlaylist: MutableStateFlow<LocalResource<List<PlaylistType>>> =
        MutableStateFlow(LocalResource.Loading())
    val favoritePlaylist: StateFlow<LocalResource<List<PlaylistType>>> get() = _favoritePlaylist.asStateFlow()

    private val _favoritePodcasts: MutableStateFlow<LocalResource<List<PlaylistType>>> =
        MutableStateFlow(LocalResource.Loading())
    val favoritePodcasts: StateFlow<LocalResource<List<PlaylistType>>> get() = _favoritePodcasts.asStateFlow()

    private val _downloadedPlaylist: MutableStateFlow<LocalResource<List<PlaylistType>>> =
        MutableStateFlow(LocalResource.Loading())
    val downloadedPlaylist: StateFlow<LocalResource<List<PlaylistType>>> get() = _downloadedPlaylist.asStateFlow()

    private val _chartPlaylists: MutableStateFlow<LocalResource<List<ChartItem>>> =
        MutableStateFlow(LocalResource.Loading())
    val chartPlaylists: StateFlow<LocalResource<List<ChartItem>>> get() = _chartPlaylists.asStateFlow()

    private val _listCanvasSong: MutableStateFlow<LocalResource<List<SongEntity>>> =
        MutableStateFlow(LocalResource.Loading())
    val listCanvasSong: StateFlow<LocalResource<List<SongEntity>>> get() = _listCanvasSong.asStateFlow()

    private val _accountThumbnail: MutableStateFlow<String?> = MutableStateFlow(null)
    val accountThumbnail: StateFlow<String?> get() = _accountThumbnail.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val youtubeLoggedIn = dataStoreManager.loggedIn.mapLatest { it == DataStoreManager.TRUE }

    init {
        viewModelScope.launch {
            val currentScreenJob =
                launch {
                    dataStoreManager.getString("library_current_screen").first()?.let { chipType ->
                        LibraryChipType.fromStringValue(chipType)?.let {
                            _currentScreen.value = it
                        }
                    }
                }
            val cookieJob =
                launch {
                    dataStoreManager.cookie.distinctUntilChanged().collect {
                        _accountThumbnail.value = dataStoreManager.getString("AccountThumbUrl").first().takeIf { !it.isNullOrEmpty() }
                    }
                }
            currentScreenJob.join()
            cookieJob.join()
        }
    }

    fun setCurrentScreen(chipType: LibraryChipType) {
        _currentScreen.value = chipType
        viewModelScope.launch {
            dataStoreManager.putString("library_current_screen", chipType.toStringValue())
        }
    }

    fun getRecentlyAdded() {
        viewModelScope.launch {
            commonRepository.getAllRecentData().collectLatest { data ->
                val temp: MutableList<RecentlyType> = mutableListOf()
                temp.addAll(data)
                temp
                    .find {
                        it is PlaylistEntity && it.id.isRadioPlaylistId()
                    }.let {
                        temp.remove(it)
                    }
                temp.removeIf { it is SongEntity && it.inLibrary == Config.REMOVED_SONG_DATE_TIME }
                if (dataStoreManager.loggedIn.first() == DataStoreManager.TRUE) {
                    temp.removeIf { it is PlaylistEntity && it.id == "LM" }
                    temp.add(
                        PlaylistEntity(
                            title = getString(Res.string.youtube_liked_music),
                            author = "YouTube Music",
                            id = "LM",
                            description = "PIN",
                            thumbnails = "https://www.gstatic.com/youtube/media/ytm/images/pbg/liked-songs-delhi-1200.png",
                        ),
                    )
                }
                temp.reverse()
                _recentlyAdded.value = LocalResource.Success(temp.toImmutableList())
            }
        }
    }

    fun getYouTubePlaylist() {
        _youTubePlaylist.value = LocalResource.Loading()
        viewModelScope.launch {
            playlistRepository.getLibraryPlaylist().collect { data ->
                _youTubePlaylist.value = LocalResource.Success(data ?: emptyList())
            }
        }
    }

    fun getYouTubeMixedForYou() {
        _youTubeMixForYou.value = LocalResource.Loading()
        viewModelScope.launch {
            playlistRepository.getMixedForYou().collect { data ->
                _youTubeMixForYou.value = LocalResource.Success(data ?: emptyList())
            }
        }
    }

    fun getYouTubeLoggedIn(): Boolean = runBlocking { dataStoreManager.loggedIn.first() } == DataStoreManager.TRUE

    fun getPlaylistFavorite() {
        viewModelScope.launch {
            albumRepository.getLikedAlbums().collect { album ->
                val temp: MutableList<PlaylistType> = mutableListOf()
                temp.addAll(album)
                playlistRepository.getLikedPlaylists().collect { playlist ->
                    temp.addAll(playlist)
                    val sortedList =
                        temp.sortedWith<PlaylistType>(
                            Comparator { p0, p1 ->
                                val timeP0: LocalDateTime? =
                                    when (p0) {
                                        is AlbumEntity -> p0.favoriteAt ?: p0.inLibrary
                                        is PlaylistEntity -> p0.favoriteAt ?: p0.inLibrary
                                        else -> null
                                    }
                                val timeP1: LocalDateTime? =
                                    when (p1) {
                                        is AlbumEntity -> p1.favoriteAt ?: p1.inLibrary
                                        is PlaylistEntity -> p1.favoriteAt ?: p1.inLibrary
                                        else -> null
                                    }
                                if (timeP0 == null || timeP1 == null) {
                                    return@Comparator if (timeP0 == null && timeP1 == null) {
                                        0
                                    } else if (timeP0 == null) {
                                        -1
                                    } else {
                                        1
                                    }
                                }
                                timeP0.compareTo(timeP1) // Sort in descending order by inLibrary time
                            },
                        )
                    _favoritePlaylist.value = LocalResource.Success(sortedList)
                }
            }
        }
    }

    fun getFavoritePodcasts() {
        viewModelScope.launch {
            podcastRepository.getFavoritePodcasts().collectLatest { podcasts ->
                val sortedList = podcasts.sortedByDescending { it.favoriteTime }
                _favoritePodcasts.value = LocalResource.Success(sortedList)
            }
        }
    }

    fun getCanvasSong() {
        _listCanvasSong.value = LocalResource.Loading()
        viewModelScope.launch {
            songRepository.getCanvasSong(max = 5).collect { data ->
                _listCanvasSong.value = LocalResource.Success(data)
            }
        }
    }

    fun getLocalPlaylist() {
        _yourLocalPlaylist.value = LocalResource.Loading()
        viewModelScope.launch {
            localPlaylistRepository.getAllLocalPlaylists().collect { values ->
//                    _listLocalPlaylist.postValue(values)
                _yourLocalPlaylist.value = LocalResource.Success(values.reversed())
            }
        }
    }

    fun getDownloadedPlaylist() {
        viewModelScope.launch {
            playlistRepository.getAllDownloadedPlaylist().collect { values ->
                _downloadedPlaylist.value = LocalResource.Success(values)
            }
        }
    }

    fun getChartPlaylists() {
        _chartPlaylists.value = LocalResource.Loading()
        viewModelScope.launch {
            playlistRepository.getChartPlaylist().collectLatest {
                when (it) {
                    is Resource.Success -> _chartPlaylists.value = LocalResource.Success(it.data ?: emptyList())
                    is Resource.Error -> _chartPlaylists.value = LocalResource.Error(it.message ?: "Unknown error")
                }
            }
        }
    }

    fun createPlaylist(title: String) {
        viewModelScope.launch {
            val localPlaylistEntity = LocalPlaylistEntity(title = title)
            localPlaylistRepository
                .insertLocalPlaylist(
                    localPlaylistEntity,
                    getString(Res.string.added_local_playlist),
                ).lastOrNull()
                ?.let {
                    log("Created playlist with id: $it")
                }
            getLocalPlaylist()
        }
    }

    private val _spotifyImportState = MutableStateFlow<SpotifyImportState>(SpotifyImportState.Idle)
    val spotifyImportState: StateFlow<SpotifyImportState> = _spotifyImportState.asStateFlow()

    /** Clears a finished (success/failure) import state. Running imports are never cleared. */
    fun resetSpotifyImportState() {
        if (isSpotifyImportRunning()) return
        _spotifyImportState.value = SpotifyImportState.Idle
    }

    /**
     * Import a public Spotify playlist as a local Decibel playlist.
     *
     * Pipeline: extract the playlist id → fetch the public embed HTML → parse it →
     * match every track against the catalog with bounded parallelism and one retry →
     * save everything in original order inside a single database transaction.
     *
     * Live progress is reported through [spotifyImportState]; terminal failures land in
     * [SpotifyImportState.Failure] with a user-readable message and are also toasted.
     */
    fun importSpotifyPlaylist(url: String) {
        if (isSpotifyImportRunning()) return
        viewModelScope.launch {
            try {
                _spotifyImportState.value = SpotifyImportState.Fetching(url)
                val playlist = spotify.getPublicPlaylist(url).getOrThrow()
                val total = playlist.tracks.size
                _spotifyImportState.value = SpotifyImportState.Matching(0, total)

                val matchedSongs = matchPlaylistTracks(playlist.tracks)
                if (matchedSongs.isEmpty()) {
                    failSpotifyImport(getString(Res.string.import_no_matches))
                    return@launch
                }

                _spotifyImportState.value = SpotifyImportState.Saving
                val playlistName = playlist.name.trim().ifEmpty { "Spotify playlist" }
                localPlaylistRepository.insertImportedPlaylist(
                    LocalPlaylistEntity(title = playlistName),
                    matchedSongs,
                )
                getLocalPlaylist()
                _spotifyImportState.value = SpotifyImportState.Success(playlistName, matchedSongs.size, total)
                makeToast(
                    org.jetbrains.compose.resources.getString(
                        Res.string.import_spotify_success,
                        matchedSongs.size,
                        total,
                        playlistName,
                    ),
                )
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                log("Spotify import failed: ${e.message}")
                failSpotifyImport(e.message ?: getString(Res.string.import_failed))
            }
        }
    }

    private fun isSpotifyImportRunning(): Boolean =
        when (_spotifyImportState.value) {
            is SpotifyImportState.Fetching,
            is SpotifyImportState.Matching,
            is SpotifyImportState.Saving -> true
            else -> false
        }

    private fun failSpotifyImport(message: String) {
        _spotifyImportState.value = SpotifyImportState.Failure(message)
        makeToast(message)
    }

    /**
     * Match every track against the catalog in parallel (bounded by
     * [SPOTIFY_IMPORT_CONCURRENCY]) while keeping the original playlist order.
     */
    private suspend fun matchPlaylistTracks(tracks: List<SpotifyPublicTrack>): List<SongEntity> {
        val total = tracks.size
        val results = arrayOfNulls<SongEntity>(total)
        val progressMutex = Mutex()
        var completed = 0
        coroutineScope {
            val semaphore = Semaphore(SPOTIFY_IMPORT_CONCURRENCY)
            tracks.forEachIndexed { index, track ->
                launch {
                    semaphore.withPermit {
                        results[index] = matchTrack(track)
                        progressMutex.withLock {
                            completed++
                            _spotifyImportState.value = SpotifyImportState.Matching(completed, total)
                        }
                    }
                }
            }
        }
        return results.filterNotNull().distinctBy { it.videoId }
    }

    /**
     * Search the catalog for one Spotify track and convert the closest match (by duration)
     * into a [SongEntity]. Returns null when nothing plausible matched.
     */
    private suspend fun matchTrack(track: SpotifyPublicTrack): SongEntity? {
        val query = SpotifyTrackQuery.build(track.title, track.artists)
        if (query.isBlank()) return null
        var candidates = searchRepository.searchSongCandidates(query)
        if (candidates.isEmpty()) {
            delay(400)
            candidates = searchRepository.searchSongCandidates(query)
        }
        val targetSeconds = if (track.durationMs > 0) track.durationMs / 1000 else null
        val best =
            candidates
                .filter { it.videoId.isNotBlank() }
                .minByOrNull { candidate ->
                    val seconds = candidate.durationSeconds
                    if (targetSeconds != null && seconds != null) abs(seconds - targetSeconds) else Int.MAX_VALUE
                } ?: return null
        val candidateSeconds = best.durationSeconds
        if (targetSeconds != null && candidateSeconds != null && abs(candidateSeconds - targetSeconds) > 60) {
            // The catalog result is over a minute off — almost certainly a different song.
            return null
        }
        return best.toSongEntity(track.durationMs)
    }

    private fun SongsResult.toSongEntity(durationMs: Int): SongEntity =
        SongEntity(
            videoId = videoId,
            albumId = album?.id,
            albumName = album?.name,
            artistId = artists?.toListId(),
            artistName = artists?.toListName(),
            duration = duration ?: "",
            durationSeconds = durationSeconds ?: durationMs / 1000,
            isAvailable = true,
            isExplicit = isExplicit ?: false,
            likeStatus = "INDIFFERENT",
            thumbnails = thumbnails?.firstOrNull()?.url,
            title = title ?: "",
            videoType = videoType ?: "",
            category = category,
            resultType = resultType,
        )

    fun deleteSong(videoId: String) {
        _recentlyAdded.value = LocalResource.Loading()
        viewModelScope.launch {
            songRepository.setInLibrary(videoId, Config.REMOVED_SONG_DATE_TIME)
            songRepository.resetTotalPlayTime(videoId)
            delay(500) // Wait for the database to update
            getRecentlyAdded()
        }
    }
}