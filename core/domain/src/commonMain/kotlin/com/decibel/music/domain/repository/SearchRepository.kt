package com.decibel.music.domain.repository

import com.decibel.music.domain.data.entities.SearchHistory
import com.decibel.music.domain.data.model.searchResult.SearchSuggestions
import com.decibel.music.domain.data.model.searchResult.albums.AlbumsResult
import com.decibel.music.domain.data.model.searchResult.artists.ArtistsResult
import com.decibel.music.domain.data.model.searchResult.playlists.PlaylistsResult
import com.decibel.music.domain.data.model.searchResult.songs.SongsResult
import com.decibel.music.domain.data.model.searchResult.videos.VideosResult
import com.decibel.music.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

interface SearchRepository {
    fun getSearchHistory(): Flow<List<SearchHistory>>

    fun insertSearchHistory(searchHistory: SearchHistory): Flow<Long>

    suspend fun deleteSearchHistory()

    fun getSearchDataSong(query: String): Flow<Resource<ArrayList<SongsResult>>>

    suspend fun searchSongs(query: String): ArrayList<SongsResult>? = getSearchDataSong(query).firstOrNull()?.data

    /**
     * First-page song search without continuation pages: much cheaper than [searchSongs]
     * for bulk operations such as importing a whole playlist. Never throws — returns an
     * empty list when the search failed or nothing matched.
     */
    suspend fun searchSongCandidates(query: String): List<SongsResult>

    fun getSearchDataVideo(query: String): Flow<Resource<ArrayList<VideosResult>>>

    fun getSearchDataPodcast(query: String): Flow<Resource<ArrayList<PlaylistsResult>>>

    fun getSearchDataFeaturedPlaylist(query: String): Flow<Resource<ArrayList<PlaylistsResult>>>

    fun getSearchDataArtist(query: String): Flow<Resource<ArrayList<ArtistsResult>>>

    fun getSearchDataAlbum(query: String): Flow<Resource<ArrayList<AlbumsResult>>>

    fun getSearchDataPlaylist(query: String): Flow<Resource<ArrayList<PlaylistsResult>>>

    fun getSuggestQuery(query: String): Flow<Resource<SearchSuggestions>>
}