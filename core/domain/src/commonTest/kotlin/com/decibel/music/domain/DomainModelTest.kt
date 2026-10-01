package com.decibel.music.domain

import com.decibel.music.domain.data.entities.SongEntity
import com.decibel.music.domain.data.entities.LocalPlaylistEntity
import com.decibel.music.domain.data.player.GenericMediaItem
import com.decibel.music.domain.data.player.GenericMediaMetadata
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class DomainModelTest {

    @Test
    fun testSongEntityCreation() {
        val song = SongEntity(
            videoId = "test_vid_123",
            title = "Test Song Title",
            artistName = listOf("Test Artist"),
            duration = "3:00",
            durationSeconds = 180,
            isAvailable = true,
            isExplicit = false,
            likeStatus = "LIKE",
            videoType = "MUSIC_VIDEO_TYPE_ATV",
            category = "Song",
            resultType = "Song",
        )

        assertEquals("test_vid_123", song.videoId)
        assertEquals("Test Song Title", song.title)
        assertEquals(listOf("Test Artist"), song.artistName)
        assertEquals(180, song.durationSeconds)
    }

    @Test
    fun testLocalPlaylistEntityCreation() {
        val playlist = LocalPlaylistEntity(
            id = 1L,
            title = "My Decibel Playlist",
        )

        assertEquals(1L, playlist.id)
        assertEquals("My Decibel Playlist", playlist.title)
    }

    @Test
    fun testGenericMediaItem() {
        val item = GenericMediaItem(
            mediaId = "media_456",
            uri = "https://example.com/audio.opus",
            metadata = GenericMediaMetadata(
                title = "Decibel Track",
                artist = "Decibel Artist",
            ),
        )

        assertNotNull(item)
        assertEquals("media_456", item.mediaId)
        assertEquals("Decibel Track", item.metadata.title)
    }
}
