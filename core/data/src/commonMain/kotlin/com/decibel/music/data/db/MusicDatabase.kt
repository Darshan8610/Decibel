package com.decibel.music.data.db

import DatabaseDao
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.decibel.music.domain.data.entities.AlbumEntity
import com.decibel.music.domain.data.entities.ArtistEntity
import com.decibel.music.domain.data.entities.EpisodeEntity
import com.decibel.music.domain.data.entities.FollowedArtistSingleAndAlbum
import com.decibel.music.domain.data.entities.GoogleAccountEntity
import com.decibel.music.domain.data.entities.LocalPlaylistEntity
import com.decibel.music.domain.data.entities.LyricsEntity
import com.decibel.music.domain.data.entities.NewFormatEntity
import com.decibel.music.domain.data.entities.NotificationEntity
import com.decibel.music.domain.data.entities.PairSongLocalPlaylist
import com.decibel.music.domain.data.entities.PlaylistEntity
import com.decibel.music.domain.data.entities.PodcastsEntity
import com.decibel.music.domain.data.entities.QueueEntity
import com.decibel.music.domain.data.entities.SearchHistory
import com.decibel.music.domain.data.entities.SetVideoIdEntity
import com.decibel.music.domain.data.entities.SongEntity
import com.decibel.music.domain.data.entities.SongInfoEntity
import com.decibel.music.domain.data.entities.TranslatedLyricsEntity
import com.decibel.music.domain.data.entities.YourYouTubePlaylistList
import com.decibel.music.domain.data.entities.analytics.EventArtistEntity
import com.decibel.music.domain.data.entities.analytics.PlaybackEventEntity

@Database(
    entities = [
        NewFormatEntity::class, SongInfoEntity::class, SearchHistory::class, SongEntity::class, ArtistEntity::class,
        AlbumEntity::class, PlaylistEntity::class, LocalPlaylistEntity::class, LyricsEntity::class, QueueEntity::class,
        SetVideoIdEntity::class, PairSongLocalPlaylist::class, GoogleAccountEntity::class, FollowedArtistSingleAndAlbum::class,
        NotificationEntity::class, TranslatedLyricsEntity::class, PodcastsEntity::class, EpisodeEntity::class,
        YourYouTubePlaylistList::class, PlaybackEventEntity::class, EventArtistEntity::class
    ],
    version = 24,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(from = 2, to = 3), AutoMigration(
            from = 1,
            to = 3,
        ), AutoMigration(from = 3, to = 4), AutoMigration(from = 2, to = 4), AutoMigration(
            from = 3,
            to = 5,
        ), AutoMigration(4, 5), AutoMigration(6, 7), AutoMigration(
            7,
            8,
            spec = AutoMigration7_8::class,
        ), AutoMigration(8, 9),
        AutoMigration(9, 10),
        AutoMigration(from = 11, to = 12, spec = AutoMigration11_12::class),
        AutoMigration(13, 14),
        AutoMigration(14, 15),
        AutoMigration(15, 16),
        AutoMigration(16, 17),
        AutoMigration(17, 18),
        AutoMigration(16, 18),
        AutoMigration(15, 18),
        AutoMigration(18, 19),
        AutoMigration(17, 19),
        AutoMigration(16, 19),
        AutoMigration(19, 20),
        AutoMigration(18, 20),
        AutoMigration(17, 20),
        AutoMigration(20, 21),
        AutoMigration(19, 21),
        AutoMigration(18, 21),
        AutoMigration(21, 22),
        AutoMigration(20, 22),
        AutoMigration(19, 22),
        AutoMigration(22, 23),
        AutoMigration(21, 23),
        AutoMigration(20, 23),
        AutoMigration(23, 24),
        AutoMigration(22, 24),
        AutoMigration(21, 24),
    ],
)
@TypeConverters(Converters::class)
abstract class MusicDatabase : RoomDatabase() {
    abstract fun getDatabaseDao(): DatabaseDao
}

expect fun getDatabaseBuilder(converters: Converters): RoomDatabase.Builder<MusicDatabase>

expect fun getDatabasePath(): String