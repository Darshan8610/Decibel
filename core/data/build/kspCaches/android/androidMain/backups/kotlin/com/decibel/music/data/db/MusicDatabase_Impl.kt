package com.decibel.music.`data`.db

import DatabaseDao
import DatabaseDao_Impl
import androidx.room.InvalidationTracker
import androidx.room.RoomOpenDelegate
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.room.util.TableInfo
import androidx.room.util.TableInfo.Companion.read
import androidx.room.util.dropFtsSyncTriggers
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import javax.`annotation`.processing.Generated
import kotlin.Lazy
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List
import kotlin.collections.Map
import kotlin.collections.MutableList
import kotlin.collections.MutableMap
import kotlin.collections.MutableSet
import kotlin.collections.Set
import kotlin.collections.mutableListOf
import kotlin.collections.mutableMapOf
import kotlin.collections.mutableSetOf
import kotlin.reflect.KClass

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class MusicDatabase_Impl : MusicDatabase() {
  private val _databaseDao: Lazy<DatabaseDao> = lazy {
    DatabaseDao_Impl(this)
  }

  protected override fun createOpenDelegate(): RoomOpenDelegate {
    val _openDelegate: RoomOpenDelegate = object : RoomOpenDelegate(24, "24e2fa4fbb1e0617287ff4e40986d1dc", "736c1b079fc88d965a74499adc943d5c") {
      public override fun createAllTables(connection: SQLiteConnection) {
        connection.execSQL("CREATE TABLE IF NOT EXISTS `new_format` (`videoId` TEXT NOT NULL, `itag` INTEGER NOT NULL, `mimeType` TEXT, `codecs` TEXT, `bitrate` INTEGER, `sampleRate` INTEGER, `contentLength` INTEGER, `loudnessDb` REAL, `lengthSeconds` INTEGER, `playbackTrackingVideostatsPlaybackUrl` TEXT, `playbackTrackingAtrUrl` TEXT, `playbackTrackingVideostatsWatchtimeUrl` TEXT, `expired_time` INTEGER NOT NULL DEFAULT 0, `cpn` TEXT, `audioUrl` TEXT, `videoUrl` TEXT, `bpm` INTEGER DEFAULT NULL, `music_key` TEXT DEFAULT NULL, `keyScale` TEXT DEFAULT NULL, PRIMARY KEY(`videoId`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `song_info` (`videoId` TEXT NOT NULL, `author` TEXT, `authorId` TEXT, `authorThumbnail` TEXT, `description` TEXT, `subscribers` TEXT, `viewCount` INTEGER, `uploadDate` TEXT, `like` INTEGER, `dislike` INTEGER, PRIMARY KEY(`videoId`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `search_history` (`query` TEXT NOT NULL, PRIMARY KEY(`query`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `song` (`videoId` TEXT NOT NULL, `albumId` TEXT, `albumName` TEXT, `artistId` TEXT, `artistName` TEXT, `duration` TEXT NOT NULL, `durationSeconds` INTEGER NOT NULL, `isAvailable` INTEGER NOT NULL, `isExplicit` INTEGER NOT NULL, `likeStatus` TEXT NOT NULL, `thumbnails` TEXT, `title` TEXT NOT NULL, `videoType` TEXT NOT NULL, `category` TEXT, `resultType` TEXT, `liked` INTEGER NOT NULL, `totalPlayTime` INTEGER NOT NULL, `downloadState` INTEGER NOT NULL, `favoriteAt` INTEGER, `downloadedAt` INTEGER, `inLibrary` INTEGER NOT NULL, `canvasUrl` TEXT, `canvasThumbUrl` TEXT, PRIMARY KEY(`videoId`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `artist` (`channelId` TEXT NOT NULL, `name` TEXT NOT NULL, `thumbnails` TEXT, `followed` INTEGER NOT NULL, `followedAt` INTEGER, `inLibrary` INTEGER NOT NULL, `nameLogoUrl` TEXT, `nameLogoColor` TEXT, PRIMARY KEY(`channelId`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `album` (`browseId` TEXT NOT NULL, `artistId` TEXT, `artistName` TEXT, `audioPlaylistId` TEXT NOT NULL, `description` TEXT NOT NULL, `duration` TEXT, `durationSeconds` INTEGER NOT NULL, `thumbnails` TEXT, `title` TEXT NOT NULL, `trackCount` INTEGER NOT NULL, `tracks` TEXT, `type` TEXT NOT NULL, `year` TEXT, `liked` INTEGER NOT NULL, `inLibrary` INTEGER NOT NULL, `favoriteAt` INTEGER, `downloadedAt` INTEGER, `downloadState` INTEGER NOT NULL, PRIMARY KEY(`browseId`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `playlist` (`id` TEXT NOT NULL, `author` TEXT, `description` TEXT NOT NULL, `duration` TEXT NOT NULL, `durationSeconds` INTEGER NOT NULL, `privacy` TEXT NOT NULL, `thumbnails` TEXT NOT NULL, `title` TEXT NOT NULL, `trackCount` INTEGER NOT NULL, `tracks` TEXT, `year` TEXT, `liked` INTEGER NOT NULL, `inLibrary` INTEGER NOT NULL, `favoriteAt` INTEGER, `downloadedAt` INTEGER, `downloadState` INTEGER NOT NULL, PRIMARY KEY(`id`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `local_playlist` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `title` TEXT NOT NULL, `thumbnail` TEXT, `inLibrary` INTEGER NOT NULL, `downloadedAt` INTEGER, `downloadState` INTEGER NOT NULL, `youtubePlaylistId` TEXT, `youtube_sync_state` INTEGER NOT NULL DEFAULT 0, `tracks` TEXT)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `lyrics` (`videoId` TEXT NOT NULL, `error` INTEGER NOT NULL, `lines` TEXT, `syncType` TEXT, PRIMARY KEY(`videoId`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `queue` (`queueId` INTEGER NOT NULL, `listTrack` TEXT NOT NULL, PRIMARY KEY(`queueId`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `set_video_id` (`videoId` TEXT NOT NULL, `setVideoId` TEXT, `youtubePlaylistId` TEXT NOT NULL, PRIMARY KEY(`videoId`, `youtubePlaylistId`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `pair_song_local_playlist` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `playlistId` INTEGER NOT NULL, `songId` TEXT NOT NULL, `position` INTEGER NOT NULL, `inPlaylist` INTEGER NOT NULL, FOREIGN KEY(`playlistId`) REFERENCES `local_playlist`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE , FOREIGN KEY(`songId`) REFERENCES `song`(`videoId`) ON UPDATE NO ACTION ON DELETE CASCADE )")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_pair_song_local_playlist_playlistId` ON `pair_song_local_playlist` (`playlistId`)")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_pair_song_local_playlist_songId` ON `pair_song_local_playlist` (`songId`)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `GoogleAccountEntity` (`email` TEXT NOT NULL, `name` TEXT NOT NULL, `thumbnailUrl` TEXT NOT NULL, `pageId` TEXT, `cache` TEXT, `isUsed` INTEGER NOT NULL, `netscapeCookie` TEXT, PRIMARY KEY(`email`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `followed_artist_single_and_album` (`channelId` TEXT NOT NULL, `name` TEXT NOT NULL, `single` TEXT NOT NULL, `album` TEXT NOT NULL, PRIMARY KEY(`channelId`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `notification` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `channelId` TEXT NOT NULL, `thumbnail` TEXT, `name` TEXT NOT NULL, `single` TEXT NOT NULL, `album` TEXT NOT NULL, `time` INTEGER NOT NULL, `type` TEXT NOT NULL DEFAULT 'artist', `link` TEXT, `description` TEXT)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `translated_lyrics` (`videoId` TEXT NOT NULL, `language` TEXT NOT NULL, `error` INTEGER NOT NULL, `lines` TEXT, `syncType` TEXT, PRIMARY KEY(`videoId`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `podcast_table` (`podcastId` TEXT NOT NULL, `title` TEXT NOT NULL, `authorId` TEXT NOT NULL, `authorName` TEXT NOT NULL, `authorThumbnail` TEXT, `description` TEXT, `thumbnail` TEXT, `isFavorite` INTEGER NOT NULL, `inLibrary` INTEGER NOT NULL, `favoriteTime` INTEGER, `listEpisodes` TEXT NOT NULL, PRIMARY KEY(`podcastId`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `podcast_episode_table` (`videoId` TEXT NOT NULL, `podcastId` TEXT NOT NULL, `title` TEXT NOT NULL, `authorName` TEXT NOT NULL, `authorId` TEXT NOT NULL, `description` TEXT, `createdDay` TEXT, `durationString` TEXT, `thumbnail` TEXT, PRIMARY KEY(`videoId`), FOREIGN KEY(`podcastId`) REFERENCES `podcast_table`(`podcastId`) ON UPDATE NO ACTION ON DELETE CASCADE )")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_podcast_episode_table_podcastId` ON `podcast_episode_table` (`podcastId`)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `your_youtube_playlist_list` (`emailPageId` TEXT NOT NULL, `listBrowseIds` TEXT NOT NULL, PRIMARY KEY(`emailPageId`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `playback_event` (`eventId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `timestamp` INTEGER NOT NULL, `videoId` TEXT NOT NULL, `albumBrowseId` TEXT, `durationSecond` INTEGER NOT NULL, `listenedSecond` INTEGER NOT NULL)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `event_artist` (`eventId` INTEGER NOT NULL, `channelId` TEXT NOT NULL, `timestamp` INTEGER NOT NULL, PRIMARY KEY(`eventId`, `channelId`), FOREIGN KEY(`eventId`) REFERENCES `playback_event`(`eventId`) ON UPDATE NO ACTION ON DELETE CASCADE )")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_event_artist_eventId` ON `event_artist` (`eventId`)")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_event_artist_channelId` ON `event_artist` (`channelId`)")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_event_artist_timestamp_channelId` ON `event_artist` (`timestamp`, `channelId`)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)")
        connection.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '24e2fa4fbb1e0617287ff4e40986d1dc')")
      }

      public override fun dropAllTables(connection: SQLiteConnection) {
        connection.execSQL("DROP TABLE IF EXISTS `new_format`")
        connection.execSQL("DROP TABLE IF EXISTS `song_info`")
        connection.execSQL("DROP TABLE IF EXISTS `search_history`")
        connection.execSQL("DROP TABLE IF EXISTS `song`")
        connection.execSQL("DROP TABLE IF EXISTS `artist`")
        connection.execSQL("DROP TABLE IF EXISTS `album`")
        connection.execSQL("DROP TABLE IF EXISTS `playlist`")
        connection.execSQL("DROP TABLE IF EXISTS `local_playlist`")
        connection.execSQL("DROP TABLE IF EXISTS `lyrics`")
        connection.execSQL("DROP TABLE IF EXISTS `queue`")
        connection.execSQL("DROP TABLE IF EXISTS `set_video_id`")
        connection.execSQL("DROP TABLE IF EXISTS `pair_song_local_playlist`")
        connection.execSQL("DROP TABLE IF EXISTS `GoogleAccountEntity`")
        connection.execSQL("DROP TABLE IF EXISTS `followed_artist_single_and_album`")
        connection.execSQL("DROP TABLE IF EXISTS `notification`")
        connection.execSQL("DROP TABLE IF EXISTS `translated_lyrics`")
        connection.execSQL("DROP TABLE IF EXISTS `podcast_table`")
        connection.execSQL("DROP TABLE IF EXISTS `podcast_episode_table`")
        connection.execSQL("DROP TABLE IF EXISTS `your_youtube_playlist_list`")
        connection.execSQL("DROP TABLE IF EXISTS `playback_event`")
        connection.execSQL("DROP TABLE IF EXISTS `event_artist`")
      }

      public override fun onCreate(connection: SQLiteConnection) {
      }

      public override fun onOpen(connection: SQLiteConnection) {
        connection.execSQL("PRAGMA foreign_keys = ON")
        internalInitInvalidationTracker(connection)
      }

      public override fun onPreMigrate(connection: SQLiteConnection) {
        dropFtsSyncTriggers(connection)
      }

      public override fun onPostMigrate(connection: SQLiteConnection) {
      }

      public override fun onValidateSchema(connection: SQLiteConnection): RoomOpenDelegate.ValidationResult {
        val _columnsNewFormat: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsNewFormat.put("videoId", TableInfo.Column("videoId", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsNewFormat.put("itag", TableInfo.Column("itag", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsNewFormat.put("mimeType", TableInfo.Column("mimeType", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsNewFormat.put("codecs", TableInfo.Column("codecs", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsNewFormat.put("bitrate", TableInfo.Column("bitrate", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsNewFormat.put("sampleRate", TableInfo.Column("sampleRate", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsNewFormat.put("contentLength", TableInfo.Column("contentLength", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsNewFormat.put("loudnessDb", TableInfo.Column("loudnessDb", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsNewFormat.put("lengthSeconds", TableInfo.Column("lengthSeconds", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsNewFormat.put("playbackTrackingVideostatsPlaybackUrl", TableInfo.Column("playbackTrackingVideostatsPlaybackUrl", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsNewFormat.put("playbackTrackingAtrUrl", TableInfo.Column("playbackTrackingAtrUrl", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsNewFormat.put("playbackTrackingVideostatsWatchtimeUrl", TableInfo.Column("playbackTrackingVideostatsWatchtimeUrl", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsNewFormat.put("expired_time", TableInfo.Column("expired_time", "INTEGER", true, 0, "0", TableInfo.CREATED_FROM_ENTITY))
        _columnsNewFormat.put("cpn", TableInfo.Column("cpn", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsNewFormat.put("audioUrl", TableInfo.Column("audioUrl", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsNewFormat.put("videoUrl", TableInfo.Column("videoUrl", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsNewFormat.put("bpm", TableInfo.Column("bpm", "INTEGER", false, 0, "NULL", TableInfo.CREATED_FROM_ENTITY))
        _columnsNewFormat.put("music_key", TableInfo.Column("music_key", "TEXT", false, 0, "NULL", TableInfo.CREATED_FROM_ENTITY))
        _columnsNewFormat.put("keyScale", TableInfo.Column("keyScale", "TEXT", false, 0, "NULL", TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysNewFormat: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesNewFormat: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoNewFormat: TableInfo = TableInfo("new_format", _columnsNewFormat, _foreignKeysNewFormat, _indicesNewFormat)
        val _existingNewFormat: TableInfo = read(connection, "new_format")
        if (!_infoNewFormat.equals(_existingNewFormat)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |new_format(com.decibel.music.domain.data.entities.NewFormatEntity).
              | Expected:
              |""".trimMargin() + _infoNewFormat + """
              |
              | Found:
              |""".trimMargin() + _existingNewFormat)
        }
        val _columnsSongInfo: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsSongInfo.put("videoId", TableInfo.Column("videoId", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsSongInfo.put("author", TableInfo.Column("author", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsSongInfo.put("authorId", TableInfo.Column("authorId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsSongInfo.put("authorThumbnail", TableInfo.Column("authorThumbnail", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsSongInfo.put("description", TableInfo.Column("description", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsSongInfo.put("subscribers", TableInfo.Column("subscribers", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsSongInfo.put("viewCount", TableInfo.Column("viewCount", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsSongInfo.put("uploadDate", TableInfo.Column("uploadDate", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsSongInfo.put("like", TableInfo.Column("like", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsSongInfo.put("dislike", TableInfo.Column("dislike", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysSongInfo: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesSongInfo: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoSongInfo: TableInfo = TableInfo("song_info", _columnsSongInfo, _foreignKeysSongInfo, _indicesSongInfo)
        val _existingSongInfo: TableInfo = read(connection, "song_info")
        if (!_infoSongInfo.equals(_existingSongInfo)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |song_info(com.decibel.music.domain.data.entities.SongInfoEntity).
              | Expected:
              |""".trimMargin() + _infoSongInfo + """
              |
              | Found:
              |""".trimMargin() + _existingSongInfo)
        }
        val _columnsSearchHistory: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsSearchHistory.put("query", TableInfo.Column("query", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysSearchHistory: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesSearchHistory: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoSearchHistory: TableInfo = TableInfo("search_history", _columnsSearchHistory, _foreignKeysSearchHistory, _indicesSearchHistory)
        val _existingSearchHistory: TableInfo = read(connection, "search_history")
        if (!_infoSearchHistory.equals(_existingSearchHistory)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |search_history(com.decibel.music.domain.data.entities.SearchHistory).
              | Expected:
              |""".trimMargin() + _infoSearchHistory + """
              |
              | Found:
              |""".trimMargin() + _existingSearchHistory)
        }
        val _columnsSong: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsSong.put("videoId", TableInfo.Column("videoId", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsSong.put("albumId", TableInfo.Column("albumId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsSong.put("albumName", TableInfo.Column("albumName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsSong.put("artistId", TableInfo.Column("artistId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsSong.put("artistName", TableInfo.Column("artistName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsSong.put("duration", TableInfo.Column("duration", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsSong.put("durationSeconds", TableInfo.Column("durationSeconds", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsSong.put("isAvailable", TableInfo.Column("isAvailable", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsSong.put("isExplicit", TableInfo.Column("isExplicit", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsSong.put("likeStatus", TableInfo.Column("likeStatus", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsSong.put("thumbnails", TableInfo.Column("thumbnails", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsSong.put("title", TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsSong.put("videoType", TableInfo.Column("videoType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsSong.put("category", TableInfo.Column("category", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsSong.put("resultType", TableInfo.Column("resultType", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsSong.put("liked", TableInfo.Column("liked", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsSong.put("totalPlayTime", TableInfo.Column("totalPlayTime", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsSong.put("downloadState", TableInfo.Column("downloadState", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsSong.put("favoriteAt", TableInfo.Column("favoriteAt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsSong.put("downloadedAt", TableInfo.Column("downloadedAt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsSong.put("inLibrary", TableInfo.Column("inLibrary", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsSong.put("canvasUrl", TableInfo.Column("canvasUrl", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsSong.put("canvasThumbUrl", TableInfo.Column("canvasThumbUrl", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysSong: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesSong: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoSong: TableInfo = TableInfo("song", _columnsSong, _foreignKeysSong, _indicesSong)
        val _existingSong: TableInfo = read(connection, "song")
        if (!_infoSong.equals(_existingSong)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |song(com.decibel.music.domain.data.entities.SongEntity).
              | Expected:
              |""".trimMargin() + _infoSong + """
              |
              | Found:
              |""".trimMargin() + _existingSong)
        }
        val _columnsArtist: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsArtist.put("channelId", TableInfo.Column("channelId", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsArtist.put("name", TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsArtist.put("thumbnails", TableInfo.Column("thumbnails", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsArtist.put("followed", TableInfo.Column("followed", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsArtist.put("followedAt", TableInfo.Column("followedAt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsArtist.put("inLibrary", TableInfo.Column("inLibrary", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsArtist.put("nameLogoUrl", TableInfo.Column("nameLogoUrl", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsArtist.put("nameLogoColor", TableInfo.Column("nameLogoColor", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysArtist: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesArtist: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoArtist: TableInfo = TableInfo("artist", _columnsArtist, _foreignKeysArtist, _indicesArtist)
        val _existingArtist: TableInfo = read(connection, "artist")
        if (!_infoArtist.equals(_existingArtist)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |artist(com.decibel.music.domain.data.entities.ArtistEntity).
              | Expected:
              |""".trimMargin() + _infoArtist + """
              |
              | Found:
              |""".trimMargin() + _existingArtist)
        }
        val _columnsAlbum: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsAlbum.put("browseId", TableInfo.Column("browseId", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsAlbum.put("artistId", TableInfo.Column("artistId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsAlbum.put("artistName", TableInfo.Column("artistName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsAlbum.put("audioPlaylistId", TableInfo.Column("audioPlaylistId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsAlbum.put("description", TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsAlbum.put("duration", TableInfo.Column("duration", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsAlbum.put("durationSeconds", TableInfo.Column("durationSeconds", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsAlbum.put("thumbnails", TableInfo.Column("thumbnails", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsAlbum.put("title", TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsAlbum.put("trackCount", TableInfo.Column("trackCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsAlbum.put("tracks", TableInfo.Column("tracks", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsAlbum.put("type", TableInfo.Column("type", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsAlbum.put("year", TableInfo.Column("year", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsAlbum.put("liked", TableInfo.Column("liked", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsAlbum.put("inLibrary", TableInfo.Column("inLibrary", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsAlbum.put("favoriteAt", TableInfo.Column("favoriteAt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsAlbum.put("downloadedAt", TableInfo.Column("downloadedAt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsAlbum.put("downloadState", TableInfo.Column("downloadState", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysAlbum: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesAlbum: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoAlbum: TableInfo = TableInfo("album", _columnsAlbum, _foreignKeysAlbum, _indicesAlbum)
        val _existingAlbum: TableInfo = read(connection, "album")
        if (!_infoAlbum.equals(_existingAlbum)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |album(com.decibel.music.domain.data.entities.AlbumEntity).
              | Expected:
              |""".trimMargin() + _infoAlbum + """
              |
              | Found:
              |""".trimMargin() + _existingAlbum)
        }
        val _columnsPlaylist: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsPlaylist.put("id", TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsPlaylist.put("author", TableInfo.Column("author", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsPlaylist.put("description", TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsPlaylist.put("duration", TableInfo.Column("duration", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsPlaylist.put("durationSeconds", TableInfo.Column("durationSeconds", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsPlaylist.put("privacy", TableInfo.Column("privacy", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsPlaylist.put("thumbnails", TableInfo.Column("thumbnails", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsPlaylist.put("title", TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsPlaylist.put("trackCount", TableInfo.Column("trackCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsPlaylist.put("tracks", TableInfo.Column("tracks", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsPlaylist.put("year", TableInfo.Column("year", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsPlaylist.put("liked", TableInfo.Column("liked", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsPlaylist.put("inLibrary", TableInfo.Column("inLibrary", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsPlaylist.put("favoriteAt", TableInfo.Column("favoriteAt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsPlaylist.put("downloadedAt", TableInfo.Column("downloadedAt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsPlaylist.put("downloadState", TableInfo.Column("downloadState", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysPlaylist: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesPlaylist: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoPlaylist: TableInfo = TableInfo("playlist", _columnsPlaylist, _foreignKeysPlaylist, _indicesPlaylist)
        val _existingPlaylist: TableInfo = read(connection, "playlist")
        if (!_infoPlaylist.equals(_existingPlaylist)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |playlist(com.decibel.music.domain.data.entities.PlaylistEntity).
              | Expected:
              |""".trimMargin() + _infoPlaylist + """
              |
              | Found:
              |""".trimMargin() + _existingPlaylist)
        }
        val _columnsLocalPlaylist: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsLocalPlaylist.put("id", TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsLocalPlaylist.put("title", TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsLocalPlaylist.put("thumbnail", TableInfo.Column("thumbnail", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsLocalPlaylist.put("inLibrary", TableInfo.Column("inLibrary", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsLocalPlaylist.put("downloadedAt", TableInfo.Column("downloadedAt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsLocalPlaylist.put("downloadState", TableInfo.Column("downloadState", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsLocalPlaylist.put("youtubePlaylistId", TableInfo.Column("youtubePlaylistId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsLocalPlaylist.put("youtube_sync_state", TableInfo.Column("youtube_sync_state", "INTEGER", true, 0, "0", TableInfo.CREATED_FROM_ENTITY))
        _columnsLocalPlaylist.put("tracks", TableInfo.Column("tracks", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysLocalPlaylist: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesLocalPlaylist: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoLocalPlaylist: TableInfo = TableInfo("local_playlist", _columnsLocalPlaylist, _foreignKeysLocalPlaylist, _indicesLocalPlaylist)
        val _existingLocalPlaylist: TableInfo = read(connection, "local_playlist")
        if (!_infoLocalPlaylist.equals(_existingLocalPlaylist)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |local_playlist(com.decibel.music.domain.data.entities.LocalPlaylistEntity).
              | Expected:
              |""".trimMargin() + _infoLocalPlaylist + """
              |
              | Found:
              |""".trimMargin() + _existingLocalPlaylist)
        }
        val _columnsLyrics: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsLyrics.put("videoId", TableInfo.Column("videoId", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsLyrics.put("error", TableInfo.Column("error", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsLyrics.put("lines", TableInfo.Column("lines", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsLyrics.put("syncType", TableInfo.Column("syncType", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysLyrics: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesLyrics: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoLyrics: TableInfo = TableInfo("lyrics", _columnsLyrics, _foreignKeysLyrics, _indicesLyrics)
        val _existingLyrics: TableInfo = read(connection, "lyrics")
        if (!_infoLyrics.equals(_existingLyrics)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |lyrics(com.decibel.music.domain.data.entities.LyricsEntity).
              | Expected:
              |""".trimMargin() + _infoLyrics + """
              |
              | Found:
              |""".trimMargin() + _existingLyrics)
        }
        val _columnsQueue: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsQueue.put("queueId", TableInfo.Column("queueId", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsQueue.put("listTrack", TableInfo.Column("listTrack", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysQueue: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesQueue: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoQueue: TableInfo = TableInfo("queue", _columnsQueue, _foreignKeysQueue, _indicesQueue)
        val _existingQueue: TableInfo = read(connection, "queue")
        if (!_infoQueue.equals(_existingQueue)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |queue(com.decibel.music.domain.data.entities.QueueEntity).
              | Expected:
              |""".trimMargin() + _infoQueue + """
              |
              | Found:
              |""".trimMargin() + _existingQueue)
        }
        val _columnsSetVideoId: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsSetVideoId.put("videoId", TableInfo.Column("videoId", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsSetVideoId.put("setVideoId", TableInfo.Column("setVideoId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsSetVideoId.put("youtubePlaylistId", TableInfo.Column("youtubePlaylistId", "TEXT", true, 2, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysSetVideoId: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesSetVideoId: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoSetVideoId: TableInfo = TableInfo("set_video_id", _columnsSetVideoId, _foreignKeysSetVideoId, _indicesSetVideoId)
        val _existingSetVideoId: TableInfo = read(connection, "set_video_id")
        if (!_infoSetVideoId.equals(_existingSetVideoId)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |set_video_id(com.decibel.music.domain.data.entities.SetVideoIdEntity).
              | Expected:
              |""".trimMargin() + _infoSetVideoId + """
              |
              | Found:
              |""".trimMargin() + _existingSetVideoId)
        }
        val _columnsPairSongLocalPlaylist: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsPairSongLocalPlaylist.put("id", TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsPairSongLocalPlaylist.put("playlistId", TableInfo.Column("playlistId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsPairSongLocalPlaylist.put("songId", TableInfo.Column("songId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsPairSongLocalPlaylist.put("position", TableInfo.Column("position", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsPairSongLocalPlaylist.put("inPlaylist", TableInfo.Column("inPlaylist", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysPairSongLocalPlaylist: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        _foreignKeysPairSongLocalPlaylist.add(TableInfo.ForeignKey("local_playlist", "CASCADE", "NO ACTION", listOf("playlistId"), listOf("id")))
        _foreignKeysPairSongLocalPlaylist.add(TableInfo.ForeignKey("song", "CASCADE", "NO ACTION", listOf("songId"), listOf("videoId")))
        val _indicesPairSongLocalPlaylist: MutableSet<TableInfo.Index> = mutableSetOf()
        _indicesPairSongLocalPlaylist.add(TableInfo.Index("index_pair_song_local_playlist_playlistId", false, listOf("playlistId"), listOf("ASC")))
        _indicesPairSongLocalPlaylist.add(TableInfo.Index("index_pair_song_local_playlist_songId", false, listOf("songId"), listOf("ASC")))
        val _infoPairSongLocalPlaylist: TableInfo = TableInfo("pair_song_local_playlist", _columnsPairSongLocalPlaylist, _foreignKeysPairSongLocalPlaylist, _indicesPairSongLocalPlaylist)
        val _existingPairSongLocalPlaylist: TableInfo = read(connection, "pair_song_local_playlist")
        if (!_infoPairSongLocalPlaylist.equals(_existingPairSongLocalPlaylist)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |pair_song_local_playlist(com.decibel.music.domain.data.entities.PairSongLocalPlaylist).
              | Expected:
              |""".trimMargin() + _infoPairSongLocalPlaylist + """
              |
              | Found:
              |""".trimMargin() + _existingPairSongLocalPlaylist)
        }
        val _columnsGoogleAccountEntity: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsGoogleAccountEntity.put("email", TableInfo.Column("email", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsGoogleAccountEntity.put("name", TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsGoogleAccountEntity.put("thumbnailUrl", TableInfo.Column("thumbnailUrl", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsGoogleAccountEntity.put("pageId", TableInfo.Column("pageId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsGoogleAccountEntity.put("cache", TableInfo.Column("cache", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsGoogleAccountEntity.put("isUsed", TableInfo.Column("isUsed", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsGoogleAccountEntity.put("netscapeCookie", TableInfo.Column("netscapeCookie", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysGoogleAccountEntity: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesGoogleAccountEntity: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoGoogleAccountEntity: TableInfo = TableInfo("GoogleAccountEntity", _columnsGoogleAccountEntity, _foreignKeysGoogleAccountEntity, _indicesGoogleAccountEntity)
        val _existingGoogleAccountEntity: TableInfo = read(connection, "GoogleAccountEntity")
        if (!_infoGoogleAccountEntity.equals(_existingGoogleAccountEntity)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |GoogleAccountEntity(com.decibel.music.domain.data.entities.GoogleAccountEntity).
              | Expected:
              |""".trimMargin() + _infoGoogleAccountEntity + """
              |
              | Found:
              |""".trimMargin() + _existingGoogleAccountEntity)
        }
        val _columnsFollowedArtistSingleAndAlbum: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsFollowedArtistSingleAndAlbum.put("channelId", TableInfo.Column("channelId", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsFollowedArtistSingleAndAlbum.put("name", TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsFollowedArtistSingleAndAlbum.put("single", TableInfo.Column("single", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsFollowedArtistSingleAndAlbum.put("album", TableInfo.Column("album", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysFollowedArtistSingleAndAlbum: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesFollowedArtistSingleAndAlbum: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoFollowedArtistSingleAndAlbum: TableInfo = TableInfo("followed_artist_single_and_album", _columnsFollowedArtistSingleAndAlbum, _foreignKeysFollowedArtistSingleAndAlbum, _indicesFollowedArtistSingleAndAlbum)
        val _existingFollowedArtistSingleAndAlbum: TableInfo = read(connection, "followed_artist_single_and_album")
        if (!_infoFollowedArtistSingleAndAlbum.equals(_existingFollowedArtistSingleAndAlbum)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |followed_artist_single_and_album(com.decibel.music.domain.data.entities.FollowedArtistSingleAndAlbum).
              | Expected:
              |""".trimMargin() + _infoFollowedArtistSingleAndAlbum + """
              |
              | Found:
              |""".trimMargin() + _existingFollowedArtistSingleAndAlbum)
        }
        val _columnsNotification: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsNotification.put("id", TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsNotification.put("channelId", TableInfo.Column("channelId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsNotification.put("thumbnail", TableInfo.Column("thumbnail", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsNotification.put("name", TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsNotification.put("single", TableInfo.Column("single", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsNotification.put("album", TableInfo.Column("album", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsNotification.put("time", TableInfo.Column("time", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsNotification.put("type", TableInfo.Column("type", "TEXT", true, 0, "'artist'", TableInfo.CREATED_FROM_ENTITY))
        _columnsNotification.put("link", TableInfo.Column("link", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsNotification.put("description", TableInfo.Column("description", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysNotification: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesNotification: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoNotification: TableInfo = TableInfo("notification", _columnsNotification, _foreignKeysNotification, _indicesNotification)
        val _existingNotification: TableInfo = read(connection, "notification")
        if (!_infoNotification.equals(_existingNotification)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |notification(com.decibel.music.domain.data.entities.NotificationEntity).
              | Expected:
              |""".trimMargin() + _infoNotification + """
              |
              | Found:
              |""".trimMargin() + _existingNotification)
        }
        val _columnsTranslatedLyrics: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsTranslatedLyrics.put("videoId", TableInfo.Column("videoId", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsTranslatedLyrics.put("language", TableInfo.Column("language", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsTranslatedLyrics.put("error", TableInfo.Column("error", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsTranslatedLyrics.put("lines", TableInfo.Column("lines", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsTranslatedLyrics.put("syncType", TableInfo.Column("syncType", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysTranslatedLyrics: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesTranslatedLyrics: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoTranslatedLyrics: TableInfo = TableInfo("translated_lyrics", _columnsTranslatedLyrics, _foreignKeysTranslatedLyrics, _indicesTranslatedLyrics)
        val _existingTranslatedLyrics: TableInfo = read(connection, "translated_lyrics")
        if (!_infoTranslatedLyrics.equals(_existingTranslatedLyrics)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |translated_lyrics(com.decibel.music.domain.data.entities.TranslatedLyricsEntity).
              | Expected:
              |""".trimMargin() + _infoTranslatedLyrics + """
              |
              | Found:
              |""".trimMargin() + _existingTranslatedLyrics)
        }
        val _columnsPodcastTable: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsPodcastTable.put("podcastId", TableInfo.Column("podcastId", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsPodcastTable.put("title", TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsPodcastTable.put("authorId", TableInfo.Column("authorId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsPodcastTable.put("authorName", TableInfo.Column("authorName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsPodcastTable.put("authorThumbnail", TableInfo.Column("authorThumbnail", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsPodcastTable.put("description", TableInfo.Column("description", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsPodcastTable.put("thumbnail", TableInfo.Column("thumbnail", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsPodcastTable.put("isFavorite", TableInfo.Column("isFavorite", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsPodcastTable.put("inLibrary", TableInfo.Column("inLibrary", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsPodcastTable.put("favoriteTime", TableInfo.Column("favoriteTime", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsPodcastTable.put("listEpisodes", TableInfo.Column("listEpisodes", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysPodcastTable: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesPodcastTable: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoPodcastTable: TableInfo = TableInfo("podcast_table", _columnsPodcastTable, _foreignKeysPodcastTable, _indicesPodcastTable)
        val _existingPodcastTable: TableInfo = read(connection, "podcast_table")
        if (!_infoPodcastTable.equals(_existingPodcastTable)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |podcast_table(com.decibel.music.domain.data.entities.PodcastsEntity).
              | Expected:
              |""".trimMargin() + _infoPodcastTable + """
              |
              | Found:
              |""".trimMargin() + _existingPodcastTable)
        }
        val _columnsPodcastEpisodeTable: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsPodcastEpisodeTable.put("videoId", TableInfo.Column("videoId", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsPodcastEpisodeTable.put("podcastId", TableInfo.Column("podcastId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsPodcastEpisodeTable.put("title", TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsPodcastEpisodeTable.put("authorName", TableInfo.Column("authorName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsPodcastEpisodeTable.put("authorId", TableInfo.Column("authorId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsPodcastEpisodeTable.put("description", TableInfo.Column("description", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsPodcastEpisodeTable.put("createdDay", TableInfo.Column("createdDay", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsPodcastEpisodeTable.put("durationString", TableInfo.Column("durationString", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsPodcastEpisodeTable.put("thumbnail", TableInfo.Column("thumbnail", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysPodcastEpisodeTable: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        _foreignKeysPodcastEpisodeTable.add(TableInfo.ForeignKey("podcast_table", "CASCADE", "NO ACTION", listOf("podcastId"), listOf("podcastId")))
        val _indicesPodcastEpisodeTable: MutableSet<TableInfo.Index> = mutableSetOf()
        _indicesPodcastEpisodeTable.add(TableInfo.Index("index_podcast_episode_table_podcastId", false, listOf("podcastId"), listOf("ASC")))
        val _infoPodcastEpisodeTable: TableInfo = TableInfo("podcast_episode_table", _columnsPodcastEpisodeTable, _foreignKeysPodcastEpisodeTable, _indicesPodcastEpisodeTable)
        val _existingPodcastEpisodeTable: TableInfo = read(connection, "podcast_episode_table")
        if (!_infoPodcastEpisodeTable.equals(_existingPodcastEpisodeTable)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |podcast_episode_table(com.decibel.music.domain.data.entities.EpisodeEntity).
              | Expected:
              |""".trimMargin() + _infoPodcastEpisodeTable + """
              |
              | Found:
              |""".trimMargin() + _existingPodcastEpisodeTable)
        }
        val _columnsYourYoutubePlaylistList: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsYourYoutubePlaylistList.put("emailPageId", TableInfo.Column("emailPageId", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsYourYoutubePlaylistList.put("listBrowseIds", TableInfo.Column("listBrowseIds", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysYourYoutubePlaylistList: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesYourYoutubePlaylistList: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoYourYoutubePlaylistList: TableInfo = TableInfo("your_youtube_playlist_list", _columnsYourYoutubePlaylistList, _foreignKeysYourYoutubePlaylistList, _indicesYourYoutubePlaylistList)
        val _existingYourYoutubePlaylistList: TableInfo = read(connection, "your_youtube_playlist_list")
        if (!_infoYourYoutubePlaylistList.equals(_existingYourYoutubePlaylistList)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |your_youtube_playlist_list(com.decibel.music.domain.data.entities.YourYouTubePlaylistList).
              | Expected:
              |""".trimMargin() + _infoYourYoutubePlaylistList + """
              |
              | Found:
              |""".trimMargin() + _existingYourYoutubePlaylistList)
        }
        val _columnsPlaybackEvent: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsPlaybackEvent.put("eventId", TableInfo.Column("eventId", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsPlaybackEvent.put("timestamp", TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsPlaybackEvent.put("videoId", TableInfo.Column("videoId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsPlaybackEvent.put("albumBrowseId", TableInfo.Column("albumBrowseId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsPlaybackEvent.put("durationSecond", TableInfo.Column("durationSecond", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsPlaybackEvent.put("listenedSecond", TableInfo.Column("listenedSecond", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysPlaybackEvent: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesPlaybackEvent: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoPlaybackEvent: TableInfo = TableInfo("playback_event", _columnsPlaybackEvent, _foreignKeysPlaybackEvent, _indicesPlaybackEvent)
        val _existingPlaybackEvent: TableInfo = read(connection, "playback_event")
        if (!_infoPlaybackEvent.equals(_existingPlaybackEvent)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |playback_event(com.decibel.music.domain.data.entities.analytics.PlaybackEventEntity).
              | Expected:
              |""".trimMargin() + _infoPlaybackEvent + """
              |
              | Found:
              |""".trimMargin() + _existingPlaybackEvent)
        }
        val _columnsEventArtist: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsEventArtist.put("eventId", TableInfo.Column("eventId", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsEventArtist.put("channelId", TableInfo.Column("channelId", "TEXT", true, 2, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsEventArtist.put("timestamp", TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysEventArtist: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        _foreignKeysEventArtist.add(TableInfo.ForeignKey("playback_event", "CASCADE", "NO ACTION", listOf("eventId"), listOf("eventId")))
        val _indicesEventArtist: MutableSet<TableInfo.Index> = mutableSetOf()
        _indicesEventArtist.add(TableInfo.Index("index_event_artist_eventId", false, listOf("eventId"), listOf("ASC")))
        _indicesEventArtist.add(TableInfo.Index("index_event_artist_channelId", false, listOf("channelId"), listOf("ASC")))
        _indicesEventArtist.add(TableInfo.Index("index_event_artist_timestamp_channelId", false, listOf("timestamp", "channelId"), listOf("ASC", "ASC")))
        val _infoEventArtist: TableInfo = TableInfo("event_artist", _columnsEventArtist, _foreignKeysEventArtist, _indicesEventArtist)
        val _existingEventArtist: TableInfo = read(connection, "event_artist")
        if (!_infoEventArtist.equals(_existingEventArtist)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |event_artist(com.decibel.music.domain.data.entities.analytics.EventArtistEntity).
              | Expected:
              |""".trimMargin() + _infoEventArtist + """
              |
              | Found:
              |""".trimMargin() + _existingEventArtist)
        }
        return RoomOpenDelegate.ValidationResult(true, null)
      }
    }
    return _openDelegate
  }

  protected override fun createInvalidationTracker(): InvalidationTracker {
    val _shadowTablesMap: MutableMap<String, String> = mutableMapOf()
    val _viewTables: MutableMap<String, Set<String>> = mutableMapOf()
    return InvalidationTracker(this, _shadowTablesMap, _viewTables, "new_format", "song_info", "search_history", "song", "artist", "album", "playlist", "local_playlist", "lyrics", "queue", "set_video_id", "pair_song_local_playlist", "GoogleAccountEntity", "followed_artist_single_and_album", "notification", "translated_lyrics", "podcast_table", "podcast_episode_table", "your_youtube_playlist_list", "playback_event", "event_artist")
  }

  public override fun clearAllTables() {
    super.performClear(true, "new_format", "song_info", "search_history", "song", "artist", "album", "playlist", "local_playlist", "lyrics", "queue", "set_video_id", "pair_song_local_playlist", "GoogleAccountEntity", "followed_artist_single_and_album", "notification", "translated_lyrics", "podcast_table", "podcast_episode_table", "your_youtube_playlist_list", "playback_event", "event_artist")
  }

  protected override fun getRequiredTypeConverterClasses(): Map<KClass<*>, List<KClass<*>>> {
    val _typeConvertersMap: MutableMap<KClass<*>, List<KClass<*>>> = mutableMapOf()
    _typeConvertersMap.put(DatabaseDao::class, DatabaseDao_Impl.getRequiredConverters())
    return _typeConvertersMap
  }

  public override fun getRequiredAutoMigrationSpecClasses(): Set<KClass<out AutoMigrationSpec>> {
    val _autoMigrationSpecsSet: MutableSet<KClass<out AutoMigrationSpec>> = mutableSetOf()
    return _autoMigrationSpecsSet
  }

  public override fun createAutoMigrations(autoMigrationSpecs: Map<KClass<out AutoMigrationSpec>, AutoMigrationSpec>): List<Migration> {
    val _autoMigrations: MutableList<Migration> = mutableListOf()
    _autoMigrations.add(MusicDatabase_AutoMigration_2_3_Impl())
    _autoMigrations.add(MusicDatabase_AutoMigration_1_3_Impl())
    _autoMigrations.add(MusicDatabase_AutoMigration_3_4_Impl())
    _autoMigrations.add(MusicDatabase_AutoMigration_2_4_Impl())
    _autoMigrations.add(MusicDatabase_AutoMigration_3_5_Impl())
    _autoMigrations.add(MusicDatabase_AutoMigration_4_5_Impl())
    _autoMigrations.add(MusicDatabase_AutoMigration_6_7_Impl())
    _autoMigrations.add(MusicDatabase_AutoMigration_7_8_Impl())
    _autoMigrations.add(MusicDatabase_AutoMigration_8_9_Impl())
    _autoMigrations.add(MusicDatabase_AutoMigration_9_10_Impl())
    _autoMigrations.add(MusicDatabase_AutoMigration_11_12_Impl())
    _autoMigrations.add(MusicDatabase_AutoMigration_13_14_Impl())
    _autoMigrations.add(MusicDatabase_AutoMigration_14_15_Impl())
    _autoMigrations.add(MusicDatabase_AutoMigration_15_16_Impl())
    _autoMigrations.add(MusicDatabase_AutoMigration_16_17_Impl())
    _autoMigrations.add(MusicDatabase_AutoMigration_17_18_Impl())
    _autoMigrations.add(MusicDatabase_AutoMigration_16_18_Impl())
    _autoMigrations.add(MusicDatabase_AutoMigration_15_18_Impl())
    _autoMigrations.add(MusicDatabase_AutoMigration_18_19_Impl())
    _autoMigrations.add(MusicDatabase_AutoMigration_17_19_Impl())
    _autoMigrations.add(MusicDatabase_AutoMigration_16_19_Impl())
    _autoMigrations.add(MusicDatabase_AutoMigration_19_20_Impl())
    _autoMigrations.add(MusicDatabase_AutoMigration_18_20_Impl())
    _autoMigrations.add(MusicDatabase_AutoMigration_17_20_Impl())
    _autoMigrations.add(MusicDatabase_AutoMigration_20_21_Impl())
    _autoMigrations.add(MusicDatabase_AutoMigration_19_21_Impl())
    _autoMigrations.add(MusicDatabase_AutoMigration_18_21_Impl())
    _autoMigrations.add(MusicDatabase_AutoMigration_21_22_Impl())
    _autoMigrations.add(MusicDatabase_AutoMigration_20_22_Impl())
    _autoMigrations.add(MusicDatabase_AutoMigration_19_22_Impl())
    _autoMigrations.add(MusicDatabase_AutoMigration_22_23_Impl())
    _autoMigrations.add(MusicDatabase_AutoMigration_21_23_Impl())
    _autoMigrations.add(MusicDatabase_AutoMigration_20_23_Impl())
    _autoMigrations.add(MusicDatabase_AutoMigration_23_24_Impl())
    _autoMigrations.add(MusicDatabase_AutoMigration_22_24_Impl())
    _autoMigrations.add(MusicDatabase_AutoMigration_21_24_Impl())
    return _autoMigrations
  }

  public override fun getDatabaseDao(): DatabaseDao = _databaseDao.value
}
