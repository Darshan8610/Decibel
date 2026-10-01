import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.RoomRawQuery
import androidx.room.coroutines.createFlow
import androidx.room.util.appendPlaceholders
import androidx.room.util.getColumnIndex
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.getTotalChangedRows
import androidx.room.util.performInTransactionSuspending
import androidx.room.util.performSuspending
import androidx.room.util.recursiveFetchMap
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.SQLiteStatement
import com.decibel.music.`data`.db.Converters
import com.decibel.music.domain.`data`.entities.AlbumEntity
import com.decibel.music.domain.`data`.entities.ArtistEntity
import com.decibel.music.domain.`data`.entities.EpisodeEntity
import com.decibel.music.domain.`data`.entities.FollowedArtistSingleAndAlbum
import com.decibel.music.domain.`data`.entities.GoogleAccountEntity
import com.decibel.music.domain.`data`.entities.LocalPlaylistEntity
import com.decibel.music.domain.`data`.entities.LyricsEntity
import com.decibel.music.domain.`data`.entities.NewFormatEntity
import com.decibel.music.domain.`data`.entities.NotificationEntity
import com.decibel.music.domain.`data`.entities.PairSongLocalPlaylist
import com.decibel.music.domain.`data`.entities.PlaylistEntity
import com.decibel.music.domain.`data`.entities.PodcastWithEpisodes
import com.decibel.music.domain.`data`.entities.PodcastsEntity
import com.decibel.music.domain.`data`.entities.QueueEntity
import com.decibel.music.domain.`data`.entities.SearchHistory
import com.decibel.music.domain.`data`.entities.SetVideoIdEntity
import com.decibel.music.domain.`data`.entities.SongEntity
import com.decibel.music.domain.`data`.entities.SongInfoEntity
import com.decibel.music.domain.`data`.entities.TranslatedLyricsEntity
import com.decibel.music.domain.`data`.entities.YourYouTubePlaylistList
import com.decibel.music.domain.`data`.entities.analytics.EventArtistEntity
import com.decibel.music.domain.`data`.entities.analytics.PlaybackEventEntity
import com.decibel.music.domain.`data`.entities.analytics.query.TopPlayedAlbum
import com.decibel.music.domain.`data`.entities.analytics.query.TopPlayedArtist
import com.decibel.music.domain.`data`.entities.analytics.query.TopPlayedTracks
import com.decibel.music.domain.`data`.model.browse.album.Track
import com.decibel.music.domain.`data`.model.metadata.Line
import com.decibel.music.domain.`data`.type.PlaylistType
import com.decibel.music.domain.`data`.type.RecentlyType
import javax.`annotation`.processing.Generated
import kotlin.Boolean
import kotlin.Float
import kotlin.Int
import kotlin.Lazy
import kotlin.Long
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlin.collections.List
import kotlin.collections.Map
import kotlin.collections.MutableList
import kotlin.collections.MutableMap
import kotlin.collections.Set
import kotlin.collections.mutableListOf
import kotlin.collections.mutableMapOf
import kotlin.reflect.KClass
import kotlin.text.StringBuilder
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDateTime

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class DatabaseDao_Impl(
  __db: RoomDatabase,
) : DatabaseDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfSearchHistory: EntityInsertAdapter<SearchHistory>

  private val __insertAdapterOfSongEntity: EntityInsertAdapter<SongEntity>

  private val __converters: Lazy<Converters> = lazy {
    checkNotNull(__db.getTypeConverter(Converters::class))
  }

  private val __insertAdapterOfArtistEntity: EntityInsertAdapter<ArtistEntity>

  private val __insertAdapterOfAlbumEntity: EntityInsertAdapter<AlbumEntity>

  private val __insertAdapterOfPlaylistEntity: EntityInsertAdapter<PlaylistEntity>

  private val __insertAdapterOfPlaylistEntity_1: EntityInsertAdapter<PlaylistEntity>

  private val __insertAdapterOfLocalPlaylistEntity: EntityInsertAdapter<LocalPlaylistEntity>

  private val __insertAdapterOfLyricsEntity: EntityInsertAdapter<LyricsEntity>

  private val __insertAdapterOfNewFormatEntity: EntityInsertAdapter<NewFormatEntity>

  private val __insertAdapterOfSongInfoEntity: EntityInsertAdapter<SongInfoEntity>

  private val __insertAdapterOfQueueEntity: EntityInsertAdapter<QueueEntity>

  private val __insertAdapterOfSetVideoIdEntity: EntityInsertAdapter<SetVideoIdEntity>

  private val __insertAdapterOfPairSongLocalPlaylist: EntityInsertAdapter<PairSongLocalPlaylist>

  private val __insertAdapterOfGoogleAccountEntity: EntityInsertAdapter<GoogleAccountEntity>

  private val __insertAdapterOfFollowedArtistSingleAndAlbum:
      EntityInsertAdapter<FollowedArtistSingleAndAlbum>

  private val __insertAdapterOfNotificationEntity: EntityInsertAdapter<NotificationEntity>

  private val __insertAdapterOfTranslatedLyricsEntity: EntityInsertAdapter<TranslatedLyricsEntity>

  private val __insertAdapterOfPodcastsEntity: EntityInsertAdapter<PodcastsEntity>

  private val __insertAdapterOfEpisodeEntity: EntityInsertAdapter<EpisodeEntity>

  private val __insertAdapterOfYourYouTubePlaylistList: EntityInsertAdapter<YourYouTubePlaylistList>

  private val __insertAdapterOfPlaybackEventEntity: EntityInsertAdapter<PlaybackEventEntity>

  private val __insertAdapterOfEventArtistEntity: EntityInsertAdapter<EventArtistEntity>

  private val __updateAdapterOfNewFormatEntity: EntityDeleteOrUpdateAdapter<NewFormatEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfSearchHistory = object : EntityInsertAdapter<SearchHistory>() {
      protected override fun createQuery(): String = "INSERT OR REPLACE INTO `search_history` (`query`) VALUES (?)"

      protected override fun bind(statement: SQLiteStatement, entity: SearchHistory) {
        statement.bindText(1, entity.query)
      }
    }
    this.__insertAdapterOfSongEntity = object : EntityInsertAdapter<SongEntity>() {
      protected override fun createQuery(): String = "INSERT OR IGNORE INTO `song` (`videoId`,`albumId`,`albumName`,`artistId`,`artistName`,`duration`,`durationSeconds`,`isAvailable`,`isExplicit`,`likeStatus`,`thumbnails`,`title`,`videoType`,`category`,`resultType`,`liked`,`totalPlayTime`,`downloadState`,`favoriteAt`,`downloadedAt`,`inLibrary`,`canvasUrl`,`canvasThumbUrl`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: SongEntity) {
        statement.bindText(1, entity.videoId)
        val _tmpAlbumId: String? = entity.albumId
        if (_tmpAlbumId == null) {
          statement.bindNull(2)
        } else {
          statement.bindText(2, _tmpAlbumId)
        }
        val _tmpAlbumName: String? = entity.albumName
        if (_tmpAlbumName == null) {
          statement.bindNull(3)
        } else {
          statement.bindText(3, _tmpAlbumName)
        }
        val _tmpArtistId: List<String>? = entity.artistId
        val _tmp: String? = __converters().fromArrayList(_tmpArtistId)
        if (_tmp == null) {
          statement.bindNull(4)
        } else {
          statement.bindText(4, _tmp)
        }
        val _tmpArtistName: List<String>? = entity.artistName
        val _tmp_1: String? = __converters().fromArrayList(_tmpArtistName)
        if (_tmp_1 == null) {
          statement.bindNull(5)
        } else {
          statement.bindText(5, _tmp_1)
        }
        statement.bindText(6, entity.duration)
        statement.bindLong(7, entity.durationSeconds.toLong())
        val _tmp_2: Int = if (entity.isAvailable) 1 else 0
        statement.bindLong(8, _tmp_2.toLong())
        val _tmp_3: Int = if (entity.isExplicit) 1 else 0
        statement.bindLong(9, _tmp_3.toLong())
        statement.bindText(10, entity.likeStatus)
        val _tmpThumbnails: String? = entity.thumbnails
        if (_tmpThumbnails == null) {
          statement.bindNull(11)
        } else {
          statement.bindText(11, _tmpThumbnails)
        }
        statement.bindText(12, entity.title)
        statement.bindText(13, entity.videoType)
        val _tmpCategory: String? = entity.category
        if (_tmpCategory == null) {
          statement.bindNull(14)
        } else {
          statement.bindText(14, _tmpCategory)
        }
        val _tmpResultType: String? = entity.resultType
        if (_tmpResultType == null) {
          statement.bindNull(15)
        } else {
          statement.bindText(15, _tmpResultType)
        }
        val _tmp_4: Int = if (entity.liked) 1 else 0
        statement.bindLong(16, _tmp_4.toLong())
        statement.bindLong(17, entity.totalPlayTime)
        statement.bindLong(18, entity.downloadState.toLong())
        val _tmpFavoriteAt: LocalDateTime? = entity.favoriteAt
        val _tmp_5: Long? = __converters().dateToTimestamp(_tmpFavoriteAt)
        if (_tmp_5 == null) {
          statement.bindNull(19)
        } else {
          statement.bindLong(19, _tmp_5)
        }
        val _tmpDownloadedAt: LocalDateTime? = entity.downloadedAt
        val _tmp_6: Long? = __converters().dateToTimestamp(_tmpDownloadedAt)
        if (_tmp_6 == null) {
          statement.bindNull(20)
        } else {
          statement.bindLong(20, _tmp_6)
        }
        val _tmp_7: Long? = __converters().dateToTimestamp(entity.inLibrary)
        if (_tmp_7 == null) {
          statement.bindNull(21)
        } else {
          statement.bindLong(21, _tmp_7)
        }
        val _tmpCanvasUrl: String? = entity.canvasUrl
        if (_tmpCanvasUrl == null) {
          statement.bindNull(22)
        } else {
          statement.bindText(22, _tmpCanvasUrl)
        }
        val _tmpCanvasThumbUrl: String? = entity.canvasThumbUrl
        if (_tmpCanvasThumbUrl == null) {
          statement.bindNull(23)
        } else {
          statement.bindText(23, _tmpCanvasThumbUrl)
        }
      }
    }
    this.__insertAdapterOfArtistEntity = object : EntityInsertAdapter<ArtistEntity>() {
      protected override fun createQuery(): String = "INSERT OR IGNORE INTO `artist` (`channelId`,`name`,`thumbnails`,`followed`,`followedAt`,`inLibrary`,`nameLogoUrl`,`nameLogoColor`) VALUES (?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: ArtistEntity) {
        statement.bindText(1, entity.channelId)
        statement.bindText(2, entity.name)
        val _tmpThumbnails: String? = entity.thumbnails
        if (_tmpThumbnails == null) {
          statement.bindNull(3)
        } else {
          statement.bindText(3, _tmpThumbnails)
        }
        val _tmp: Int = if (entity.followed) 1 else 0
        statement.bindLong(4, _tmp.toLong())
        val _tmpFollowedAt: LocalDateTime? = entity.followedAt
        val _tmp_1: Long? = __converters().dateToTimestamp(_tmpFollowedAt)
        if (_tmp_1 == null) {
          statement.bindNull(5)
        } else {
          statement.bindLong(5, _tmp_1)
        }
        val _tmp_2: Long? = __converters().dateToTimestamp(entity.inLibrary)
        if (_tmp_2 == null) {
          statement.bindNull(6)
        } else {
          statement.bindLong(6, _tmp_2)
        }
        val _tmpNameLogoUrl: String? = entity.nameLogoUrl
        if (_tmpNameLogoUrl == null) {
          statement.bindNull(7)
        } else {
          statement.bindText(7, _tmpNameLogoUrl)
        }
        val _tmpNameLogoColor: String? = entity.nameLogoColor
        if (_tmpNameLogoColor == null) {
          statement.bindNull(8)
        } else {
          statement.bindText(8, _tmpNameLogoColor)
        }
      }
    }
    this.__insertAdapterOfAlbumEntity = object : EntityInsertAdapter<AlbumEntity>() {
      protected override fun createQuery(): String = "INSERT OR IGNORE INTO `album` (`browseId`,`artistId`,`artistName`,`audioPlaylistId`,`description`,`duration`,`durationSeconds`,`thumbnails`,`title`,`trackCount`,`tracks`,`type`,`year`,`liked`,`inLibrary`,`favoriteAt`,`downloadedAt`,`downloadState`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: AlbumEntity) {
        statement.bindText(1, entity.browseId)
        val _tmpArtistId: List<String?>? = entity.artistId
        val _tmp: String? = __converters().fromArrayListNull(_tmpArtistId)
        if (_tmp == null) {
          statement.bindNull(2)
        } else {
          statement.bindText(2, _tmp)
        }
        val _tmpArtistName: List<String>? = entity.artistName
        val _tmp_1: String? = __converters().fromArrayList(_tmpArtistName)
        if (_tmp_1 == null) {
          statement.bindNull(3)
        } else {
          statement.bindText(3, _tmp_1)
        }
        statement.bindText(4, entity.audioPlaylistId)
        statement.bindText(5, entity.description)
        val _tmpDuration: String? = entity.duration
        if (_tmpDuration == null) {
          statement.bindNull(6)
        } else {
          statement.bindText(6, _tmpDuration)
        }
        statement.bindLong(7, entity.durationSeconds.toLong())
        val _tmpThumbnails: String? = entity.thumbnails
        if (_tmpThumbnails == null) {
          statement.bindNull(8)
        } else {
          statement.bindText(8, _tmpThumbnails)
        }
        statement.bindText(9, entity.title)
        statement.bindLong(10, entity.trackCount.toLong())
        val _tmpTracks: List<String>? = entity.tracks
        val _tmp_2: String? = __converters().fromArrayList(_tmpTracks)
        if (_tmp_2 == null) {
          statement.bindNull(11)
        } else {
          statement.bindText(11, _tmp_2)
        }
        statement.bindText(12, entity.type)
        val _tmpYear: String? = entity.year
        if (_tmpYear == null) {
          statement.bindNull(13)
        } else {
          statement.bindText(13, _tmpYear)
        }
        val _tmp_3: Int = if (entity.liked) 1 else 0
        statement.bindLong(14, _tmp_3.toLong())
        val _tmp_4: Long? = __converters().dateToTimestamp(entity.inLibrary)
        if (_tmp_4 == null) {
          statement.bindNull(15)
        } else {
          statement.bindLong(15, _tmp_4)
        }
        val _tmpFavoriteAt: LocalDateTime? = entity.favoriteAt
        val _tmp_5: Long? = __converters().dateToTimestamp(_tmpFavoriteAt)
        if (_tmp_5 == null) {
          statement.bindNull(16)
        } else {
          statement.bindLong(16, _tmp_5)
        }
        val _tmpDownloadedAt: LocalDateTime? = entity.downloadedAt
        val _tmp_6: Long? = __converters().dateToTimestamp(_tmpDownloadedAt)
        if (_tmp_6 == null) {
          statement.bindNull(17)
        } else {
          statement.bindLong(17, _tmp_6)
        }
        statement.bindLong(18, entity.downloadState.toLong())
      }
    }
    this.__insertAdapterOfPlaylistEntity = object : EntityInsertAdapter<PlaylistEntity>() {
      protected override fun createQuery(): String = "INSERT OR IGNORE INTO `playlist` (`id`,`author`,`description`,`duration`,`durationSeconds`,`privacy`,`thumbnails`,`title`,`trackCount`,`tracks`,`year`,`liked`,`inLibrary`,`favoriteAt`,`downloadedAt`,`downloadState`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: PlaylistEntity) {
        statement.bindText(1, entity.id)
        val _tmpAuthor: String? = entity.author
        if (_tmpAuthor == null) {
          statement.bindNull(2)
        } else {
          statement.bindText(2, _tmpAuthor)
        }
        statement.bindText(3, entity.description)
        statement.bindText(4, entity.duration)
        statement.bindLong(5, entity.durationSeconds.toLong())
        statement.bindText(6, entity.privacy)
        statement.bindText(7, entity.thumbnails)
        statement.bindText(8, entity.title)
        statement.bindLong(9, entity.trackCount.toLong())
        val _tmpTracks: List<String>? = entity.tracks
        val _tmp: String? = __converters().fromArrayList(_tmpTracks)
        if (_tmp == null) {
          statement.bindNull(10)
        } else {
          statement.bindText(10, _tmp)
        }
        val _tmpYear: String? = entity.year
        if (_tmpYear == null) {
          statement.bindNull(11)
        } else {
          statement.bindText(11, _tmpYear)
        }
        val _tmp_1: Int = if (entity.liked) 1 else 0
        statement.bindLong(12, _tmp_1.toLong())
        val _tmp_2: Long? = __converters().dateToTimestamp(entity.inLibrary)
        if (_tmp_2 == null) {
          statement.bindNull(13)
        } else {
          statement.bindLong(13, _tmp_2)
        }
        val _tmpFavoriteAt: LocalDateTime? = entity.favoriteAt
        val _tmp_3: Long? = __converters().dateToTimestamp(_tmpFavoriteAt)
        if (_tmp_3 == null) {
          statement.bindNull(14)
        } else {
          statement.bindLong(14, _tmp_3)
        }
        val _tmpDownloadedAt: LocalDateTime? = entity.downloadedAt
        val _tmp_4: Long? = __converters().dateToTimestamp(_tmpDownloadedAt)
        if (_tmp_4 == null) {
          statement.bindNull(15)
        } else {
          statement.bindLong(15, _tmp_4)
        }
        statement.bindLong(16, entity.downloadState.toLong())
      }
    }
    this.__insertAdapterOfPlaylistEntity_1 = object : EntityInsertAdapter<PlaylistEntity>() {
      protected override fun createQuery(): String = "INSERT OR REPLACE INTO `playlist` (`id`,`author`,`description`,`duration`,`durationSeconds`,`privacy`,`thumbnails`,`title`,`trackCount`,`tracks`,`year`,`liked`,`inLibrary`,`favoriteAt`,`downloadedAt`,`downloadState`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: PlaylistEntity) {
        statement.bindText(1, entity.id)
        val _tmpAuthor: String? = entity.author
        if (_tmpAuthor == null) {
          statement.bindNull(2)
        } else {
          statement.bindText(2, _tmpAuthor)
        }
        statement.bindText(3, entity.description)
        statement.bindText(4, entity.duration)
        statement.bindLong(5, entity.durationSeconds.toLong())
        statement.bindText(6, entity.privacy)
        statement.bindText(7, entity.thumbnails)
        statement.bindText(8, entity.title)
        statement.bindLong(9, entity.trackCount.toLong())
        val _tmpTracks: List<String>? = entity.tracks
        val _tmp: String? = __converters().fromArrayList(_tmpTracks)
        if (_tmp == null) {
          statement.bindNull(10)
        } else {
          statement.bindText(10, _tmp)
        }
        val _tmpYear: String? = entity.year
        if (_tmpYear == null) {
          statement.bindNull(11)
        } else {
          statement.bindText(11, _tmpYear)
        }
        val _tmp_1: Int = if (entity.liked) 1 else 0
        statement.bindLong(12, _tmp_1.toLong())
        val _tmp_2: Long? = __converters().dateToTimestamp(entity.inLibrary)
        if (_tmp_2 == null) {
          statement.bindNull(13)
        } else {
          statement.bindLong(13, _tmp_2)
        }
        val _tmpFavoriteAt: LocalDateTime? = entity.favoriteAt
        val _tmp_3: Long? = __converters().dateToTimestamp(_tmpFavoriteAt)
        if (_tmp_3 == null) {
          statement.bindNull(14)
        } else {
          statement.bindLong(14, _tmp_3)
        }
        val _tmpDownloadedAt: LocalDateTime? = entity.downloadedAt
        val _tmp_4: Long? = __converters().dateToTimestamp(_tmpDownloadedAt)
        if (_tmp_4 == null) {
          statement.bindNull(15)
        } else {
          statement.bindLong(15, _tmp_4)
        }
        statement.bindLong(16, entity.downloadState.toLong())
      }
    }
    this.__insertAdapterOfLocalPlaylistEntity = object : EntityInsertAdapter<LocalPlaylistEntity>() {
      protected override fun createQuery(): String = "INSERT OR IGNORE INTO `local_playlist` (`id`,`title`,`thumbnail`,`inLibrary`,`downloadedAt`,`downloadState`,`youtubePlaylistId`,`youtube_sync_state`,`tracks`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: LocalPlaylistEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.title)
        val _tmpThumbnail: String? = entity.thumbnail
        if (_tmpThumbnail == null) {
          statement.bindNull(3)
        } else {
          statement.bindText(3, _tmpThumbnail)
        }
        val _tmp: Long? = __converters().dateToTimestamp(entity.inLibrary)
        if (_tmp == null) {
          statement.bindNull(4)
        } else {
          statement.bindLong(4, _tmp)
        }
        val _tmpDownloadedAt: LocalDateTime? = entity.downloadedAt
        val _tmp_1: Long? = __converters().dateToTimestamp(_tmpDownloadedAt)
        if (_tmp_1 == null) {
          statement.bindNull(5)
        } else {
          statement.bindLong(5, _tmp_1)
        }
        statement.bindLong(6, entity.downloadState.toLong())
        val _tmpYoutubePlaylistId: String? = entity.youtubePlaylistId
        if (_tmpYoutubePlaylistId == null) {
          statement.bindNull(7)
        } else {
          statement.bindText(7, _tmpYoutubePlaylistId)
        }
        statement.bindLong(8, entity.syncState.toLong())
        val _tmpTracks: List<String>? = entity.tracks
        val _tmp_2: String? = __converters().fromArrayList(_tmpTracks)
        if (_tmp_2 == null) {
          statement.bindNull(9)
        } else {
          statement.bindText(9, _tmp_2)
        }
      }
    }
    this.__insertAdapterOfLyricsEntity = object : EntityInsertAdapter<LyricsEntity>() {
      protected override fun createQuery(): String = "INSERT OR REPLACE INTO `lyrics` (`videoId`,`error`,`lines`,`syncType`) VALUES (?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: LyricsEntity) {
        statement.bindText(1, entity.videoId)
        val _tmp: Int = if (entity.error) 1 else 0
        statement.bindLong(2, _tmp.toLong())
        val _tmpLines: List<Line>? = entity.lines
        val _tmp_1: String? = __converters().fromListLineToString(_tmpLines)
        if (_tmp_1 == null) {
          statement.bindNull(3)
        } else {
          statement.bindText(3, _tmp_1)
        }
        val _tmpSyncType: String? = entity.syncType
        if (_tmpSyncType == null) {
          statement.bindNull(4)
        } else {
          statement.bindText(4, _tmpSyncType)
        }
      }
    }
    this.__insertAdapterOfNewFormatEntity = object : EntityInsertAdapter<NewFormatEntity>() {
      protected override fun createQuery(): String = "INSERT OR REPLACE INTO `new_format` (`videoId`,`itag`,`mimeType`,`codecs`,`bitrate`,`sampleRate`,`contentLength`,`loudnessDb`,`lengthSeconds`,`playbackTrackingVideostatsPlaybackUrl`,`playbackTrackingAtrUrl`,`playbackTrackingVideostatsWatchtimeUrl`,`expired_time`,`cpn`,`audioUrl`,`videoUrl`,`bpm`,`music_key`,`keyScale`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: NewFormatEntity) {
        statement.bindText(1, entity.videoId)
        statement.bindLong(2, entity.itag.toLong())
        val _tmpMimeType: String? = entity.mimeType
        if (_tmpMimeType == null) {
          statement.bindNull(3)
        } else {
          statement.bindText(3, _tmpMimeType)
        }
        val _tmpCodecs: String? = entity.codecs
        if (_tmpCodecs == null) {
          statement.bindNull(4)
        } else {
          statement.bindText(4, _tmpCodecs)
        }
        val _tmpBitrate: Int? = entity.bitrate
        if (_tmpBitrate == null) {
          statement.bindNull(5)
        } else {
          statement.bindLong(5, _tmpBitrate.toLong())
        }
        val _tmpSampleRate: Int? = entity.sampleRate
        if (_tmpSampleRate == null) {
          statement.bindNull(6)
        } else {
          statement.bindLong(6, _tmpSampleRate.toLong())
        }
        val _tmpContentLength: Long? = entity.contentLength
        if (_tmpContentLength == null) {
          statement.bindNull(7)
        } else {
          statement.bindLong(7, _tmpContentLength)
        }
        val _tmpLoudnessDb: Float? = entity.loudnessDb
        if (_tmpLoudnessDb == null) {
          statement.bindNull(8)
        } else {
          statement.bindDouble(8, _tmpLoudnessDb.toDouble())
        }
        val _tmpLengthSeconds: Int? = entity.lengthSeconds
        if (_tmpLengthSeconds == null) {
          statement.bindNull(9)
        } else {
          statement.bindLong(9, _tmpLengthSeconds.toLong())
        }
        val _tmpPlaybackTrackingVideostatsPlaybackUrl: String? = entity.playbackTrackingVideostatsPlaybackUrl
        if (_tmpPlaybackTrackingVideostatsPlaybackUrl == null) {
          statement.bindNull(10)
        } else {
          statement.bindText(10, _tmpPlaybackTrackingVideostatsPlaybackUrl)
        }
        val _tmpPlaybackTrackingAtrUrl: String? = entity.playbackTrackingAtrUrl
        if (_tmpPlaybackTrackingAtrUrl == null) {
          statement.bindNull(11)
        } else {
          statement.bindText(11, _tmpPlaybackTrackingAtrUrl)
        }
        val _tmpPlaybackTrackingVideostatsWatchtimeUrl: String? = entity.playbackTrackingVideostatsWatchtimeUrl
        if (_tmpPlaybackTrackingVideostatsWatchtimeUrl == null) {
          statement.bindNull(12)
        } else {
          statement.bindText(12, _tmpPlaybackTrackingVideostatsWatchtimeUrl)
        }
        val _tmp: Long? = __converters().dateToTimestamp(entity.expiredTime)
        if (_tmp == null) {
          statement.bindNull(13)
        } else {
          statement.bindLong(13, _tmp)
        }
        val _tmpCpn: String? = entity.cpn
        if (_tmpCpn == null) {
          statement.bindNull(14)
        } else {
          statement.bindText(14, _tmpCpn)
        }
        val _tmpAudioUrl: String? = entity.audioUrl
        if (_tmpAudioUrl == null) {
          statement.bindNull(15)
        } else {
          statement.bindText(15, _tmpAudioUrl)
        }
        val _tmpVideoUrl: String? = entity.videoUrl
        if (_tmpVideoUrl == null) {
          statement.bindNull(16)
        } else {
          statement.bindText(16, _tmpVideoUrl)
        }
        val _tmpBpm: Int? = entity.bpm
        if (_tmpBpm == null) {
          statement.bindNull(17)
        } else {
          statement.bindLong(17, _tmpBpm.toLong())
        }
        val _tmpMusicKey: String? = entity.musicKey
        if (_tmpMusicKey == null) {
          statement.bindNull(18)
        } else {
          statement.bindText(18, _tmpMusicKey)
        }
        val _tmpKeyScale: String? = entity.keyScale
        if (_tmpKeyScale == null) {
          statement.bindNull(19)
        } else {
          statement.bindText(19, _tmpKeyScale)
        }
      }
    }
    this.__insertAdapterOfSongInfoEntity = object : EntityInsertAdapter<SongInfoEntity>() {
      protected override fun createQuery(): String = "INSERT OR REPLACE INTO `song_info` (`videoId`,`author`,`authorId`,`authorThumbnail`,`description`,`subscribers`,`viewCount`,`uploadDate`,`like`,`dislike`) VALUES (?,?,?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: SongInfoEntity) {
        statement.bindText(1, entity.videoId)
        val _tmpAuthor: String? = entity.author
        if (_tmpAuthor == null) {
          statement.bindNull(2)
        } else {
          statement.bindText(2, _tmpAuthor)
        }
        val _tmpAuthorId: String? = entity.authorId
        if (_tmpAuthorId == null) {
          statement.bindNull(3)
        } else {
          statement.bindText(3, _tmpAuthorId)
        }
        val _tmpAuthorThumbnail: String? = entity.authorThumbnail
        if (_tmpAuthorThumbnail == null) {
          statement.bindNull(4)
        } else {
          statement.bindText(4, _tmpAuthorThumbnail)
        }
        val _tmpDescription: String? = entity.description
        if (_tmpDescription == null) {
          statement.bindNull(5)
        } else {
          statement.bindText(5, _tmpDescription)
        }
        val _tmpSubscribers: String? = entity.subscribers
        if (_tmpSubscribers == null) {
          statement.bindNull(6)
        } else {
          statement.bindText(6, _tmpSubscribers)
        }
        val _tmpViewCount: Int? = entity.viewCount
        if (_tmpViewCount == null) {
          statement.bindNull(7)
        } else {
          statement.bindLong(7, _tmpViewCount.toLong())
        }
        val _tmpUploadDate: String? = entity.uploadDate
        if (_tmpUploadDate == null) {
          statement.bindNull(8)
        } else {
          statement.bindText(8, _tmpUploadDate)
        }
        val _tmpLike: Int? = entity.like
        if (_tmpLike == null) {
          statement.bindNull(9)
        } else {
          statement.bindLong(9, _tmpLike.toLong())
        }
        val _tmpDislike: Int? = entity.dislike
        if (_tmpDislike == null) {
          statement.bindNull(10)
        } else {
          statement.bindLong(10, _tmpDislike.toLong())
        }
      }
    }
    this.__insertAdapterOfQueueEntity = object : EntityInsertAdapter<QueueEntity>() {
      protected override fun createQuery(): String = "INSERT OR REPLACE INTO `queue` (`queueId`,`listTrack`) VALUES (?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: QueueEntity) {
        statement.bindLong(1, entity.queueId)
        val _tmp: String? = __converters().fromListTrackToString(entity.listTrack)
        if (_tmp == null) {
          statement.bindNull(2)
        } else {
          statement.bindText(2, _tmp)
        }
      }
    }
    this.__insertAdapterOfSetVideoIdEntity = object : EntityInsertAdapter<SetVideoIdEntity>() {
      protected override fun createQuery(): String = "INSERT OR REPLACE INTO `set_video_id` (`videoId`,`setVideoId`,`youtubePlaylistId`) VALUES (?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: SetVideoIdEntity) {
        statement.bindText(1, entity.videoId)
        val _tmpSetVideoId: String? = entity.setVideoId
        if (_tmpSetVideoId == null) {
          statement.bindNull(2)
        } else {
          statement.bindText(2, _tmpSetVideoId)
        }
        statement.bindText(3, entity.youtubePlaylistId)
      }
    }
    this.__insertAdapterOfPairSongLocalPlaylist = object : EntityInsertAdapter<PairSongLocalPlaylist>() {
      protected override fun createQuery(): String = "INSERT OR REPLACE INTO `pair_song_local_playlist` (`id`,`playlistId`,`songId`,`position`,`inPlaylist`) VALUES (nullif(?, 0),?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: PairSongLocalPlaylist) {
        statement.bindLong(1, entity.id.toLong())
        statement.bindLong(2, entity.playlistId)
        statement.bindText(3, entity.songId)
        statement.bindLong(4, entity.position.toLong())
        val _tmp: Long? = __converters().dateToTimestamp(entity.inPlaylist)
        if (_tmp == null) {
          statement.bindNull(5)
        } else {
          statement.bindLong(5, _tmp)
        }
      }
    }
    this.__insertAdapterOfGoogleAccountEntity = object : EntityInsertAdapter<GoogleAccountEntity>() {
      protected override fun createQuery(): String = "INSERT OR REPLACE INTO `GoogleAccountEntity` (`email`,`name`,`thumbnailUrl`,`pageId`,`cache`,`isUsed`,`netscapeCookie`) VALUES (?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: GoogleAccountEntity) {
        statement.bindText(1, entity.email)
        statement.bindText(2, entity.name)
        statement.bindText(3, entity.thumbnailUrl)
        val _tmpPageId: String? = entity.pageId
        if (_tmpPageId == null) {
          statement.bindNull(4)
        } else {
          statement.bindText(4, _tmpPageId)
        }
        val _tmpCache: String? = entity.cache
        if (_tmpCache == null) {
          statement.bindNull(5)
        } else {
          statement.bindText(5, _tmpCache)
        }
        val _tmp: Int = if (entity.isUsed) 1 else 0
        statement.bindLong(6, _tmp.toLong())
        val _tmpNetscapeCookie: String? = entity.netscapeCookie
        if (_tmpNetscapeCookie == null) {
          statement.bindNull(7)
        } else {
          statement.bindText(7, _tmpNetscapeCookie)
        }
      }
    }
    this.__insertAdapterOfFollowedArtistSingleAndAlbum = object : EntityInsertAdapter<FollowedArtistSingleAndAlbum>() {
      protected override fun createQuery(): String = "INSERT OR REPLACE INTO `followed_artist_single_and_album` (`channelId`,`name`,`single`,`album`) VALUES (?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: FollowedArtistSingleAndAlbum) {
        statement.bindText(1, entity.channelId)
        statement.bindText(2, entity.name)
        val _tmp: String = __converters().fromListMapToString(entity.single)
        statement.bindText(3, _tmp)
        val _tmp_1: String = __converters().fromListMapToString(entity.album)
        statement.bindText(4, _tmp_1)
      }
    }
    this.__insertAdapterOfNotificationEntity = object : EntityInsertAdapter<NotificationEntity>() {
      protected override fun createQuery(): String = "INSERT OR ABORT INTO `notification` (`id`,`channelId`,`thumbnail`,`name`,`single`,`album`,`time`,`type`,`link`,`description`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: NotificationEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.channelId)
        val _tmpThumbnail: String? = entity.thumbnail
        if (_tmpThumbnail == null) {
          statement.bindNull(3)
        } else {
          statement.bindText(3, _tmpThumbnail)
        }
        statement.bindText(4, entity.name)
        val _tmp: String = __converters().fromListMapToString(entity.single)
        statement.bindText(5, _tmp)
        val _tmp_1: String = __converters().fromListMapToString(entity.album)
        statement.bindText(6, _tmp_1)
        val _tmp_2: Long? = __converters().dateToTimestamp(entity.time)
        if (_tmp_2 == null) {
          statement.bindNull(7)
        } else {
          statement.bindLong(7, _tmp_2)
        }
        statement.bindText(8, entity.type)
        val _tmpLink: String? = entity.link
        if (_tmpLink == null) {
          statement.bindNull(9)
        } else {
          statement.bindText(9, _tmpLink)
        }
        val _tmpDescription: String? = entity.description
        if (_tmpDescription == null) {
          statement.bindNull(10)
        } else {
          statement.bindText(10, _tmpDescription)
        }
      }
    }
    this.__insertAdapterOfTranslatedLyricsEntity = object : EntityInsertAdapter<TranslatedLyricsEntity>() {
      protected override fun createQuery(): String = "INSERT OR REPLACE INTO `translated_lyrics` (`videoId`,`language`,`error`,`lines`,`syncType`) VALUES (?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: TranslatedLyricsEntity) {
        statement.bindText(1, entity.videoId)
        statement.bindText(2, entity.language)
        val _tmp: Int = if (entity.error) 1 else 0
        statement.bindLong(3, _tmp.toLong())
        val _tmpLines: List<Line>? = entity.lines
        val _tmp_1: String? = __converters().fromListLineToString(_tmpLines)
        if (_tmp_1 == null) {
          statement.bindNull(4)
        } else {
          statement.bindText(4, _tmp_1)
        }
        val _tmpSyncType: String? = entity.syncType
        if (_tmpSyncType == null) {
          statement.bindNull(5)
        } else {
          statement.bindText(5, _tmpSyncType)
        }
      }
    }
    this.__insertAdapterOfPodcastsEntity = object : EntityInsertAdapter<PodcastsEntity>() {
      protected override fun createQuery(): String = "INSERT OR REPLACE INTO `podcast_table` (`podcastId`,`title`,`authorId`,`authorName`,`authorThumbnail`,`description`,`thumbnail`,`isFavorite`,`inLibrary`,`favoriteTime`,`listEpisodes`) VALUES (?,?,?,?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: PodcastsEntity) {
        statement.bindText(1, entity.podcastId)
        statement.bindText(2, entity.title)
        statement.bindText(3, entity.authorId)
        statement.bindText(4, entity.authorName)
        val _tmpAuthorThumbnail: String? = entity.authorThumbnail
        if (_tmpAuthorThumbnail == null) {
          statement.bindNull(5)
        } else {
          statement.bindText(5, _tmpAuthorThumbnail)
        }
        val _tmpDescription: String? = entity.description
        if (_tmpDescription == null) {
          statement.bindNull(6)
        } else {
          statement.bindText(6, _tmpDescription)
        }
        val _tmpThumbnail: String? = entity.thumbnail
        if (_tmpThumbnail == null) {
          statement.bindNull(7)
        } else {
          statement.bindText(7, _tmpThumbnail)
        }
        val _tmp: Int = if (entity.isFavorite) 1 else 0
        statement.bindLong(8, _tmp.toLong())
        val _tmp_1: Long? = __converters().dateToTimestamp(entity.inLibrary)
        if (_tmp_1 == null) {
          statement.bindNull(9)
        } else {
          statement.bindLong(9, _tmp_1)
        }
        val _tmpFavoriteTime: LocalDateTime? = entity.favoriteTime
        val _tmp_2: Long? = __converters().dateToTimestamp(_tmpFavoriteTime)
        if (_tmp_2 == null) {
          statement.bindNull(10)
        } else {
          statement.bindLong(10, _tmp_2)
        }
        val _tmp_3: String? = __converters().fromArrayList(entity.listEpisodes)
        if (_tmp_3 == null) {
          statement.bindNull(11)
        } else {
          statement.bindText(11, _tmp_3)
        }
      }
    }
    this.__insertAdapterOfEpisodeEntity = object : EntityInsertAdapter<EpisodeEntity>() {
      protected override fun createQuery(): String = "INSERT OR REPLACE INTO `podcast_episode_table` (`videoId`,`podcastId`,`title`,`authorName`,`authorId`,`description`,`createdDay`,`durationString`,`thumbnail`) VALUES (?,?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: EpisodeEntity) {
        statement.bindText(1, entity.videoId)
        statement.bindText(2, entity.podcastId)
        statement.bindText(3, entity.title)
        statement.bindText(4, entity.authorName)
        statement.bindText(5, entity.authorId)
        val _tmpDescription: String? = entity.description
        if (_tmpDescription == null) {
          statement.bindNull(6)
        } else {
          statement.bindText(6, _tmpDescription)
        }
        val _tmpCreatedDay: String? = entity.createdDay
        if (_tmpCreatedDay == null) {
          statement.bindNull(7)
        } else {
          statement.bindText(7, _tmpCreatedDay)
        }
        val _tmpDurationString: String? = entity.durationString
        if (_tmpDurationString == null) {
          statement.bindNull(8)
        } else {
          statement.bindText(8, _tmpDurationString)
        }
        val _tmpThumbnail: String? = entity.thumbnail
        if (_tmpThumbnail == null) {
          statement.bindNull(9)
        } else {
          statement.bindText(9, _tmpThumbnail)
        }
      }
    }
    this.__insertAdapterOfYourYouTubePlaylistList = object : EntityInsertAdapter<YourYouTubePlaylistList>() {
      protected override fun createQuery(): String = "INSERT OR REPLACE INTO `your_youtube_playlist_list` (`emailPageId`,`listBrowseIds`) VALUES (?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: YourYouTubePlaylistList) {
        statement.bindText(1, entity.emailPageId)
        val _tmp: String? = __converters().fromArrayList(entity.listBrowseIds)
        if (_tmp == null) {
          statement.bindNull(2)
        } else {
          statement.bindText(2, _tmp)
        }
      }
    }
    this.__insertAdapterOfPlaybackEventEntity = object : EntityInsertAdapter<PlaybackEventEntity>() {
      protected override fun createQuery(): String = "INSERT OR ABORT INTO `playback_event` (`eventId`,`timestamp`,`videoId`,`albumBrowseId`,`durationSecond`,`listenedSecond`) VALUES (nullif(?, 0),?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: PlaybackEventEntity) {
        statement.bindLong(1, entity.eventId)
        val _tmp: Long? = __converters().dateToTimestamp(entity.timestamp)
        if (_tmp == null) {
          statement.bindNull(2)
        } else {
          statement.bindLong(2, _tmp)
        }
        statement.bindText(3, entity.videoId)
        val _tmpAlbumBrowseId: String? = entity.albumBrowseId
        if (_tmpAlbumBrowseId == null) {
          statement.bindNull(4)
        } else {
          statement.bindText(4, _tmpAlbumBrowseId)
        }
        statement.bindLong(5, entity.durationSecond)
        statement.bindLong(6, entity.listenedSecond)
      }
    }
    this.__insertAdapterOfEventArtistEntity = object : EntityInsertAdapter<EventArtistEntity>() {
      protected override fun createQuery(): String = "INSERT OR ABORT INTO `event_artist` (`eventId`,`channelId`,`timestamp`) VALUES (?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: EventArtistEntity) {
        statement.bindLong(1, entity.eventId)
        statement.bindText(2, entity.channelId)
        val _tmp: Long? = __converters().dateToTimestamp(entity.timestamp)
        if (_tmp == null) {
          statement.bindNull(3)
        } else {
          statement.bindLong(3, _tmp)
        }
      }
    }
    this.__updateAdapterOfNewFormatEntity = object : EntityDeleteOrUpdateAdapter<NewFormatEntity>() {
      protected override fun createQuery(): String = "UPDATE OR ABORT `new_format` SET `videoId` = ?,`itag` = ?,`mimeType` = ?,`codecs` = ?,`bitrate` = ?,`sampleRate` = ?,`contentLength` = ?,`loudnessDb` = ?,`lengthSeconds` = ?,`playbackTrackingVideostatsPlaybackUrl` = ?,`playbackTrackingAtrUrl` = ?,`playbackTrackingVideostatsWatchtimeUrl` = ?,`expired_time` = ?,`cpn` = ?,`audioUrl` = ?,`videoUrl` = ?,`bpm` = ?,`music_key` = ?,`keyScale` = ? WHERE `videoId` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: NewFormatEntity) {
        statement.bindText(1, entity.videoId)
        statement.bindLong(2, entity.itag.toLong())
        val _tmpMimeType: String? = entity.mimeType
        if (_tmpMimeType == null) {
          statement.bindNull(3)
        } else {
          statement.bindText(3, _tmpMimeType)
        }
        val _tmpCodecs: String? = entity.codecs
        if (_tmpCodecs == null) {
          statement.bindNull(4)
        } else {
          statement.bindText(4, _tmpCodecs)
        }
        val _tmpBitrate: Int? = entity.bitrate
        if (_tmpBitrate == null) {
          statement.bindNull(5)
        } else {
          statement.bindLong(5, _tmpBitrate.toLong())
        }
        val _tmpSampleRate: Int? = entity.sampleRate
        if (_tmpSampleRate == null) {
          statement.bindNull(6)
        } else {
          statement.bindLong(6, _tmpSampleRate.toLong())
        }
        val _tmpContentLength: Long? = entity.contentLength
        if (_tmpContentLength == null) {
          statement.bindNull(7)
        } else {
          statement.bindLong(7, _tmpContentLength)
        }
        val _tmpLoudnessDb: Float? = entity.loudnessDb
        if (_tmpLoudnessDb == null) {
          statement.bindNull(8)
        } else {
          statement.bindDouble(8, _tmpLoudnessDb.toDouble())
        }
        val _tmpLengthSeconds: Int? = entity.lengthSeconds
        if (_tmpLengthSeconds == null) {
          statement.bindNull(9)
        } else {
          statement.bindLong(9, _tmpLengthSeconds.toLong())
        }
        val _tmpPlaybackTrackingVideostatsPlaybackUrl: String? = entity.playbackTrackingVideostatsPlaybackUrl
        if (_tmpPlaybackTrackingVideostatsPlaybackUrl == null) {
          statement.bindNull(10)
        } else {
          statement.bindText(10, _tmpPlaybackTrackingVideostatsPlaybackUrl)
        }
        val _tmpPlaybackTrackingAtrUrl: String? = entity.playbackTrackingAtrUrl
        if (_tmpPlaybackTrackingAtrUrl == null) {
          statement.bindNull(11)
        } else {
          statement.bindText(11, _tmpPlaybackTrackingAtrUrl)
        }
        val _tmpPlaybackTrackingVideostatsWatchtimeUrl: String? = entity.playbackTrackingVideostatsWatchtimeUrl
        if (_tmpPlaybackTrackingVideostatsWatchtimeUrl == null) {
          statement.bindNull(12)
        } else {
          statement.bindText(12, _tmpPlaybackTrackingVideostatsWatchtimeUrl)
        }
        val _tmp: Long? = __converters().dateToTimestamp(entity.expiredTime)
        if (_tmp == null) {
          statement.bindNull(13)
        } else {
          statement.bindLong(13, _tmp)
        }
        val _tmpCpn: String? = entity.cpn
        if (_tmpCpn == null) {
          statement.bindNull(14)
        } else {
          statement.bindText(14, _tmpCpn)
        }
        val _tmpAudioUrl: String? = entity.audioUrl
        if (_tmpAudioUrl == null) {
          statement.bindNull(15)
        } else {
          statement.bindText(15, _tmpAudioUrl)
        }
        val _tmpVideoUrl: String? = entity.videoUrl
        if (_tmpVideoUrl == null) {
          statement.bindNull(16)
        } else {
          statement.bindText(16, _tmpVideoUrl)
        }
        val _tmpBpm: Int? = entity.bpm
        if (_tmpBpm == null) {
          statement.bindNull(17)
        } else {
          statement.bindLong(17, _tmpBpm.toLong())
        }
        val _tmpMusicKey: String? = entity.musicKey
        if (_tmpMusicKey == null) {
          statement.bindNull(18)
        } else {
          statement.bindText(18, _tmpMusicKey)
        }
        val _tmpKeyScale: String? = entity.keyScale
        if (_tmpKeyScale == null) {
          statement.bindNull(19)
        } else {
          statement.bindText(19, _tmpKeyScale)
        }
        statement.bindText(20, entity.videoId)
      }
    }
  }

  public override suspend fun insertSearchHistory(searchHistory: SearchHistory): Long = performSuspending(__db, false, true) { _connection ->
    val _result: Long = __insertAdapterOfSearchHistory.insertAndReturnId(_connection, searchHistory)
    _result
  }

  public override suspend fun insertSong(song: SongEntity): Long = performSuspending(__db, false, true) { _connection ->
    val _result: Long = __insertAdapterOfSongEntity.insertAndReturnId(_connection, song)
    _result
  }

  public override suspend fun insertArtist(artist: ArtistEntity): Unit = performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfArtistEntity.insert(_connection, artist)
  }

  public override suspend fun insertAlbum(album: AlbumEntity): Long = performSuspending(__db, false, true) { _connection ->
    val _result: Long = __insertAdapterOfAlbumEntity.insertAndReturnId(_connection, album)
    _result
  }

  public override suspend fun insertPlaylist(playlist: PlaylistEntity): Unit = performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfPlaylistEntity.insert(_connection, playlist)
  }

  public override suspend fun insertAndReplacePlaylist(playlist: PlaylistEntity): Unit = performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfPlaylistEntity_1.insert(_connection, playlist)
  }

  public override suspend fun insertRadioPlaylist(playlist: PlaylistEntity): Unit = performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfPlaylistEntity_1.insert(_connection, playlist)
  }

  public override suspend fun insertLocalPlaylist(localPlaylist: LocalPlaylistEntity): Long = performSuspending(__db, false, true) { _connection ->
    val _result: Long = __insertAdapterOfLocalPlaylistEntity.insertAndReturnId(_connection, localPlaylist)
    _result
  }

  public override suspend fun insertLyrics(lyrics: LyricsEntity): Unit = performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfLyricsEntity.insert(_connection, lyrics)
  }

  public override suspend fun insertNewFormat(format: NewFormatEntity): Unit = performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfNewFormatEntity.insert(_connection, format)
  }

  public override suspend fun insertSongInfo(songInfo: SongInfoEntity): Unit = performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfSongInfoEntity.insert(_connection, songInfo)
  }

  public override suspend fun recoverQueue(queue: QueueEntity): Unit = performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfQueueEntity.insert(_connection, queue)
  }

  public override suspend fun insertSetVideoId(setVideoIdEntity: SetVideoIdEntity): Unit = performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfSetVideoIdEntity.insert(_connection, setVideoIdEntity)
  }

  public override suspend fun insertPairSongLocalPlaylist(pairSongLocalPlaylist: PairSongLocalPlaylist): Unit = performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfPairSongLocalPlaylist.insert(_connection, pairSongLocalPlaylist)
  }

  public override suspend fun insertGoogleAccount(googleAccountEntity: GoogleAccountEntity): Long = performSuspending(__db, false, true) { _connection ->
    val _result: Long = __insertAdapterOfGoogleAccountEntity.insertAndReturnId(_connection, googleAccountEntity)
    _result
  }

  public override suspend fun insertFollowedArtistSingleAndAlbum(followedArtistSingleAndAlbum: FollowedArtistSingleAndAlbum): Unit = performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfFollowedArtistSingleAndAlbum.insert(_connection, followedArtistSingleAndAlbum)
  }

  public override suspend fun insertNotification(notificationEntity: NotificationEntity): Unit = performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfNotificationEntity.insert(_connection, notificationEntity)
  }

  public override suspend fun insertTranslatedLyrics(translatedLyricsEntity: TranslatedLyricsEntity): Unit = performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfTranslatedLyricsEntity.insert(_connection, translatedLyricsEntity)
  }

  public override suspend fun insertPodcast(podcast: PodcastsEntity): Long = performSuspending(__db, false, true) { _connection ->
    val _result: Long = __insertAdapterOfPodcastsEntity.insertAndReturnId(_connection, podcast)
    _result
  }

  public override suspend fun insertEpisodes(episodes: List<EpisodeEntity>): List<Long> = performSuspending(__db, false, true) { _connection ->
    val _result: List<Long> = __insertAdapterOfEpisodeEntity.insertAndReturnIdsList(_connection, episodes)
    _result
  }

  public override suspend fun insertYourYouTubePlaylist(yourYouTubePlaylist: YourYouTubePlaylistList): Unit = performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfYourYouTubePlaylistList.insert(_connection, yourYouTubePlaylist)
  }

  public override suspend fun insertPlaybackEvent(playbackEventEntity: PlaybackEventEntity): Long = performSuspending(__db, false, true) { _connection ->
    val _result: Long = __insertAdapterOfPlaybackEventEntity.insertAndReturnId(_connection, playbackEventEntity)
    _result
  }

  public override suspend fun insertEventArtist(eventArtist: EventArtistEntity): Unit = performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfEventArtistEntity.insert(_connection, eventArtist)
  }

  public override suspend fun updateNewFormat(format: NewFormatEntity): Unit = performSuspending(__db, false, true) { _connection ->
    __updateAdapterOfNewFormatEntity.handle(_connection, format)
  }

  public override suspend fun getAllRecentData(): List<RecentlyType> = performInTransactionSuspending(__db) {
    super@DatabaseDao_Impl.getAllRecentData()
  }

  public override suspend fun getAllDownloadedPlaylist(): List<PlaylistType> = performInTransactionSuspending(__db) {
    super@DatabaseDao_Impl.getAllDownloadedPlaylist()
  }

  public override suspend fun getAllDownloadingPlaylist(): List<PlaylistType> = performInTransactionSuspending(__db) {
    super@DatabaseDao_Impl.getAllDownloadingPlaylist()
  }

  public override suspend fun insertSongs(songs: List<SongEntity>): Unit = performInTransactionSuspending(__db) {
    super@DatabaseDao_Impl.insertSongs(songs)
  }

  public override suspend fun insertLocalPlaylistWithTracks(localPlaylist: LocalPlaylistEntity, videoIds: List<String>): Long = performInTransactionSuspending(__db) {
    super@DatabaseDao_Impl.insertLocalPlaylistWithTracks(localPlaylist, videoIds)
  }

  public override suspend fun insertImportedPlaylist(localPlaylist: LocalPlaylistEntity, songs: List<SongEntity>): Long = performInTransactionSuspending(__db) {
    super@DatabaseDao_Impl.insertImportedPlaylist(localPlaylist, songs)
  }

  public override suspend fun insertPlaybackWithArtists(
    videoId: String,
    channelIds: List<String>,
    albumBrowseId: String?,
    durationSecond: Long,
    listenedSecond: Long,
  ): Long = performInTransactionSuspending(__db) {
    super@DatabaseDao_Impl.insertPlaybackWithArtists(videoId, channelIds, albumBrowseId, durationSecond, listenedSecond)
  }

  public override suspend fun getSearchHistory(): List<SearchHistory> {
    val _sql: String = "SELECT * FROM search_history"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfQuery: Int = getColumnIndexOrThrow(_stmt, "query")
        val _result: MutableList<SearchHistory> = mutableListOf()
        while (_stmt.step()) {
          val _item: SearchHistory
          val _tmpQuery: String
          _tmpQuery = _stmt.getText(_columnIndexOfQuery)
          _item = SearchHistory(_tmpQuery)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getRecentSongs(limit: Int, offset: Int): List<SongEntity> {
    val _sql: String = "SELECT * FROM song ORDER BY inLibrary DESC LIMIT ? OFFSET ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, limit.toLong())
        _argIndex = 2
        _stmt.bindLong(_argIndex, offset.toLong())
        val _columnIndexOfVideoId: Int = getColumnIndexOrThrow(_stmt, "videoId")
        val _columnIndexOfAlbumId: Int = getColumnIndexOrThrow(_stmt, "albumId")
        val _columnIndexOfAlbumName: Int = getColumnIndexOrThrow(_stmt, "albumName")
        val _columnIndexOfArtistId: Int = getColumnIndexOrThrow(_stmt, "artistId")
        val _columnIndexOfArtistName: Int = getColumnIndexOrThrow(_stmt, "artistName")
        val _columnIndexOfDuration: Int = getColumnIndexOrThrow(_stmt, "duration")
        val _columnIndexOfDurationSeconds: Int = getColumnIndexOrThrow(_stmt, "durationSeconds")
        val _columnIndexOfIsAvailable: Int = getColumnIndexOrThrow(_stmt, "isAvailable")
        val _columnIndexOfIsExplicit: Int = getColumnIndexOrThrow(_stmt, "isExplicit")
        val _columnIndexOfLikeStatus: Int = getColumnIndexOrThrow(_stmt, "likeStatus")
        val _columnIndexOfThumbnails: Int = getColumnIndexOrThrow(_stmt, "thumbnails")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfVideoType: Int = getColumnIndexOrThrow(_stmt, "videoType")
        val _columnIndexOfCategory: Int = getColumnIndexOrThrow(_stmt, "category")
        val _columnIndexOfResultType: Int = getColumnIndexOrThrow(_stmt, "resultType")
        val _columnIndexOfLiked: Int = getColumnIndexOrThrow(_stmt, "liked")
        val _columnIndexOfTotalPlayTime: Int = getColumnIndexOrThrow(_stmt, "totalPlayTime")
        val _columnIndexOfDownloadState: Int = getColumnIndexOrThrow(_stmt, "downloadState")
        val _columnIndexOfFavoriteAt: Int = getColumnIndexOrThrow(_stmt, "favoriteAt")
        val _columnIndexOfDownloadedAt: Int = getColumnIndexOrThrow(_stmt, "downloadedAt")
        val _columnIndexOfInLibrary: Int = getColumnIndexOrThrow(_stmt, "inLibrary")
        val _columnIndexOfCanvasUrl: Int = getColumnIndexOrThrow(_stmt, "canvasUrl")
        val _columnIndexOfCanvasThumbUrl: Int = getColumnIndexOrThrow(_stmt, "canvasThumbUrl")
        val _result: MutableList<SongEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: SongEntity
          val _tmpVideoId: String
          _tmpVideoId = _stmt.getText(_columnIndexOfVideoId)
          val _tmpAlbumId: String?
          if (_stmt.isNull(_columnIndexOfAlbumId)) {
            _tmpAlbumId = null
          } else {
            _tmpAlbumId = _stmt.getText(_columnIndexOfAlbumId)
          }
          val _tmpAlbumName: String?
          if (_stmt.isNull(_columnIndexOfAlbumName)) {
            _tmpAlbumName = null
          } else {
            _tmpAlbumName = _stmt.getText(_columnIndexOfAlbumName)
          }
          val _tmpArtistId: List<String>?
          val _tmp: String?
          if (_stmt.isNull(_columnIndexOfArtistId)) {
            _tmp = null
          } else {
            _tmp = _stmt.getText(_columnIndexOfArtistId)
          }
          _tmpArtistId = __converters().fromString(_tmp)
          val _tmpArtistName: List<String>?
          val _tmp_1: String?
          if (_stmt.isNull(_columnIndexOfArtistName)) {
            _tmp_1 = null
          } else {
            _tmp_1 = _stmt.getText(_columnIndexOfArtistName)
          }
          _tmpArtistName = __converters().fromString(_tmp_1)
          val _tmpDuration: String
          _tmpDuration = _stmt.getText(_columnIndexOfDuration)
          val _tmpDurationSeconds: Int
          _tmpDurationSeconds = _stmt.getLong(_columnIndexOfDurationSeconds).toInt()
          val _tmpIsAvailable: Boolean
          val _tmp_2: Int
          _tmp_2 = _stmt.getLong(_columnIndexOfIsAvailable).toInt()
          _tmpIsAvailable = _tmp_2 != 0
          val _tmpIsExplicit: Boolean
          val _tmp_3: Int
          _tmp_3 = _stmt.getLong(_columnIndexOfIsExplicit).toInt()
          _tmpIsExplicit = _tmp_3 != 0
          val _tmpLikeStatus: String
          _tmpLikeStatus = _stmt.getText(_columnIndexOfLikeStatus)
          val _tmpThumbnails: String?
          if (_stmt.isNull(_columnIndexOfThumbnails)) {
            _tmpThumbnails = null
          } else {
            _tmpThumbnails = _stmt.getText(_columnIndexOfThumbnails)
          }
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpVideoType: String
          _tmpVideoType = _stmt.getText(_columnIndexOfVideoType)
          val _tmpCategory: String?
          if (_stmt.isNull(_columnIndexOfCategory)) {
            _tmpCategory = null
          } else {
            _tmpCategory = _stmt.getText(_columnIndexOfCategory)
          }
          val _tmpResultType: String?
          if (_stmt.isNull(_columnIndexOfResultType)) {
            _tmpResultType = null
          } else {
            _tmpResultType = _stmt.getText(_columnIndexOfResultType)
          }
          val _tmpLiked: Boolean
          val _tmp_4: Int
          _tmp_4 = _stmt.getLong(_columnIndexOfLiked).toInt()
          _tmpLiked = _tmp_4 != 0
          val _tmpTotalPlayTime: Long
          _tmpTotalPlayTime = _stmt.getLong(_columnIndexOfTotalPlayTime)
          val _tmpDownloadState: Int
          _tmpDownloadState = _stmt.getLong(_columnIndexOfDownloadState).toInt()
          val _tmpFavoriteAt: LocalDateTime?
          val _tmp_5: Long?
          if (_stmt.isNull(_columnIndexOfFavoriteAt)) {
            _tmp_5 = null
          } else {
            _tmp_5 = _stmt.getLong(_columnIndexOfFavoriteAt)
          }
          _tmpFavoriteAt = __converters().fromTimestamp(_tmp_5)
          val _tmpDownloadedAt: LocalDateTime?
          val _tmp_6: Long?
          if (_stmt.isNull(_columnIndexOfDownloadedAt)) {
            _tmp_6 = null
          } else {
            _tmp_6 = _stmt.getLong(_columnIndexOfDownloadedAt)
          }
          _tmpDownloadedAt = __converters().fromTimestamp(_tmp_6)
          val _tmpInLibrary: LocalDateTime
          val _tmp_7: Long?
          if (_stmt.isNull(_columnIndexOfInLibrary)) {
            _tmp_7 = null
          } else {
            _tmp_7 = _stmt.getLong(_columnIndexOfInLibrary)
          }
          val _tmp_8: LocalDateTime? = __converters().fromTimestamp(_tmp_7)
          if (_tmp_8 == null) {
            error("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.")
          } else {
            _tmpInLibrary = _tmp_8
          }
          val _tmpCanvasUrl: String?
          if (_stmt.isNull(_columnIndexOfCanvasUrl)) {
            _tmpCanvasUrl = null
          } else {
            _tmpCanvasUrl = _stmt.getText(_columnIndexOfCanvasUrl)
          }
          val _tmpCanvasThumbUrl: String?
          if (_stmt.isNull(_columnIndexOfCanvasThumbUrl)) {
            _tmpCanvasThumbUrl = null
          } else {
            _tmpCanvasThumbUrl = _stmt.getText(_columnIndexOfCanvasThumbUrl)
          }
          _item = SongEntity(_tmpVideoId,_tmpAlbumId,_tmpAlbumName,_tmpArtistId,_tmpArtistName,_tmpDuration,_tmpDurationSeconds,_tmpIsAvailable,_tmpIsExplicit,_tmpLikeStatus,_tmpThumbnails,_tmpTitle,_tmpVideoType,_tmpCategory,_tmpResultType,_tmpLiked,_tmpTotalPlayTime,_tmpDownloadState,_tmpFavoriteAt,_tmpDownloadedAt,_tmpInLibrary,_tmpCanvasUrl,_tmpCanvasThumbUrl)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getAllSongs(limit: Int): List<SongEntity> {
    val _sql: String = "SELECT * FROM song ORDER BY inLibrary DESC LIMIT ? OFFSET 0"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, limit.toLong())
        val _columnIndexOfVideoId: Int = getColumnIndexOrThrow(_stmt, "videoId")
        val _columnIndexOfAlbumId: Int = getColumnIndexOrThrow(_stmt, "albumId")
        val _columnIndexOfAlbumName: Int = getColumnIndexOrThrow(_stmt, "albumName")
        val _columnIndexOfArtistId: Int = getColumnIndexOrThrow(_stmt, "artistId")
        val _columnIndexOfArtistName: Int = getColumnIndexOrThrow(_stmt, "artistName")
        val _columnIndexOfDuration: Int = getColumnIndexOrThrow(_stmt, "duration")
        val _columnIndexOfDurationSeconds: Int = getColumnIndexOrThrow(_stmt, "durationSeconds")
        val _columnIndexOfIsAvailable: Int = getColumnIndexOrThrow(_stmt, "isAvailable")
        val _columnIndexOfIsExplicit: Int = getColumnIndexOrThrow(_stmt, "isExplicit")
        val _columnIndexOfLikeStatus: Int = getColumnIndexOrThrow(_stmt, "likeStatus")
        val _columnIndexOfThumbnails: Int = getColumnIndexOrThrow(_stmt, "thumbnails")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfVideoType: Int = getColumnIndexOrThrow(_stmt, "videoType")
        val _columnIndexOfCategory: Int = getColumnIndexOrThrow(_stmt, "category")
        val _columnIndexOfResultType: Int = getColumnIndexOrThrow(_stmt, "resultType")
        val _columnIndexOfLiked: Int = getColumnIndexOrThrow(_stmt, "liked")
        val _columnIndexOfTotalPlayTime: Int = getColumnIndexOrThrow(_stmt, "totalPlayTime")
        val _columnIndexOfDownloadState: Int = getColumnIndexOrThrow(_stmt, "downloadState")
        val _columnIndexOfFavoriteAt: Int = getColumnIndexOrThrow(_stmt, "favoriteAt")
        val _columnIndexOfDownloadedAt: Int = getColumnIndexOrThrow(_stmt, "downloadedAt")
        val _columnIndexOfInLibrary: Int = getColumnIndexOrThrow(_stmt, "inLibrary")
        val _columnIndexOfCanvasUrl: Int = getColumnIndexOrThrow(_stmt, "canvasUrl")
        val _columnIndexOfCanvasThumbUrl: Int = getColumnIndexOrThrow(_stmt, "canvasThumbUrl")
        val _result: MutableList<SongEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: SongEntity
          val _tmpVideoId: String
          _tmpVideoId = _stmt.getText(_columnIndexOfVideoId)
          val _tmpAlbumId: String?
          if (_stmt.isNull(_columnIndexOfAlbumId)) {
            _tmpAlbumId = null
          } else {
            _tmpAlbumId = _stmt.getText(_columnIndexOfAlbumId)
          }
          val _tmpAlbumName: String?
          if (_stmt.isNull(_columnIndexOfAlbumName)) {
            _tmpAlbumName = null
          } else {
            _tmpAlbumName = _stmt.getText(_columnIndexOfAlbumName)
          }
          val _tmpArtistId: List<String>?
          val _tmp: String?
          if (_stmt.isNull(_columnIndexOfArtistId)) {
            _tmp = null
          } else {
            _tmp = _stmt.getText(_columnIndexOfArtistId)
          }
          _tmpArtistId = __converters().fromString(_tmp)
          val _tmpArtistName: List<String>?
          val _tmp_1: String?
          if (_stmt.isNull(_columnIndexOfArtistName)) {
            _tmp_1 = null
          } else {
            _tmp_1 = _stmt.getText(_columnIndexOfArtistName)
          }
          _tmpArtistName = __converters().fromString(_tmp_1)
          val _tmpDuration: String
          _tmpDuration = _stmt.getText(_columnIndexOfDuration)
          val _tmpDurationSeconds: Int
          _tmpDurationSeconds = _stmt.getLong(_columnIndexOfDurationSeconds).toInt()
          val _tmpIsAvailable: Boolean
          val _tmp_2: Int
          _tmp_2 = _stmt.getLong(_columnIndexOfIsAvailable).toInt()
          _tmpIsAvailable = _tmp_2 != 0
          val _tmpIsExplicit: Boolean
          val _tmp_3: Int
          _tmp_3 = _stmt.getLong(_columnIndexOfIsExplicit).toInt()
          _tmpIsExplicit = _tmp_3 != 0
          val _tmpLikeStatus: String
          _tmpLikeStatus = _stmt.getText(_columnIndexOfLikeStatus)
          val _tmpThumbnails: String?
          if (_stmt.isNull(_columnIndexOfThumbnails)) {
            _tmpThumbnails = null
          } else {
            _tmpThumbnails = _stmt.getText(_columnIndexOfThumbnails)
          }
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpVideoType: String
          _tmpVideoType = _stmt.getText(_columnIndexOfVideoType)
          val _tmpCategory: String?
          if (_stmt.isNull(_columnIndexOfCategory)) {
            _tmpCategory = null
          } else {
            _tmpCategory = _stmt.getText(_columnIndexOfCategory)
          }
          val _tmpResultType: String?
          if (_stmt.isNull(_columnIndexOfResultType)) {
            _tmpResultType = null
          } else {
            _tmpResultType = _stmt.getText(_columnIndexOfResultType)
          }
          val _tmpLiked: Boolean
          val _tmp_4: Int
          _tmp_4 = _stmt.getLong(_columnIndexOfLiked).toInt()
          _tmpLiked = _tmp_4 != 0
          val _tmpTotalPlayTime: Long
          _tmpTotalPlayTime = _stmt.getLong(_columnIndexOfTotalPlayTime)
          val _tmpDownloadState: Int
          _tmpDownloadState = _stmt.getLong(_columnIndexOfDownloadState).toInt()
          val _tmpFavoriteAt: LocalDateTime?
          val _tmp_5: Long?
          if (_stmt.isNull(_columnIndexOfFavoriteAt)) {
            _tmp_5 = null
          } else {
            _tmp_5 = _stmt.getLong(_columnIndexOfFavoriteAt)
          }
          _tmpFavoriteAt = __converters().fromTimestamp(_tmp_5)
          val _tmpDownloadedAt: LocalDateTime?
          val _tmp_6: Long?
          if (_stmt.isNull(_columnIndexOfDownloadedAt)) {
            _tmp_6 = null
          } else {
            _tmp_6 = _stmt.getLong(_columnIndexOfDownloadedAt)
          }
          _tmpDownloadedAt = __converters().fromTimestamp(_tmp_6)
          val _tmpInLibrary: LocalDateTime
          val _tmp_7: Long?
          if (_stmt.isNull(_columnIndexOfInLibrary)) {
            _tmp_7 = null
          } else {
            _tmp_7 = _stmt.getLong(_columnIndexOfInLibrary)
          }
          val _tmp_8: LocalDateTime? = __converters().fromTimestamp(_tmp_7)
          if (_tmp_8 == null) {
            error("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.")
          } else {
            _tmpInLibrary = _tmp_8
          }
          val _tmpCanvasUrl: String?
          if (_stmt.isNull(_columnIndexOfCanvasUrl)) {
            _tmpCanvasUrl = null
          } else {
            _tmpCanvasUrl = _stmt.getText(_columnIndexOfCanvasUrl)
          }
          val _tmpCanvasThumbUrl: String?
          if (_stmt.isNull(_columnIndexOfCanvasThumbUrl)) {
            _tmpCanvasThumbUrl = null
          } else {
            _tmpCanvasThumbUrl = _stmt.getText(_columnIndexOfCanvasThumbUrl)
          }
          _item = SongEntity(_tmpVideoId,_tmpAlbumId,_tmpAlbumName,_tmpArtistId,_tmpArtistName,_tmpDuration,_tmpDurationSeconds,_tmpIsAvailable,_tmpIsExplicit,_tmpLikeStatus,_tmpThumbnails,_tmpTitle,_tmpVideoType,_tmpCategory,_tmpResultType,_tmpLiked,_tmpTotalPlayTime,_tmpDownloadState,_tmpFavoriteAt,_tmpDownloadedAt,_tmpInLibrary,_tmpCanvasUrl,_tmpCanvasThumbUrl)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getLikedSongs(limit: Int, offset: Int): List<SongEntity> {
    val _sql: String = "SELECT * FROM song WHERE liked = 1 ORDER BY favoriteAt DESC LIMIT ? OFFSET ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, limit.toLong())
        _argIndex = 2
        _stmt.bindLong(_argIndex, offset.toLong())
        val _columnIndexOfVideoId: Int = getColumnIndexOrThrow(_stmt, "videoId")
        val _columnIndexOfAlbumId: Int = getColumnIndexOrThrow(_stmt, "albumId")
        val _columnIndexOfAlbumName: Int = getColumnIndexOrThrow(_stmt, "albumName")
        val _columnIndexOfArtistId: Int = getColumnIndexOrThrow(_stmt, "artistId")
        val _columnIndexOfArtistName: Int = getColumnIndexOrThrow(_stmt, "artistName")
        val _columnIndexOfDuration: Int = getColumnIndexOrThrow(_stmt, "duration")
        val _columnIndexOfDurationSeconds: Int = getColumnIndexOrThrow(_stmt, "durationSeconds")
        val _columnIndexOfIsAvailable: Int = getColumnIndexOrThrow(_stmt, "isAvailable")
        val _columnIndexOfIsExplicit: Int = getColumnIndexOrThrow(_stmt, "isExplicit")
        val _columnIndexOfLikeStatus: Int = getColumnIndexOrThrow(_stmt, "likeStatus")
        val _columnIndexOfThumbnails: Int = getColumnIndexOrThrow(_stmt, "thumbnails")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfVideoType: Int = getColumnIndexOrThrow(_stmt, "videoType")
        val _columnIndexOfCategory: Int = getColumnIndexOrThrow(_stmt, "category")
        val _columnIndexOfResultType: Int = getColumnIndexOrThrow(_stmt, "resultType")
        val _columnIndexOfLiked: Int = getColumnIndexOrThrow(_stmt, "liked")
        val _columnIndexOfTotalPlayTime: Int = getColumnIndexOrThrow(_stmt, "totalPlayTime")
        val _columnIndexOfDownloadState: Int = getColumnIndexOrThrow(_stmt, "downloadState")
        val _columnIndexOfFavoriteAt: Int = getColumnIndexOrThrow(_stmt, "favoriteAt")
        val _columnIndexOfDownloadedAt: Int = getColumnIndexOrThrow(_stmt, "downloadedAt")
        val _columnIndexOfInLibrary: Int = getColumnIndexOrThrow(_stmt, "inLibrary")
        val _columnIndexOfCanvasUrl: Int = getColumnIndexOrThrow(_stmt, "canvasUrl")
        val _columnIndexOfCanvasThumbUrl: Int = getColumnIndexOrThrow(_stmt, "canvasThumbUrl")
        val _result: MutableList<SongEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: SongEntity
          val _tmpVideoId: String
          _tmpVideoId = _stmt.getText(_columnIndexOfVideoId)
          val _tmpAlbumId: String?
          if (_stmt.isNull(_columnIndexOfAlbumId)) {
            _tmpAlbumId = null
          } else {
            _tmpAlbumId = _stmt.getText(_columnIndexOfAlbumId)
          }
          val _tmpAlbumName: String?
          if (_stmt.isNull(_columnIndexOfAlbumName)) {
            _tmpAlbumName = null
          } else {
            _tmpAlbumName = _stmt.getText(_columnIndexOfAlbumName)
          }
          val _tmpArtistId: List<String>?
          val _tmp: String?
          if (_stmt.isNull(_columnIndexOfArtistId)) {
            _tmp = null
          } else {
            _tmp = _stmt.getText(_columnIndexOfArtistId)
          }
          _tmpArtistId = __converters().fromString(_tmp)
          val _tmpArtistName: List<String>?
          val _tmp_1: String?
          if (_stmt.isNull(_columnIndexOfArtistName)) {
            _tmp_1 = null
          } else {
            _tmp_1 = _stmt.getText(_columnIndexOfArtistName)
          }
          _tmpArtistName = __converters().fromString(_tmp_1)
          val _tmpDuration: String
          _tmpDuration = _stmt.getText(_columnIndexOfDuration)
          val _tmpDurationSeconds: Int
          _tmpDurationSeconds = _stmt.getLong(_columnIndexOfDurationSeconds).toInt()
          val _tmpIsAvailable: Boolean
          val _tmp_2: Int
          _tmp_2 = _stmt.getLong(_columnIndexOfIsAvailable).toInt()
          _tmpIsAvailable = _tmp_2 != 0
          val _tmpIsExplicit: Boolean
          val _tmp_3: Int
          _tmp_3 = _stmt.getLong(_columnIndexOfIsExplicit).toInt()
          _tmpIsExplicit = _tmp_3 != 0
          val _tmpLikeStatus: String
          _tmpLikeStatus = _stmt.getText(_columnIndexOfLikeStatus)
          val _tmpThumbnails: String?
          if (_stmt.isNull(_columnIndexOfThumbnails)) {
            _tmpThumbnails = null
          } else {
            _tmpThumbnails = _stmt.getText(_columnIndexOfThumbnails)
          }
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpVideoType: String
          _tmpVideoType = _stmt.getText(_columnIndexOfVideoType)
          val _tmpCategory: String?
          if (_stmt.isNull(_columnIndexOfCategory)) {
            _tmpCategory = null
          } else {
            _tmpCategory = _stmt.getText(_columnIndexOfCategory)
          }
          val _tmpResultType: String?
          if (_stmt.isNull(_columnIndexOfResultType)) {
            _tmpResultType = null
          } else {
            _tmpResultType = _stmt.getText(_columnIndexOfResultType)
          }
          val _tmpLiked: Boolean
          val _tmp_4: Int
          _tmp_4 = _stmt.getLong(_columnIndexOfLiked).toInt()
          _tmpLiked = _tmp_4 != 0
          val _tmpTotalPlayTime: Long
          _tmpTotalPlayTime = _stmt.getLong(_columnIndexOfTotalPlayTime)
          val _tmpDownloadState: Int
          _tmpDownloadState = _stmt.getLong(_columnIndexOfDownloadState).toInt()
          val _tmpFavoriteAt: LocalDateTime?
          val _tmp_5: Long?
          if (_stmt.isNull(_columnIndexOfFavoriteAt)) {
            _tmp_5 = null
          } else {
            _tmp_5 = _stmt.getLong(_columnIndexOfFavoriteAt)
          }
          _tmpFavoriteAt = __converters().fromTimestamp(_tmp_5)
          val _tmpDownloadedAt: LocalDateTime?
          val _tmp_6: Long?
          if (_stmt.isNull(_columnIndexOfDownloadedAt)) {
            _tmp_6 = null
          } else {
            _tmp_6 = _stmt.getLong(_columnIndexOfDownloadedAt)
          }
          _tmpDownloadedAt = __converters().fromTimestamp(_tmp_6)
          val _tmpInLibrary: LocalDateTime
          val _tmp_7: Long?
          if (_stmt.isNull(_columnIndexOfInLibrary)) {
            _tmp_7 = null
          } else {
            _tmp_7 = _stmt.getLong(_columnIndexOfInLibrary)
          }
          val _tmp_8: LocalDateTime? = __converters().fromTimestamp(_tmp_7)
          if (_tmp_8 == null) {
            error("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.")
          } else {
            _tmpInLibrary = _tmp_8
          }
          val _tmpCanvasUrl: String?
          if (_stmt.isNull(_columnIndexOfCanvasUrl)) {
            _tmpCanvasUrl = null
          } else {
            _tmpCanvasUrl = _stmt.getText(_columnIndexOfCanvasUrl)
          }
          val _tmpCanvasThumbUrl: String?
          if (_stmt.isNull(_columnIndexOfCanvasThumbUrl)) {
            _tmpCanvasThumbUrl = null
          } else {
            _tmpCanvasThumbUrl = _stmt.getText(_columnIndexOfCanvasThumbUrl)
          }
          _item = SongEntity(_tmpVideoId,_tmpAlbumId,_tmpAlbumName,_tmpArtistId,_tmpArtistName,_tmpDuration,_tmpDurationSeconds,_tmpIsAvailable,_tmpIsExplicit,_tmpLikeStatus,_tmpThumbnails,_tmpTitle,_tmpVideoType,_tmpCategory,_tmpResultType,_tmpLiked,_tmpTotalPlayTime,_tmpDownloadState,_tmpFavoriteAt,_tmpDownloadedAt,_tmpInLibrary,_tmpCanvasUrl,_tmpCanvasThumbUrl)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getSong(videoId: String): SongEntity? {
    val _sql: String = "SELECT * FROM song WHERE videoId = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, videoId)
        val _columnIndexOfVideoId: Int = getColumnIndexOrThrow(_stmt, "videoId")
        val _columnIndexOfAlbumId: Int = getColumnIndexOrThrow(_stmt, "albumId")
        val _columnIndexOfAlbumName: Int = getColumnIndexOrThrow(_stmt, "albumName")
        val _columnIndexOfArtistId: Int = getColumnIndexOrThrow(_stmt, "artistId")
        val _columnIndexOfArtistName: Int = getColumnIndexOrThrow(_stmt, "artistName")
        val _columnIndexOfDuration: Int = getColumnIndexOrThrow(_stmt, "duration")
        val _columnIndexOfDurationSeconds: Int = getColumnIndexOrThrow(_stmt, "durationSeconds")
        val _columnIndexOfIsAvailable: Int = getColumnIndexOrThrow(_stmt, "isAvailable")
        val _columnIndexOfIsExplicit: Int = getColumnIndexOrThrow(_stmt, "isExplicit")
        val _columnIndexOfLikeStatus: Int = getColumnIndexOrThrow(_stmt, "likeStatus")
        val _columnIndexOfThumbnails: Int = getColumnIndexOrThrow(_stmt, "thumbnails")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfVideoType: Int = getColumnIndexOrThrow(_stmt, "videoType")
        val _columnIndexOfCategory: Int = getColumnIndexOrThrow(_stmt, "category")
        val _columnIndexOfResultType: Int = getColumnIndexOrThrow(_stmt, "resultType")
        val _columnIndexOfLiked: Int = getColumnIndexOrThrow(_stmt, "liked")
        val _columnIndexOfTotalPlayTime: Int = getColumnIndexOrThrow(_stmt, "totalPlayTime")
        val _columnIndexOfDownloadState: Int = getColumnIndexOrThrow(_stmt, "downloadState")
        val _columnIndexOfFavoriteAt: Int = getColumnIndexOrThrow(_stmt, "favoriteAt")
        val _columnIndexOfDownloadedAt: Int = getColumnIndexOrThrow(_stmt, "downloadedAt")
        val _columnIndexOfInLibrary: Int = getColumnIndexOrThrow(_stmt, "inLibrary")
        val _columnIndexOfCanvasUrl: Int = getColumnIndexOrThrow(_stmt, "canvasUrl")
        val _columnIndexOfCanvasThumbUrl: Int = getColumnIndexOrThrow(_stmt, "canvasThumbUrl")
        val _result: SongEntity?
        if (_stmt.step()) {
          val _tmpVideoId: String
          _tmpVideoId = _stmt.getText(_columnIndexOfVideoId)
          val _tmpAlbumId: String?
          if (_stmt.isNull(_columnIndexOfAlbumId)) {
            _tmpAlbumId = null
          } else {
            _tmpAlbumId = _stmt.getText(_columnIndexOfAlbumId)
          }
          val _tmpAlbumName: String?
          if (_stmt.isNull(_columnIndexOfAlbumName)) {
            _tmpAlbumName = null
          } else {
            _tmpAlbumName = _stmt.getText(_columnIndexOfAlbumName)
          }
          val _tmpArtistId: List<String>?
          val _tmp: String?
          if (_stmt.isNull(_columnIndexOfArtistId)) {
            _tmp = null
          } else {
            _tmp = _stmt.getText(_columnIndexOfArtistId)
          }
          _tmpArtistId = __converters().fromString(_tmp)
          val _tmpArtistName: List<String>?
          val _tmp_1: String?
          if (_stmt.isNull(_columnIndexOfArtistName)) {
            _tmp_1 = null
          } else {
            _tmp_1 = _stmt.getText(_columnIndexOfArtistName)
          }
          _tmpArtistName = __converters().fromString(_tmp_1)
          val _tmpDuration: String
          _tmpDuration = _stmt.getText(_columnIndexOfDuration)
          val _tmpDurationSeconds: Int
          _tmpDurationSeconds = _stmt.getLong(_columnIndexOfDurationSeconds).toInt()
          val _tmpIsAvailable: Boolean
          val _tmp_2: Int
          _tmp_2 = _stmt.getLong(_columnIndexOfIsAvailable).toInt()
          _tmpIsAvailable = _tmp_2 != 0
          val _tmpIsExplicit: Boolean
          val _tmp_3: Int
          _tmp_3 = _stmt.getLong(_columnIndexOfIsExplicit).toInt()
          _tmpIsExplicit = _tmp_3 != 0
          val _tmpLikeStatus: String
          _tmpLikeStatus = _stmt.getText(_columnIndexOfLikeStatus)
          val _tmpThumbnails: String?
          if (_stmt.isNull(_columnIndexOfThumbnails)) {
            _tmpThumbnails = null
          } else {
            _tmpThumbnails = _stmt.getText(_columnIndexOfThumbnails)
          }
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpVideoType: String
          _tmpVideoType = _stmt.getText(_columnIndexOfVideoType)
          val _tmpCategory: String?
          if (_stmt.isNull(_columnIndexOfCategory)) {
            _tmpCategory = null
          } else {
            _tmpCategory = _stmt.getText(_columnIndexOfCategory)
          }
          val _tmpResultType: String?
          if (_stmt.isNull(_columnIndexOfResultType)) {
            _tmpResultType = null
          } else {
            _tmpResultType = _stmt.getText(_columnIndexOfResultType)
          }
          val _tmpLiked: Boolean
          val _tmp_4: Int
          _tmp_4 = _stmt.getLong(_columnIndexOfLiked).toInt()
          _tmpLiked = _tmp_4 != 0
          val _tmpTotalPlayTime: Long
          _tmpTotalPlayTime = _stmt.getLong(_columnIndexOfTotalPlayTime)
          val _tmpDownloadState: Int
          _tmpDownloadState = _stmt.getLong(_columnIndexOfDownloadState).toInt()
          val _tmpFavoriteAt: LocalDateTime?
          val _tmp_5: Long?
          if (_stmt.isNull(_columnIndexOfFavoriteAt)) {
            _tmp_5 = null
          } else {
            _tmp_5 = _stmt.getLong(_columnIndexOfFavoriteAt)
          }
          _tmpFavoriteAt = __converters().fromTimestamp(_tmp_5)
          val _tmpDownloadedAt: LocalDateTime?
          val _tmp_6: Long?
          if (_stmt.isNull(_columnIndexOfDownloadedAt)) {
            _tmp_6 = null
          } else {
            _tmp_6 = _stmt.getLong(_columnIndexOfDownloadedAt)
          }
          _tmpDownloadedAt = __converters().fromTimestamp(_tmp_6)
          val _tmpInLibrary: LocalDateTime
          val _tmp_7: Long?
          if (_stmt.isNull(_columnIndexOfInLibrary)) {
            _tmp_7 = null
          } else {
            _tmp_7 = _stmt.getLong(_columnIndexOfInLibrary)
          }
          val _tmp_8: LocalDateTime? = __converters().fromTimestamp(_tmp_7)
          if (_tmp_8 == null) {
            error("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.")
          } else {
            _tmpInLibrary = _tmp_8
          }
          val _tmpCanvasUrl: String?
          if (_stmt.isNull(_columnIndexOfCanvasUrl)) {
            _tmpCanvasUrl = null
          } else {
            _tmpCanvasUrl = _stmt.getText(_columnIndexOfCanvasUrl)
          }
          val _tmpCanvasThumbUrl: String?
          if (_stmt.isNull(_columnIndexOfCanvasThumbUrl)) {
            _tmpCanvasThumbUrl = null
          } else {
            _tmpCanvasThumbUrl = _stmt.getText(_columnIndexOfCanvasThumbUrl)
          }
          _result = SongEntity(_tmpVideoId,_tmpAlbumId,_tmpAlbumName,_tmpArtistId,_tmpArtistName,_tmpDuration,_tmpDurationSeconds,_tmpIsAvailable,_tmpIsExplicit,_tmpLikeStatus,_tmpThumbnails,_tmpTitle,_tmpVideoType,_tmpCategory,_tmpResultType,_tmpLiked,_tmpTotalPlayTime,_tmpDownloadState,_tmpFavoriteAt,_tmpDownloadedAt,_tmpInLibrary,_tmpCanvasUrl,_tmpCanvasThumbUrl)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getSongAsFlow(videoId: String): Flow<SongEntity?> {
    val _sql: String = "SELECT * FROM song WHERE videoId = ?"
    return createFlow(__db, false, arrayOf("song")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, videoId)
        val _columnIndexOfVideoId: Int = getColumnIndexOrThrow(_stmt, "videoId")
        val _columnIndexOfAlbumId: Int = getColumnIndexOrThrow(_stmt, "albumId")
        val _columnIndexOfAlbumName: Int = getColumnIndexOrThrow(_stmt, "albumName")
        val _columnIndexOfArtistId: Int = getColumnIndexOrThrow(_stmt, "artistId")
        val _columnIndexOfArtistName: Int = getColumnIndexOrThrow(_stmt, "artistName")
        val _columnIndexOfDuration: Int = getColumnIndexOrThrow(_stmt, "duration")
        val _columnIndexOfDurationSeconds: Int = getColumnIndexOrThrow(_stmt, "durationSeconds")
        val _columnIndexOfIsAvailable: Int = getColumnIndexOrThrow(_stmt, "isAvailable")
        val _columnIndexOfIsExplicit: Int = getColumnIndexOrThrow(_stmt, "isExplicit")
        val _columnIndexOfLikeStatus: Int = getColumnIndexOrThrow(_stmt, "likeStatus")
        val _columnIndexOfThumbnails: Int = getColumnIndexOrThrow(_stmt, "thumbnails")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfVideoType: Int = getColumnIndexOrThrow(_stmt, "videoType")
        val _columnIndexOfCategory: Int = getColumnIndexOrThrow(_stmt, "category")
        val _columnIndexOfResultType: Int = getColumnIndexOrThrow(_stmt, "resultType")
        val _columnIndexOfLiked: Int = getColumnIndexOrThrow(_stmt, "liked")
        val _columnIndexOfTotalPlayTime: Int = getColumnIndexOrThrow(_stmt, "totalPlayTime")
        val _columnIndexOfDownloadState: Int = getColumnIndexOrThrow(_stmt, "downloadState")
        val _columnIndexOfFavoriteAt: Int = getColumnIndexOrThrow(_stmt, "favoriteAt")
        val _columnIndexOfDownloadedAt: Int = getColumnIndexOrThrow(_stmt, "downloadedAt")
        val _columnIndexOfInLibrary: Int = getColumnIndexOrThrow(_stmt, "inLibrary")
        val _columnIndexOfCanvasUrl: Int = getColumnIndexOrThrow(_stmt, "canvasUrl")
        val _columnIndexOfCanvasThumbUrl: Int = getColumnIndexOrThrow(_stmt, "canvasThumbUrl")
        val _result: SongEntity?
        if (_stmt.step()) {
          val _tmpVideoId: String
          _tmpVideoId = _stmt.getText(_columnIndexOfVideoId)
          val _tmpAlbumId: String?
          if (_stmt.isNull(_columnIndexOfAlbumId)) {
            _tmpAlbumId = null
          } else {
            _tmpAlbumId = _stmt.getText(_columnIndexOfAlbumId)
          }
          val _tmpAlbumName: String?
          if (_stmt.isNull(_columnIndexOfAlbumName)) {
            _tmpAlbumName = null
          } else {
            _tmpAlbumName = _stmt.getText(_columnIndexOfAlbumName)
          }
          val _tmpArtistId: List<String>?
          val _tmp: String?
          if (_stmt.isNull(_columnIndexOfArtistId)) {
            _tmp = null
          } else {
            _tmp = _stmt.getText(_columnIndexOfArtistId)
          }
          _tmpArtistId = __converters().fromString(_tmp)
          val _tmpArtistName: List<String>?
          val _tmp_1: String?
          if (_stmt.isNull(_columnIndexOfArtistName)) {
            _tmp_1 = null
          } else {
            _tmp_1 = _stmt.getText(_columnIndexOfArtistName)
          }
          _tmpArtistName = __converters().fromString(_tmp_1)
          val _tmpDuration: String
          _tmpDuration = _stmt.getText(_columnIndexOfDuration)
          val _tmpDurationSeconds: Int
          _tmpDurationSeconds = _stmt.getLong(_columnIndexOfDurationSeconds).toInt()
          val _tmpIsAvailable: Boolean
          val _tmp_2: Int
          _tmp_2 = _stmt.getLong(_columnIndexOfIsAvailable).toInt()
          _tmpIsAvailable = _tmp_2 != 0
          val _tmpIsExplicit: Boolean
          val _tmp_3: Int
          _tmp_3 = _stmt.getLong(_columnIndexOfIsExplicit).toInt()
          _tmpIsExplicit = _tmp_3 != 0
          val _tmpLikeStatus: String
          _tmpLikeStatus = _stmt.getText(_columnIndexOfLikeStatus)
          val _tmpThumbnails: String?
          if (_stmt.isNull(_columnIndexOfThumbnails)) {
            _tmpThumbnails = null
          } else {
            _tmpThumbnails = _stmt.getText(_columnIndexOfThumbnails)
          }
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpVideoType: String
          _tmpVideoType = _stmt.getText(_columnIndexOfVideoType)
          val _tmpCategory: String?
          if (_stmt.isNull(_columnIndexOfCategory)) {
            _tmpCategory = null
          } else {
            _tmpCategory = _stmt.getText(_columnIndexOfCategory)
          }
          val _tmpResultType: String?
          if (_stmt.isNull(_columnIndexOfResultType)) {
            _tmpResultType = null
          } else {
            _tmpResultType = _stmt.getText(_columnIndexOfResultType)
          }
          val _tmpLiked: Boolean
          val _tmp_4: Int
          _tmp_4 = _stmt.getLong(_columnIndexOfLiked).toInt()
          _tmpLiked = _tmp_4 != 0
          val _tmpTotalPlayTime: Long
          _tmpTotalPlayTime = _stmt.getLong(_columnIndexOfTotalPlayTime)
          val _tmpDownloadState: Int
          _tmpDownloadState = _stmt.getLong(_columnIndexOfDownloadState).toInt()
          val _tmpFavoriteAt: LocalDateTime?
          val _tmp_5: Long?
          if (_stmt.isNull(_columnIndexOfFavoriteAt)) {
            _tmp_5 = null
          } else {
            _tmp_5 = _stmt.getLong(_columnIndexOfFavoriteAt)
          }
          _tmpFavoriteAt = __converters().fromTimestamp(_tmp_5)
          val _tmpDownloadedAt: LocalDateTime?
          val _tmp_6: Long?
          if (_stmt.isNull(_columnIndexOfDownloadedAt)) {
            _tmp_6 = null
          } else {
            _tmp_6 = _stmt.getLong(_columnIndexOfDownloadedAt)
          }
          _tmpDownloadedAt = __converters().fromTimestamp(_tmp_6)
          val _tmpInLibrary: LocalDateTime
          val _tmp_7: Long?
          if (_stmt.isNull(_columnIndexOfInLibrary)) {
            _tmp_7 = null
          } else {
            _tmp_7 = _stmt.getLong(_columnIndexOfInLibrary)
          }
          val _tmp_8: LocalDateTime? = __converters().fromTimestamp(_tmp_7)
          if (_tmp_8 == null) {
            error("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.")
          } else {
            _tmpInLibrary = _tmp_8
          }
          val _tmpCanvasUrl: String?
          if (_stmt.isNull(_columnIndexOfCanvasUrl)) {
            _tmpCanvasUrl = null
          } else {
            _tmpCanvasUrl = _stmt.getText(_columnIndexOfCanvasUrl)
          }
          val _tmpCanvasThumbUrl: String?
          if (_stmt.isNull(_columnIndexOfCanvasThumbUrl)) {
            _tmpCanvasThumbUrl = null
          } else {
            _tmpCanvasThumbUrl = _stmt.getText(_columnIndexOfCanvasThumbUrl)
          }
          _result = SongEntity(_tmpVideoId,_tmpAlbumId,_tmpAlbumName,_tmpArtistId,_tmpArtistName,_tmpDuration,_tmpDurationSeconds,_tmpIsAvailable,_tmpIsExplicit,_tmpLikeStatus,_tmpThumbnails,_tmpTitle,_tmpVideoType,_tmpCategory,_tmpResultType,_tmpLiked,_tmpTotalPlayTime,_tmpDownloadState,_tmpFavoriteAt,_tmpDownloadedAt,_tmpInLibrary,_tmpCanvasUrl,_tmpCanvasThumbUrl)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getMostPlayedSongs(): Flow<List<SongEntity>> {
    val _sql: String = "SELECT * FROM song WHERE totalPlayTime > 1 ORDER BY totalPlayTime DESC LIMIT 50"
    return createFlow(__db, false, arrayOf("song")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfVideoId: Int = getColumnIndexOrThrow(_stmt, "videoId")
        val _columnIndexOfAlbumId: Int = getColumnIndexOrThrow(_stmt, "albumId")
        val _columnIndexOfAlbumName: Int = getColumnIndexOrThrow(_stmt, "albumName")
        val _columnIndexOfArtistId: Int = getColumnIndexOrThrow(_stmt, "artistId")
        val _columnIndexOfArtistName: Int = getColumnIndexOrThrow(_stmt, "artistName")
        val _columnIndexOfDuration: Int = getColumnIndexOrThrow(_stmt, "duration")
        val _columnIndexOfDurationSeconds: Int = getColumnIndexOrThrow(_stmt, "durationSeconds")
        val _columnIndexOfIsAvailable: Int = getColumnIndexOrThrow(_stmt, "isAvailable")
        val _columnIndexOfIsExplicit: Int = getColumnIndexOrThrow(_stmt, "isExplicit")
        val _columnIndexOfLikeStatus: Int = getColumnIndexOrThrow(_stmt, "likeStatus")
        val _columnIndexOfThumbnails: Int = getColumnIndexOrThrow(_stmt, "thumbnails")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfVideoType: Int = getColumnIndexOrThrow(_stmt, "videoType")
        val _columnIndexOfCategory: Int = getColumnIndexOrThrow(_stmt, "category")
        val _columnIndexOfResultType: Int = getColumnIndexOrThrow(_stmt, "resultType")
        val _columnIndexOfLiked: Int = getColumnIndexOrThrow(_stmt, "liked")
        val _columnIndexOfTotalPlayTime: Int = getColumnIndexOrThrow(_stmt, "totalPlayTime")
        val _columnIndexOfDownloadState: Int = getColumnIndexOrThrow(_stmt, "downloadState")
        val _columnIndexOfFavoriteAt: Int = getColumnIndexOrThrow(_stmt, "favoriteAt")
        val _columnIndexOfDownloadedAt: Int = getColumnIndexOrThrow(_stmt, "downloadedAt")
        val _columnIndexOfInLibrary: Int = getColumnIndexOrThrow(_stmt, "inLibrary")
        val _columnIndexOfCanvasUrl: Int = getColumnIndexOrThrow(_stmt, "canvasUrl")
        val _columnIndexOfCanvasThumbUrl: Int = getColumnIndexOrThrow(_stmt, "canvasThumbUrl")
        val _result: MutableList<SongEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: SongEntity
          val _tmpVideoId: String
          _tmpVideoId = _stmt.getText(_columnIndexOfVideoId)
          val _tmpAlbumId: String?
          if (_stmt.isNull(_columnIndexOfAlbumId)) {
            _tmpAlbumId = null
          } else {
            _tmpAlbumId = _stmt.getText(_columnIndexOfAlbumId)
          }
          val _tmpAlbumName: String?
          if (_stmt.isNull(_columnIndexOfAlbumName)) {
            _tmpAlbumName = null
          } else {
            _tmpAlbumName = _stmt.getText(_columnIndexOfAlbumName)
          }
          val _tmpArtistId: List<String>?
          val _tmp: String?
          if (_stmt.isNull(_columnIndexOfArtistId)) {
            _tmp = null
          } else {
            _tmp = _stmt.getText(_columnIndexOfArtistId)
          }
          _tmpArtistId = __converters().fromString(_tmp)
          val _tmpArtistName: List<String>?
          val _tmp_1: String?
          if (_stmt.isNull(_columnIndexOfArtistName)) {
            _tmp_1 = null
          } else {
            _tmp_1 = _stmt.getText(_columnIndexOfArtistName)
          }
          _tmpArtistName = __converters().fromString(_tmp_1)
          val _tmpDuration: String
          _tmpDuration = _stmt.getText(_columnIndexOfDuration)
          val _tmpDurationSeconds: Int
          _tmpDurationSeconds = _stmt.getLong(_columnIndexOfDurationSeconds).toInt()
          val _tmpIsAvailable: Boolean
          val _tmp_2: Int
          _tmp_2 = _stmt.getLong(_columnIndexOfIsAvailable).toInt()
          _tmpIsAvailable = _tmp_2 != 0
          val _tmpIsExplicit: Boolean
          val _tmp_3: Int
          _tmp_3 = _stmt.getLong(_columnIndexOfIsExplicit).toInt()
          _tmpIsExplicit = _tmp_3 != 0
          val _tmpLikeStatus: String
          _tmpLikeStatus = _stmt.getText(_columnIndexOfLikeStatus)
          val _tmpThumbnails: String?
          if (_stmt.isNull(_columnIndexOfThumbnails)) {
            _tmpThumbnails = null
          } else {
            _tmpThumbnails = _stmt.getText(_columnIndexOfThumbnails)
          }
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpVideoType: String
          _tmpVideoType = _stmt.getText(_columnIndexOfVideoType)
          val _tmpCategory: String?
          if (_stmt.isNull(_columnIndexOfCategory)) {
            _tmpCategory = null
          } else {
            _tmpCategory = _stmt.getText(_columnIndexOfCategory)
          }
          val _tmpResultType: String?
          if (_stmt.isNull(_columnIndexOfResultType)) {
            _tmpResultType = null
          } else {
            _tmpResultType = _stmt.getText(_columnIndexOfResultType)
          }
          val _tmpLiked: Boolean
          val _tmp_4: Int
          _tmp_4 = _stmt.getLong(_columnIndexOfLiked).toInt()
          _tmpLiked = _tmp_4 != 0
          val _tmpTotalPlayTime: Long
          _tmpTotalPlayTime = _stmt.getLong(_columnIndexOfTotalPlayTime)
          val _tmpDownloadState: Int
          _tmpDownloadState = _stmt.getLong(_columnIndexOfDownloadState).toInt()
          val _tmpFavoriteAt: LocalDateTime?
          val _tmp_5: Long?
          if (_stmt.isNull(_columnIndexOfFavoriteAt)) {
            _tmp_5 = null
          } else {
            _tmp_5 = _stmt.getLong(_columnIndexOfFavoriteAt)
          }
          _tmpFavoriteAt = __converters().fromTimestamp(_tmp_5)
          val _tmpDownloadedAt: LocalDateTime?
          val _tmp_6: Long?
          if (_stmt.isNull(_columnIndexOfDownloadedAt)) {
            _tmp_6 = null
          } else {
            _tmp_6 = _stmt.getLong(_columnIndexOfDownloadedAt)
          }
          _tmpDownloadedAt = __converters().fromTimestamp(_tmp_6)
          val _tmpInLibrary: LocalDateTime
          val _tmp_7: Long?
          if (_stmt.isNull(_columnIndexOfInLibrary)) {
            _tmp_7 = null
          } else {
            _tmp_7 = _stmt.getLong(_columnIndexOfInLibrary)
          }
          val _tmp_8: LocalDateTime? = __converters().fromTimestamp(_tmp_7)
          if (_tmp_8 == null) {
            error("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.")
          } else {
            _tmpInLibrary = _tmp_8
          }
          val _tmpCanvasUrl: String?
          if (_stmt.isNull(_columnIndexOfCanvasUrl)) {
            _tmpCanvasUrl = null
          } else {
            _tmpCanvasUrl = _stmt.getText(_columnIndexOfCanvasUrl)
          }
          val _tmpCanvasThumbUrl: String?
          if (_stmt.isNull(_columnIndexOfCanvasThumbUrl)) {
            _tmpCanvasThumbUrl = null
          } else {
            _tmpCanvasThumbUrl = _stmt.getText(_columnIndexOfCanvasThumbUrl)
          }
          _item = SongEntity(_tmpVideoId,_tmpAlbumId,_tmpAlbumName,_tmpArtistId,_tmpArtistName,_tmpDuration,_tmpDurationSeconds,_tmpIsAvailable,_tmpIsExplicit,_tmpLikeStatus,_tmpThumbnails,_tmpTitle,_tmpVideoType,_tmpCategory,_tmpResultType,_tmpLiked,_tmpTotalPlayTime,_tmpDownloadState,_tmpFavoriteAt,_tmpDownloadedAt,_tmpInLibrary,_tmpCanvasUrl,_tmpCanvasThumbUrl)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getDownloadedSongs(limit: Int, offset: Int): List<SongEntity> {
    val _sql: String = "SELECT * FROM song WHERE downloadState = 3 LIMIT ? OFFSET ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, limit.toLong())
        _argIndex = 2
        _stmt.bindLong(_argIndex, offset.toLong())
        val _columnIndexOfVideoId: Int = getColumnIndexOrThrow(_stmt, "videoId")
        val _columnIndexOfAlbumId: Int = getColumnIndexOrThrow(_stmt, "albumId")
        val _columnIndexOfAlbumName: Int = getColumnIndexOrThrow(_stmt, "albumName")
        val _columnIndexOfArtistId: Int = getColumnIndexOrThrow(_stmt, "artistId")
        val _columnIndexOfArtistName: Int = getColumnIndexOrThrow(_stmt, "artistName")
        val _columnIndexOfDuration: Int = getColumnIndexOrThrow(_stmt, "duration")
        val _columnIndexOfDurationSeconds: Int = getColumnIndexOrThrow(_stmt, "durationSeconds")
        val _columnIndexOfIsAvailable: Int = getColumnIndexOrThrow(_stmt, "isAvailable")
        val _columnIndexOfIsExplicit: Int = getColumnIndexOrThrow(_stmt, "isExplicit")
        val _columnIndexOfLikeStatus: Int = getColumnIndexOrThrow(_stmt, "likeStatus")
        val _columnIndexOfThumbnails: Int = getColumnIndexOrThrow(_stmt, "thumbnails")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfVideoType: Int = getColumnIndexOrThrow(_stmt, "videoType")
        val _columnIndexOfCategory: Int = getColumnIndexOrThrow(_stmt, "category")
        val _columnIndexOfResultType: Int = getColumnIndexOrThrow(_stmt, "resultType")
        val _columnIndexOfLiked: Int = getColumnIndexOrThrow(_stmt, "liked")
        val _columnIndexOfTotalPlayTime: Int = getColumnIndexOrThrow(_stmt, "totalPlayTime")
        val _columnIndexOfDownloadState: Int = getColumnIndexOrThrow(_stmt, "downloadState")
        val _columnIndexOfFavoriteAt: Int = getColumnIndexOrThrow(_stmt, "favoriteAt")
        val _columnIndexOfDownloadedAt: Int = getColumnIndexOrThrow(_stmt, "downloadedAt")
        val _columnIndexOfInLibrary: Int = getColumnIndexOrThrow(_stmt, "inLibrary")
        val _columnIndexOfCanvasUrl: Int = getColumnIndexOrThrow(_stmt, "canvasUrl")
        val _columnIndexOfCanvasThumbUrl: Int = getColumnIndexOrThrow(_stmt, "canvasThumbUrl")
        val _result: MutableList<SongEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: SongEntity
          val _tmpVideoId: String
          _tmpVideoId = _stmt.getText(_columnIndexOfVideoId)
          val _tmpAlbumId: String?
          if (_stmt.isNull(_columnIndexOfAlbumId)) {
            _tmpAlbumId = null
          } else {
            _tmpAlbumId = _stmt.getText(_columnIndexOfAlbumId)
          }
          val _tmpAlbumName: String?
          if (_stmt.isNull(_columnIndexOfAlbumName)) {
            _tmpAlbumName = null
          } else {
            _tmpAlbumName = _stmt.getText(_columnIndexOfAlbumName)
          }
          val _tmpArtistId: List<String>?
          val _tmp: String?
          if (_stmt.isNull(_columnIndexOfArtistId)) {
            _tmp = null
          } else {
            _tmp = _stmt.getText(_columnIndexOfArtistId)
          }
          _tmpArtistId = __converters().fromString(_tmp)
          val _tmpArtistName: List<String>?
          val _tmp_1: String?
          if (_stmt.isNull(_columnIndexOfArtistName)) {
            _tmp_1 = null
          } else {
            _tmp_1 = _stmt.getText(_columnIndexOfArtistName)
          }
          _tmpArtistName = __converters().fromString(_tmp_1)
          val _tmpDuration: String
          _tmpDuration = _stmt.getText(_columnIndexOfDuration)
          val _tmpDurationSeconds: Int
          _tmpDurationSeconds = _stmt.getLong(_columnIndexOfDurationSeconds).toInt()
          val _tmpIsAvailable: Boolean
          val _tmp_2: Int
          _tmp_2 = _stmt.getLong(_columnIndexOfIsAvailable).toInt()
          _tmpIsAvailable = _tmp_2 != 0
          val _tmpIsExplicit: Boolean
          val _tmp_3: Int
          _tmp_3 = _stmt.getLong(_columnIndexOfIsExplicit).toInt()
          _tmpIsExplicit = _tmp_3 != 0
          val _tmpLikeStatus: String
          _tmpLikeStatus = _stmt.getText(_columnIndexOfLikeStatus)
          val _tmpThumbnails: String?
          if (_stmt.isNull(_columnIndexOfThumbnails)) {
            _tmpThumbnails = null
          } else {
            _tmpThumbnails = _stmt.getText(_columnIndexOfThumbnails)
          }
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpVideoType: String
          _tmpVideoType = _stmt.getText(_columnIndexOfVideoType)
          val _tmpCategory: String?
          if (_stmt.isNull(_columnIndexOfCategory)) {
            _tmpCategory = null
          } else {
            _tmpCategory = _stmt.getText(_columnIndexOfCategory)
          }
          val _tmpResultType: String?
          if (_stmt.isNull(_columnIndexOfResultType)) {
            _tmpResultType = null
          } else {
            _tmpResultType = _stmt.getText(_columnIndexOfResultType)
          }
          val _tmpLiked: Boolean
          val _tmp_4: Int
          _tmp_4 = _stmt.getLong(_columnIndexOfLiked).toInt()
          _tmpLiked = _tmp_4 != 0
          val _tmpTotalPlayTime: Long
          _tmpTotalPlayTime = _stmt.getLong(_columnIndexOfTotalPlayTime)
          val _tmpDownloadState: Int
          _tmpDownloadState = _stmt.getLong(_columnIndexOfDownloadState).toInt()
          val _tmpFavoriteAt: LocalDateTime?
          val _tmp_5: Long?
          if (_stmt.isNull(_columnIndexOfFavoriteAt)) {
            _tmp_5 = null
          } else {
            _tmp_5 = _stmt.getLong(_columnIndexOfFavoriteAt)
          }
          _tmpFavoriteAt = __converters().fromTimestamp(_tmp_5)
          val _tmpDownloadedAt: LocalDateTime?
          val _tmp_6: Long?
          if (_stmt.isNull(_columnIndexOfDownloadedAt)) {
            _tmp_6 = null
          } else {
            _tmp_6 = _stmt.getLong(_columnIndexOfDownloadedAt)
          }
          _tmpDownloadedAt = __converters().fromTimestamp(_tmp_6)
          val _tmpInLibrary: LocalDateTime
          val _tmp_7: Long?
          if (_stmt.isNull(_columnIndexOfInLibrary)) {
            _tmp_7 = null
          } else {
            _tmp_7 = _stmt.getLong(_columnIndexOfInLibrary)
          }
          val _tmp_8: LocalDateTime? = __converters().fromTimestamp(_tmp_7)
          if (_tmp_8 == null) {
            error("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.")
          } else {
            _tmpInLibrary = _tmp_8
          }
          val _tmpCanvasUrl: String?
          if (_stmt.isNull(_columnIndexOfCanvasUrl)) {
            _tmpCanvasUrl = null
          } else {
            _tmpCanvasUrl = _stmt.getText(_columnIndexOfCanvasUrl)
          }
          val _tmpCanvasThumbUrl: String?
          if (_stmt.isNull(_columnIndexOfCanvasThumbUrl)) {
            _tmpCanvasThumbUrl = null
          } else {
            _tmpCanvasThumbUrl = _stmt.getText(_columnIndexOfCanvasThumbUrl)
          }
          _item = SongEntity(_tmpVideoId,_tmpAlbumId,_tmpAlbumName,_tmpArtistId,_tmpArtistName,_tmpDuration,_tmpDurationSeconds,_tmpIsAvailable,_tmpIsExplicit,_tmpLikeStatus,_tmpThumbnails,_tmpTitle,_tmpVideoType,_tmpCategory,_tmpResultType,_tmpLiked,_tmpTotalPlayTime,_tmpDownloadState,_tmpFavoriteAt,_tmpDownloadedAt,_tmpInLibrary,_tmpCanvasUrl,_tmpCanvasThumbUrl)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getDownloadingSongs(limit: Int, offset: Int): List<SongEntity> {
    val _sql: String = "SELECT * FROM song WHERE downloadState = 1 OR downloadState = 2 LIMIT ? OFFSET ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, limit.toLong())
        _argIndex = 2
        _stmt.bindLong(_argIndex, offset.toLong())
        val _columnIndexOfVideoId: Int = getColumnIndexOrThrow(_stmt, "videoId")
        val _columnIndexOfAlbumId: Int = getColumnIndexOrThrow(_stmt, "albumId")
        val _columnIndexOfAlbumName: Int = getColumnIndexOrThrow(_stmt, "albumName")
        val _columnIndexOfArtistId: Int = getColumnIndexOrThrow(_stmt, "artistId")
        val _columnIndexOfArtistName: Int = getColumnIndexOrThrow(_stmt, "artistName")
        val _columnIndexOfDuration: Int = getColumnIndexOrThrow(_stmt, "duration")
        val _columnIndexOfDurationSeconds: Int = getColumnIndexOrThrow(_stmt, "durationSeconds")
        val _columnIndexOfIsAvailable: Int = getColumnIndexOrThrow(_stmt, "isAvailable")
        val _columnIndexOfIsExplicit: Int = getColumnIndexOrThrow(_stmt, "isExplicit")
        val _columnIndexOfLikeStatus: Int = getColumnIndexOrThrow(_stmt, "likeStatus")
        val _columnIndexOfThumbnails: Int = getColumnIndexOrThrow(_stmt, "thumbnails")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfVideoType: Int = getColumnIndexOrThrow(_stmt, "videoType")
        val _columnIndexOfCategory: Int = getColumnIndexOrThrow(_stmt, "category")
        val _columnIndexOfResultType: Int = getColumnIndexOrThrow(_stmt, "resultType")
        val _columnIndexOfLiked: Int = getColumnIndexOrThrow(_stmt, "liked")
        val _columnIndexOfTotalPlayTime: Int = getColumnIndexOrThrow(_stmt, "totalPlayTime")
        val _columnIndexOfDownloadState: Int = getColumnIndexOrThrow(_stmt, "downloadState")
        val _columnIndexOfFavoriteAt: Int = getColumnIndexOrThrow(_stmt, "favoriteAt")
        val _columnIndexOfDownloadedAt: Int = getColumnIndexOrThrow(_stmt, "downloadedAt")
        val _columnIndexOfInLibrary: Int = getColumnIndexOrThrow(_stmt, "inLibrary")
        val _columnIndexOfCanvasUrl: Int = getColumnIndexOrThrow(_stmt, "canvasUrl")
        val _columnIndexOfCanvasThumbUrl: Int = getColumnIndexOrThrow(_stmt, "canvasThumbUrl")
        val _result: MutableList<SongEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: SongEntity
          val _tmpVideoId: String
          _tmpVideoId = _stmt.getText(_columnIndexOfVideoId)
          val _tmpAlbumId: String?
          if (_stmt.isNull(_columnIndexOfAlbumId)) {
            _tmpAlbumId = null
          } else {
            _tmpAlbumId = _stmt.getText(_columnIndexOfAlbumId)
          }
          val _tmpAlbumName: String?
          if (_stmt.isNull(_columnIndexOfAlbumName)) {
            _tmpAlbumName = null
          } else {
            _tmpAlbumName = _stmt.getText(_columnIndexOfAlbumName)
          }
          val _tmpArtistId: List<String>?
          val _tmp: String?
          if (_stmt.isNull(_columnIndexOfArtistId)) {
            _tmp = null
          } else {
            _tmp = _stmt.getText(_columnIndexOfArtistId)
          }
          _tmpArtistId = __converters().fromString(_tmp)
          val _tmpArtistName: List<String>?
          val _tmp_1: String?
          if (_stmt.isNull(_columnIndexOfArtistName)) {
            _tmp_1 = null
          } else {
            _tmp_1 = _stmt.getText(_columnIndexOfArtistName)
          }
          _tmpArtistName = __converters().fromString(_tmp_1)
          val _tmpDuration: String
          _tmpDuration = _stmt.getText(_columnIndexOfDuration)
          val _tmpDurationSeconds: Int
          _tmpDurationSeconds = _stmt.getLong(_columnIndexOfDurationSeconds).toInt()
          val _tmpIsAvailable: Boolean
          val _tmp_2: Int
          _tmp_2 = _stmt.getLong(_columnIndexOfIsAvailable).toInt()
          _tmpIsAvailable = _tmp_2 != 0
          val _tmpIsExplicit: Boolean
          val _tmp_3: Int
          _tmp_3 = _stmt.getLong(_columnIndexOfIsExplicit).toInt()
          _tmpIsExplicit = _tmp_3 != 0
          val _tmpLikeStatus: String
          _tmpLikeStatus = _stmt.getText(_columnIndexOfLikeStatus)
          val _tmpThumbnails: String?
          if (_stmt.isNull(_columnIndexOfThumbnails)) {
            _tmpThumbnails = null
          } else {
            _tmpThumbnails = _stmt.getText(_columnIndexOfThumbnails)
          }
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpVideoType: String
          _tmpVideoType = _stmt.getText(_columnIndexOfVideoType)
          val _tmpCategory: String?
          if (_stmt.isNull(_columnIndexOfCategory)) {
            _tmpCategory = null
          } else {
            _tmpCategory = _stmt.getText(_columnIndexOfCategory)
          }
          val _tmpResultType: String?
          if (_stmt.isNull(_columnIndexOfResultType)) {
            _tmpResultType = null
          } else {
            _tmpResultType = _stmt.getText(_columnIndexOfResultType)
          }
          val _tmpLiked: Boolean
          val _tmp_4: Int
          _tmp_4 = _stmt.getLong(_columnIndexOfLiked).toInt()
          _tmpLiked = _tmp_4 != 0
          val _tmpTotalPlayTime: Long
          _tmpTotalPlayTime = _stmt.getLong(_columnIndexOfTotalPlayTime)
          val _tmpDownloadState: Int
          _tmpDownloadState = _stmt.getLong(_columnIndexOfDownloadState).toInt()
          val _tmpFavoriteAt: LocalDateTime?
          val _tmp_5: Long?
          if (_stmt.isNull(_columnIndexOfFavoriteAt)) {
            _tmp_5 = null
          } else {
            _tmp_5 = _stmt.getLong(_columnIndexOfFavoriteAt)
          }
          _tmpFavoriteAt = __converters().fromTimestamp(_tmp_5)
          val _tmpDownloadedAt: LocalDateTime?
          val _tmp_6: Long?
          if (_stmt.isNull(_columnIndexOfDownloadedAt)) {
            _tmp_6 = null
          } else {
            _tmp_6 = _stmt.getLong(_columnIndexOfDownloadedAt)
          }
          _tmpDownloadedAt = __converters().fromTimestamp(_tmp_6)
          val _tmpInLibrary: LocalDateTime
          val _tmp_7: Long?
          if (_stmt.isNull(_columnIndexOfInLibrary)) {
            _tmp_7 = null
          } else {
            _tmp_7 = _stmt.getLong(_columnIndexOfInLibrary)
          }
          val _tmp_8: LocalDateTime? = __converters().fromTimestamp(_tmp_7)
          if (_tmp_8 == null) {
            error("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.")
          } else {
            _tmpInLibrary = _tmp_8
          }
          val _tmpCanvasUrl: String?
          if (_stmt.isNull(_columnIndexOfCanvasUrl)) {
            _tmpCanvasUrl = null
          } else {
            _tmpCanvasUrl = _stmt.getText(_columnIndexOfCanvasUrl)
          }
          val _tmpCanvasThumbUrl: String?
          if (_stmt.isNull(_columnIndexOfCanvasThumbUrl)) {
            _tmpCanvasThumbUrl = null
          } else {
            _tmpCanvasThumbUrl = _stmt.getText(_columnIndexOfCanvasThumbUrl)
          }
          _item = SongEntity(_tmpVideoId,_tmpAlbumId,_tmpAlbumName,_tmpArtistId,_tmpArtistName,_tmpDuration,_tmpDurationSeconds,_tmpIsAvailable,_tmpIsExplicit,_tmpLikeStatus,_tmpThumbnails,_tmpTitle,_tmpVideoType,_tmpCategory,_tmpResultType,_tmpLiked,_tmpTotalPlayTime,_tmpDownloadState,_tmpFavoriteAt,_tmpDownloadedAt,_tmpInLibrary,_tmpCanvasUrl,_tmpCanvasThumbUrl)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getSongByListVideoIdFull(primaryKeyList: List<String>): List<SongEntity> {
    val _stringBuilder: StringBuilder = StringBuilder()
    _stringBuilder.append("SELECT * FROM song WHERE videoId IN (")
    val _inputSize: Int = primaryKeyList.size
    appendPlaceholders(_stringBuilder, _inputSize)
    _stringBuilder.append(") LIMIT 1000")
    val _sql: String = _stringBuilder.toString()
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        for (_item: String in primaryKeyList) {
          _stmt.bindText(_argIndex, _item)
          _argIndex++
        }
        val _columnIndexOfVideoId: Int = getColumnIndexOrThrow(_stmt, "videoId")
        val _columnIndexOfAlbumId: Int = getColumnIndexOrThrow(_stmt, "albumId")
        val _columnIndexOfAlbumName: Int = getColumnIndexOrThrow(_stmt, "albumName")
        val _columnIndexOfArtistId: Int = getColumnIndexOrThrow(_stmt, "artistId")
        val _columnIndexOfArtistName: Int = getColumnIndexOrThrow(_stmt, "artistName")
        val _columnIndexOfDuration: Int = getColumnIndexOrThrow(_stmt, "duration")
        val _columnIndexOfDurationSeconds: Int = getColumnIndexOrThrow(_stmt, "durationSeconds")
        val _columnIndexOfIsAvailable: Int = getColumnIndexOrThrow(_stmt, "isAvailable")
        val _columnIndexOfIsExplicit: Int = getColumnIndexOrThrow(_stmt, "isExplicit")
        val _columnIndexOfLikeStatus: Int = getColumnIndexOrThrow(_stmt, "likeStatus")
        val _columnIndexOfThumbnails: Int = getColumnIndexOrThrow(_stmt, "thumbnails")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfVideoType: Int = getColumnIndexOrThrow(_stmt, "videoType")
        val _columnIndexOfCategory: Int = getColumnIndexOrThrow(_stmt, "category")
        val _columnIndexOfResultType: Int = getColumnIndexOrThrow(_stmt, "resultType")
        val _columnIndexOfLiked: Int = getColumnIndexOrThrow(_stmt, "liked")
        val _columnIndexOfTotalPlayTime: Int = getColumnIndexOrThrow(_stmt, "totalPlayTime")
        val _columnIndexOfDownloadState: Int = getColumnIndexOrThrow(_stmt, "downloadState")
        val _columnIndexOfFavoriteAt: Int = getColumnIndexOrThrow(_stmt, "favoriteAt")
        val _columnIndexOfDownloadedAt: Int = getColumnIndexOrThrow(_stmt, "downloadedAt")
        val _columnIndexOfInLibrary: Int = getColumnIndexOrThrow(_stmt, "inLibrary")
        val _columnIndexOfCanvasUrl: Int = getColumnIndexOrThrow(_stmt, "canvasUrl")
        val _columnIndexOfCanvasThumbUrl: Int = getColumnIndexOrThrow(_stmt, "canvasThumbUrl")
        val _result: MutableList<SongEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item_1: SongEntity
          val _tmpVideoId: String
          _tmpVideoId = _stmt.getText(_columnIndexOfVideoId)
          val _tmpAlbumId: String?
          if (_stmt.isNull(_columnIndexOfAlbumId)) {
            _tmpAlbumId = null
          } else {
            _tmpAlbumId = _stmt.getText(_columnIndexOfAlbumId)
          }
          val _tmpAlbumName: String?
          if (_stmt.isNull(_columnIndexOfAlbumName)) {
            _tmpAlbumName = null
          } else {
            _tmpAlbumName = _stmt.getText(_columnIndexOfAlbumName)
          }
          val _tmpArtistId: List<String>?
          val _tmp: String?
          if (_stmt.isNull(_columnIndexOfArtistId)) {
            _tmp = null
          } else {
            _tmp = _stmt.getText(_columnIndexOfArtistId)
          }
          _tmpArtistId = __converters().fromString(_tmp)
          val _tmpArtistName: List<String>?
          val _tmp_1: String?
          if (_stmt.isNull(_columnIndexOfArtistName)) {
            _tmp_1 = null
          } else {
            _tmp_1 = _stmt.getText(_columnIndexOfArtistName)
          }
          _tmpArtistName = __converters().fromString(_tmp_1)
          val _tmpDuration: String
          _tmpDuration = _stmt.getText(_columnIndexOfDuration)
          val _tmpDurationSeconds: Int
          _tmpDurationSeconds = _stmt.getLong(_columnIndexOfDurationSeconds).toInt()
          val _tmpIsAvailable: Boolean
          val _tmp_2: Int
          _tmp_2 = _stmt.getLong(_columnIndexOfIsAvailable).toInt()
          _tmpIsAvailable = _tmp_2 != 0
          val _tmpIsExplicit: Boolean
          val _tmp_3: Int
          _tmp_3 = _stmt.getLong(_columnIndexOfIsExplicit).toInt()
          _tmpIsExplicit = _tmp_3 != 0
          val _tmpLikeStatus: String
          _tmpLikeStatus = _stmt.getText(_columnIndexOfLikeStatus)
          val _tmpThumbnails: String?
          if (_stmt.isNull(_columnIndexOfThumbnails)) {
            _tmpThumbnails = null
          } else {
            _tmpThumbnails = _stmt.getText(_columnIndexOfThumbnails)
          }
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpVideoType: String
          _tmpVideoType = _stmt.getText(_columnIndexOfVideoType)
          val _tmpCategory: String?
          if (_stmt.isNull(_columnIndexOfCategory)) {
            _tmpCategory = null
          } else {
            _tmpCategory = _stmt.getText(_columnIndexOfCategory)
          }
          val _tmpResultType: String?
          if (_stmt.isNull(_columnIndexOfResultType)) {
            _tmpResultType = null
          } else {
            _tmpResultType = _stmt.getText(_columnIndexOfResultType)
          }
          val _tmpLiked: Boolean
          val _tmp_4: Int
          _tmp_4 = _stmt.getLong(_columnIndexOfLiked).toInt()
          _tmpLiked = _tmp_4 != 0
          val _tmpTotalPlayTime: Long
          _tmpTotalPlayTime = _stmt.getLong(_columnIndexOfTotalPlayTime)
          val _tmpDownloadState: Int
          _tmpDownloadState = _stmt.getLong(_columnIndexOfDownloadState).toInt()
          val _tmpFavoriteAt: LocalDateTime?
          val _tmp_5: Long?
          if (_stmt.isNull(_columnIndexOfFavoriteAt)) {
            _tmp_5 = null
          } else {
            _tmp_5 = _stmt.getLong(_columnIndexOfFavoriteAt)
          }
          _tmpFavoriteAt = __converters().fromTimestamp(_tmp_5)
          val _tmpDownloadedAt: LocalDateTime?
          val _tmp_6: Long?
          if (_stmt.isNull(_columnIndexOfDownloadedAt)) {
            _tmp_6 = null
          } else {
            _tmp_6 = _stmt.getLong(_columnIndexOfDownloadedAt)
          }
          _tmpDownloadedAt = __converters().fromTimestamp(_tmp_6)
          val _tmpInLibrary: LocalDateTime
          val _tmp_7: Long?
          if (_stmt.isNull(_columnIndexOfInLibrary)) {
            _tmp_7 = null
          } else {
            _tmp_7 = _stmt.getLong(_columnIndexOfInLibrary)
          }
          val _tmp_8: LocalDateTime? = __converters().fromTimestamp(_tmp_7)
          if (_tmp_8 == null) {
            error("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.")
          } else {
            _tmpInLibrary = _tmp_8
          }
          val _tmpCanvasUrl: String?
          if (_stmt.isNull(_columnIndexOfCanvasUrl)) {
            _tmpCanvasUrl = null
          } else {
            _tmpCanvasUrl = _stmt.getText(_columnIndexOfCanvasUrl)
          }
          val _tmpCanvasThumbUrl: String?
          if (_stmt.isNull(_columnIndexOfCanvasThumbUrl)) {
            _tmpCanvasThumbUrl = null
          } else {
            _tmpCanvasThumbUrl = _stmt.getText(_columnIndexOfCanvasThumbUrl)
          }
          _item_1 = SongEntity(_tmpVideoId,_tmpAlbumId,_tmpAlbumName,_tmpArtistId,_tmpArtistName,_tmpDuration,_tmpDurationSeconds,_tmpIsAvailable,_tmpIsExplicit,_tmpLikeStatus,_tmpThumbnails,_tmpTitle,_tmpVideoType,_tmpCategory,_tmpResultType,_tmpLiked,_tmpTotalPlayTime,_tmpDownloadState,_tmpFavoriteAt,_tmpDownloadedAt,_tmpInLibrary,_tmpCanvasUrl,_tmpCanvasThumbUrl)
          _result.add(_item_1)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getSongByListVideoId(primaryKeyList: List<String>, offset: Int): List<SongEntity> {
    val _stringBuilder: StringBuilder = StringBuilder()
    _stringBuilder.append("SELECT * FROM song WHERE videoId IN (")
    val _inputSize: Int = primaryKeyList.size
    appendPlaceholders(_stringBuilder, _inputSize)
    _stringBuilder.append(") LIMIT 50 OFFSET ")
    _stringBuilder.append("?")
    val _sql: String = _stringBuilder.toString()
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        for (_item: String in primaryKeyList) {
          _stmt.bindText(_argIndex, _item)
          _argIndex++
        }
        _argIndex = 1 + _inputSize
        _stmt.bindLong(_argIndex, offset.toLong())
        val _columnIndexOfVideoId: Int = getColumnIndexOrThrow(_stmt, "videoId")
        val _columnIndexOfAlbumId: Int = getColumnIndexOrThrow(_stmt, "albumId")
        val _columnIndexOfAlbumName: Int = getColumnIndexOrThrow(_stmt, "albumName")
        val _columnIndexOfArtistId: Int = getColumnIndexOrThrow(_stmt, "artistId")
        val _columnIndexOfArtistName: Int = getColumnIndexOrThrow(_stmt, "artistName")
        val _columnIndexOfDuration: Int = getColumnIndexOrThrow(_stmt, "duration")
        val _columnIndexOfDurationSeconds: Int = getColumnIndexOrThrow(_stmt, "durationSeconds")
        val _columnIndexOfIsAvailable: Int = getColumnIndexOrThrow(_stmt, "isAvailable")
        val _columnIndexOfIsExplicit: Int = getColumnIndexOrThrow(_stmt, "isExplicit")
        val _columnIndexOfLikeStatus: Int = getColumnIndexOrThrow(_stmt, "likeStatus")
        val _columnIndexOfThumbnails: Int = getColumnIndexOrThrow(_stmt, "thumbnails")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfVideoType: Int = getColumnIndexOrThrow(_stmt, "videoType")
        val _columnIndexOfCategory: Int = getColumnIndexOrThrow(_stmt, "category")
        val _columnIndexOfResultType: Int = getColumnIndexOrThrow(_stmt, "resultType")
        val _columnIndexOfLiked: Int = getColumnIndexOrThrow(_stmt, "liked")
        val _columnIndexOfTotalPlayTime: Int = getColumnIndexOrThrow(_stmt, "totalPlayTime")
        val _columnIndexOfDownloadState: Int = getColumnIndexOrThrow(_stmt, "downloadState")
        val _columnIndexOfFavoriteAt: Int = getColumnIndexOrThrow(_stmt, "favoriteAt")
        val _columnIndexOfDownloadedAt: Int = getColumnIndexOrThrow(_stmt, "downloadedAt")
        val _columnIndexOfInLibrary: Int = getColumnIndexOrThrow(_stmt, "inLibrary")
        val _columnIndexOfCanvasUrl: Int = getColumnIndexOrThrow(_stmt, "canvasUrl")
        val _columnIndexOfCanvasThumbUrl: Int = getColumnIndexOrThrow(_stmt, "canvasThumbUrl")
        val _result: MutableList<SongEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item_1: SongEntity
          val _tmpVideoId: String
          _tmpVideoId = _stmt.getText(_columnIndexOfVideoId)
          val _tmpAlbumId: String?
          if (_stmt.isNull(_columnIndexOfAlbumId)) {
            _tmpAlbumId = null
          } else {
            _tmpAlbumId = _stmt.getText(_columnIndexOfAlbumId)
          }
          val _tmpAlbumName: String?
          if (_stmt.isNull(_columnIndexOfAlbumName)) {
            _tmpAlbumName = null
          } else {
            _tmpAlbumName = _stmt.getText(_columnIndexOfAlbumName)
          }
          val _tmpArtistId: List<String>?
          val _tmp: String?
          if (_stmt.isNull(_columnIndexOfArtistId)) {
            _tmp = null
          } else {
            _tmp = _stmt.getText(_columnIndexOfArtistId)
          }
          _tmpArtistId = __converters().fromString(_tmp)
          val _tmpArtistName: List<String>?
          val _tmp_1: String?
          if (_stmt.isNull(_columnIndexOfArtistName)) {
            _tmp_1 = null
          } else {
            _tmp_1 = _stmt.getText(_columnIndexOfArtistName)
          }
          _tmpArtistName = __converters().fromString(_tmp_1)
          val _tmpDuration: String
          _tmpDuration = _stmt.getText(_columnIndexOfDuration)
          val _tmpDurationSeconds: Int
          _tmpDurationSeconds = _stmt.getLong(_columnIndexOfDurationSeconds).toInt()
          val _tmpIsAvailable: Boolean
          val _tmp_2: Int
          _tmp_2 = _stmt.getLong(_columnIndexOfIsAvailable).toInt()
          _tmpIsAvailable = _tmp_2 != 0
          val _tmpIsExplicit: Boolean
          val _tmp_3: Int
          _tmp_3 = _stmt.getLong(_columnIndexOfIsExplicit).toInt()
          _tmpIsExplicit = _tmp_3 != 0
          val _tmpLikeStatus: String
          _tmpLikeStatus = _stmt.getText(_columnIndexOfLikeStatus)
          val _tmpThumbnails: String?
          if (_stmt.isNull(_columnIndexOfThumbnails)) {
            _tmpThumbnails = null
          } else {
            _tmpThumbnails = _stmt.getText(_columnIndexOfThumbnails)
          }
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpVideoType: String
          _tmpVideoType = _stmt.getText(_columnIndexOfVideoType)
          val _tmpCategory: String?
          if (_stmt.isNull(_columnIndexOfCategory)) {
            _tmpCategory = null
          } else {
            _tmpCategory = _stmt.getText(_columnIndexOfCategory)
          }
          val _tmpResultType: String?
          if (_stmt.isNull(_columnIndexOfResultType)) {
            _tmpResultType = null
          } else {
            _tmpResultType = _stmt.getText(_columnIndexOfResultType)
          }
          val _tmpLiked: Boolean
          val _tmp_4: Int
          _tmp_4 = _stmt.getLong(_columnIndexOfLiked).toInt()
          _tmpLiked = _tmp_4 != 0
          val _tmpTotalPlayTime: Long
          _tmpTotalPlayTime = _stmt.getLong(_columnIndexOfTotalPlayTime)
          val _tmpDownloadState: Int
          _tmpDownloadState = _stmt.getLong(_columnIndexOfDownloadState).toInt()
          val _tmpFavoriteAt: LocalDateTime?
          val _tmp_5: Long?
          if (_stmt.isNull(_columnIndexOfFavoriteAt)) {
            _tmp_5 = null
          } else {
            _tmp_5 = _stmt.getLong(_columnIndexOfFavoriteAt)
          }
          _tmpFavoriteAt = __converters().fromTimestamp(_tmp_5)
          val _tmpDownloadedAt: LocalDateTime?
          val _tmp_6: Long?
          if (_stmt.isNull(_columnIndexOfDownloadedAt)) {
            _tmp_6 = null
          } else {
            _tmp_6 = _stmt.getLong(_columnIndexOfDownloadedAt)
          }
          _tmpDownloadedAt = __converters().fromTimestamp(_tmp_6)
          val _tmpInLibrary: LocalDateTime
          val _tmp_7: Long?
          if (_stmt.isNull(_columnIndexOfInLibrary)) {
            _tmp_7 = null
          } else {
            _tmp_7 = _stmt.getLong(_columnIndexOfInLibrary)
          }
          val _tmp_8: LocalDateTime? = __converters().fromTimestamp(_tmp_7)
          if (_tmp_8 == null) {
            error("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.")
          } else {
            _tmpInLibrary = _tmp_8
          }
          val _tmpCanvasUrl: String?
          if (_stmt.isNull(_columnIndexOfCanvasUrl)) {
            _tmpCanvasUrl = null
          } else {
            _tmpCanvasUrl = _stmt.getText(_columnIndexOfCanvasUrl)
          }
          val _tmpCanvasThumbUrl: String?
          if (_stmt.isNull(_columnIndexOfCanvasThumbUrl)) {
            _tmpCanvasThumbUrl = null
          } else {
            _tmpCanvasThumbUrl = _stmt.getText(_columnIndexOfCanvasThumbUrl)
          }
          _item_1 = SongEntity(_tmpVideoId,_tmpAlbumId,_tmpAlbumName,_tmpArtistId,_tmpArtistName,_tmpDuration,_tmpDurationSeconds,_tmpIsAvailable,_tmpIsExplicit,_tmpLikeStatus,_tmpThumbnails,_tmpTitle,_tmpVideoType,_tmpCategory,_tmpResultType,_tmpLiked,_tmpTotalPlayTime,_tmpDownloadState,_tmpFavoriteAt,_tmpDownloadedAt,_tmpInLibrary,_tmpCanvasUrl,_tmpCanvasThumbUrl)
          _result.add(_item_1)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getCanvasSong(max: Int): List<SongEntity> {
    val _sql: String = "SELECT * FROM song WHERE canvasThumbUrl IS NOT NULL ORDER BY totalPlayTime DESC LIMIT ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, max.toLong())
        val _columnIndexOfVideoId: Int = getColumnIndexOrThrow(_stmt, "videoId")
        val _columnIndexOfAlbumId: Int = getColumnIndexOrThrow(_stmt, "albumId")
        val _columnIndexOfAlbumName: Int = getColumnIndexOrThrow(_stmt, "albumName")
        val _columnIndexOfArtistId: Int = getColumnIndexOrThrow(_stmt, "artistId")
        val _columnIndexOfArtistName: Int = getColumnIndexOrThrow(_stmt, "artistName")
        val _columnIndexOfDuration: Int = getColumnIndexOrThrow(_stmt, "duration")
        val _columnIndexOfDurationSeconds: Int = getColumnIndexOrThrow(_stmt, "durationSeconds")
        val _columnIndexOfIsAvailable: Int = getColumnIndexOrThrow(_stmt, "isAvailable")
        val _columnIndexOfIsExplicit: Int = getColumnIndexOrThrow(_stmt, "isExplicit")
        val _columnIndexOfLikeStatus: Int = getColumnIndexOrThrow(_stmt, "likeStatus")
        val _columnIndexOfThumbnails: Int = getColumnIndexOrThrow(_stmt, "thumbnails")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfVideoType: Int = getColumnIndexOrThrow(_stmt, "videoType")
        val _columnIndexOfCategory: Int = getColumnIndexOrThrow(_stmt, "category")
        val _columnIndexOfResultType: Int = getColumnIndexOrThrow(_stmt, "resultType")
        val _columnIndexOfLiked: Int = getColumnIndexOrThrow(_stmt, "liked")
        val _columnIndexOfTotalPlayTime: Int = getColumnIndexOrThrow(_stmt, "totalPlayTime")
        val _columnIndexOfDownloadState: Int = getColumnIndexOrThrow(_stmt, "downloadState")
        val _columnIndexOfFavoriteAt: Int = getColumnIndexOrThrow(_stmt, "favoriteAt")
        val _columnIndexOfDownloadedAt: Int = getColumnIndexOrThrow(_stmt, "downloadedAt")
        val _columnIndexOfInLibrary: Int = getColumnIndexOrThrow(_stmt, "inLibrary")
        val _columnIndexOfCanvasUrl: Int = getColumnIndexOrThrow(_stmt, "canvasUrl")
        val _columnIndexOfCanvasThumbUrl: Int = getColumnIndexOrThrow(_stmt, "canvasThumbUrl")
        val _result: MutableList<SongEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: SongEntity
          val _tmpVideoId: String
          _tmpVideoId = _stmt.getText(_columnIndexOfVideoId)
          val _tmpAlbumId: String?
          if (_stmt.isNull(_columnIndexOfAlbumId)) {
            _tmpAlbumId = null
          } else {
            _tmpAlbumId = _stmt.getText(_columnIndexOfAlbumId)
          }
          val _tmpAlbumName: String?
          if (_stmt.isNull(_columnIndexOfAlbumName)) {
            _tmpAlbumName = null
          } else {
            _tmpAlbumName = _stmt.getText(_columnIndexOfAlbumName)
          }
          val _tmpArtistId: List<String>?
          val _tmp: String?
          if (_stmt.isNull(_columnIndexOfArtistId)) {
            _tmp = null
          } else {
            _tmp = _stmt.getText(_columnIndexOfArtistId)
          }
          _tmpArtistId = __converters().fromString(_tmp)
          val _tmpArtistName: List<String>?
          val _tmp_1: String?
          if (_stmt.isNull(_columnIndexOfArtistName)) {
            _tmp_1 = null
          } else {
            _tmp_1 = _stmt.getText(_columnIndexOfArtistName)
          }
          _tmpArtistName = __converters().fromString(_tmp_1)
          val _tmpDuration: String
          _tmpDuration = _stmt.getText(_columnIndexOfDuration)
          val _tmpDurationSeconds: Int
          _tmpDurationSeconds = _stmt.getLong(_columnIndexOfDurationSeconds).toInt()
          val _tmpIsAvailable: Boolean
          val _tmp_2: Int
          _tmp_2 = _stmt.getLong(_columnIndexOfIsAvailable).toInt()
          _tmpIsAvailable = _tmp_2 != 0
          val _tmpIsExplicit: Boolean
          val _tmp_3: Int
          _tmp_3 = _stmt.getLong(_columnIndexOfIsExplicit).toInt()
          _tmpIsExplicit = _tmp_3 != 0
          val _tmpLikeStatus: String
          _tmpLikeStatus = _stmt.getText(_columnIndexOfLikeStatus)
          val _tmpThumbnails: String?
          if (_stmt.isNull(_columnIndexOfThumbnails)) {
            _tmpThumbnails = null
          } else {
            _tmpThumbnails = _stmt.getText(_columnIndexOfThumbnails)
          }
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpVideoType: String
          _tmpVideoType = _stmt.getText(_columnIndexOfVideoType)
          val _tmpCategory: String?
          if (_stmt.isNull(_columnIndexOfCategory)) {
            _tmpCategory = null
          } else {
            _tmpCategory = _stmt.getText(_columnIndexOfCategory)
          }
          val _tmpResultType: String?
          if (_stmt.isNull(_columnIndexOfResultType)) {
            _tmpResultType = null
          } else {
            _tmpResultType = _stmt.getText(_columnIndexOfResultType)
          }
          val _tmpLiked: Boolean
          val _tmp_4: Int
          _tmp_4 = _stmt.getLong(_columnIndexOfLiked).toInt()
          _tmpLiked = _tmp_4 != 0
          val _tmpTotalPlayTime: Long
          _tmpTotalPlayTime = _stmt.getLong(_columnIndexOfTotalPlayTime)
          val _tmpDownloadState: Int
          _tmpDownloadState = _stmt.getLong(_columnIndexOfDownloadState).toInt()
          val _tmpFavoriteAt: LocalDateTime?
          val _tmp_5: Long?
          if (_stmt.isNull(_columnIndexOfFavoriteAt)) {
            _tmp_5 = null
          } else {
            _tmp_5 = _stmt.getLong(_columnIndexOfFavoriteAt)
          }
          _tmpFavoriteAt = __converters().fromTimestamp(_tmp_5)
          val _tmpDownloadedAt: LocalDateTime?
          val _tmp_6: Long?
          if (_stmt.isNull(_columnIndexOfDownloadedAt)) {
            _tmp_6 = null
          } else {
            _tmp_6 = _stmt.getLong(_columnIndexOfDownloadedAt)
          }
          _tmpDownloadedAt = __converters().fromTimestamp(_tmp_6)
          val _tmpInLibrary: LocalDateTime
          val _tmp_7: Long?
          if (_stmt.isNull(_columnIndexOfInLibrary)) {
            _tmp_7 = null
          } else {
            _tmp_7 = _stmt.getLong(_columnIndexOfInLibrary)
          }
          val _tmp_8: LocalDateTime? = __converters().fromTimestamp(_tmp_7)
          if (_tmp_8 == null) {
            error("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.")
          } else {
            _tmpInLibrary = _tmp_8
          }
          val _tmpCanvasUrl: String?
          if (_stmt.isNull(_columnIndexOfCanvasUrl)) {
            _tmpCanvasUrl = null
          } else {
            _tmpCanvasUrl = _stmt.getText(_columnIndexOfCanvasUrl)
          }
          val _tmpCanvasThumbUrl: String?
          if (_stmt.isNull(_columnIndexOfCanvasThumbUrl)) {
            _tmpCanvasThumbUrl = null
          } else {
            _tmpCanvasThumbUrl = _stmt.getText(_columnIndexOfCanvasThumbUrl)
          }
          _item = SongEntity(_tmpVideoId,_tmpAlbumId,_tmpAlbumName,_tmpArtistId,_tmpArtistName,_tmpDuration,_tmpDurationSeconds,_tmpIsAvailable,_tmpIsExplicit,_tmpLikeStatus,_tmpThumbnails,_tmpTitle,_tmpVideoType,_tmpCategory,_tmpResultType,_tmpLiked,_tmpTotalPlayTime,_tmpDownloadState,_tmpFavoriteAt,_tmpDownloadedAt,_tmpInLibrary,_tmpCanvasUrl,_tmpCanvasThumbUrl)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getDownloadedVideoIdByListVideoId(primaryKeyList: List<String>): Flow<List<String>> {
    val _stringBuilder: StringBuilder = StringBuilder()
    _stringBuilder.append("SELECT videoId FROM song WHERE videoId IN (")
    val _inputSize: Int = primaryKeyList.size
    appendPlaceholders(_stringBuilder, _inputSize)
    _stringBuilder.append(") AND downloadState = 3")
    val _sql: String = _stringBuilder.toString()
    return createFlow(__db, false, arrayOf("song")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        for (_item: String in primaryKeyList) {
          _stmt.bindText(_argIndex, _item)
          _argIndex++
        }
        val _result: MutableList<String> = mutableListOf()
        while (_stmt.step()) {
          val _item_1: String
          _item_1 = _stmt.getText(0)
          _result.add(_item_1)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getAllArtists(limit: Int): List<ArtistEntity> {
    val _sql: String = "SELECT * FROM artist ORDER BY inLibrary DESC LIMIT ? OFFSET 0"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, limit.toLong())
        val _columnIndexOfChannelId: Int = getColumnIndexOrThrow(_stmt, "channelId")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfThumbnails: Int = getColumnIndexOrThrow(_stmt, "thumbnails")
        val _columnIndexOfFollowed: Int = getColumnIndexOrThrow(_stmt, "followed")
        val _columnIndexOfFollowedAt: Int = getColumnIndexOrThrow(_stmt, "followedAt")
        val _columnIndexOfInLibrary: Int = getColumnIndexOrThrow(_stmt, "inLibrary")
        val _columnIndexOfNameLogoUrl: Int = getColumnIndexOrThrow(_stmt, "nameLogoUrl")
        val _columnIndexOfNameLogoColor: Int = getColumnIndexOrThrow(_stmt, "nameLogoColor")
        val _result: MutableList<ArtistEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: ArtistEntity
          val _tmpChannelId: String
          _tmpChannelId = _stmt.getText(_columnIndexOfChannelId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpThumbnails: String?
          if (_stmt.isNull(_columnIndexOfThumbnails)) {
            _tmpThumbnails = null
          } else {
            _tmpThumbnails = _stmt.getText(_columnIndexOfThumbnails)
          }
          val _tmpFollowed: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfFollowed).toInt()
          _tmpFollowed = _tmp != 0
          val _tmpFollowedAt: LocalDateTime?
          val _tmp_1: Long?
          if (_stmt.isNull(_columnIndexOfFollowedAt)) {
            _tmp_1 = null
          } else {
            _tmp_1 = _stmt.getLong(_columnIndexOfFollowedAt)
          }
          _tmpFollowedAt = __converters().fromTimestamp(_tmp_1)
          val _tmpInLibrary: LocalDateTime
          val _tmp_2: Long?
          if (_stmt.isNull(_columnIndexOfInLibrary)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getLong(_columnIndexOfInLibrary)
          }
          val _tmp_3: LocalDateTime? = __converters().fromTimestamp(_tmp_2)
          if (_tmp_3 == null) {
            error("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.")
          } else {
            _tmpInLibrary = _tmp_3
          }
          val _tmpNameLogoUrl: String?
          if (_stmt.isNull(_columnIndexOfNameLogoUrl)) {
            _tmpNameLogoUrl = null
          } else {
            _tmpNameLogoUrl = _stmt.getText(_columnIndexOfNameLogoUrl)
          }
          val _tmpNameLogoColor: String?
          if (_stmt.isNull(_columnIndexOfNameLogoColor)) {
            _tmpNameLogoColor = null
          } else {
            _tmpNameLogoColor = _stmt.getText(_columnIndexOfNameLogoColor)
          }
          _item = ArtistEntity(_tmpChannelId,_tmpName,_tmpThumbnails,_tmpFollowed,_tmpFollowedAt,_tmpInLibrary,_tmpNameLogoUrl,_tmpNameLogoColor)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getArtist(channelId: String): ArtistEntity? {
    val _sql: String = "SELECT * FROM artist WHERE channelId = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, channelId)
        val _columnIndexOfChannelId: Int = getColumnIndexOrThrow(_stmt, "channelId")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfThumbnails: Int = getColumnIndexOrThrow(_stmt, "thumbnails")
        val _columnIndexOfFollowed: Int = getColumnIndexOrThrow(_stmt, "followed")
        val _columnIndexOfFollowedAt: Int = getColumnIndexOrThrow(_stmt, "followedAt")
        val _columnIndexOfInLibrary: Int = getColumnIndexOrThrow(_stmt, "inLibrary")
        val _columnIndexOfNameLogoUrl: Int = getColumnIndexOrThrow(_stmt, "nameLogoUrl")
        val _columnIndexOfNameLogoColor: Int = getColumnIndexOrThrow(_stmt, "nameLogoColor")
        val _result: ArtistEntity?
        if (_stmt.step()) {
          val _tmpChannelId: String
          _tmpChannelId = _stmt.getText(_columnIndexOfChannelId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpThumbnails: String?
          if (_stmt.isNull(_columnIndexOfThumbnails)) {
            _tmpThumbnails = null
          } else {
            _tmpThumbnails = _stmt.getText(_columnIndexOfThumbnails)
          }
          val _tmpFollowed: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfFollowed).toInt()
          _tmpFollowed = _tmp != 0
          val _tmpFollowedAt: LocalDateTime?
          val _tmp_1: Long?
          if (_stmt.isNull(_columnIndexOfFollowedAt)) {
            _tmp_1 = null
          } else {
            _tmp_1 = _stmt.getLong(_columnIndexOfFollowedAt)
          }
          _tmpFollowedAt = __converters().fromTimestamp(_tmp_1)
          val _tmpInLibrary: LocalDateTime
          val _tmp_2: Long?
          if (_stmt.isNull(_columnIndexOfInLibrary)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getLong(_columnIndexOfInLibrary)
          }
          val _tmp_3: LocalDateTime? = __converters().fromTimestamp(_tmp_2)
          if (_tmp_3 == null) {
            error("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.")
          } else {
            _tmpInLibrary = _tmp_3
          }
          val _tmpNameLogoUrl: String?
          if (_stmt.isNull(_columnIndexOfNameLogoUrl)) {
            _tmpNameLogoUrl = null
          } else {
            _tmpNameLogoUrl = _stmt.getText(_columnIndexOfNameLogoUrl)
          }
          val _tmpNameLogoColor: String?
          if (_stmt.isNull(_columnIndexOfNameLogoColor)) {
            _tmpNameLogoColor = null
          } else {
            _tmpNameLogoColor = _stmt.getText(_columnIndexOfNameLogoColor)
          }
          _result = ArtistEntity(_tmpChannelId,_tmpName,_tmpThumbnails,_tmpFollowed,_tmpFollowedAt,_tmpInLibrary,_tmpNameLogoUrl,_tmpNameLogoColor)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getFollowedArtists(limit: Int, offset: Int): List<ArtistEntity> {
    val _sql: String = "SELECT * FROM artist WHERE followed = 1 ORDER BY followedAt DESC LIMIT ? OFFSET ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, limit.toLong())
        _argIndex = 2
        _stmt.bindLong(_argIndex, offset.toLong())
        val _columnIndexOfChannelId: Int = getColumnIndexOrThrow(_stmt, "channelId")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfThumbnails: Int = getColumnIndexOrThrow(_stmt, "thumbnails")
        val _columnIndexOfFollowed: Int = getColumnIndexOrThrow(_stmt, "followed")
        val _columnIndexOfFollowedAt: Int = getColumnIndexOrThrow(_stmt, "followedAt")
        val _columnIndexOfInLibrary: Int = getColumnIndexOrThrow(_stmt, "inLibrary")
        val _columnIndexOfNameLogoUrl: Int = getColumnIndexOrThrow(_stmt, "nameLogoUrl")
        val _columnIndexOfNameLogoColor: Int = getColumnIndexOrThrow(_stmt, "nameLogoColor")
        val _result: MutableList<ArtistEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: ArtistEntity
          val _tmpChannelId: String
          _tmpChannelId = _stmt.getText(_columnIndexOfChannelId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpThumbnails: String?
          if (_stmt.isNull(_columnIndexOfThumbnails)) {
            _tmpThumbnails = null
          } else {
            _tmpThumbnails = _stmt.getText(_columnIndexOfThumbnails)
          }
          val _tmpFollowed: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfFollowed).toInt()
          _tmpFollowed = _tmp != 0
          val _tmpFollowedAt: LocalDateTime?
          val _tmp_1: Long?
          if (_stmt.isNull(_columnIndexOfFollowedAt)) {
            _tmp_1 = null
          } else {
            _tmp_1 = _stmt.getLong(_columnIndexOfFollowedAt)
          }
          _tmpFollowedAt = __converters().fromTimestamp(_tmp_1)
          val _tmpInLibrary: LocalDateTime
          val _tmp_2: Long?
          if (_stmt.isNull(_columnIndexOfInLibrary)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getLong(_columnIndexOfInLibrary)
          }
          val _tmp_3: LocalDateTime? = __converters().fromTimestamp(_tmp_2)
          if (_tmp_3 == null) {
            error("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.")
          } else {
            _tmpInLibrary = _tmp_3
          }
          val _tmpNameLogoUrl: String?
          if (_stmt.isNull(_columnIndexOfNameLogoUrl)) {
            _tmpNameLogoUrl = null
          } else {
            _tmpNameLogoUrl = _stmt.getText(_columnIndexOfNameLogoUrl)
          }
          val _tmpNameLogoColor: String?
          if (_stmt.isNull(_columnIndexOfNameLogoColor)) {
            _tmpNameLogoColor = null
          } else {
            _tmpNameLogoColor = _stmt.getText(_columnIndexOfNameLogoColor)
          }
          _item = ArtistEntity(_tmpChannelId,_tmpName,_tmpThumbnails,_tmpFollowed,_tmpFollowedAt,_tmpInLibrary,_tmpNameLogoUrl,_tmpNameLogoColor)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getAllAlbums(limit: Int): List<AlbumEntity> {
    val _sql: String = "SELECT * FROM album ORDER BY inLibrary DESC LIMIT ? OFFSET 0"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, limit.toLong())
        val _columnIndexOfBrowseId: Int = getColumnIndexOrThrow(_stmt, "browseId")
        val _columnIndexOfArtistId: Int = getColumnIndexOrThrow(_stmt, "artistId")
        val _columnIndexOfArtistName: Int = getColumnIndexOrThrow(_stmt, "artistName")
        val _columnIndexOfAudioPlaylistId: Int = getColumnIndexOrThrow(_stmt, "audioPlaylistId")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfDuration: Int = getColumnIndexOrThrow(_stmt, "duration")
        val _columnIndexOfDurationSeconds: Int = getColumnIndexOrThrow(_stmt, "durationSeconds")
        val _columnIndexOfThumbnails: Int = getColumnIndexOrThrow(_stmt, "thumbnails")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfTrackCount: Int = getColumnIndexOrThrow(_stmt, "trackCount")
        val _columnIndexOfTracks: Int = getColumnIndexOrThrow(_stmt, "tracks")
        val _columnIndexOfType: Int = getColumnIndexOrThrow(_stmt, "type")
        val _columnIndexOfYear: Int = getColumnIndexOrThrow(_stmt, "year")
        val _columnIndexOfLiked: Int = getColumnIndexOrThrow(_stmt, "liked")
        val _columnIndexOfInLibrary: Int = getColumnIndexOrThrow(_stmt, "inLibrary")
        val _columnIndexOfFavoriteAt: Int = getColumnIndexOrThrow(_stmt, "favoriteAt")
        val _columnIndexOfDownloadedAt: Int = getColumnIndexOrThrow(_stmt, "downloadedAt")
        val _columnIndexOfDownloadState: Int = getColumnIndexOrThrow(_stmt, "downloadState")
        val _result: MutableList<AlbumEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: AlbumEntity
          val _tmpBrowseId: String
          _tmpBrowseId = _stmt.getText(_columnIndexOfBrowseId)
          val _tmpArtistId: List<String?>?
          val _tmp: String?
          if (_stmt.isNull(_columnIndexOfArtistId)) {
            _tmp = null
          } else {
            _tmp = _stmt.getText(_columnIndexOfArtistId)
          }
          _tmpArtistId = __converters().fromStringNull(_tmp)
          val _tmpArtistName: List<String>?
          val _tmp_1: String?
          if (_stmt.isNull(_columnIndexOfArtistName)) {
            _tmp_1 = null
          } else {
            _tmp_1 = _stmt.getText(_columnIndexOfArtistName)
          }
          _tmpArtistName = __converters().fromString(_tmp_1)
          val _tmpAudioPlaylistId: String
          _tmpAudioPlaylistId = _stmt.getText(_columnIndexOfAudioPlaylistId)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpDuration: String?
          if (_stmt.isNull(_columnIndexOfDuration)) {
            _tmpDuration = null
          } else {
            _tmpDuration = _stmt.getText(_columnIndexOfDuration)
          }
          val _tmpDurationSeconds: Int
          _tmpDurationSeconds = _stmt.getLong(_columnIndexOfDurationSeconds).toInt()
          val _tmpThumbnails: String?
          if (_stmt.isNull(_columnIndexOfThumbnails)) {
            _tmpThumbnails = null
          } else {
            _tmpThumbnails = _stmt.getText(_columnIndexOfThumbnails)
          }
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpTrackCount: Int
          _tmpTrackCount = _stmt.getLong(_columnIndexOfTrackCount).toInt()
          val _tmpTracks: List<String>?
          val _tmp_2: String?
          if (_stmt.isNull(_columnIndexOfTracks)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getText(_columnIndexOfTracks)
          }
          _tmpTracks = __converters().fromString(_tmp_2)
          val _tmpType: String
          _tmpType = _stmt.getText(_columnIndexOfType)
          val _tmpYear: String?
          if (_stmt.isNull(_columnIndexOfYear)) {
            _tmpYear = null
          } else {
            _tmpYear = _stmt.getText(_columnIndexOfYear)
          }
          val _tmpLiked: Boolean
          val _tmp_3: Int
          _tmp_3 = _stmt.getLong(_columnIndexOfLiked).toInt()
          _tmpLiked = _tmp_3 != 0
          val _tmpInLibrary: LocalDateTime
          val _tmp_4: Long?
          if (_stmt.isNull(_columnIndexOfInLibrary)) {
            _tmp_4 = null
          } else {
            _tmp_4 = _stmt.getLong(_columnIndexOfInLibrary)
          }
          val _tmp_5: LocalDateTime? = __converters().fromTimestamp(_tmp_4)
          if (_tmp_5 == null) {
            error("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.")
          } else {
            _tmpInLibrary = _tmp_5
          }
          val _tmpFavoriteAt: LocalDateTime?
          val _tmp_6: Long?
          if (_stmt.isNull(_columnIndexOfFavoriteAt)) {
            _tmp_6 = null
          } else {
            _tmp_6 = _stmt.getLong(_columnIndexOfFavoriteAt)
          }
          _tmpFavoriteAt = __converters().fromTimestamp(_tmp_6)
          val _tmpDownloadedAt: LocalDateTime?
          val _tmp_7: Long?
          if (_stmt.isNull(_columnIndexOfDownloadedAt)) {
            _tmp_7 = null
          } else {
            _tmp_7 = _stmt.getLong(_columnIndexOfDownloadedAt)
          }
          _tmpDownloadedAt = __converters().fromTimestamp(_tmp_7)
          val _tmpDownloadState: Int
          _tmpDownloadState = _stmt.getLong(_columnIndexOfDownloadState).toInt()
          _item = AlbumEntity(_tmpBrowseId,_tmpArtistId,_tmpArtistName,_tmpAudioPlaylistId,_tmpDescription,_tmpDuration,_tmpDurationSeconds,_tmpThumbnails,_tmpTitle,_tmpTrackCount,_tmpTracks,_tmpType,_tmpYear,_tmpLiked,_tmpInLibrary,_tmpFavoriteAt,_tmpDownloadedAt,_tmpDownloadState)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getAlbum(browseId: String): AlbumEntity? {
    val _sql: String = "SELECT * FROM album WHERE browseId = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, browseId)
        val _columnIndexOfBrowseId: Int = getColumnIndexOrThrow(_stmt, "browseId")
        val _columnIndexOfArtistId: Int = getColumnIndexOrThrow(_stmt, "artistId")
        val _columnIndexOfArtistName: Int = getColumnIndexOrThrow(_stmt, "artistName")
        val _columnIndexOfAudioPlaylistId: Int = getColumnIndexOrThrow(_stmt, "audioPlaylistId")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfDuration: Int = getColumnIndexOrThrow(_stmt, "duration")
        val _columnIndexOfDurationSeconds: Int = getColumnIndexOrThrow(_stmt, "durationSeconds")
        val _columnIndexOfThumbnails: Int = getColumnIndexOrThrow(_stmt, "thumbnails")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfTrackCount: Int = getColumnIndexOrThrow(_stmt, "trackCount")
        val _columnIndexOfTracks: Int = getColumnIndexOrThrow(_stmt, "tracks")
        val _columnIndexOfType: Int = getColumnIndexOrThrow(_stmt, "type")
        val _columnIndexOfYear: Int = getColumnIndexOrThrow(_stmt, "year")
        val _columnIndexOfLiked: Int = getColumnIndexOrThrow(_stmt, "liked")
        val _columnIndexOfInLibrary: Int = getColumnIndexOrThrow(_stmt, "inLibrary")
        val _columnIndexOfFavoriteAt: Int = getColumnIndexOrThrow(_stmt, "favoriteAt")
        val _columnIndexOfDownloadedAt: Int = getColumnIndexOrThrow(_stmt, "downloadedAt")
        val _columnIndexOfDownloadState: Int = getColumnIndexOrThrow(_stmt, "downloadState")
        val _result: AlbumEntity?
        if (_stmt.step()) {
          val _tmpBrowseId: String
          _tmpBrowseId = _stmt.getText(_columnIndexOfBrowseId)
          val _tmpArtistId: List<String?>?
          val _tmp: String?
          if (_stmt.isNull(_columnIndexOfArtistId)) {
            _tmp = null
          } else {
            _tmp = _stmt.getText(_columnIndexOfArtistId)
          }
          _tmpArtistId = __converters().fromStringNull(_tmp)
          val _tmpArtistName: List<String>?
          val _tmp_1: String?
          if (_stmt.isNull(_columnIndexOfArtistName)) {
            _tmp_1 = null
          } else {
            _tmp_1 = _stmt.getText(_columnIndexOfArtistName)
          }
          _tmpArtistName = __converters().fromString(_tmp_1)
          val _tmpAudioPlaylistId: String
          _tmpAudioPlaylistId = _stmt.getText(_columnIndexOfAudioPlaylistId)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpDuration: String?
          if (_stmt.isNull(_columnIndexOfDuration)) {
            _tmpDuration = null
          } else {
            _tmpDuration = _stmt.getText(_columnIndexOfDuration)
          }
          val _tmpDurationSeconds: Int
          _tmpDurationSeconds = _stmt.getLong(_columnIndexOfDurationSeconds).toInt()
          val _tmpThumbnails: String?
          if (_stmt.isNull(_columnIndexOfThumbnails)) {
            _tmpThumbnails = null
          } else {
            _tmpThumbnails = _stmt.getText(_columnIndexOfThumbnails)
          }
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpTrackCount: Int
          _tmpTrackCount = _stmt.getLong(_columnIndexOfTrackCount).toInt()
          val _tmpTracks: List<String>?
          val _tmp_2: String?
          if (_stmt.isNull(_columnIndexOfTracks)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getText(_columnIndexOfTracks)
          }
          _tmpTracks = __converters().fromString(_tmp_2)
          val _tmpType: String
          _tmpType = _stmt.getText(_columnIndexOfType)
          val _tmpYear: String?
          if (_stmt.isNull(_columnIndexOfYear)) {
            _tmpYear = null
          } else {
            _tmpYear = _stmt.getText(_columnIndexOfYear)
          }
          val _tmpLiked: Boolean
          val _tmp_3: Int
          _tmp_3 = _stmt.getLong(_columnIndexOfLiked).toInt()
          _tmpLiked = _tmp_3 != 0
          val _tmpInLibrary: LocalDateTime
          val _tmp_4: Long?
          if (_stmt.isNull(_columnIndexOfInLibrary)) {
            _tmp_4 = null
          } else {
            _tmp_4 = _stmt.getLong(_columnIndexOfInLibrary)
          }
          val _tmp_5: LocalDateTime? = __converters().fromTimestamp(_tmp_4)
          if (_tmp_5 == null) {
            error("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.")
          } else {
            _tmpInLibrary = _tmp_5
          }
          val _tmpFavoriteAt: LocalDateTime?
          val _tmp_6: Long?
          if (_stmt.isNull(_columnIndexOfFavoriteAt)) {
            _tmp_6 = null
          } else {
            _tmp_6 = _stmt.getLong(_columnIndexOfFavoriteAt)
          }
          _tmpFavoriteAt = __converters().fromTimestamp(_tmp_6)
          val _tmpDownloadedAt: LocalDateTime?
          val _tmp_7: Long?
          if (_stmt.isNull(_columnIndexOfDownloadedAt)) {
            _tmp_7 = null
          } else {
            _tmp_7 = _stmt.getLong(_columnIndexOfDownloadedAt)
          }
          _tmpDownloadedAt = __converters().fromTimestamp(_tmp_7)
          val _tmpDownloadState: Int
          _tmpDownloadState = _stmt.getLong(_columnIndexOfDownloadState).toInt()
          _result = AlbumEntity(_tmpBrowseId,_tmpArtistId,_tmpArtistName,_tmpAudioPlaylistId,_tmpDescription,_tmpDuration,_tmpDurationSeconds,_tmpThumbnails,_tmpTitle,_tmpTrackCount,_tmpTracks,_tmpType,_tmpYear,_tmpLiked,_tmpInLibrary,_tmpFavoriteAt,_tmpDownloadedAt,_tmpDownloadState)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getAlbumAsFlow(browseId: String): Flow<AlbumEntity?> {
    val _sql: String = "SELECT * FROM album WHERE browseId = ?"
    return createFlow(__db, false, arrayOf("album")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, browseId)
        val _columnIndexOfBrowseId: Int = getColumnIndexOrThrow(_stmt, "browseId")
        val _columnIndexOfArtistId: Int = getColumnIndexOrThrow(_stmt, "artistId")
        val _columnIndexOfArtistName: Int = getColumnIndexOrThrow(_stmt, "artistName")
        val _columnIndexOfAudioPlaylistId: Int = getColumnIndexOrThrow(_stmt, "audioPlaylistId")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfDuration: Int = getColumnIndexOrThrow(_stmt, "duration")
        val _columnIndexOfDurationSeconds: Int = getColumnIndexOrThrow(_stmt, "durationSeconds")
        val _columnIndexOfThumbnails: Int = getColumnIndexOrThrow(_stmt, "thumbnails")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfTrackCount: Int = getColumnIndexOrThrow(_stmt, "trackCount")
        val _columnIndexOfTracks: Int = getColumnIndexOrThrow(_stmt, "tracks")
        val _columnIndexOfType: Int = getColumnIndexOrThrow(_stmt, "type")
        val _columnIndexOfYear: Int = getColumnIndexOrThrow(_stmt, "year")
        val _columnIndexOfLiked: Int = getColumnIndexOrThrow(_stmt, "liked")
        val _columnIndexOfInLibrary: Int = getColumnIndexOrThrow(_stmt, "inLibrary")
        val _columnIndexOfFavoriteAt: Int = getColumnIndexOrThrow(_stmt, "favoriteAt")
        val _columnIndexOfDownloadedAt: Int = getColumnIndexOrThrow(_stmt, "downloadedAt")
        val _columnIndexOfDownloadState: Int = getColumnIndexOrThrow(_stmt, "downloadState")
        val _result: AlbumEntity?
        if (_stmt.step()) {
          val _tmpBrowseId: String
          _tmpBrowseId = _stmt.getText(_columnIndexOfBrowseId)
          val _tmpArtistId: List<String?>?
          val _tmp: String?
          if (_stmt.isNull(_columnIndexOfArtistId)) {
            _tmp = null
          } else {
            _tmp = _stmt.getText(_columnIndexOfArtistId)
          }
          _tmpArtistId = __converters().fromStringNull(_tmp)
          val _tmpArtistName: List<String>?
          val _tmp_1: String?
          if (_stmt.isNull(_columnIndexOfArtistName)) {
            _tmp_1 = null
          } else {
            _tmp_1 = _stmt.getText(_columnIndexOfArtistName)
          }
          _tmpArtistName = __converters().fromString(_tmp_1)
          val _tmpAudioPlaylistId: String
          _tmpAudioPlaylistId = _stmt.getText(_columnIndexOfAudioPlaylistId)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpDuration: String?
          if (_stmt.isNull(_columnIndexOfDuration)) {
            _tmpDuration = null
          } else {
            _tmpDuration = _stmt.getText(_columnIndexOfDuration)
          }
          val _tmpDurationSeconds: Int
          _tmpDurationSeconds = _stmt.getLong(_columnIndexOfDurationSeconds).toInt()
          val _tmpThumbnails: String?
          if (_stmt.isNull(_columnIndexOfThumbnails)) {
            _tmpThumbnails = null
          } else {
            _tmpThumbnails = _stmt.getText(_columnIndexOfThumbnails)
          }
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpTrackCount: Int
          _tmpTrackCount = _stmt.getLong(_columnIndexOfTrackCount).toInt()
          val _tmpTracks: List<String>?
          val _tmp_2: String?
          if (_stmt.isNull(_columnIndexOfTracks)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getText(_columnIndexOfTracks)
          }
          _tmpTracks = __converters().fromString(_tmp_2)
          val _tmpType: String
          _tmpType = _stmt.getText(_columnIndexOfType)
          val _tmpYear: String?
          if (_stmt.isNull(_columnIndexOfYear)) {
            _tmpYear = null
          } else {
            _tmpYear = _stmt.getText(_columnIndexOfYear)
          }
          val _tmpLiked: Boolean
          val _tmp_3: Int
          _tmp_3 = _stmt.getLong(_columnIndexOfLiked).toInt()
          _tmpLiked = _tmp_3 != 0
          val _tmpInLibrary: LocalDateTime
          val _tmp_4: Long?
          if (_stmt.isNull(_columnIndexOfInLibrary)) {
            _tmp_4 = null
          } else {
            _tmp_4 = _stmt.getLong(_columnIndexOfInLibrary)
          }
          val _tmp_5: LocalDateTime? = __converters().fromTimestamp(_tmp_4)
          if (_tmp_5 == null) {
            error("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.")
          } else {
            _tmpInLibrary = _tmp_5
          }
          val _tmpFavoriteAt: LocalDateTime?
          val _tmp_6: Long?
          if (_stmt.isNull(_columnIndexOfFavoriteAt)) {
            _tmp_6 = null
          } else {
            _tmp_6 = _stmt.getLong(_columnIndexOfFavoriteAt)
          }
          _tmpFavoriteAt = __converters().fromTimestamp(_tmp_6)
          val _tmpDownloadedAt: LocalDateTime?
          val _tmp_7: Long?
          if (_stmt.isNull(_columnIndexOfDownloadedAt)) {
            _tmp_7 = null
          } else {
            _tmp_7 = _stmt.getLong(_columnIndexOfDownloadedAt)
          }
          _tmpDownloadedAt = __converters().fromTimestamp(_tmp_7)
          val _tmpDownloadState: Int
          _tmpDownloadState = _stmt.getLong(_columnIndexOfDownloadState).toInt()
          _result = AlbumEntity(_tmpBrowseId,_tmpArtistId,_tmpArtistName,_tmpAudioPlaylistId,_tmpDescription,_tmpDuration,_tmpDurationSeconds,_tmpThumbnails,_tmpTitle,_tmpTrackCount,_tmpTracks,_tmpType,_tmpYear,_tmpLiked,_tmpInLibrary,_tmpFavoriteAt,_tmpDownloadedAt,_tmpDownloadState)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getLikedAlbums(limit: Int, offset: Int): List<AlbumEntity> {
    val _sql: String = "SELECT * FROM album WHERE liked = 1 ORDER BY favoriteAt DESC LIMIT ? OFFSET ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, limit.toLong())
        _argIndex = 2
        _stmt.bindLong(_argIndex, offset.toLong())
        val _columnIndexOfBrowseId: Int = getColumnIndexOrThrow(_stmt, "browseId")
        val _columnIndexOfArtistId: Int = getColumnIndexOrThrow(_stmt, "artistId")
        val _columnIndexOfArtistName: Int = getColumnIndexOrThrow(_stmt, "artistName")
        val _columnIndexOfAudioPlaylistId: Int = getColumnIndexOrThrow(_stmt, "audioPlaylistId")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfDuration: Int = getColumnIndexOrThrow(_stmt, "duration")
        val _columnIndexOfDurationSeconds: Int = getColumnIndexOrThrow(_stmt, "durationSeconds")
        val _columnIndexOfThumbnails: Int = getColumnIndexOrThrow(_stmt, "thumbnails")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfTrackCount: Int = getColumnIndexOrThrow(_stmt, "trackCount")
        val _columnIndexOfTracks: Int = getColumnIndexOrThrow(_stmt, "tracks")
        val _columnIndexOfType: Int = getColumnIndexOrThrow(_stmt, "type")
        val _columnIndexOfYear: Int = getColumnIndexOrThrow(_stmt, "year")
        val _columnIndexOfLiked: Int = getColumnIndexOrThrow(_stmt, "liked")
        val _columnIndexOfInLibrary: Int = getColumnIndexOrThrow(_stmt, "inLibrary")
        val _columnIndexOfFavoriteAt: Int = getColumnIndexOrThrow(_stmt, "favoriteAt")
        val _columnIndexOfDownloadedAt: Int = getColumnIndexOrThrow(_stmt, "downloadedAt")
        val _columnIndexOfDownloadState: Int = getColumnIndexOrThrow(_stmt, "downloadState")
        val _result: MutableList<AlbumEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: AlbumEntity
          val _tmpBrowseId: String
          _tmpBrowseId = _stmt.getText(_columnIndexOfBrowseId)
          val _tmpArtistId: List<String?>?
          val _tmp: String?
          if (_stmt.isNull(_columnIndexOfArtistId)) {
            _tmp = null
          } else {
            _tmp = _stmt.getText(_columnIndexOfArtistId)
          }
          _tmpArtistId = __converters().fromStringNull(_tmp)
          val _tmpArtistName: List<String>?
          val _tmp_1: String?
          if (_stmt.isNull(_columnIndexOfArtistName)) {
            _tmp_1 = null
          } else {
            _tmp_1 = _stmt.getText(_columnIndexOfArtistName)
          }
          _tmpArtistName = __converters().fromString(_tmp_1)
          val _tmpAudioPlaylistId: String
          _tmpAudioPlaylistId = _stmt.getText(_columnIndexOfAudioPlaylistId)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpDuration: String?
          if (_stmt.isNull(_columnIndexOfDuration)) {
            _tmpDuration = null
          } else {
            _tmpDuration = _stmt.getText(_columnIndexOfDuration)
          }
          val _tmpDurationSeconds: Int
          _tmpDurationSeconds = _stmt.getLong(_columnIndexOfDurationSeconds).toInt()
          val _tmpThumbnails: String?
          if (_stmt.isNull(_columnIndexOfThumbnails)) {
            _tmpThumbnails = null
          } else {
            _tmpThumbnails = _stmt.getText(_columnIndexOfThumbnails)
          }
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpTrackCount: Int
          _tmpTrackCount = _stmt.getLong(_columnIndexOfTrackCount).toInt()
          val _tmpTracks: List<String>?
          val _tmp_2: String?
          if (_stmt.isNull(_columnIndexOfTracks)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getText(_columnIndexOfTracks)
          }
          _tmpTracks = __converters().fromString(_tmp_2)
          val _tmpType: String
          _tmpType = _stmt.getText(_columnIndexOfType)
          val _tmpYear: String?
          if (_stmt.isNull(_columnIndexOfYear)) {
            _tmpYear = null
          } else {
            _tmpYear = _stmt.getText(_columnIndexOfYear)
          }
          val _tmpLiked: Boolean
          val _tmp_3: Int
          _tmp_3 = _stmt.getLong(_columnIndexOfLiked).toInt()
          _tmpLiked = _tmp_3 != 0
          val _tmpInLibrary: LocalDateTime
          val _tmp_4: Long?
          if (_stmt.isNull(_columnIndexOfInLibrary)) {
            _tmp_4 = null
          } else {
            _tmp_4 = _stmt.getLong(_columnIndexOfInLibrary)
          }
          val _tmp_5: LocalDateTime? = __converters().fromTimestamp(_tmp_4)
          if (_tmp_5 == null) {
            error("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.")
          } else {
            _tmpInLibrary = _tmp_5
          }
          val _tmpFavoriteAt: LocalDateTime?
          val _tmp_6: Long?
          if (_stmt.isNull(_columnIndexOfFavoriteAt)) {
            _tmp_6 = null
          } else {
            _tmp_6 = _stmt.getLong(_columnIndexOfFavoriteAt)
          }
          _tmpFavoriteAt = __converters().fromTimestamp(_tmp_6)
          val _tmpDownloadedAt: LocalDateTime?
          val _tmp_7: Long?
          if (_stmt.isNull(_columnIndexOfDownloadedAt)) {
            _tmp_7 = null
          } else {
            _tmp_7 = _stmt.getLong(_columnIndexOfDownloadedAt)
          }
          _tmpDownloadedAt = __converters().fromTimestamp(_tmp_7)
          val _tmpDownloadState: Int
          _tmpDownloadState = _stmt.getLong(_columnIndexOfDownloadState).toInt()
          _item = AlbumEntity(_tmpBrowseId,_tmpArtistId,_tmpArtistName,_tmpAudioPlaylistId,_tmpDescription,_tmpDuration,_tmpDurationSeconds,_tmpThumbnails,_tmpTitle,_tmpTrackCount,_tmpTracks,_tmpType,_tmpYear,_tmpLiked,_tmpInLibrary,_tmpFavoriteAt,_tmpDownloadedAt,_tmpDownloadState)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getDownloadedAlbums(limit: Int, offset: Int): List<AlbumEntity> {
    val _sql: String = "SELECT * FROM album WHERE downloadState = 3 ORDER BY downloadedAt DESC LIMIT ? OFFSET ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, limit.toLong())
        _argIndex = 2
        _stmt.bindLong(_argIndex, offset.toLong())
        val _columnIndexOfBrowseId: Int = getColumnIndexOrThrow(_stmt, "browseId")
        val _columnIndexOfArtistId: Int = getColumnIndexOrThrow(_stmt, "artistId")
        val _columnIndexOfArtistName: Int = getColumnIndexOrThrow(_stmt, "artistName")
        val _columnIndexOfAudioPlaylistId: Int = getColumnIndexOrThrow(_stmt, "audioPlaylistId")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfDuration: Int = getColumnIndexOrThrow(_stmt, "duration")
        val _columnIndexOfDurationSeconds: Int = getColumnIndexOrThrow(_stmt, "durationSeconds")
        val _columnIndexOfThumbnails: Int = getColumnIndexOrThrow(_stmt, "thumbnails")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfTrackCount: Int = getColumnIndexOrThrow(_stmt, "trackCount")
        val _columnIndexOfTracks: Int = getColumnIndexOrThrow(_stmt, "tracks")
        val _columnIndexOfType: Int = getColumnIndexOrThrow(_stmt, "type")
        val _columnIndexOfYear: Int = getColumnIndexOrThrow(_stmt, "year")
        val _columnIndexOfLiked: Int = getColumnIndexOrThrow(_stmt, "liked")
        val _columnIndexOfInLibrary: Int = getColumnIndexOrThrow(_stmt, "inLibrary")
        val _columnIndexOfFavoriteAt: Int = getColumnIndexOrThrow(_stmt, "favoriteAt")
        val _columnIndexOfDownloadedAt: Int = getColumnIndexOrThrow(_stmt, "downloadedAt")
        val _columnIndexOfDownloadState: Int = getColumnIndexOrThrow(_stmt, "downloadState")
        val _result: MutableList<AlbumEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: AlbumEntity
          val _tmpBrowseId: String
          _tmpBrowseId = _stmt.getText(_columnIndexOfBrowseId)
          val _tmpArtistId: List<String?>?
          val _tmp: String?
          if (_stmt.isNull(_columnIndexOfArtistId)) {
            _tmp = null
          } else {
            _tmp = _stmt.getText(_columnIndexOfArtistId)
          }
          _tmpArtistId = __converters().fromStringNull(_tmp)
          val _tmpArtistName: List<String>?
          val _tmp_1: String?
          if (_stmt.isNull(_columnIndexOfArtistName)) {
            _tmp_1 = null
          } else {
            _tmp_1 = _stmt.getText(_columnIndexOfArtistName)
          }
          _tmpArtistName = __converters().fromString(_tmp_1)
          val _tmpAudioPlaylistId: String
          _tmpAudioPlaylistId = _stmt.getText(_columnIndexOfAudioPlaylistId)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpDuration: String?
          if (_stmt.isNull(_columnIndexOfDuration)) {
            _tmpDuration = null
          } else {
            _tmpDuration = _stmt.getText(_columnIndexOfDuration)
          }
          val _tmpDurationSeconds: Int
          _tmpDurationSeconds = _stmt.getLong(_columnIndexOfDurationSeconds).toInt()
          val _tmpThumbnails: String?
          if (_stmt.isNull(_columnIndexOfThumbnails)) {
            _tmpThumbnails = null
          } else {
            _tmpThumbnails = _stmt.getText(_columnIndexOfThumbnails)
          }
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpTrackCount: Int
          _tmpTrackCount = _stmt.getLong(_columnIndexOfTrackCount).toInt()
          val _tmpTracks: List<String>?
          val _tmp_2: String?
          if (_stmt.isNull(_columnIndexOfTracks)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getText(_columnIndexOfTracks)
          }
          _tmpTracks = __converters().fromString(_tmp_2)
          val _tmpType: String
          _tmpType = _stmt.getText(_columnIndexOfType)
          val _tmpYear: String?
          if (_stmt.isNull(_columnIndexOfYear)) {
            _tmpYear = null
          } else {
            _tmpYear = _stmt.getText(_columnIndexOfYear)
          }
          val _tmpLiked: Boolean
          val _tmp_3: Int
          _tmp_3 = _stmt.getLong(_columnIndexOfLiked).toInt()
          _tmpLiked = _tmp_3 != 0
          val _tmpInLibrary: LocalDateTime
          val _tmp_4: Long?
          if (_stmt.isNull(_columnIndexOfInLibrary)) {
            _tmp_4 = null
          } else {
            _tmp_4 = _stmt.getLong(_columnIndexOfInLibrary)
          }
          val _tmp_5: LocalDateTime? = __converters().fromTimestamp(_tmp_4)
          if (_tmp_5 == null) {
            error("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.")
          } else {
            _tmpInLibrary = _tmp_5
          }
          val _tmpFavoriteAt: LocalDateTime?
          val _tmp_6: Long?
          if (_stmt.isNull(_columnIndexOfFavoriteAt)) {
            _tmp_6 = null
          } else {
            _tmp_6 = _stmt.getLong(_columnIndexOfFavoriteAt)
          }
          _tmpFavoriteAt = __converters().fromTimestamp(_tmp_6)
          val _tmpDownloadedAt: LocalDateTime?
          val _tmp_7: Long?
          if (_stmt.isNull(_columnIndexOfDownloadedAt)) {
            _tmp_7 = null
          } else {
            _tmp_7 = _stmt.getLong(_columnIndexOfDownloadedAt)
          }
          _tmpDownloadedAt = __converters().fromTimestamp(_tmp_7)
          val _tmpDownloadState: Int
          _tmpDownloadState = _stmt.getLong(_columnIndexOfDownloadState).toInt()
          _item = AlbumEntity(_tmpBrowseId,_tmpArtistId,_tmpArtistName,_tmpAudioPlaylistId,_tmpDescription,_tmpDuration,_tmpDurationSeconds,_tmpThumbnails,_tmpTitle,_tmpTrackCount,_tmpTracks,_tmpType,_tmpYear,_tmpLiked,_tmpInLibrary,_tmpFavoriteAt,_tmpDownloadedAt,_tmpDownloadState)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getDownloadingAlbums(limit: Int, offset: Int): List<AlbumEntity> {
    val _sql: String = "SELECT * FROM album WHERE downloadState = 2 OR downloadState = 1 LIMIT ? OFFSET ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, limit.toLong())
        _argIndex = 2
        _stmt.bindLong(_argIndex, offset.toLong())
        val _columnIndexOfBrowseId: Int = getColumnIndexOrThrow(_stmt, "browseId")
        val _columnIndexOfArtistId: Int = getColumnIndexOrThrow(_stmt, "artistId")
        val _columnIndexOfArtistName: Int = getColumnIndexOrThrow(_stmt, "artistName")
        val _columnIndexOfAudioPlaylistId: Int = getColumnIndexOrThrow(_stmt, "audioPlaylistId")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfDuration: Int = getColumnIndexOrThrow(_stmt, "duration")
        val _columnIndexOfDurationSeconds: Int = getColumnIndexOrThrow(_stmt, "durationSeconds")
        val _columnIndexOfThumbnails: Int = getColumnIndexOrThrow(_stmt, "thumbnails")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfTrackCount: Int = getColumnIndexOrThrow(_stmt, "trackCount")
        val _columnIndexOfTracks: Int = getColumnIndexOrThrow(_stmt, "tracks")
        val _columnIndexOfType: Int = getColumnIndexOrThrow(_stmt, "type")
        val _columnIndexOfYear: Int = getColumnIndexOrThrow(_stmt, "year")
        val _columnIndexOfLiked: Int = getColumnIndexOrThrow(_stmt, "liked")
        val _columnIndexOfInLibrary: Int = getColumnIndexOrThrow(_stmt, "inLibrary")
        val _columnIndexOfFavoriteAt: Int = getColumnIndexOrThrow(_stmt, "favoriteAt")
        val _columnIndexOfDownloadedAt: Int = getColumnIndexOrThrow(_stmt, "downloadedAt")
        val _columnIndexOfDownloadState: Int = getColumnIndexOrThrow(_stmt, "downloadState")
        val _result: MutableList<AlbumEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: AlbumEntity
          val _tmpBrowseId: String
          _tmpBrowseId = _stmt.getText(_columnIndexOfBrowseId)
          val _tmpArtistId: List<String?>?
          val _tmp: String?
          if (_stmt.isNull(_columnIndexOfArtistId)) {
            _tmp = null
          } else {
            _tmp = _stmt.getText(_columnIndexOfArtistId)
          }
          _tmpArtistId = __converters().fromStringNull(_tmp)
          val _tmpArtistName: List<String>?
          val _tmp_1: String?
          if (_stmt.isNull(_columnIndexOfArtistName)) {
            _tmp_1 = null
          } else {
            _tmp_1 = _stmt.getText(_columnIndexOfArtistName)
          }
          _tmpArtistName = __converters().fromString(_tmp_1)
          val _tmpAudioPlaylistId: String
          _tmpAudioPlaylistId = _stmt.getText(_columnIndexOfAudioPlaylistId)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpDuration: String?
          if (_stmt.isNull(_columnIndexOfDuration)) {
            _tmpDuration = null
          } else {
            _tmpDuration = _stmt.getText(_columnIndexOfDuration)
          }
          val _tmpDurationSeconds: Int
          _tmpDurationSeconds = _stmt.getLong(_columnIndexOfDurationSeconds).toInt()
          val _tmpThumbnails: String?
          if (_stmt.isNull(_columnIndexOfThumbnails)) {
            _tmpThumbnails = null
          } else {
            _tmpThumbnails = _stmt.getText(_columnIndexOfThumbnails)
          }
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpTrackCount: Int
          _tmpTrackCount = _stmt.getLong(_columnIndexOfTrackCount).toInt()
          val _tmpTracks: List<String>?
          val _tmp_2: String?
          if (_stmt.isNull(_columnIndexOfTracks)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getText(_columnIndexOfTracks)
          }
          _tmpTracks = __converters().fromString(_tmp_2)
          val _tmpType: String
          _tmpType = _stmt.getText(_columnIndexOfType)
          val _tmpYear: String?
          if (_stmt.isNull(_columnIndexOfYear)) {
            _tmpYear = null
          } else {
            _tmpYear = _stmt.getText(_columnIndexOfYear)
          }
          val _tmpLiked: Boolean
          val _tmp_3: Int
          _tmp_3 = _stmt.getLong(_columnIndexOfLiked).toInt()
          _tmpLiked = _tmp_3 != 0
          val _tmpInLibrary: LocalDateTime
          val _tmp_4: Long?
          if (_stmt.isNull(_columnIndexOfInLibrary)) {
            _tmp_4 = null
          } else {
            _tmp_4 = _stmt.getLong(_columnIndexOfInLibrary)
          }
          val _tmp_5: LocalDateTime? = __converters().fromTimestamp(_tmp_4)
          if (_tmp_5 == null) {
            error("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.")
          } else {
            _tmpInLibrary = _tmp_5
          }
          val _tmpFavoriteAt: LocalDateTime?
          val _tmp_6: Long?
          if (_stmt.isNull(_columnIndexOfFavoriteAt)) {
            _tmp_6 = null
          } else {
            _tmp_6 = _stmt.getLong(_columnIndexOfFavoriteAt)
          }
          _tmpFavoriteAt = __converters().fromTimestamp(_tmp_6)
          val _tmpDownloadedAt: LocalDateTime?
          val _tmp_7: Long?
          if (_stmt.isNull(_columnIndexOfDownloadedAt)) {
            _tmp_7 = null
          } else {
            _tmp_7 = _stmt.getLong(_columnIndexOfDownloadedAt)
          }
          _tmpDownloadedAt = __converters().fromTimestamp(_tmp_7)
          val _tmpDownloadState: Int
          _tmpDownloadState = _stmt.getLong(_columnIndexOfDownloadState).toInt()
          _item = AlbumEntity(_tmpBrowseId,_tmpArtistId,_tmpArtistName,_tmpAudioPlaylistId,_tmpDescription,_tmpDuration,_tmpDurationSeconds,_tmpThumbnails,_tmpTitle,_tmpTrackCount,_tmpTracks,_tmpType,_tmpYear,_tmpLiked,_tmpInLibrary,_tmpFavoriteAt,_tmpDownloadedAt,_tmpDownloadState)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getAllPlaylists(limit: Int): List<PlaylistEntity> {
    val _sql: String = "SELECT * FROM playlist ORDER BY inLibrary DESC LIMIT ? OFFSET 0"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, limit.toLong())
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfAuthor: Int = getColumnIndexOrThrow(_stmt, "author")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfDuration: Int = getColumnIndexOrThrow(_stmt, "duration")
        val _columnIndexOfDurationSeconds: Int = getColumnIndexOrThrow(_stmt, "durationSeconds")
        val _columnIndexOfPrivacy: Int = getColumnIndexOrThrow(_stmt, "privacy")
        val _columnIndexOfThumbnails: Int = getColumnIndexOrThrow(_stmt, "thumbnails")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfTrackCount: Int = getColumnIndexOrThrow(_stmt, "trackCount")
        val _columnIndexOfTracks: Int = getColumnIndexOrThrow(_stmt, "tracks")
        val _columnIndexOfYear: Int = getColumnIndexOrThrow(_stmt, "year")
        val _columnIndexOfLiked: Int = getColumnIndexOrThrow(_stmt, "liked")
        val _columnIndexOfInLibrary: Int = getColumnIndexOrThrow(_stmt, "inLibrary")
        val _columnIndexOfFavoriteAt: Int = getColumnIndexOrThrow(_stmt, "favoriteAt")
        val _columnIndexOfDownloadedAt: Int = getColumnIndexOrThrow(_stmt, "downloadedAt")
        val _columnIndexOfDownloadState: Int = getColumnIndexOrThrow(_stmt, "downloadState")
        val _result: MutableList<PlaylistEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: PlaylistEntity
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpAuthor: String?
          if (_stmt.isNull(_columnIndexOfAuthor)) {
            _tmpAuthor = null
          } else {
            _tmpAuthor = _stmt.getText(_columnIndexOfAuthor)
          }
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpDuration: String
          _tmpDuration = _stmt.getText(_columnIndexOfDuration)
          val _tmpDurationSeconds: Int
          _tmpDurationSeconds = _stmt.getLong(_columnIndexOfDurationSeconds).toInt()
          val _tmpPrivacy: String
          _tmpPrivacy = _stmt.getText(_columnIndexOfPrivacy)
          val _tmpThumbnails: String
          _tmpThumbnails = _stmt.getText(_columnIndexOfThumbnails)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpTrackCount: Int
          _tmpTrackCount = _stmt.getLong(_columnIndexOfTrackCount).toInt()
          val _tmpTracks: List<String>?
          val _tmp: String?
          if (_stmt.isNull(_columnIndexOfTracks)) {
            _tmp = null
          } else {
            _tmp = _stmt.getText(_columnIndexOfTracks)
          }
          _tmpTracks = __converters().fromString(_tmp)
          val _tmpYear: String?
          if (_stmt.isNull(_columnIndexOfYear)) {
            _tmpYear = null
          } else {
            _tmpYear = _stmt.getText(_columnIndexOfYear)
          }
          val _tmpLiked: Boolean
          val _tmp_1: Int
          _tmp_1 = _stmt.getLong(_columnIndexOfLiked).toInt()
          _tmpLiked = _tmp_1 != 0
          val _tmpInLibrary: LocalDateTime
          val _tmp_2: Long?
          if (_stmt.isNull(_columnIndexOfInLibrary)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getLong(_columnIndexOfInLibrary)
          }
          val _tmp_3: LocalDateTime? = __converters().fromTimestamp(_tmp_2)
          if (_tmp_3 == null) {
            error("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.")
          } else {
            _tmpInLibrary = _tmp_3
          }
          val _tmpFavoriteAt: LocalDateTime?
          val _tmp_4: Long?
          if (_stmt.isNull(_columnIndexOfFavoriteAt)) {
            _tmp_4 = null
          } else {
            _tmp_4 = _stmt.getLong(_columnIndexOfFavoriteAt)
          }
          _tmpFavoriteAt = __converters().fromTimestamp(_tmp_4)
          val _tmpDownloadedAt: LocalDateTime?
          val _tmp_5: Long?
          if (_stmt.isNull(_columnIndexOfDownloadedAt)) {
            _tmp_5 = null
          } else {
            _tmp_5 = _stmt.getLong(_columnIndexOfDownloadedAt)
          }
          _tmpDownloadedAt = __converters().fromTimestamp(_tmp_5)
          val _tmpDownloadState: Int
          _tmpDownloadState = _stmt.getLong(_columnIndexOfDownloadState).toInt()
          _item = PlaylistEntity(_tmpId,_tmpAuthor,_tmpDescription,_tmpDuration,_tmpDurationSeconds,_tmpPrivacy,_tmpThumbnails,_tmpTitle,_tmpTrackCount,_tmpTracks,_tmpYear,_tmpLiked,_tmpInLibrary,_tmpFavoriteAt,_tmpDownloadedAt,_tmpDownloadState)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getPlaylist(playlistId: String): PlaylistEntity? {
    val _sql: String = "SELECT * FROM playlist WHERE id = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, playlistId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfAuthor: Int = getColumnIndexOrThrow(_stmt, "author")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfDuration: Int = getColumnIndexOrThrow(_stmt, "duration")
        val _columnIndexOfDurationSeconds: Int = getColumnIndexOrThrow(_stmt, "durationSeconds")
        val _columnIndexOfPrivacy: Int = getColumnIndexOrThrow(_stmt, "privacy")
        val _columnIndexOfThumbnails: Int = getColumnIndexOrThrow(_stmt, "thumbnails")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfTrackCount: Int = getColumnIndexOrThrow(_stmt, "trackCount")
        val _columnIndexOfTracks: Int = getColumnIndexOrThrow(_stmt, "tracks")
        val _columnIndexOfYear: Int = getColumnIndexOrThrow(_stmt, "year")
        val _columnIndexOfLiked: Int = getColumnIndexOrThrow(_stmt, "liked")
        val _columnIndexOfInLibrary: Int = getColumnIndexOrThrow(_stmt, "inLibrary")
        val _columnIndexOfFavoriteAt: Int = getColumnIndexOrThrow(_stmt, "favoriteAt")
        val _columnIndexOfDownloadedAt: Int = getColumnIndexOrThrow(_stmt, "downloadedAt")
        val _columnIndexOfDownloadState: Int = getColumnIndexOrThrow(_stmt, "downloadState")
        val _result: PlaylistEntity?
        if (_stmt.step()) {
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpAuthor: String?
          if (_stmt.isNull(_columnIndexOfAuthor)) {
            _tmpAuthor = null
          } else {
            _tmpAuthor = _stmt.getText(_columnIndexOfAuthor)
          }
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpDuration: String
          _tmpDuration = _stmt.getText(_columnIndexOfDuration)
          val _tmpDurationSeconds: Int
          _tmpDurationSeconds = _stmt.getLong(_columnIndexOfDurationSeconds).toInt()
          val _tmpPrivacy: String
          _tmpPrivacy = _stmt.getText(_columnIndexOfPrivacy)
          val _tmpThumbnails: String
          _tmpThumbnails = _stmt.getText(_columnIndexOfThumbnails)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpTrackCount: Int
          _tmpTrackCount = _stmt.getLong(_columnIndexOfTrackCount).toInt()
          val _tmpTracks: List<String>?
          val _tmp: String?
          if (_stmt.isNull(_columnIndexOfTracks)) {
            _tmp = null
          } else {
            _tmp = _stmt.getText(_columnIndexOfTracks)
          }
          _tmpTracks = __converters().fromString(_tmp)
          val _tmpYear: String?
          if (_stmt.isNull(_columnIndexOfYear)) {
            _tmpYear = null
          } else {
            _tmpYear = _stmt.getText(_columnIndexOfYear)
          }
          val _tmpLiked: Boolean
          val _tmp_1: Int
          _tmp_1 = _stmt.getLong(_columnIndexOfLiked).toInt()
          _tmpLiked = _tmp_1 != 0
          val _tmpInLibrary: LocalDateTime
          val _tmp_2: Long?
          if (_stmt.isNull(_columnIndexOfInLibrary)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getLong(_columnIndexOfInLibrary)
          }
          val _tmp_3: LocalDateTime? = __converters().fromTimestamp(_tmp_2)
          if (_tmp_3 == null) {
            error("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.")
          } else {
            _tmpInLibrary = _tmp_3
          }
          val _tmpFavoriteAt: LocalDateTime?
          val _tmp_4: Long?
          if (_stmt.isNull(_columnIndexOfFavoriteAt)) {
            _tmp_4 = null
          } else {
            _tmp_4 = _stmt.getLong(_columnIndexOfFavoriteAt)
          }
          _tmpFavoriteAt = __converters().fromTimestamp(_tmp_4)
          val _tmpDownloadedAt: LocalDateTime?
          val _tmp_5: Long?
          if (_stmt.isNull(_columnIndexOfDownloadedAt)) {
            _tmp_5 = null
          } else {
            _tmp_5 = _stmt.getLong(_columnIndexOfDownloadedAt)
          }
          _tmpDownloadedAt = __converters().fromTimestamp(_tmp_5)
          val _tmpDownloadState: Int
          _tmpDownloadState = _stmt.getLong(_columnIndexOfDownloadState).toInt()
          _result = PlaylistEntity(_tmpId,_tmpAuthor,_tmpDescription,_tmpDuration,_tmpDurationSeconds,_tmpPrivacy,_tmpThumbnails,_tmpTitle,_tmpTrackCount,_tmpTracks,_tmpYear,_tmpLiked,_tmpInLibrary,_tmpFavoriteAt,_tmpDownloadedAt,_tmpDownloadState)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getLikedPlaylists(limit: Int, offset: Int): List<PlaylistEntity> {
    val _sql: String = "SELECT * FROM playlist WHERE liked = 1 ORDER BY favoriteAt DESC LIMIT ? OFFSET ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, limit.toLong())
        _argIndex = 2
        _stmt.bindLong(_argIndex, offset.toLong())
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfAuthor: Int = getColumnIndexOrThrow(_stmt, "author")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfDuration: Int = getColumnIndexOrThrow(_stmt, "duration")
        val _columnIndexOfDurationSeconds: Int = getColumnIndexOrThrow(_stmt, "durationSeconds")
        val _columnIndexOfPrivacy: Int = getColumnIndexOrThrow(_stmt, "privacy")
        val _columnIndexOfThumbnails: Int = getColumnIndexOrThrow(_stmt, "thumbnails")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfTrackCount: Int = getColumnIndexOrThrow(_stmt, "trackCount")
        val _columnIndexOfTracks: Int = getColumnIndexOrThrow(_stmt, "tracks")
        val _columnIndexOfYear: Int = getColumnIndexOrThrow(_stmt, "year")
        val _columnIndexOfLiked: Int = getColumnIndexOrThrow(_stmt, "liked")
        val _columnIndexOfInLibrary: Int = getColumnIndexOrThrow(_stmt, "inLibrary")
        val _columnIndexOfFavoriteAt: Int = getColumnIndexOrThrow(_stmt, "favoriteAt")
        val _columnIndexOfDownloadedAt: Int = getColumnIndexOrThrow(_stmt, "downloadedAt")
        val _columnIndexOfDownloadState: Int = getColumnIndexOrThrow(_stmt, "downloadState")
        val _result: MutableList<PlaylistEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: PlaylistEntity
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpAuthor: String?
          if (_stmt.isNull(_columnIndexOfAuthor)) {
            _tmpAuthor = null
          } else {
            _tmpAuthor = _stmt.getText(_columnIndexOfAuthor)
          }
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpDuration: String
          _tmpDuration = _stmt.getText(_columnIndexOfDuration)
          val _tmpDurationSeconds: Int
          _tmpDurationSeconds = _stmt.getLong(_columnIndexOfDurationSeconds).toInt()
          val _tmpPrivacy: String
          _tmpPrivacy = _stmt.getText(_columnIndexOfPrivacy)
          val _tmpThumbnails: String
          _tmpThumbnails = _stmt.getText(_columnIndexOfThumbnails)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpTrackCount: Int
          _tmpTrackCount = _stmt.getLong(_columnIndexOfTrackCount).toInt()
          val _tmpTracks: List<String>?
          val _tmp: String?
          if (_stmt.isNull(_columnIndexOfTracks)) {
            _tmp = null
          } else {
            _tmp = _stmt.getText(_columnIndexOfTracks)
          }
          _tmpTracks = __converters().fromString(_tmp)
          val _tmpYear: String?
          if (_stmt.isNull(_columnIndexOfYear)) {
            _tmpYear = null
          } else {
            _tmpYear = _stmt.getText(_columnIndexOfYear)
          }
          val _tmpLiked: Boolean
          val _tmp_1: Int
          _tmp_1 = _stmt.getLong(_columnIndexOfLiked).toInt()
          _tmpLiked = _tmp_1 != 0
          val _tmpInLibrary: LocalDateTime
          val _tmp_2: Long?
          if (_stmt.isNull(_columnIndexOfInLibrary)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getLong(_columnIndexOfInLibrary)
          }
          val _tmp_3: LocalDateTime? = __converters().fromTimestamp(_tmp_2)
          if (_tmp_3 == null) {
            error("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.")
          } else {
            _tmpInLibrary = _tmp_3
          }
          val _tmpFavoriteAt: LocalDateTime?
          val _tmp_4: Long?
          if (_stmt.isNull(_columnIndexOfFavoriteAt)) {
            _tmp_4 = null
          } else {
            _tmp_4 = _stmt.getLong(_columnIndexOfFavoriteAt)
          }
          _tmpFavoriteAt = __converters().fromTimestamp(_tmp_4)
          val _tmpDownloadedAt: LocalDateTime?
          val _tmp_5: Long?
          if (_stmt.isNull(_columnIndexOfDownloadedAt)) {
            _tmp_5 = null
          } else {
            _tmp_5 = _stmt.getLong(_columnIndexOfDownloadedAt)
          }
          _tmpDownloadedAt = __converters().fromTimestamp(_tmp_5)
          val _tmpDownloadState: Int
          _tmpDownloadState = _stmt.getLong(_columnIndexOfDownloadState).toInt()
          _item = PlaylistEntity(_tmpId,_tmpAuthor,_tmpDescription,_tmpDuration,_tmpDurationSeconds,_tmpPrivacy,_tmpThumbnails,_tmpTitle,_tmpTrackCount,_tmpTracks,_tmpYear,_tmpLiked,_tmpInLibrary,_tmpFavoriteAt,_tmpDownloadedAt,_tmpDownloadState)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getDownloadedPlaylists(limit: Int, offset: Int): List<PlaylistEntity> {
    val _sql: String = "SELECT * FROM playlist WHERE downloadState = 3 ORDER BY downloadedAt DESC LIMIT ? OFFSET ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, limit.toLong())
        _argIndex = 2
        _stmt.bindLong(_argIndex, offset.toLong())
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfAuthor: Int = getColumnIndexOrThrow(_stmt, "author")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfDuration: Int = getColumnIndexOrThrow(_stmt, "duration")
        val _columnIndexOfDurationSeconds: Int = getColumnIndexOrThrow(_stmt, "durationSeconds")
        val _columnIndexOfPrivacy: Int = getColumnIndexOrThrow(_stmt, "privacy")
        val _columnIndexOfThumbnails: Int = getColumnIndexOrThrow(_stmt, "thumbnails")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfTrackCount: Int = getColumnIndexOrThrow(_stmt, "trackCount")
        val _columnIndexOfTracks: Int = getColumnIndexOrThrow(_stmt, "tracks")
        val _columnIndexOfYear: Int = getColumnIndexOrThrow(_stmt, "year")
        val _columnIndexOfLiked: Int = getColumnIndexOrThrow(_stmt, "liked")
        val _columnIndexOfInLibrary: Int = getColumnIndexOrThrow(_stmt, "inLibrary")
        val _columnIndexOfFavoriteAt: Int = getColumnIndexOrThrow(_stmt, "favoriteAt")
        val _columnIndexOfDownloadedAt: Int = getColumnIndexOrThrow(_stmt, "downloadedAt")
        val _columnIndexOfDownloadState: Int = getColumnIndexOrThrow(_stmt, "downloadState")
        val _result: MutableList<PlaylistEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: PlaylistEntity
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpAuthor: String?
          if (_stmt.isNull(_columnIndexOfAuthor)) {
            _tmpAuthor = null
          } else {
            _tmpAuthor = _stmt.getText(_columnIndexOfAuthor)
          }
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpDuration: String
          _tmpDuration = _stmt.getText(_columnIndexOfDuration)
          val _tmpDurationSeconds: Int
          _tmpDurationSeconds = _stmt.getLong(_columnIndexOfDurationSeconds).toInt()
          val _tmpPrivacy: String
          _tmpPrivacy = _stmt.getText(_columnIndexOfPrivacy)
          val _tmpThumbnails: String
          _tmpThumbnails = _stmt.getText(_columnIndexOfThumbnails)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpTrackCount: Int
          _tmpTrackCount = _stmt.getLong(_columnIndexOfTrackCount).toInt()
          val _tmpTracks: List<String>?
          val _tmp: String?
          if (_stmt.isNull(_columnIndexOfTracks)) {
            _tmp = null
          } else {
            _tmp = _stmt.getText(_columnIndexOfTracks)
          }
          _tmpTracks = __converters().fromString(_tmp)
          val _tmpYear: String?
          if (_stmt.isNull(_columnIndexOfYear)) {
            _tmpYear = null
          } else {
            _tmpYear = _stmt.getText(_columnIndexOfYear)
          }
          val _tmpLiked: Boolean
          val _tmp_1: Int
          _tmp_1 = _stmt.getLong(_columnIndexOfLiked).toInt()
          _tmpLiked = _tmp_1 != 0
          val _tmpInLibrary: LocalDateTime
          val _tmp_2: Long?
          if (_stmt.isNull(_columnIndexOfInLibrary)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getLong(_columnIndexOfInLibrary)
          }
          val _tmp_3: LocalDateTime? = __converters().fromTimestamp(_tmp_2)
          if (_tmp_3 == null) {
            error("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.")
          } else {
            _tmpInLibrary = _tmp_3
          }
          val _tmpFavoriteAt: LocalDateTime?
          val _tmp_4: Long?
          if (_stmt.isNull(_columnIndexOfFavoriteAt)) {
            _tmp_4 = null
          } else {
            _tmp_4 = _stmt.getLong(_columnIndexOfFavoriteAt)
          }
          _tmpFavoriteAt = __converters().fromTimestamp(_tmp_4)
          val _tmpDownloadedAt: LocalDateTime?
          val _tmp_5: Long?
          if (_stmt.isNull(_columnIndexOfDownloadedAt)) {
            _tmp_5 = null
          } else {
            _tmp_5 = _stmt.getLong(_columnIndexOfDownloadedAt)
          }
          _tmpDownloadedAt = __converters().fromTimestamp(_tmp_5)
          val _tmpDownloadState: Int
          _tmpDownloadState = _stmt.getLong(_columnIndexOfDownloadState).toInt()
          _item = PlaylistEntity(_tmpId,_tmpAuthor,_tmpDescription,_tmpDuration,_tmpDurationSeconds,_tmpPrivacy,_tmpThumbnails,_tmpTitle,_tmpTrackCount,_tmpTracks,_tmpYear,_tmpLiked,_tmpInLibrary,_tmpFavoriteAt,_tmpDownloadedAt,_tmpDownloadState)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getDownloadingPlaylists(limit: Int, offset: Int): List<PlaylistEntity> {
    val _sql: String = "SELECT * FROM playlist WHERE downloadState = 1 OR downloadState = 2 LIMIT ? OFFSET ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, limit.toLong())
        _argIndex = 2
        _stmt.bindLong(_argIndex, offset.toLong())
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfAuthor: Int = getColumnIndexOrThrow(_stmt, "author")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfDuration: Int = getColumnIndexOrThrow(_stmt, "duration")
        val _columnIndexOfDurationSeconds: Int = getColumnIndexOrThrow(_stmt, "durationSeconds")
        val _columnIndexOfPrivacy: Int = getColumnIndexOrThrow(_stmt, "privacy")
        val _columnIndexOfThumbnails: Int = getColumnIndexOrThrow(_stmt, "thumbnails")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfTrackCount: Int = getColumnIndexOrThrow(_stmt, "trackCount")
        val _columnIndexOfTracks: Int = getColumnIndexOrThrow(_stmt, "tracks")
        val _columnIndexOfYear: Int = getColumnIndexOrThrow(_stmt, "year")
        val _columnIndexOfLiked: Int = getColumnIndexOrThrow(_stmt, "liked")
        val _columnIndexOfInLibrary: Int = getColumnIndexOrThrow(_stmt, "inLibrary")
        val _columnIndexOfFavoriteAt: Int = getColumnIndexOrThrow(_stmt, "favoriteAt")
        val _columnIndexOfDownloadedAt: Int = getColumnIndexOrThrow(_stmt, "downloadedAt")
        val _columnIndexOfDownloadState: Int = getColumnIndexOrThrow(_stmt, "downloadState")
        val _result: MutableList<PlaylistEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: PlaylistEntity
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpAuthor: String?
          if (_stmt.isNull(_columnIndexOfAuthor)) {
            _tmpAuthor = null
          } else {
            _tmpAuthor = _stmt.getText(_columnIndexOfAuthor)
          }
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpDuration: String
          _tmpDuration = _stmt.getText(_columnIndexOfDuration)
          val _tmpDurationSeconds: Int
          _tmpDurationSeconds = _stmt.getLong(_columnIndexOfDurationSeconds).toInt()
          val _tmpPrivacy: String
          _tmpPrivacy = _stmt.getText(_columnIndexOfPrivacy)
          val _tmpThumbnails: String
          _tmpThumbnails = _stmt.getText(_columnIndexOfThumbnails)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpTrackCount: Int
          _tmpTrackCount = _stmt.getLong(_columnIndexOfTrackCount).toInt()
          val _tmpTracks: List<String>?
          val _tmp: String?
          if (_stmt.isNull(_columnIndexOfTracks)) {
            _tmp = null
          } else {
            _tmp = _stmt.getText(_columnIndexOfTracks)
          }
          _tmpTracks = __converters().fromString(_tmp)
          val _tmpYear: String?
          if (_stmt.isNull(_columnIndexOfYear)) {
            _tmpYear = null
          } else {
            _tmpYear = _stmt.getText(_columnIndexOfYear)
          }
          val _tmpLiked: Boolean
          val _tmp_1: Int
          _tmp_1 = _stmt.getLong(_columnIndexOfLiked).toInt()
          _tmpLiked = _tmp_1 != 0
          val _tmpInLibrary: LocalDateTime
          val _tmp_2: Long?
          if (_stmt.isNull(_columnIndexOfInLibrary)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getLong(_columnIndexOfInLibrary)
          }
          val _tmp_3: LocalDateTime? = __converters().fromTimestamp(_tmp_2)
          if (_tmp_3 == null) {
            error("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.")
          } else {
            _tmpInLibrary = _tmp_3
          }
          val _tmpFavoriteAt: LocalDateTime?
          val _tmp_4: Long?
          if (_stmt.isNull(_columnIndexOfFavoriteAt)) {
            _tmp_4 = null
          } else {
            _tmp_4 = _stmt.getLong(_columnIndexOfFavoriteAt)
          }
          _tmpFavoriteAt = __converters().fromTimestamp(_tmp_4)
          val _tmpDownloadedAt: LocalDateTime?
          val _tmp_5: Long?
          if (_stmt.isNull(_columnIndexOfDownloadedAt)) {
            _tmp_5 = null
          } else {
            _tmp_5 = _stmt.getLong(_columnIndexOfDownloadedAt)
          }
          _tmpDownloadedAt = __converters().fromTimestamp(_tmp_5)
          val _tmpDownloadState: Int
          _tmpDownloadState = _stmt.getLong(_columnIndexOfDownloadState).toInt()
          _item = PlaylistEntity(_tmpId,_tmpAuthor,_tmpDescription,_tmpDuration,_tmpDurationSeconds,_tmpPrivacy,_tmpThumbnails,_tmpTitle,_tmpTrackCount,_tmpTracks,_tmpYear,_tmpLiked,_tmpInLibrary,_tmpFavoriteAt,_tmpDownloadedAt,_tmpDownloadState)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getAllLocalPlaylists(limit: Int, offset: Int): List<LocalPlaylistEntity> {
    val _sql: String = "SELECT * FROM local_playlist ORDER BY inLibrary DESC LIMIT ? OFFSET ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, limit.toLong())
        _argIndex = 2
        _stmt.bindLong(_argIndex, offset.toLong())
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfThumbnail: Int = getColumnIndexOrThrow(_stmt, "thumbnail")
        val _columnIndexOfInLibrary: Int = getColumnIndexOrThrow(_stmt, "inLibrary")
        val _columnIndexOfDownloadedAt: Int = getColumnIndexOrThrow(_stmt, "downloadedAt")
        val _columnIndexOfDownloadState: Int = getColumnIndexOrThrow(_stmt, "downloadState")
        val _columnIndexOfYoutubePlaylistId: Int = getColumnIndexOrThrow(_stmt, "youtubePlaylistId")
        val _columnIndexOfSyncState: Int = getColumnIndexOrThrow(_stmt, "youtube_sync_state")
        val _columnIndexOfTracks: Int = getColumnIndexOrThrow(_stmt, "tracks")
        val _result: MutableList<LocalPlaylistEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: LocalPlaylistEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpThumbnail: String?
          if (_stmt.isNull(_columnIndexOfThumbnail)) {
            _tmpThumbnail = null
          } else {
            _tmpThumbnail = _stmt.getText(_columnIndexOfThumbnail)
          }
          val _tmpInLibrary: LocalDateTime
          val _tmp: Long?
          if (_stmt.isNull(_columnIndexOfInLibrary)) {
            _tmp = null
          } else {
            _tmp = _stmt.getLong(_columnIndexOfInLibrary)
          }
          val _tmp_1: LocalDateTime? = __converters().fromTimestamp(_tmp)
          if (_tmp_1 == null) {
            error("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.")
          } else {
            _tmpInLibrary = _tmp_1
          }
          val _tmpDownloadedAt: LocalDateTime?
          val _tmp_2: Long?
          if (_stmt.isNull(_columnIndexOfDownloadedAt)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getLong(_columnIndexOfDownloadedAt)
          }
          _tmpDownloadedAt = __converters().fromTimestamp(_tmp_2)
          val _tmpDownloadState: Int
          _tmpDownloadState = _stmt.getLong(_columnIndexOfDownloadState).toInt()
          val _tmpYoutubePlaylistId: String?
          if (_stmt.isNull(_columnIndexOfYoutubePlaylistId)) {
            _tmpYoutubePlaylistId = null
          } else {
            _tmpYoutubePlaylistId = _stmt.getText(_columnIndexOfYoutubePlaylistId)
          }
          val _tmpSyncState: Int
          _tmpSyncState = _stmt.getLong(_columnIndexOfSyncState).toInt()
          val _tmpTracks: List<String>?
          val _tmp_3: String?
          if (_stmt.isNull(_columnIndexOfTracks)) {
            _tmp_3 = null
          } else {
            _tmp_3 = _stmt.getText(_columnIndexOfTracks)
          }
          _tmpTracks = __converters().fromString(_tmp_3)
          _item = LocalPlaylistEntity(_tmpId,_tmpTitle,_tmpThumbnail,_tmpInLibrary,_tmpDownloadedAt,_tmpDownloadState,_tmpYoutubePlaylistId,_tmpSyncState,_tmpTracks)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getAllDownloadingLocalPlaylists(limit: Int, offset: Int): List<LocalPlaylistEntity> {
    val _sql: String = "SELECT * FROM local_playlist WHERE downloadState = 1 OR downloadState = 2 LIMIT ? OFFSET ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, limit.toLong())
        _argIndex = 2
        _stmt.bindLong(_argIndex, offset.toLong())
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfThumbnail: Int = getColumnIndexOrThrow(_stmt, "thumbnail")
        val _columnIndexOfInLibrary: Int = getColumnIndexOrThrow(_stmt, "inLibrary")
        val _columnIndexOfDownloadedAt: Int = getColumnIndexOrThrow(_stmt, "downloadedAt")
        val _columnIndexOfDownloadState: Int = getColumnIndexOrThrow(_stmt, "downloadState")
        val _columnIndexOfYoutubePlaylistId: Int = getColumnIndexOrThrow(_stmt, "youtubePlaylistId")
        val _columnIndexOfSyncState: Int = getColumnIndexOrThrow(_stmt, "youtube_sync_state")
        val _columnIndexOfTracks: Int = getColumnIndexOrThrow(_stmt, "tracks")
        val _result: MutableList<LocalPlaylistEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: LocalPlaylistEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpThumbnail: String?
          if (_stmt.isNull(_columnIndexOfThumbnail)) {
            _tmpThumbnail = null
          } else {
            _tmpThumbnail = _stmt.getText(_columnIndexOfThumbnail)
          }
          val _tmpInLibrary: LocalDateTime
          val _tmp: Long?
          if (_stmt.isNull(_columnIndexOfInLibrary)) {
            _tmp = null
          } else {
            _tmp = _stmt.getLong(_columnIndexOfInLibrary)
          }
          val _tmp_1: LocalDateTime? = __converters().fromTimestamp(_tmp)
          if (_tmp_1 == null) {
            error("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.")
          } else {
            _tmpInLibrary = _tmp_1
          }
          val _tmpDownloadedAt: LocalDateTime?
          val _tmp_2: Long?
          if (_stmt.isNull(_columnIndexOfDownloadedAt)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getLong(_columnIndexOfDownloadedAt)
          }
          _tmpDownloadedAt = __converters().fromTimestamp(_tmp_2)
          val _tmpDownloadState: Int
          _tmpDownloadState = _stmt.getLong(_columnIndexOfDownloadState).toInt()
          val _tmpYoutubePlaylistId: String?
          if (_stmt.isNull(_columnIndexOfYoutubePlaylistId)) {
            _tmpYoutubePlaylistId = null
          } else {
            _tmpYoutubePlaylistId = _stmt.getText(_columnIndexOfYoutubePlaylistId)
          }
          val _tmpSyncState: Int
          _tmpSyncState = _stmt.getLong(_columnIndexOfSyncState).toInt()
          val _tmpTracks: List<String>?
          val _tmp_3: String?
          if (_stmt.isNull(_columnIndexOfTracks)) {
            _tmp_3 = null
          } else {
            _tmp_3 = _stmt.getText(_columnIndexOfTracks)
          }
          _tmpTracks = __converters().fromString(_tmp_3)
          _item = LocalPlaylistEntity(_tmpId,_tmpTitle,_tmpThumbnail,_tmpInLibrary,_tmpDownloadedAt,_tmpDownloadState,_tmpYoutubePlaylistId,_tmpSyncState,_tmpTracks)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getLocalPlaylist(id: Long): LocalPlaylistEntity? {
    val _sql: String = "SELECT * FROM local_playlist WHERE id = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, id)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfThumbnail: Int = getColumnIndexOrThrow(_stmt, "thumbnail")
        val _columnIndexOfInLibrary: Int = getColumnIndexOrThrow(_stmt, "inLibrary")
        val _columnIndexOfDownloadedAt: Int = getColumnIndexOrThrow(_stmt, "downloadedAt")
        val _columnIndexOfDownloadState: Int = getColumnIndexOrThrow(_stmt, "downloadState")
        val _columnIndexOfYoutubePlaylistId: Int = getColumnIndexOrThrow(_stmt, "youtubePlaylistId")
        val _columnIndexOfSyncState: Int = getColumnIndexOrThrow(_stmt, "youtube_sync_state")
        val _columnIndexOfTracks: Int = getColumnIndexOrThrow(_stmt, "tracks")
        val _result: LocalPlaylistEntity?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpThumbnail: String?
          if (_stmt.isNull(_columnIndexOfThumbnail)) {
            _tmpThumbnail = null
          } else {
            _tmpThumbnail = _stmt.getText(_columnIndexOfThumbnail)
          }
          val _tmpInLibrary: LocalDateTime
          val _tmp: Long?
          if (_stmt.isNull(_columnIndexOfInLibrary)) {
            _tmp = null
          } else {
            _tmp = _stmt.getLong(_columnIndexOfInLibrary)
          }
          val _tmp_1: LocalDateTime? = __converters().fromTimestamp(_tmp)
          if (_tmp_1 == null) {
            error("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.")
          } else {
            _tmpInLibrary = _tmp_1
          }
          val _tmpDownloadedAt: LocalDateTime?
          val _tmp_2: Long?
          if (_stmt.isNull(_columnIndexOfDownloadedAt)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getLong(_columnIndexOfDownloadedAt)
          }
          _tmpDownloadedAt = __converters().fromTimestamp(_tmp_2)
          val _tmpDownloadState: Int
          _tmpDownloadState = _stmt.getLong(_columnIndexOfDownloadState).toInt()
          val _tmpYoutubePlaylistId: String?
          if (_stmt.isNull(_columnIndexOfYoutubePlaylistId)) {
            _tmpYoutubePlaylistId = null
          } else {
            _tmpYoutubePlaylistId = _stmt.getText(_columnIndexOfYoutubePlaylistId)
          }
          val _tmpSyncState: Int
          _tmpSyncState = _stmt.getLong(_columnIndexOfSyncState).toInt()
          val _tmpTracks: List<String>?
          val _tmp_3: String?
          if (_stmt.isNull(_columnIndexOfTracks)) {
            _tmp_3 = null
          } else {
            _tmp_3 = _stmt.getText(_columnIndexOfTracks)
          }
          _tmpTracks = __converters().fromString(_tmp_3)
          _result = LocalPlaylistEntity(_tmpId,_tmpTitle,_tmpThumbnail,_tmpInLibrary,_tmpDownloadedAt,_tmpDownloadState,_tmpYoutubePlaylistId,_tmpSyncState,_tmpTracks)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getDownloadedLocalPlaylists(limit: Int, offset: Int): List<LocalPlaylistEntity> {
    val _sql: String = "SELECT * FROM local_playlist WHERE downloadState = 3 ORDER BY downloadedAt DESC LIMIT ? OFFSET ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, limit.toLong())
        _argIndex = 2
        _stmt.bindLong(_argIndex, offset.toLong())
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfThumbnail: Int = getColumnIndexOrThrow(_stmt, "thumbnail")
        val _columnIndexOfInLibrary: Int = getColumnIndexOrThrow(_stmt, "inLibrary")
        val _columnIndexOfDownloadedAt: Int = getColumnIndexOrThrow(_stmt, "downloadedAt")
        val _columnIndexOfDownloadState: Int = getColumnIndexOrThrow(_stmt, "downloadState")
        val _columnIndexOfYoutubePlaylistId: Int = getColumnIndexOrThrow(_stmt, "youtubePlaylistId")
        val _columnIndexOfSyncState: Int = getColumnIndexOrThrow(_stmt, "youtube_sync_state")
        val _columnIndexOfTracks: Int = getColumnIndexOrThrow(_stmt, "tracks")
        val _result: MutableList<LocalPlaylistEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: LocalPlaylistEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpThumbnail: String?
          if (_stmt.isNull(_columnIndexOfThumbnail)) {
            _tmpThumbnail = null
          } else {
            _tmpThumbnail = _stmt.getText(_columnIndexOfThumbnail)
          }
          val _tmpInLibrary: LocalDateTime
          val _tmp: Long?
          if (_stmt.isNull(_columnIndexOfInLibrary)) {
            _tmp = null
          } else {
            _tmp = _stmt.getLong(_columnIndexOfInLibrary)
          }
          val _tmp_1: LocalDateTime? = __converters().fromTimestamp(_tmp)
          if (_tmp_1 == null) {
            error("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.")
          } else {
            _tmpInLibrary = _tmp_1
          }
          val _tmpDownloadedAt: LocalDateTime?
          val _tmp_2: Long?
          if (_stmt.isNull(_columnIndexOfDownloadedAt)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getLong(_columnIndexOfDownloadedAt)
          }
          _tmpDownloadedAt = __converters().fromTimestamp(_tmp_2)
          val _tmpDownloadState: Int
          _tmpDownloadState = _stmt.getLong(_columnIndexOfDownloadState).toInt()
          val _tmpYoutubePlaylistId: String?
          if (_stmt.isNull(_columnIndexOfYoutubePlaylistId)) {
            _tmpYoutubePlaylistId = null
          } else {
            _tmpYoutubePlaylistId = _stmt.getText(_columnIndexOfYoutubePlaylistId)
          }
          val _tmpSyncState: Int
          _tmpSyncState = _stmt.getLong(_columnIndexOfSyncState).toInt()
          val _tmpTracks: List<String>?
          val _tmp_3: String?
          if (_stmt.isNull(_columnIndexOfTracks)) {
            _tmp_3 = null
          } else {
            _tmp_3 = _stmt.getText(_columnIndexOfTracks)
          }
          _tmpTracks = __converters().fromString(_tmp_3)
          _item = LocalPlaylistEntity(_tmpId,_tmpTitle,_tmpThumbnail,_tmpInLibrary,_tmpDownloadedAt,_tmpDownloadState,_tmpYoutubePlaylistId,_tmpSyncState,_tmpTracks)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getDownloadStateFlowOfLocalPlaylist(id: Long): Flow<Int> {
    val _sql: String = "SELECT downloadState FROM local_playlist WHERE id = ?"
    return createFlow(__db, false, arrayOf("local_playlist")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, id)
        val _result: Int
        if (_stmt.step()) {
          _result = _stmt.getLong(0).toInt()
        } else {
          _result = 0
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getListTracksFlowOfLocalPlaylist(id: Long): Flow<List<String>> {
    val _sql: String = "SELECT tracks FROM local_playlist WHERE id = ?"
    return createFlow(__db, false, arrayOf("local_playlist")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, id)
        val _result: MutableList<String> = mutableListOf()
        while (_stmt.step()) {
          val _item: String
          _item = _stmt.getText(0)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getLyrics(videoId: String): LyricsEntity? {
    val _sql: String = "SELECT * FROM lyrics WHERE videoId = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, videoId)
        val _columnIndexOfVideoId: Int = getColumnIndexOrThrow(_stmt, "videoId")
        val _columnIndexOfError: Int = getColumnIndexOrThrow(_stmt, "error")
        val _columnIndexOfLines: Int = getColumnIndexOrThrow(_stmt, "lines")
        val _columnIndexOfSyncType: Int = getColumnIndexOrThrow(_stmt, "syncType")
        val _result: LyricsEntity?
        if (_stmt.step()) {
          val _tmpVideoId: String
          _tmpVideoId = _stmt.getText(_columnIndexOfVideoId)
          val _tmpError: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfError).toInt()
          _tmpError = _tmp != 0
          val _tmpLines: List<Line>?
          val _tmp_1: String?
          if (_stmt.isNull(_columnIndexOfLines)) {
            _tmp_1 = null
          } else {
            _tmp_1 = _stmt.getText(_columnIndexOfLines)
          }
          _tmpLines = __converters().fromStringToListLine(_tmp_1)
          val _tmpSyncType: String?
          if (_stmt.isNull(_columnIndexOfSyncType)) {
            _tmpSyncType = null
          } else {
            _tmpSyncType = _stmt.getText(_columnIndexOfSyncType)
          }
          _result = LyricsEntity(_tmpVideoId,_tmpError,_tmpLines,_tmpSyncType)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getPreparingSongs(limit: Int, offset: Int): List<SongEntity> {
    val _sql: String = "SELECT * FROM song WHERE downloadState = 1 OR downloadState = 2 ORDER BY downloadedAt ASC LIMIT ? OFFSET ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, limit.toLong())
        _argIndex = 2
        _stmt.bindLong(_argIndex, offset.toLong())
        val _columnIndexOfVideoId: Int = getColumnIndexOrThrow(_stmt, "videoId")
        val _columnIndexOfAlbumId: Int = getColumnIndexOrThrow(_stmt, "albumId")
        val _columnIndexOfAlbumName: Int = getColumnIndexOrThrow(_stmt, "albumName")
        val _columnIndexOfArtistId: Int = getColumnIndexOrThrow(_stmt, "artistId")
        val _columnIndexOfArtistName: Int = getColumnIndexOrThrow(_stmt, "artistName")
        val _columnIndexOfDuration: Int = getColumnIndexOrThrow(_stmt, "duration")
        val _columnIndexOfDurationSeconds: Int = getColumnIndexOrThrow(_stmt, "durationSeconds")
        val _columnIndexOfIsAvailable: Int = getColumnIndexOrThrow(_stmt, "isAvailable")
        val _columnIndexOfIsExplicit: Int = getColumnIndexOrThrow(_stmt, "isExplicit")
        val _columnIndexOfLikeStatus: Int = getColumnIndexOrThrow(_stmt, "likeStatus")
        val _columnIndexOfThumbnails: Int = getColumnIndexOrThrow(_stmt, "thumbnails")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfVideoType: Int = getColumnIndexOrThrow(_stmt, "videoType")
        val _columnIndexOfCategory: Int = getColumnIndexOrThrow(_stmt, "category")
        val _columnIndexOfResultType: Int = getColumnIndexOrThrow(_stmt, "resultType")
        val _columnIndexOfLiked: Int = getColumnIndexOrThrow(_stmt, "liked")
        val _columnIndexOfTotalPlayTime: Int = getColumnIndexOrThrow(_stmt, "totalPlayTime")
        val _columnIndexOfDownloadState: Int = getColumnIndexOrThrow(_stmt, "downloadState")
        val _columnIndexOfFavoriteAt: Int = getColumnIndexOrThrow(_stmt, "favoriteAt")
        val _columnIndexOfDownloadedAt: Int = getColumnIndexOrThrow(_stmt, "downloadedAt")
        val _columnIndexOfInLibrary: Int = getColumnIndexOrThrow(_stmt, "inLibrary")
        val _columnIndexOfCanvasUrl: Int = getColumnIndexOrThrow(_stmt, "canvasUrl")
        val _columnIndexOfCanvasThumbUrl: Int = getColumnIndexOrThrow(_stmt, "canvasThumbUrl")
        val _result: MutableList<SongEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: SongEntity
          val _tmpVideoId: String
          _tmpVideoId = _stmt.getText(_columnIndexOfVideoId)
          val _tmpAlbumId: String?
          if (_stmt.isNull(_columnIndexOfAlbumId)) {
            _tmpAlbumId = null
          } else {
            _tmpAlbumId = _stmt.getText(_columnIndexOfAlbumId)
          }
          val _tmpAlbumName: String?
          if (_stmt.isNull(_columnIndexOfAlbumName)) {
            _tmpAlbumName = null
          } else {
            _tmpAlbumName = _stmt.getText(_columnIndexOfAlbumName)
          }
          val _tmpArtistId: List<String>?
          val _tmp: String?
          if (_stmt.isNull(_columnIndexOfArtistId)) {
            _tmp = null
          } else {
            _tmp = _stmt.getText(_columnIndexOfArtistId)
          }
          _tmpArtistId = __converters().fromString(_tmp)
          val _tmpArtistName: List<String>?
          val _tmp_1: String?
          if (_stmt.isNull(_columnIndexOfArtistName)) {
            _tmp_1 = null
          } else {
            _tmp_1 = _stmt.getText(_columnIndexOfArtistName)
          }
          _tmpArtistName = __converters().fromString(_tmp_1)
          val _tmpDuration: String
          _tmpDuration = _stmt.getText(_columnIndexOfDuration)
          val _tmpDurationSeconds: Int
          _tmpDurationSeconds = _stmt.getLong(_columnIndexOfDurationSeconds).toInt()
          val _tmpIsAvailable: Boolean
          val _tmp_2: Int
          _tmp_2 = _stmt.getLong(_columnIndexOfIsAvailable).toInt()
          _tmpIsAvailable = _tmp_2 != 0
          val _tmpIsExplicit: Boolean
          val _tmp_3: Int
          _tmp_3 = _stmt.getLong(_columnIndexOfIsExplicit).toInt()
          _tmpIsExplicit = _tmp_3 != 0
          val _tmpLikeStatus: String
          _tmpLikeStatus = _stmt.getText(_columnIndexOfLikeStatus)
          val _tmpThumbnails: String?
          if (_stmt.isNull(_columnIndexOfThumbnails)) {
            _tmpThumbnails = null
          } else {
            _tmpThumbnails = _stmt.getText(_columnIndexOfThumbnails)
          }
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpVideoType: String
          _tmpVideoType = _stmt.getText(_columnIndexOfVideoType)
          val _tmpCategory: String?
          if (_stmt.isNull(_columnIndexOfCategory)) {
            _tmpCategory = null
          } else {
            _tmpCategory = _stmt.getText(_columnIndexOfCategory)
          }
          val _tmpResultType: String?
          if (_stmt.isNull(_columnIndexOfResultType)) {
            _tmpResultType = null
          } else {
            _tmpResultType = _stmt.getText(_columnIndexOfResultType)
          }
          val _tmpLiked: Boolean
          val _tmp_4: Int
          _tmp_4 = _stmt.getLong(_columnIndexOfLiked).toInt()
          _tmpLiked = _tmp_4 != 0
          val _tmpTotalPlayTime: Long
          _tmpTotalPlayTime = _stmt.getLong(_columnIndexOfTotalPlayTime)
          val _tmpDownloadState: Int
          _tmpDownloadState = _stmt.getLong(_columnIndexOfDownloadState).toInt()
          val _tmpFavoriteAt: LocalDateTime?
          val _tmp_5: Long?
          if (_stmt.isNull(_columnIndexOfFavoriteAt)) {
            _tmp_5 = null
          } else {
            _tmp_5 = _stmt.getLong(_columnIndexOfFavoriteAt)
          }
          _tmpFavoriteAt = __converters().fromTimestamp(_tmp_5)
          val _tmpDownloadedAt: LocalDateTime?
          val _tmp_6: Long?
          if (_stmt.isNull(_columnIndexOfDownloadedAt)) {
            _tmp_6 = null
          } else {
            _tmp_6 = _stmt.getLong(_columnIndexOfDownloadedAt)
          }
          _tmpDownloadedAt = __converters().fromTimestamp(_tmp_6)
          val _tmpInLibrary: LocalDateTime
          val _tmp_7: Long?
          if (_stmt.isNull(_columnIndexOfInLibrary)) {
            _tmp_7 = null
          } else {
            _tmp_7 = _stmt.getLong(_columnIndexOfInLibrary)
          }
          val _tmp_8: LocalDateTime? = __converters().fromTimestamp(_tmp_7)
          if (_tmp_8 == null) {
            error("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.")
          } else {
            _tmpInLibrary = _tmp_8
          }
          val _tmpCanvasUrl: String?
          if (_stmt.isNull(_columnIndexOfCanvasUrl)) {
            _tmpCanvasUrl = null
          } else {
            _tmpCanvasUrl = _stmt.getText(_columnIndexOfCanvasUrl)
          }
          val _tmpCanvasThumbUrl: String?
          if (_stmt.isNull(_columnIndexOfCanvasThumbUrl)) {
            _tmpCanvasThumbUrl = null
          } else {
            _tmpCanvasThumbUrl = _stmt.getText(_columnIndexOfCanvasThumbUrl)
          }
          _item = SongEntity(_tmpVideoId,_tmpAlbumId,_tmpAlbumName,_tmpArtistId,_tmpArtistName,_tmpDuration,_tmpDurationSeconds,_tmpIsAvailable,_tmpIsExplicit,_tmpLikeStatus,_tmpThumbnails,_tmpTitle,_tmpVideoType,_tmpCategory,_tmpResultType,_tmpLiked,_tmpTotalPlayTime,_tmpDownloadState,_tmpFavoriteAt,_tmpDownloadedAt,_tmpInLibrary,_tmpCanvasUrl,_tmpCanvasThumbUrl)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getNewFormat(videoId: String): NewFormatEntity? {
    val _sql: String = "SELECT * FROM new_format WHERE videoId = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, videoId)
        val _columnIndexOfVideoId: Int = getColumnIndexOrThrow(_stmt, "videoId")
        val _columnIndexOfItag: Int = getColumnIndexOrThrow(_stmt, "itag")
        val _columnIndexOfMimeType: Int = getColumnIndexOrThrow(_stmt, "mimeType")
        val _columnIndexOfCodecs: Int = getColumnIndexOrThrow(_stmt, "codecs")
        val _columnIndexOfBitrate: Int = getColumnIndexOrThrow(_stmt, "bitrate")
        val _columnIndexOfSampleRate: Int = getColumnIndexOrThrow(_stmt, "sampleRate")
        val _columnIndexOfContentLength: Int = getColumnIndexOrThrow(_stmt, "contentLength")
        val _columnIndexOfLoudnessDb: Int = getColumnIndexOrThrow(_stmt, "loudnessDb")
        val _columnIndexOfLengthSeconds: Int = getColumnIndexOrThrow(_stmt, "lengthSeconds")
        val _columnIndexOfPlaybackTrackingVideostatsPlaybackUrl: Int = getColumnIndexOrThrow(_stmt, "playbackTrackingVideostatsPlaybackUrl")
        val _columnIndexOfPlaybackTrackingAtrUrl: Int = getColumnIndexOrThrow(_stmt, "playbackTrackingAtrUrl")
        val _columnIndexOfPlaybackTrackingVideostatsWatchtimeUrl: Int = getColumnIndexOrThrow(_stmt, "playbackTrackingVideostatsWatchtimeUrl")
        val _columnIndexOfExpiredTime: Int = getColumnIndexOrThrow(_stmt, "expired_time")
        val _columnIndexOfCpn: Int = getColumnIndexOrThrow(_stmt, "cpn")
        val _columnIndexOfAudioUrl: Int = getColumnIndexOrThrow(_stmt, "audioUrl")
        val _columnIndexOfVideoUrl: Int = getColumnIndexOrThrow(_stmt, "videoUrl")
        val _columnIndexOfBpm: Int = getColumnIndexOrThrow(_stmt, "bpm")
        val _columnIndexOfMusicKey: Int = getColumnIndexOrThrow(_stmt, "music_key")
        val _columnIndexOfKeyScale: Int = getColumnIndexOrThrow(_stmt, "keyScale")
        val _result: NewFormatEntity?
        if (_stmt.step()) {
          val _tmpVideoId: String
          _tmpVideoId = _stmt.getText(_columnIndexOfVideoId)
          val _tmpItag: Int
          _tmpItag = _stmt.getLong(_columnIndexOfItag).toInt()
          val _tmpMimeType: String?
          if (_stmt.isNull(_columnIndexOfMimeType)) {
            _tmpMimeType = null
          } else {
            _tmpMimeType = _stmt.getText(_columnIndexOfMimeType)
          }
          val _tmpCodecs: String?
          if (_stmt.isNull(_columnIndexOfCodecs)) {
            _tmpCodecs = null
          } else {
            _tmpCodecs = _stmt.getText(_columnIndexOfCodecs)
          }
          val _tmpBitrate: Int?
          if (_stmt.isNull(_columnIndexOfBitrate)) {
            _tmpBitrate = null
          } else {
            _tmpBitrate = _stmt.getLong(_columnIndexOfBitrate).toInt()
          }
          val _tmpSampleRate: Int?
          if (_stmt.isNull(_columnIndexOfSampleRate)) {
            _tmpSampleRate = null
          } else {
            _tmpSampleRate = _stmt.getLong(_columnIndexOfSampleRate).toInt()
          }
          val _tmpContentLength: Long?
          if (_stmt.isNull(_columnIndexOfContentLength)) {
            _tmpContentLength = null
          } else {
            _tmpContentLength = _stmt.getLong(_columnIndexOfContentLength)
          }
          val _tmpLoudnessDb: Float?
          if (_stmt.isNull(_columnIndexOfLoudnessDb)) {
            _tmpLoudnessDb = null
          } else {
            _tmpLoudnessDb = _stmt.getDouble(_columnIndexOfLoudnessDb).toFloat()
          }
          val _tmpLengthSeconds: Int?
          if (_stmt.isNull(_columnIndexOfLengthSeconds)) {
            _tmpLengthSeconds = null
          } else {
            _tmpLengthSeconds = _stmt.getLong(_columnIndexOfLengthSeconds).toInt()
          }
          val _tmpPlaybackTrackingVideostatsPlaybackUrl: String?
          if (_stmt.isNull(_columnIndexOfPlaybackTrackingVideostatsPlaybackUrl)) {
            _tmpPlaybackTrackingVideostatsPlaybackUrl = null
          } else {
            _tmpPlaybackTrackingVideostatsPlaybackUrl = _stmt.getText(_columnIndexOfPlaybackTrackingVideostatsPlaybackUrl)
          }
          val _tmpPlaybackTrackingAtrUrl: String?
          if (_stmt.isNull(_columnIndexOfPlaybackTrackingAtrUrl)) {
            _tmpPlaybackTrackingAtrUrl = null
          } else {
            _tmpPlaybackTrackingAtrUrl = _stmt.getText(_columnIndexOfPlaybackTrackingAtrUrl)
          }
          val _tmpPlaybackTrackingVideostatsWatchtimeUrl: String?
          if (_stmt.isNull(_columnIndexOfPlaybackTrackingVideostatsWatchtimeUrl)) {
            _tmpPlaybackTrackingVideostatsWatchtimeUrl = null
          } else {
            _tmpPlaybackTrackingVideostatsWatchtimeUrl = _stmt.getText(_columnIndexOfPlaybackTrackingVideostatsWatchtimeUrl)
          }
          val _tmpExpiredTime: LocalDateTime
          val _tmp: Long?
          if (_stmt.isNull(_columnIndexOfExpiredTime)) {
            _tmp = null
          } else {
            _tmp = _stmt.getLong(_columnIndexOfExpiredTime)
          }
          val _tmp_1: LocalDateTime? = __converters().fromTimestamp(_tmp)
          if (_tmp_1 == null) {
            error("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.")
          } else {
            _tmpExpiredTime = _tmp_1
          }
          val _tmpCpn: String?
          if (_stmt.isNull(_columnIndexOfCpn)) {
            _tmpCpn = null
          } else {
            _tmpCpn = _stmt.getText(_columnIndexOfCpn)
          }
          val _tmpAudioUrl: String?
          if (_stmt.isNull(_columnIndexOfAudioUrl)) {
            _tmpAudioUrl = null
          } else {
            _tmpAudioUrl = _stmt.getText(_columnIndexOfAudioUrl)
          }
          val _tmpVideoUrl: String?
          if (_stmt.isNull(_columnIndexOfVideoUrl)) {
            _tmpVideoUrl = null
          } else {
            _tmpVideoUrl = _stmt.getText(_columnIndexOfVideoUrl)
          }
          val _tmpBpm: Int?
          if (_stmt.isNull(_columnIndexOfBpm)) {
            _tmpBpm = null
          } else {
            _tmpBpm = _stmt.getLong(_columnIndexOfBpm).toInt()
          }
          val _tmpMusicKey: String?
          if (_stmt.isNull(_columnIndexOfMusicKey)) {
            _tmpMusicKey = null
          } else {
            _tmpMusicKey = _stmt.getText(_columnIndexOfMusicKey)
          }
          val _tmpKeyScale: String?
          if (_stmt.isNull(_columnIndexOfKeyScale)) {
            _tmpKeyScale = null
          } else {
            _tmpKeyScale = _stmt.getText(_columnIndexOfKeyScale)
          }
          _result = NewFormatEntity(_tmpVideoId,_tmpItag,_tmpMimeType,_tmpCodecs,_tmpBitrate,_tmpSampleRate,_tmpContentLength,_tmpLoudnessDb,_tmpLengthSeconds,_tmpPlaybackTrackingVideostatsPlaybackUrl,_tmpPlaybackTrackingAtrUrl,_tmpPlaybackTrackingVideostatsWatchtimeUrl,_tmpExpiredTime,_tmpCpn,_tmpAudioUrl,_tmpVideoUrl,_tmpBpm,_tmpMusicKey,_tmpKeyScale)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getNewFormatAsFlow(videoId: String): Flow<NewFormatEntity?> {
    val _sql: String = "SELECT * FROM new_format WHERE videoId = ?"
    return createFlow(__db, false, arrayOf("new_format")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, videoId)
        val _columnIndexOfVideoId: Int = getColumnIndexOrThrow(_stmt, "videoId")
        val _columnIndexOfItag: Int = getColumnIndexOrThrow(_stmt, "itag")
        val _columnIndexOfMimeType: Int = getColumnIndexOrThrow(_stmt, "mimeType")
        val _columnIndexOfCodecs: Int = getColumnIndexOrThrow(_stmt, "codecs")
        val _columnIndexOfBitrate: Int = getColumnIndexOrThrow(_stmt, "bitrate")
        val _columnIndexOfSampleRate: Int = getColumnIndexOrThrow(_stmt, "sampleRate")
        val _columnIndexOfContentLength: Int = getColumnIndexOrThrow(_stmt, "contentLength")
        val _columnIndexOfLoudnessDb: Int = getColumnIndexOrThrow(_stmt, "loudnessDb")
        val _columnIndexOfLengthSeconds: Int = getColumnIndexOrThrow(_stmt, "lengthSeconds")
        val _columnIndexOfPlaybackTrackingVideostatsPlaybackUrl: Int = getColumnIndexOrThrow(_stmt, "playbackTrackingVideostatsPlaybackUrl")
        val _columnIndexOfPlaybackTrackingAtrUrl: Int = getColumnIndexOrThrow(_stmt, "playbackTrackingAtrUrl")
        val _columnIndexOfPlaybackTrackingVideostatsWatchtimeUrl: Int = getColumnIndexOrThrow(_stmt, "playbackTrackingVideostatsWatchtimeUrl")
        val _columnIndexOfExpiredTime: Int = getColumnIndexOrThrow(_stmt, "expired_time")
        val _columnIndexOfCpn: Int = getColumnIndexOrThrow(_stmt, "cpn")
        val _columnIndexOfAudioUrl: Int = getColumnIndexOrThrow(_stmt, "audioUrl")
        val _columnIndexOfVideoUrl: Int = getColumnIndexOrThrow(_stmt, "videoUrl")
        val _columnIndexOfBpm: Int = getColumnIndexOrThrow(_stmt, "bpm")
        val _columnIndexOfMusicKey: Int = getColumnIndexOrThrow(_stmt, "music_key")
        val _columnIndexOfKeyScale: Int = getColumnIndexOrThrow(_stmt, "keyScale")
        val _result: NewFormatEntity?
        if (_stmt.step()) {
          val _tmpVideoId: String
          _tmpVideoId = _stmt.getText(_columnIndexOfVideoId)
          val _tmpItag: Int
          _tmpItag = _stmt.getLong(_columnIndexOfItag).toInt()
          val _tmpMimeType: String?
          if (_stmt.isNull(_columnIndexOfMimeType)) {
            _tmpMimeType = null
          } else {
            _tmpMimeType = _stmt.getText(_columnIndexOfMimeType)
          }
          val _tmpCodecs: String?
          if (_stmt.isNull(_columnIndexOfCodecs)) {
            _tmpCodecs = null
          } else {
            _tmpCodecs = _stmt.getText(_columnIndexOfCodecs)
          }
          val _tmpBitrate: Int?
          if (_stmt.isNull(_columnIndexOfBitrate)) {
            _tmpBitrate = null
          } else {
            _tmpBitrate = _stmt.getLong(_columnIndexOfBitrate).toInt()
          }
          val _tmpSampleRate: Int?
          if (_stmt.isNull(_columnIndexOfSampleRate)) {
            _tmpSampleRate = null
          } else {
            _tmpSampleRate = _stmt.getLong(_columnIndexOfSampleRate).toInt()
          }
          val _tmpContentLength: Long?
          if (_stmt.isNull(_columnIndexOfContentLength)) {
            _tmpContentLength = null
          } else {
            _tmpContentLength = _stmt.getLong(_columnIndexOfContentLength)
          }
          val _tmpLoudnessDb: Float?
          if (_stmt.isNull(_columnIndexOfLoudnessDb)) {
            _tmpLoudnessDb = null
          } else {
            _tmpLoudnessDb = _stmt.getDouble(_columnIndexOfLoudnessDb).toFloat()
          }
          val _tmpLengthSeconds: Int?
          if (_stmt.isNull(_columnIndexOfLengthSeconds)) {
            _tmpLengthSeconds = null
          } else {
            _tmpLengthSeconds = _stmt.getLong(_columnIndexOfLengthSeconds).toInt()
          }
          val _tmpPlaybackTrackingVideostatsPlaybackUrl: String?
          if (_stmt.isNull(_columnIndexOfPlaybackTrackingVideostatsPlaybackUrl)) {
            _tmpPlaybackTrackingVideostatsPlaybackUrl = null
          } else {
            _tmpPlaybackTrackingVideostatsPlaybackUrl = _stmt.getText(_columnIndexOfPlaybackTrackingVideostatsPlaybackUrl)
          }
          val _tmpPlaybackTrackingAtrUrl: String?
          if (_stmt.isNull(_columnIndexOfPlaybackTrackingAtrUrl)) {
            _tmpPlaybackTrackingAtrUrl = null
          } else {
            _tmpPlaybackTrackingAtrUrl = _stmt.getText(_columnIndexOfPlaybackTrackingAtrUrl)
          }
          val _tmpPlaybackTrackingVideostatsWatchtimeUrl: String?
          if (_stmt.isNull(_columnIndexOfPlaybackTrackingVideostatsWatchtimeUrl)) {
            _tmpPlaybackTrackingVideostatsWatchtimeUrl = null
          } else {
            _tmpPlaybackTrackingVideostatsWatchtimeUrl = _stmt.getText(_columnIndexOfPlaybackTrackingVideostatsWatchtimeUrl)
          }
          val _tmpExpiredTime: LocalDateTime
          val _tmp: Long?
          if (_stmt.isNull(_columnIndexOfExpiredTime)) {
            _tmp = null
          } else {
            _tmp = _stmt.getLong(_columnIndexOfExpiredTime)
          }
          val _tmp_1: LocalDateTime? = __converters().fromTimestamp(_tmp)
          if (_tmp_1 == null) {
            error("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.")
          } else {
            _tmpExpiredTime = _tmp_1
          }
          val _tmpCpn: String?
          if (_stmt.isNull(_columnIndexOfCpn)) {
            _tmpCpn = null
          } else {
            _tmpCpn = _stmt.getText(_columnIndexOfCpn)
          }
          val _tmpAudioUrl: String?
          if (_stmt.isNull(_columnIndexOfAudioUrl)) {
            _tmpAudioUrl = null
          } else {
            _tmpAudioUrl = _stmt.getText(_columnIndexOfAudioUrl)
          }
          val _tmpVideoUrl: String?
          if (_stmt.isNull(_columnIndexOfVideoUrl)) {
            _tmpVideoUrl = null
          } else {
            _tmpVideoUrl = _stmt.getText(_columnIndexOfVideoUrl)
          }
          val _tmpBpm: Int?
          if (_stmt.isNull(_columnIndexOfBpm)) {
            _tmpBpm = null
          } else {
            _tmpBpm = _stmt.getLong(_columnIndexOfBpm).toInt()
          }
          val _tmpMusicKey: String?
          if (_stmt.isNull(_columnIndexOfMusicKey)) {
            _tmpMusicKey = null
          } else {
            _tmpMusicKey = _stmt.getText(_columnIndexOfMusicKey)
          }
          val _tmpKeyScale: String?
          if (_stmt.isNull(_columnIndexOfKeyScale)) {
            _tmpKeyScale = null
          } else {
            _tmpKeyScale = _stmt.getText(_columnIndexOfKeyScale)
          }
          _result = NewFormatEntity(_tmpVideoId,_tmpItag,_tmpMimeType,_tmpCodecs,_tmpBitrate,_tmpSampleRate,_tmpContentLength,_tmpLoudnessDb,_tmpLengthSeconds,_tmpPlaybackTrackingVideostatsPlaybackUrl,_tmpPlaybackTrackingAtrUrl,_tmpPlaybackTrackingVideostatsWatchtimeUrl,_tmpExpiredTime,_tmpCpn,_tmpAudioUrl,_tmpVideoUrl,_tmpBpm,_tmpMusicKey,_tmpKeyScale)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getSongInfo(videoId: String): SongInfoEntity? {
    val _sql: String = "SELECT * FROM song_info WHERE videoId = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, videoId)
        val _columnIndexOfVideoId: Int = getColumnIndexOrThrow(_stmt, "videoId")
        val _columnIndexOfAuthor: Int = getColumnIndexOrThrow(_stmt, "author")
        val _columnIndexOfAuthorId: Int = getColumnIndexOrThrow(_stmt, "authorId")
        val _columnIndexOfAuthorThumbnail: Int = getColumnIndexOrThrow(_stmt, "authorThumbnail")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfSubscribers: Int = getColumnIndexOrThrow(_stmt, "subscribers")
        val _columnIndexOfViewCount: Int = getColumnIndexOrThrow(_stmt, "viewCount")
        val _columnIndexOfUploadDate: Int = getColumnIndexOrThrow(_stmt, "uploadDate")
        val _columnIndexOfLike: Int = getColumnIndexOrThrow(_stmt, "like")
        val _columnIndexOfDislike: Int = getColumnIndexOrThrow(_stmt, "dislike")
        val _result: SongInfoEntity?
        if (_stmt.step()) {
          val _tmpVideoId: String
          _tmpVideoId = _stmt.getText(_columnIndexOfVideoId)
          val _tmpAuthor: String?
          if (_stmt.isNull(_columnIndexOfAuthor)) {
            _tmpAuthor = null
          } else {
            _tmpAuthor = _stmt.getText(_columnIndexOfAuthor)
          }
          val _tmpAuthorId: String?
          if (_stmt.isNull(_columnIndexOfAuthorId)) {
            _tmpAuthorId = null
          } else {
            _tmpAuthorId = _stmt.getText(_columnIndexOfAuthorId)
          }
          val _tmpAuthorThumbnail: String?
          if (_stmt.isNull(_columnIndexOfAuthorThumbnail)) {
            _tmpAuthorThumbnail = null
          } else {
            _tmpAuthorThumbnail = _stmt.getText(_columnIndexOfAuthorThumbnail)
          }
          val _tmpDescription: String?
          if (_stmt.isNull(_columnIndexOfDescription)) {
            _tmpDescription = null
          } else {
            _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          }
          val _tmpSubscribers: String?
          if (_stmt.isNull(_columnIndexOfSubscribers)) {
            _tmpSubscribers = null
          } else {
            _tmpSubscribers = _stmt.getText(_columnIndexOfSubscribers)
          }
          val _tmpViewCount: Int?
          if (_stmt.isNull(_columnIndexOfViewCount)) {
            _tmpViewCount = null
          } else {
            _tmpViewCount = _stmt.getLong(_columnIndexOfViewCount).toInt()
          }
          val _tmpUploadDate: String?
          if (_stmt.isNull(_columnIndexOfUploadDate)) {
            _tmpUploadDate = null
          } else {
            _tmpUploadDate = _stmt.getText(_columnIndexOfUploadDate)
          }
          val _tmpLike: Int?
          if (_stmt.isNull(_columnIndexOfLike)) {
            _tmpLike = null
          } else {
            _tmpLike = _stmt.getLong(_columnIndexOfLike).toInt()
          }
          val _tmpDislike: Int?
          if (_stmt.isNull(_columnIndexOfDislike)) {
            _tmpDislike = null
          } else {
            _tmpDislike = _stmt.getLong(_columnIndexOfDislike).toInt()
          }
          _result = SongInfoEntity(_tmpVideoId,_tmpAuthor,_tmpAuthorId,_tmpAuthorThumbnail,_tmpDescription,_tmpSubscribers,_tmpViewCount,_tmpUploadDate,_tmpLike,_tmpDislike)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getQueue(): List<QueueEntity> {
    val _sql: String = "SELECT * FROM queue LIMIT 1 OFFSET 0"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfQueueId: Int = getColumnIndexOrThrow(_stmt, "queueId")
        val _columnIndexOfListTrack: Int = getColumnIndexOrThrow(_stmt, "listTrack")
        val _result: MutableList<QueueEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: QueueEntity
          val _tmpQueueId: Long
          _tmpQueueId = _stmt.getLong(_columnIndexOfQueueId)
          val _tmpListTrack: List<Track>
          val _tmp: String?
          if (_stmt.isNull(_columnIndexOfListTrack)) {
            _tmp = null
          } else {
            _tmp = _stmt.getText(_columnIndexOfListTrack)
          }
          val _tmp_1: List<Track>? = __converters().fromStringToListTrack(_tmp)
          if (_tmp_1 == null) {
            error("Expected NON-NULL 'kotlin.collections.List<com.decibel.music.domain.`data`.model.browse.album.Track>', but it was NULL.")
          } else {
            _tmpListTrack = _tmp_1
          }
          _item = QueueEntity(_tmpQueueId,_tmpListTrack)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getLocalPlaylistByYoutubePlaylistId(youtubePlaylistId: String): LocalPlaylistEntity? {
    val _sql: String = "SELECT * FROM local_playlist WHERE youtubePlaylistId = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, youtubePlaylistId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfThumbnail: Int = getColumnIndexOrThrow(_stmt, "thumbnail")
        val _columnIndexOfInLibrary: Int = getColumnIndexOrThrow(_stmt, "inLibrary")
        val _columnIndexOfDownloadedAt: Int = getColumnIndexOrThrow(_stmt, "downloadedAt")
        val _columnIndexOfDownloadState: Int = getColumnIndexOrThrow(_stmt, "downloadState")
        val _columnIndexOfYoutubePlaylistId: Int = getColumnIndexOrThrow(_stmt, "youtubePlaylistId")
        val _columnIndexOfSyncState: Int = getColumnIndexOrThrow(_stmt, "youtube_sync_state")
        val _columnIndexOfTracks: Int = getColumnIndexOrThrow(_stmt, "tracks")
        val _result: LocalPlaylistEntity?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpThumbnail: String?
          if (_stmt.isNull(_columnIndexOfThumbnail)) {
            _tmpThumbnail = null
          } else {
            _tmpThumbnail = _stmt.getText(_columnIndexOfThumbnail)
          }
          val _tmpInLibrary: LocalDateTime
          val _tmp: Long?
          if (_stmt.isNull(_columnIndexOfInLibrary)) {
            _tmp = null
          } else {
            _tmp = _stmt.getLong(_columnIndexOfInLibrary)
          }
          val _tmp_1: LocalDateTime? = __converters().fromTimestamp(_tmp)
          if (_tmp_1 == null) {
            error("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.")
          } else {
            _tmpInLibrary = _tmp_1
          }
          val _tmpDownloadedAt: LocalDateTime?
          val _tmp_2: Long?
          if (_stmt.isNull(_columnIndexOfDownloadedAt)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getLong(_columnIndexOfDownloadedAt)
          }
          _tmpDownloadedAt = __converters().fromTimestamp(_tmp_2)
          val _tmpDownloadState: Int
          _tmpDownloadState = _stmt.getLong(_columnIndexOfDownloadState).toInt()
          val _tmpYoutubePlaylistId: String?
          if (_stmt.isNull(_columnIndexOfYoutubePlaylistId)) {
            _tmpYoutubePlaylistId = null
          } else {
            _tmpYoutubePlaylistId = _stmt.getText(_columnIndexOfYoutubePlaylistId)
          }
          val _tmpSyncState: Int
          _tmpSyncState = _stmt.getLong(_columnIndexOfSyncState).toInt()
          val _tmpTracks: List<String>?
          val _tmp_3: String?
          if (_stmt.isNull(_columnIndexOfTracks)) {
            _tmp_3 = null
          } else {
            _tmp_3 = _stmt.getText(_columnIndexOfTracks)
          }
          _tmpTracks = __converters().fromString(_tmp_3)
          _result = LocalPlaylistEntity(_tmpId,_tmpTitle,_tmpThumbnail,_tmpInLibrary,_tmpDownloadedAt,_tmpDownloadState,_tmpYoutubePlaylistId,_tmpSyncState,_tmpTracks)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getSetVideoId(videoId: String): SetVideoIdEntity? {
    val _sql: String = "SELECT * FROM set_video_id WHERE videoId = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, videoId)
        val _columnIndexOfVideoId: Int = getColumnIndexOrThrow(_stmt, "videoId")
        val _columnIndexOfSetVideoId: Int = getColumnIndexOrThrow(_stmt, "setVideoId")
        val _columnIndexOfYoutubePlaylistId: Int = getColumnIndexOrThrow(_stmt, "youtubePlaylistId")
        val _result: SetVideoIdEntity?
        if (_stmt.step()) {
          val _tmpVideoId: String
          _tmpVideoId = _stmt.getText(_columnIndexOfVideoId)
          val _tmpSetVideoId: String?
          if (_stmt.isNull(_columnIndexOfSetVideoId)) {
            _tmpSetVideoId = null
          } else {
            _tmpSetVideoId = _stmt.getText(_columnIndexOfSetVideoId)
          }
          val _tmpYoutubePlaylistId: String
          _tmpYoutubePlaylistId = _stmt.getText(_columnIndexOfYoutubePlaylistId)
          _result = SetVideoIdEntity(_tmpVideoId,_tmpSetVideoId,_tmpYoutubePlaylistId)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getPlaylistPairOfSong(videoId: String, localPlaylistId: Long): PairSongLocalPlaylist {
    val _sql: String = "SELECT * FROM pair_song_local_playlist WHERE songId = ? AND playlistId = ? LIMIT 1"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, videoId)
        _argIndex = 2
        _stmt.bindLong(_argIndex, localPlaylistId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfPlaylistId: Int = getColumnIndexOrThrow(_stmt, "playlistId")
        val _columnIndexOfSongId: Int = getColumnIndexOrThrow(_stmt, "songId")
        val _columnIndexOfPosition: Int = getColumnIndexOrThrow(_stmt, "position")
        val _columnIndexOfInPlaylist: Int = getColumnIndexOrThrow(_stmt, "inPlaylist")
        val _result: PairSongLocalPlaylist
        if (_stmt.step()) {
          val _tmpId: Int
          _tmpId = _stmt.getLong(_columnIndexOfId).toInt()
          val _tmpPlaylistId: Long
          _tmpPlaylistId = _stmt.getLong(_columnIndexOfPlaylistId)
          val _tmpSongId: String
          _tmpSongId = _stmt.getText(_columnIndexOfSongId)
          val _tmpPosition: Int
          _tmpPosition = _stmt.getLong(_columnIndexOfPosition).toInt()
          val _tmpInPlaylist: LocalDateTime
          val _tmp: Long?
          if (_stmt.isNull(_columnIndexOfInPlaylist)) {
            _tmp = null
          } else {
            _tmp = _stmt.getLong(_columnIndexOfInPlaylist)
          }
          val _tmp_1: LocalDateTime? = __converters().fromTimestamp(_tmp)
          if (_tmp_1 == null) {
            error("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.")
          } else {
            _tmpInPlaylist = _tmp_1
          }
          _result = PairSongLocalPlaylist(_tmpId,_tmpPlaylistId,_tmpSongId,_tmpPosition,_tmpInPlaylist)
        } else {
          error("The query result was empty, but expected a single row to return a NON-NULL object of type 'com.decibel.music.domain.`data`.entities.PairSongLocalPlaylist'.")
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getPlaylistPairSong(
    playlistId: Long,
    limit: Int,
    offset: Int,
  ): List<PairSongLocalPlaylist> {
    val _sql: String = "SELECT * FROM pair_song_local_playlist WHERE playlistId = ? ORDER BY position ASC LIMIT ? OFFSET ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, playlistId)
        _argIndex = 2
        _stmt.bindLong(_argIndex, limit.toLong())
        _argIndex = 3
        _stmt.bindLong(_argIndex, offset.toLong())
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfPlaylistId: Int = getColumnIndexOrThrow(_stmt, "playlistId")
        val _columnIndexOfSongId: Int = getColumnIndexOrThrow(_stmt, "songId")
        val _columnIndexOfPosition: Int = getColumnIndexOrThrow(_stmt, "position")
        val _columnIndexOfInPlaylist: Int = getColumnIndexOrThrow(_stmt, "inPlaylist")
        val _result: MutableList<PairSongLocalPlaylist> = mutableListOf()
        while (_stmt.step()) {
          val _item: PairSongLocalPlaylist
          val _tmpId: Int
          _tmpId = _stmt.getLong(_columnIndexOfId).toInt()
          val _tmpPlaylistId: Long
          _tmpPlaylistId = _stmt.getLong(_columnIndexOfPlaylistId)
          val _tmpSongId: String
          _tmpSongId = _stmt.getText(_columnIndexOfSongId)
          val _tmpPosition: Int
          _tmpPosition = _stmt.getLong(_columnIndexOfPosition).toInt()
          val _tmpInPlaylist: LocalDateTime
          val _tmp: Long?
          if (_stmt.isNull(_columnIndexOfInPlaylist)) {
            _tmp = null
          } else {
            _tmp = _stmt.getLong(_columnIndexOfInPlaylist)
          }
          val _tmp_1: LocalDateTime? = __converters().fromTimestamp(_tmp)
          if (_tmp_1 == null) {
            error("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.")
          } else {
            _tmpInPlaylist = _tmp_1
          }
          _item = PairSongLocalPlaylist(_tmpId,_tmpPlaylistId,_tmpSongId,_tmpPosition,_tmpInPlaylist)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getPlaylistPairSongByListPosition(playlistId: Long, positionList: List<Int>): List<PairSongLocalPlaylist> {
    val _stringBuilder: StringBuilder = StringBuilder()
    _stringBuilder.append("SELECT * FROM pair_song_local_playlist WHERE playlistId = ")
    _stringBuilder.append("?")
    _stringBuilder.append(" AND position in (")
    val _inputSize: Int = positionList.size
    appendPlaceholders(_stringBuilder, _inputSize)
    _stringBuilder.append(")")
    val _sql: String = _stringBuilder.toString()
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, playlistId)
        _argIndex = 2
        for (_item: Int in positionList) {
          _stmt.bindLong(_argIndex, _item.toLong())
          _argIndex++
        }
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfPlaylistId: Int = getColumnIndexOrThrow(_stmt, "playlistId")
        val _columnIndexOfSongId: Int = getColumnIndexOrThrow(_stmt, "songId")
        val _columnIndexOfPosition: Int = getColumnIndexOrThrow(_stmt, "position")
        val _columnIndexOfInPlaylist: Int = getColumnIndexOrThrow(_stmt, "inPlaylist")
        val _result: MutableList<PairSongLocalPlaylist> = mutableListOf()
        while (_stmt.step()) {
          val _item_1: PairSongLocalPlaylist
          val _tmpId: Int
          _tmpId = _stmt.getLong(_columnIndexOfId).toInt()
          val _tmpPlaylistId: Long
          _tmpPlaylistId = _stmt.getLong(_columnIndexOfPlaylistId)
          val _tmpSongId: String
          _tmpSongId = _stmt.getText(_columnIndexOfSongId)
          val _tmpPosition: Int
          _tmpPosition = _stmt.getLong(_columnIndexOfPosition).toInt()
          val _tmpInPlaylist: LocalDateTime
          val _tmp: Long?
          if (_stmt.isNull(_columnIndexOfInPlaylist)) {
            _tmp = null
          } else {
            _tmp = _stmt.getLong(_columnIndexOfInPlaylist)
          }
          val _tmp_1: LocalDateTime? = __converters().fromTimestamp(_tmp)
          if (_tmp_1 == null) {
            error("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.")
          } else {
            _tmpInPlaylist = _tmp_1
          }
          _item_1 = PairSongLocalPlaylist(_tmpId,_tmpPlaylistId,_tmpSongId,_tmpPosition,_tmpInPlaylist)
          _result.add(_item_1)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getPlaylistPairSongByOffset(playlistId: Long, offset: Int): List<PairSongLocalPlaylist> {
    val _sql: String = "SELECT * FROM pair_song_local_playlist WHERE playlistId = ? ORDER BY position ASC LIMIT 50 OFFSET ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, playlistId)
        _argIndex = 2
        _stmt.bindLong(_argIndex, offset.toLong())
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfPlaylistId: Int = getColumnIndexOrThrow(_stmt, "playlistId")
        val _columnIndexOfSongId: Int = getColumnIndexOrThrow(_stmt, "songId")
        val _columnIndexOfPosition: Int = getColumnIndexOrThrow(_stmt, "position")
        val _columnIndexOfInPlaylist: Int = getColumnIndexOrThrow(_stmt, "inPlaylist")
        val _result: MutableList<PairSongLocalPlaylist> = mutableListOf()
        while (_stmt.step()) {
          val _item: PairSongLocalPlaylist
          val _tmpId: Int
          _tmpId = _stmt.getLong(_columnIndexOfId).toInt()
          val _tmpPlaylistId: Long
          _tmpPlaylistId = _stmt.getLong(_columnIndexOfPlaylistId)
          val _tmpSongId: String
          _tmpSongId = _stmt.getText(_columnIndexOfSongId)
          val _tmpPosition: Int
          _tmpPosition = _stmt.getLong(_columnIndexOfPosition).toInt()
          val _tmpInPlaylist: LocalDateTime
          val _tmp: Long?
          if (_stmt.isNull(_columnIndexOfInPlaylist)) {
            _tmp = null
          } else {
            _tmp = _stmt.getLong(_columnIndexOfInPlaylist)
          }
          val _tmp_1: LocalDateTime? = __converters().fromTimestamp(_tmp)
          if (_tmp_1 == null) {
            error("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.")
          } else {
            _tmpInPlaylist = _tmp_1
          }
          _item = PairSongLocalPlaylist(_tmpId,_tmpPlaylistId,_tmpSongId,_tmpPosition,_tmpInPlaylist)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getPlaylistPairSongByOlderFirst(playlistId: Long, cutPoint: LocalDateTime): List<PairSongLocalPlaylist> {
    val _sql: String = "SELECT * FROM pair_song_local_playlist WHERE playlistId = ? AND inPlaylist > ? ORDER BY position ASC LIMIT 50"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, playlistId)
        _argIndex = 2
        val _tmp: Long? = __converters().dateToTimestamp(cutPoint)
        if (_tmp == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindLong(_argIndex, _tmp)
        }
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfPlaylistId: Int = getColumnIndexOrThrow(_stmt, "playlistId")
        val _columnIndexOfSongId: Int = getColumnIndexOrThrow(_stmt, "songId")
        val _columnIndexOfPosition: Int = getColumnIndexOrThrow(_stmt, "position")
        val _columnIndexOfInPlaylist: Int = getColumnIndexOrThrow(_stmt, "inPlaylist")
        val _result: MutableList<PairSongLocalPlaylist> = mutableListOf()
        while (_stmt.step()) {
          val _item: PairSongLocalPlaylist
          val _tmpId: Int
          _tmpId = _stmt.getLong(_columnIndexOfId).toInt()
          val _tmpPlaylistId: Long
          _tmpPlaylistId = _stmt.getLong(_columnIndexOfPlaylistId)
          val _tmpSongId: String
          _tmpSongId = _stmt.getText(_columnIndexOfSongId)
          val _tmpPosition: Int
          _tmpPosition = _stmt.getLong(_columnIndexOfPosition).toInt()
          val _tmpInPlaylist: LocalDateTime
          val _tmp_1: Long?
          if (_stmt.isNull(_columnIndexOfInPlaylist)) {
            _tmp_1 = null
          } else {
            _tmp_1 = _stmt.getLong(_columnIndexOfInPlaylist)
          }
          val _tmp_2: LocalDateTime? = __converters().fromTimestamp(_tmp_1)
          if (_tmp_2 == null) {
            error("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.")
          } else {
            _tmpInPlaylist = _tmp_2
          }
          _item = PairSongLocalPlaylist(_tmpId,_tmpPlaylistId,_tmpSongId,_tmpPosition,_tmpInPlaylist)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getPlaylistPairSongByNewerFirst(playlistId: Long, cutPoint: LocalDateTime): List<PairSongLocalPlaylist> {
    val _sql: String = "SELECT * FROM pair_song_local_playlist WHERE playlistId = ? AND inPlaylist < ? ORDER BY position DESC LIMIT 50"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, playlistId)
        _argIndex = 2
        val _tmp: Long? = __converters().dateToTimestamp(cutPoint)
        if (_tmp == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindLong(_argIndex, _tmp)
        }
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfPlaylistId: Int = getColumnIndexOrThrow(_stmt, "playlistId")
        val _columnIndexOfSongId: Int = getColumnIndexOrThrow(_stmt, "songId")
        val _columnIndexOfPosition: Int = getColumnIndexOrThrow(_stmt, "position")
        val _columnIndexOfInPlaylist: Int = getColumnIndexOrThrow(_stmt, "inPlaylist")
        val _result: MutableList<PairSongLocalPlaylist> = mutableListOf()
        while (_stmt.step()) {
          val _item: PairSongLocalPlaylist
          val _tmpId: Int
          _tmpId = _stmt.getLong(_columnIndexOfId).toInt()
          val _tmpPlaylistId: Long
          _tmpPlaylistId = _stmt.getLong(_columnIndexOfPlaylistId)
          val _tmpSongId: String
          _tmpSongId = _stmt.getText(_columnIndexOfSongId)
          val _tmpPosition: Int
          _tmpPosition = _stmt.getLong(_columnIndexOfPosition).toInt()
          val _tmpInPlaylist: LocalDateTime
          val _tmp_1: Long?
          if (_stmt.isNull(_columnIndexOfInPlaylist)) {
            _tmp_1 = null
          } else {
            _tmp_1 = _stmt.getLong(_columnIndexOfInPlaylist)
          }
          val _tmp_2: LocalDateTime? = __converters().fromTimestamp(_tmp_1)
          if (_tmp_2 == null) {
            error("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.")
          } else {
            _tmpInPlaylist = _tmp_2
          }
          _item = PairSongLocalPlaylist(_tmpId,_tmpPlaylistId,_tmpSongId,_tmpPosition,_tmpInPlaylist)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getNewestPlaylistPairSong(playlistId: Long): PairSongLocalPlaylist? {
    val _sql: String = "SELECT * FROM pair_song_local_playlist WHERE playlistId = ? ORDER BY position DESC LIMIT 1"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, playlistId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfPlaylistId: Int = getColumnIndexOrThrow(_stmt, "playlistId")
        val _columnIndexOfSongId: Int = getColumnIndexOrThrow(_stmt, "songId")
        val _columnIndexOfPosition: Int = getColumnIndexOrThrow(_stmt, "position")
        val _columnIndexOfInPlaylist: Int = getColumnIndexOrThrow(_stmt, "inPlaylist")
        val _result: PairSongLocalPlaylist?
        if (_stmt.step()) {
          val _tmpId: Int
          _tmpId = _stmt.getLong(_columnIndexOfId).toInt()
          val _tmpPlaylistId: Long
          _tmpPlaylistId = _stmt.getLong(_columnIndexOfPlaylistId)
          val _tmpSongId: String
          _tmpSongId = _stmt.getText(_columnIndexOfSongId)
          val _tmpPosition: Int
          _tmpPosition = _stmt.getLong(_columnIndexOfPosition).toInt()
          val _tmpInPlaylist: LocalDateTime
          val _tmp: Long?
          if (_stmt.isNull(_columnIndexOfInPlaylist)) {
            _tmp = null
          } else {
            _tmp = _stmt.getLong(_columnIndexOfInPlaylist)
          }
          val _tmp_1: LocalDateTime? = __converters().fromTimestamp(_tmp)
          if (_tmp_1 == null) {
            error("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.")
          } else {
            _tmpInPlaylist = _tmp_1
          }
          _result = PairSongLocalPlaylist(_tmpId,_tmpPlaylistId,_tmpSongId,_tmpPosition,_tmpInPlaylist)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getAllPlaylistPairSongByPosition(playlistId: Long): List<PairSongLocalPlaylist> {
    val _sql: String = "SELECT * FROM pair_song_local_playlist WHERE playlistId = ? ORDER BY position ASC"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, playlistId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfPlaylistId: Int = getColumnIndexOrThrow(_stmt, "playlistId")
        val _columnIndexOfSongId: Int = getColumnIndexOrThrow(_stmt, "songId")
        val _columnIndexOfPosition: Int = getColumnIndexOrThrow(_stmt, "position")
        val _columnIndexOfInPlaylist: Int = getColumnIndexOrThrow(_stmt, "inPlaylist")
        val _result: MutableList<PairSongLocalPlaylist> = mutableListOf()
        while (_stmt.step()) {
          val _item: PairSongLocalPlaylist
          val _tmpId: Int
          _tmpId = _stmt.getLong(_columnIndexOfId).toInt()
          val _tmpPlaylistId: Long
          _tmpPlaylistId = _stmt.getLong(_columnIndexOfPlaylistId)
          val _tmpSongId: String
          _tmpSongId = _stmt.getText(_columnIndexOfSongId)
          val _tmpPosition: Int
          _tmpPosition = _stmt.getLong(_columnIndexOfPosition).toInt()
          val _tmpInPlaylist: LocalDateTime
          val _tmp: Long?
          if (_stmt.isNull(_columnIndexOfInPlaylist)) {
            _tmp = null
          } else {
            _tmp = _stmt.getLong(_columnIndexOfInPlaylist)
          }
          val _tmp_1: LocalDateTime? = __converters().fromTimestamp(_tmp)
          if (_tmp_1 == null) {
            error("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.")
          } else {
            _tmpInPlaylist = _tmp_1
          }
          _item = PairSongLocalPlaylist(_tmpId,_tmpPlaylistId,_tmpSongId,_tmpPosition,_tmpInPlaylist)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getPlaylistPairSongByFromToDesc(
    playlistId: Long,
    from: Int,
    to: Int,
  ): List<PairSongLocalPlaylist> {
    val _sql: String = "SELECT * FROM pair_song_local_playlist WHERE playlistId = ? AND position >= ? AND position < ? ORDER BY position LIMIT 50"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, playlistId)
        _argIndex = 2
        _stmt.bindLong(_argIndex, from.toLong())
        _argIndex = 3
        _stmt.bindLong(_argIndex, to.toLong())
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfPlaylistId: Int = getColumnIndexOrThrow(_stmt, "playlistId")
        val _columnIndexOfSongId: Int = getColumnIndexOrThrow(_stmt, "songId")
        val _columnIndexOfPosition: Int = getColumnIndexOrThrow(_stmt, "position")
        val _columnIndexOfInPlaylist: Int = getColumnIndexOrThrow(_stmt, "inPlaylist")
        val _result: MutableList<PairSongLocalPlaylist> = mutableListOf()
        while (_stmt.step()) {
          val _item: PairSongLocalPlaylist
          val _tmpId: Int
          _tmpId = _stmt.getLong(_columnIndexOfId).toInt()
          val _tmpPlaylistId: Long
          _tmpPlaylistId = _stmt.getLong(_columnIndexOfPlaylistId)
          val _tmpSongId: String
          _tmpSongId = _stmt.getText(_columnIndexOfSongId)
          val _tmpPosition: Int
          _tmpPosition = _stmt.getLong(_columnIndexOfPosition).toInt()
          val _tmpInPlaylist: LocalDateTime
          val _tmp: Long?
          if (_stmt.isNull(_columnIndexOfInPlaylist)) {
            _tmp = null
          } else {
            _tmp = _stmt.getLong(_columnIndexOfInPlaylist)
          }
          val _tmp_1: LocalDateTime? = __converters().fromTimestamp(_tmp)
          if (_tmp_1 == null) {
            error("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.")
          } else {
            _tmpInPlaylist = _tmp_1
          }
          _item = PairSongLocalPlaylist(_tmpId,_tmpPlaylistId,_tmpSongId,_tmpPosition,_tmpInPlaylist)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getPlaylistPairSongByTitle(playlistId: Long, offset: Int): List<PairSongLocalPlaylist> {
    val _sql: String = "SELECT p.* FROM pair_song_local_playlist p JOIN song s ON p.songId = s.videoId WHERE p.playlistId = ? ORDER BY s.title ASC LIMIT 50 OFFSET ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, playlistId)
        _argIndex = 2
        _stmt.bindLong(_argIndex, offset.toLong())
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfPlaylistId: Int = getColumnIndexOrThrow(_stmt, "playlistId")
        val _columnIndexOfSongId: Int = getColumnIndexOrThrow(_stmt, "songId")
        val _columnIndexOfPosition: Int = getColumnIndexOrThrow(_stmt, "position")
        val _columnIndexOfInPlaylist: Int = getColumnIndexOrThrow(_stmt, "inPlaylist")
        val _result: MutableList<PairSongLocalPlaylist> = mutableListOf()
        while (_stmt.step()) {
          val _item: PairSongLocalPlaylist
          val _tmpId: Int
          _tmpId = _stmt.getLong(_columnIndexOfId).toInt()
          val _tmpPlaylistId: Long
          _tmpPlaylistId = _stmt.getLong(_columnIndexOfPlaylistId)
          val _tmpSongId: String
          _tmpSongId = _stmt.getText(_columnIndexOfSongId)
          val _tmpPosition: Int
          _tmpPosition = _stmt.getLong(_columnIndexOfPosition).toInt()
          val _tmpInPlaylist: LocalDateTime
          val _tmp: Long?
          if (_stmt.isNull(_columnIndexOfInPlaylist)) {
            _tmp = null
          } else {
            _tmp = _stmt.getLong(_columnIndexOfInPlaylist)
          }
          val _tmp_1: LocalDateTime? = __converters().fromTimestamp(_tmp)
          if (_tmp_1 == null) {
            error("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.")
          } else {
            _tmpInPlaylist = _tmp_1
          }
          _item = PairSongLocalPlaylist(_tmpId,_tmpPlaylistId,_tmpSongId,_tmpPosition,_tmpInPlaylist)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getAllGoogleAccount(): List<GoogleAccountEntity> {
    val _sql: String = "SELECT * FROM googleaccountentity"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfEmail: Int = getColumnIndexOrThrow(_stmt, "email")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfThumbnailUrl: Int = getColumnIndexOrThrow(_stmt, "thumbnailUrl")
        val _columnIndexOfPageId: Int = getColumnIndexOrThrow(_stmt, "pageId")
        val _columnIndexOfCache: Int = getColumnIndexOrThrow(_stmt, "cache")
        val _columnIndexOfIsUsed: Int = getColumnIndexOrThrow(_stmt, "isUsed")
        val _columnIndexOfNetscapeCookie: Int = getColumnIndexOrThrow(_stmt, "netscapeCookie")
        val _result: MutableList<GoogleAccountEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: GoogleAccountEntity
          val _tmpEmail: String
          _tmpEmail = _stmt.getText(_columnIndexOfEmail)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpThumbnailUrl: String
          _tmpThumbnailUrl = _stmt.getText(_columnIndexOfThumbnailUrl)
          val _tmpPageId: String?
          if (_stmt.isNull(_columnIndexOfPageId)) {
            _tmpPageId = null
          } else {
            _tmpPageId = _stmt.getText(_columnIndexOfPageId)
          }
          val _tmpCache: String?
          if (_stmt.isNull(_columnIndexOfCache)) {
            _tmpCache = null
          } else {
            _tmpCache = _stmt.getText(_columnIndexOfCache)
          }
          val _tmpIsUsed: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsUsed).toInt()
          _tmpIsUsed = _tmp != 0
          val _tmpNetscapeCookie: String?
          if (_stmt.isNull(_columnIndexOfNetscapeCookie)) {
            _tmpNetscapeCookie = null
          } else {
            _tmpNetscapeCookie = _stmt.getText(_columnIndexOfNetscapeCookie)
          }
          _item = GoogleAccountEntity(_tmpEmail,_tmpName,_tmpThumbnailUrl,_tmpPageId,_tmpCache,_tmpIsUsed,_tmpNetscapeCookie)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getUsedGoogleAccount(): GoogleAccountEntity? {
    val _sql: String = "SELECT * FROM googleaccountentity WHERE isUsed = 1"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfEmail: Int = getColumnIndexOrThrow(_stmt, "email")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfThumbnailUrl: Int = getColumnIndexOrThrow(_stmt, "thumbnailUrl")
        val _columnIndexOfPageId: Int = getColumnIndexOrThrow(_stmt, "pageId")
        val _columnIndexOfCache: Int = getColumnIndexOrThrow(_stmt, "cache")
        val _columnIndexOfIsUsed: Int = getColumnIndexOrThrow(_stmt, "isUsed")
        val _columnIndexOfNetscapeCookie: Int = getColumnIndexOrThrow(_stmt, "netscapeCookie")
        val _result: GoogleAccountEntity?
        if (_stmt.step()) {
          val _tmpEmail: String
          _tmpEmail = _stmt.getText(_columnIndexOfEmail)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpThumbnailUrl: String
          _tmpThumbnailUrl = _stmt.getText(_columnIndexOfThumbnailUrl)
          val _tmpPageId: String?
          if (_stmt.isNull(_columnIndexOfPageId)) {
            _tmpPageId = null
          } else {
            _tmpPageId = _stmt.getText(_columnIndexOfPageId)
          }
          val _tmpCache: String?
          if (_stmt.isNull(_columnIndexOfCache)) {
            _tmpCache = null
          } else {
            _tmpCache = _stmt.getText(_columnIndexOfCache)
          }
          val _tmpIsUsed: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsUsed).toInt()
          _tmpIsUsed = _tmp != 0
          val _tmpNetscapeCookie: String?
          if (_stmt.isNull(_columnIndexOfNetscapeCookie)) {
            _tmpNetscapeCookie = null
          } else {
            _tmpNetscapeCookie = _stmt.getText(_columnIndexOfNetscapeCookie)
          }
          _result = GoogleAccountEntity(_tmpEmail,_tmpName,_tmpThumbnailUrl,_tmpPageId,_tmpCache,_tmpIsUsed,_tmpNetscapeCookie)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getFollowedArtistSingleAndAlbum(channelId: String): FollowedArtistSingleAndAlbum? {
    val _sql: String = "SELECT * FROM followed_artist_single_and_album WHERE channelId = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, channelId)
        val _columnIndexOfChannelId: Int = getColumnIndexOrThrow(_stmt, "channelId")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfSingle: Int = getColumnIndexOrThrow(_stmt, "single")
        val _columnIndexOfAlbum: Int = getColumnIndexOrThrow(_stmt, "album")
        val _result: FollowedArtistSingleAndAlbum?
        if (_stmt.step()) {
          val _tmpChannelId: String
          _tmpChannelId = _stmt.getText(_columnIndexOfChannelId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpSingle: List<Map<String, String>>
          val _tmp: String
          _tmp = _stmt.getText(_columnIndexOfSingle)
          _tmpSingle = __converters().fromStringToListMap(_tmp)
          val _tmpAlbum: List<Map<String, String>>
          val _tmp_1: String
          _tmp_1 = _stmt.getText(_columnIndexOfAlbum)
          _tmpAlbum = __converters().fromStringToListMap(_tmp_1)
          _result = FollowedArtistSingleAndAlbum(_tmpChannelId,_tmpName,_tmpSingle,_tmpAlbum)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getAllFollowedArtistSingleAndAlbum(limit: Int, offset: Int): List<FollowedArtistSingleAndAlbum> {
    val _sql: String = "SELECT * FROM followed_artist_single_and_album LIMIT ? OFFSET ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, limit.toLong())
        _argIndex = 2
        _stmt.bindLong(_argIndex, offset.toLong())
        val _columnIndexOfChannelId: Int = getColumnIndexOrThrow(_stmt, "channelId")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfSingle: Int = getColumnIndexOrThrow(_stmt, "single")
        val _columnIndexOfAlbum: Int = getColumnIndexOrThrow(_stmt, "album")
        val _result: MutableList<FollowedArtistSingleAndAlbum> = mutableListOf()
        while (_stmt.step()) {
          val _item: FollowedArtistSingleAndAlbum
          val _tmpChannelId: String
          _tmpChannelId = _stmt.getText(_columnIndexOfChannelId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpSingle: List<Map<String, String>>
          val _tmp: String
          _tmp = _stmt.getText(_columnIndexOfSingle)
          _tmpSingle = __converters().fromStringToListMap(_tmp)
          val _tmpAlbum: List<Map<String, String>>
          val _tmp_1: String
          _tmp_1 = _stmt.getText(_columnIndexOfAlbum)
          _tmpAlbum = __converters().fromStringToListMap(_tmp_1)
          _item = FollowedArtistSingleAndAlbum(_tmpChannelId,_tmpName,_tmpSingle,_tmpAlbum)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getAllNotification(): List<NotificationEntity> {
    val _sql: String = "SELECT * FROM notification ORDER BY time DESC LIMIT 100"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfChannelId: Int = getColumnIndexOrThrow(_stmt, "channelId")
        val _columnIndexOfThumbnail: Int = getColumnIndexOrThrow(_stmt, "thumbnail")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfSingle: Int = getColumnIndexOrThrow(_stmt, "single")
        val _columnIndexOfAlbum: Int = getColumnIndexOrThrow(_stmt, "album")
        val _columnIndexOfTime: Int = getColumnIndexOrThrow(_stmt, "time")
        val _columnIndexOfType: Int = getColumnIndexOrThrow(_stmt, "type")
        val _columnIndexOfLink: Int = getColumnIndexOrThrow(_stmt, "link")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _result: MutableList<NotificationEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: NotificationEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpChannelId: String
          _tmpChannelId = _stmt.getText(_columnIndexOfChannelId)
          val _tmpThumbnail: String?
          if (_stmt.isNull(_columnIndexOfThumbnail)) {
            _tmpThumbnail = null
          } else {
            _tmpThumbnail = _stmt.getText(_columnIndexOfThumbnail)
          }
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpSingle: List<Map<String, String>>
          val _tmp: String
          _tmp = _stmt.getText(_columnIndexOfSingle)
          _tmpSingle = __converters().fromStringToListMap(_tmp)
          val _tmpAlbum: List<Map<String, String>>
          val _tmp_1: String
          _tmp_1 = _stmt.getText(_columnIndexOfAlbum)
          _tmpAlbum = __converters().fromStringToListMap(_tmp_1)
          val _tmpTime: LocalDateTime
          val _tmp_2: Long?
          if (_stmt.isNull(_columnIndexOfTime)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getLong(_columnIndexOfTime)
          }
          val _tmp_3: LocalDateTime? = __converters().fromTimestamp(_tmp_2)
          if (_tmp_3 == null) {
            error("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.")
          } else {
            _tmpTime = _tmp_3
          }
          val _tmpType: String
          _tmpType = _stmt.getText(_columnIndexOfType)
          val _tmpLink: String?
          if (_stmt.isNull(_columnIndexOfLink)) {
            _tmpLink = null
          } else {
            _tmpLink = _stmt.getText(_columnIndexOfLink)
          }
          val _tmpDescription: String?
          if (_stmt.isNull(_columnIndexOfDescription)) {
            _tmpDescription = null
          } else {
            _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          }
          _item = NotificationEntity(_tmpId,_tmpChannelId,_tmpThumbnail,_tmpName,_tmpSingle,_tmpAlbum,_tmpTime,_tmpType,_tmpLink,_tmpDescription)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun countNotificationByLink(link: String): Int {
    val _sql: String = "SELECT COUNT(*) FROM notification WHERE link = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, link)
        val _result: Int
        if (_stmt.step()) {
          val _tmp: Int
          _tmp = _stmt.getLong(0).toInt()
          _result = _tmp
        } else {
          _result = 0
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getTranslatedLyrics(videoId: String, language: String): TranslatedLyricsEntity? {
    val _sql: String = "SELECT * FROM translated_lyrics WHERE videoId = ? AND language = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, videoId)
        _argIndex = 2
        _stmt.bindText(_argIndex, language)
        val _columnIndexOfVideoId: Int = getColumnIndexOrThrow(_stmt, "videoId")
        val _columnIndexOfLanguage: Int = getColumnIndexOrThrow(_stmt, "language")
        val _columnIndexOfError: Int = getColumnIndexOrThrow(_stmt, "error")
        val _columnIndexOfLines: Int = getColumnIndexOrThrow(_stmt, "lines")
        val _columnIndexOfSyncType: Int = getColumnIndexOrThrow(_stmt, "syncType")
        val _result: TranslatedLyricsEntity?
        if (_stmt.step()) {
          val _tmpVideoId: String
          _tmpVideoId = _stmt.getText(_columnIndexOfVideoId)
          val _tmpLanguage: String
          _tmpLanguage = _stmt.getText(_columnIndexOfLanguage)
          val _tmpError: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfError).toInt()
          _tmpError = _tmp != 0
          val _tmpLines: List<Line>?
          val _tmp_1: String?
          if (_stmt.isNull(_columnIndexOfLines)) {
            _tmp_1 = null
          } else {
            _tmp_1 = _stmt.getText(_columnIndexOfLines)
          }
          _tmpLines = __converters().fromStringToListLine(_tmp_1)
          val _tmpSyncType: String?
          if (_stmt.isNull(_columnIndexOfSyncType)) {
            _tmpSyncType = null
          } else {
            _tmpSyncType = _stmt.getText(_columnIndexOfSyncType)
          }
          _result = TranslatedLyricsEntity(_tmpVideoId,_tmpLanguage,_tmpError,_tmpLines,_tmpSyncType)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getPodcastWithEpisodes(podcastId: String): PodcastWithEpisodes? {
    val _sql: String = "SELECT * FROM podcast_table WHERE podcastId = ?"
    return performSuspending(__db, true, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, podcastId)
        val _columnIndexOfPodcastId: Int = getColumnIndexOrThrow(_stmt, "podcastId")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfAuthorId: Int = getColumnIndexOrThrow(_stmt, "authorId")
        val _columnIndexOfAuthorName: Int = getColumnIndexOrThrow(_stmt, "authorName")
        val _columnIndexOfAuthorThumbnail: Int = getColumnIndexOrThrow(_stmt, "authorThumbnail")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfThumbnail: Int = getColumnIndexOrThrow(_stmt, "thumbnail")
        val _columnIndexOfIsFavorite: Int = getColumnIndexOrThrow(_stmt, "isFavorite")
        val _columnIndexOfInLibrary: Int = getColumnIndexOrThrow(_stmt, "inLibrary")
        val _columnIndexOfFavoriteTime: Int = getColumnIndexOrThrow(_stmt, "favoriteTime")
        val _columnIndexOfListEpisodes: Int = getColumnIndexOrThrow(_stmt, "listEpisodes")
        val _collectionEpisodes: MutableMap<String, MutableList<EpisodeEntity>> = mutableMapOf()
        while (_stmt.step()) {
          val _tmpKey: String
          _tmpKey = _stmt.getText(_columnIndexOfPodcastId)
          if (!_collectionEpisodes.containsKey(_tmpKey)) {
            _collectionEpisodes.put(_tmpKey, mutableListOf())
          }
        }
        _stmt.reset()
        __fetchRelationshippodcastEpisodeTableAscomDecibelMusicDomainDataEntitiesEpisodeEntity(_connection, _collectionEpisodes)
        val _result: PodcastWithEpisodes?
        if (_stmt.step()) {
          val _tmpPodcast: PodcastsEntity
          val _tmpPodcastId: String
          _tmpPodcastId = _stmt.getText(_columnIndexOfPodcastId)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpAuthorId: String
          _tmpAuthorId = _stmt.getText(_columnIndexOfAuthorId)
          val _tmpAuthorName: String
          _tmpAuthorName = _stmt.getText(_columnIndexOfAuthorName)
          val _tmpAuthorThumbnail: String?
          if (_stmt.isNull(_columnIndexOfAuthorThumbnail)) {
            _tmpAuthorThumbnail = null
          } else {
            _tmpAuthorThumbnail = _stmt.getText(_columnIndexOfAuthorThumbnail)
          }
          val _tmpDescription: String?
          if (_stmt.isNull(_columnIndexOfDescription)) {
            _tmpDescription = null
          } else {
            _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          }
          val _tmpThumbnail: String?
          if (_stmt.isNull(_columnIndexOfThumbnail)) {
            _tmpThumbnail = null
          } else {
            _tmpThumbnail = _stmt.getText(_columnIndexOfThumbnail)
          }
          val _tmpIsFavorite: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsFavorite).toInt()
          _tmpIsFavorite = _tmp != 0
          val _tmpInLibrary: LocalDateTime
          val _tmp_1: Long?
          if (_stmt.isNull(_columnIndexOfInLibrary)) {
            _tmp_1 = null
          } else {
            _tmp_1 = _stmt.getLong(_columnIndexOfInLibrary)
          }
          val _tmp_2: LocalDateTime? = __converters().fromTimestamp(_tmp_1)
          if (_tmp_2 == null) {
            error("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.")
          } else {
            _tmpInLibrary = _tmp_2
          }
          val _tmpFavoriteTime: LocalDateTime?
          val _tmp_3: Long?
          if (_stmt.isNull(_columnIndexOfFavoriteTime)) {
            _tmp_3 = null
          } else {
            _tmp_3 = _stmt.getLong(_columnIndexOfFavoriteTime)
          }
          _tmpFavoriteTime = __converters().fromTimestamp(_tmp_3)
          val _tmpListEpisodes: List<String>
          val _tmp_4: String?
          if (_stmt.isNull(_columnIndexOfListEpisodes)) {
            _tmp_4 = null
          } else {
            _tmp_4 = _stmt.getText(_columnIndexOfListEpisodes)
          }
          val _tmp_5: List<String>? = __converters().fromString(_tmp_4)
          if (_tmp_5 == null) {
            error("Expected NON-NULL 'kotlin.collections.List<kotlin.String>', but it was NULL.")
          } else {
            _tmpListEpisodes = _tmp_5
          }
          _tmpPodcast = PodcastsEntity(_tmpPodcastId,_tmpTitle,_tmpAuthorId,_tmpAuthorName,_tmpAuthorThumbnail,_tmpDescription,_tmpThumbnail,_tmpIsFavorite,_tmpInLibrary,_tmpFavoriteTime,_tmpListEpisodes)
          val _tmpEpisodesCollection: MutableList<EpisodeEntity>
          val _tmpKey_1: String
          _tmpKey_1 = _stmt.getText(_columnIndexOfPodcastId)
          _tmpEpisodesCollection = _collectionEpisodes.getValue(_tmpKey_1)
          _result = PodcastWithEpisodes(_tmpPodcast,_tmpEpisodesCollection)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getAllPodcastWithEpisodes(limit: Int, offset: Int): List<PodcastWithEpisodes> {
    val _sql: String = "SELECT * FROM podcast_table ORDER BY inLibrary DESC LIMIT ? OFFSET ?"
    return performSuspending(__db, true, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, limit.toLong())
        _argIndex = 2
        _stmt.bindLong(_argIndex, offset.toLong())
        val _columnIndexOfPodcastId: Int = getColumnIndexOrThrow(_stmt, "podcastId")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfAuthorId: Int = getColumnIndexOrThrow(_stmt, "authorId")
        val _columnIndexOfAuthorName: Int = getColumnIndexOrThrow(_stmt, "authorName")
        val _columnIndexOfAuthorThumbnail: Int = getColumnIndexOrThrow(_stmt, "authorThumbnail")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfThumbnail: Int = getColumnIndexOrThrow(_stmt, "thumbnail")
        val _columnIndexOfIsFavorite: Int = getColumnIndexOrThrow(_stmt, "isFavorite")
        val _columnIndexOfInLibrary: Int = getColumnIndexOrThrow(_stmt, "inLibrary")
        val _columnIndexOfFavoriteTime: Int = getColumnIndexOrThrow(_stmt, "favoriteTime")
        val _columnIndexOfListEpisodes: Int = getColumnIndexOrThrow(_stmt, "listEpisodes")
        val _collectionEpisodes: MutableMap<String, MutableList<EpisodeEntity>> = mutableMapOf()
        while (_stmt.step()) {
          val _tmpKey: String
          _tmpKey = _stmt.getText(_columnIndexOfPodcastId)
          if (!_collectionEpisodes.containsKey(_tmpKey)) {
            _collectionEpisodes.put(_tmpKey, mutableListOf())
          }
        }
        _stmt.reset()
        __fetchRelationshippodcastEpisodeTableAscomDecibelMusicDomainDataEntitiesEpisodeEntity(_connection, _collectionEpisodes)
        val _result: MutableList<PodcastWithEpisodes> = mutableListOf()
        while (_stmt.step()) {
          val _item: PodcastWithEpisodes
          val _tmpPodcast: PodcastsEntity
          val _tmpPodcastId: String
          _tmpPodcastId = _stmt.getText(_columnIndexOfPodcastId)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpAuthorId: String
          _tmpAuthorId = _stmt.getText(_columnIndexOfAuthorId)
          val _tmpAuthorName: String
          _tmpAuthorName = _stmt.getText(_columnIndexOfAuthorName)
          val _tmpAuthorThumbnail: String?
          if (_stmt.isNull(_columnIndexOfAuthorThumbnail)) {
            _tmpAuthorThumbnail = null
          } else {
            _tmpAuthorThumbnail = _stmt.getText(_columnIndexOfAuthorThumbnail)
          }
          val _tmpDescription: String?
          if (_stmt.isNull(_columnIndexOfDescription)) {
            _tmpDescription = null
          } else {
            _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          }
          val _tmpThumbnail: String?
          if (_stmt.isNull(_columnIndexOfThumbnail)) {
            _tmpThumbnail = null
          } else {
            _tmpThumbnail = _stmt.getText(_columnIndexOfThumbnail)
          }
          val _tmpIsFavorite: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsFavorite).toInt()
          _tmpIsFavorite = _tmp != 0
          val _tmpInLibrary: LocalDateTime
          val _tmp_1: Long?
          if (_stmt.isNull(_columnIndexOfInLibrary)) {
            _tmp_1 = null
          } else {
            _tmp_1 = _stmt.getLong(_columnIndexOfInLibrary)
          }
          val _tmp_2: LocalDateTime? = __converters().fromTimestamp(_tmp_1)
          if (_tmp_2 == null) {
            error("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.")
          } else {
            _tmpInLibrary = _tmp_2
          }
          val _tmpFavoriteTime: LocalDateTime?
          val _tmp_3: Long?
          if (_stmt.isNull(_columnIndexOfFavoriteTime)) {
            _tmp_3 = null
          } else {
            _tmp_3 = _stmt.getLong(_columnIndexOfFavoriteTime)
          }
          _tmpFavoriteTime = __converters().fromTimestamp(_tmp_3)
          val _tmpListEpisodes: List<String>
          val _tmp_4: String?
          if (_stmt.isNull(_columnIndexOfListEpisodes)) {
            _tmp_4 = null
          } else {
            _tmp_4 = _stmt.getText(_columnIndexOfListEpisodes)
          }
          val _tmp_5: List<String>? = __converters().fromString(_tmp_4)
          if (_tmp_5 == null) {
            error("Expected NON-NULL 'kotlin.collections.List<kotlin.String>', but it was NULL.")
          } else {
            _tmpListEpisodes = _tmp_5
          }
          _tmpPodcast = PodcastsEntity(_tmpPodcastId,_tmpTitle,_tmpAuthorId,_tmpAuthorName,_tmpAuthorThumbnail,_tmpDescription,_tmpThumbnail,_tmpIsFavorite,_tmpInLibrary,_tmpFavoriteTime,_tmpListEpisodes)
          val _tmpEpisodesCollection: MutableList<EpisodeEntity>
          val _tmpKey_1: String
          _tmpKey_1 = _stmt.getText(_columnIndexOfPodcastId)
          _tmpEpisodesCollection = _collectionEpisodes.getValue(_tmpKey_1)
          _item = PodcastWithEpisodes(_tmpPodcast,_tmpEpisodesCollection)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getAllPodcasts(limit: Int): List<PodcastsEntity> {
    val _sql: String = "SELECT * FROM podcast_table ORDER BY inLibrary DESC LIMIT ? OFFSET 0"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, limit.toLong())
        val _columnIndexOfPodcastId: Int = getColumnIndexOrThrow(_stmt, "podcastId")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfAuthorId: Int = getColumnIndexOrThrow(_stmt, "authorId")
        val _columnIndexOfAuthorName: Int = getColumnIndexOrThrow(_stmt, "authorName")
        val _columnIndexOfAuthorThumbnail: Int = getColumnIndexOrThrow(_stmt, "authorThumbnail")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfThumbnail: Int = getColumnIndexOrThrow(_stmt, "thumbnail")
        val _columnIndexOfIsFavorite: Int = getColumnIndexOrThrow(_stmt, "isFavorite")
        val _columnIndexOfInLibrary: Int = getColumnIndexOrThrow(_stmt, "inLibrary")
        val _columnIndexOfFavoriteTime: Int = getColumnIndexOrThrow(_stmt, "favoriteTime")
        val _columnIndexOfListEpisodes: Int = getColumnIndexOrThrow(_stmt, "listEpisodes")
        val _result: MutableList<PodcastsEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: PodcastsEntity
          val _tmpPodcastId: String
          _tmpPodcastId = _stmt.getText(_columnIndexOfPodcastId)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpAuthorId: String
          _tmpAuthorId = _stmt.getText(_columnIndexOfAuthorId)
          val _tmpAuthorName: String
          _tmpAuthorName = _stmt.getText(_columnIndexOfAuthorName)
          val _tmpAuthorThumbnail: String?
          if (_stmt.isNull(_columnIndexOfAuthorThumbnail)) {
            _tmpAuthorThumbnail = null
          } else {
            _tmpAuthorThumbnail = _stmt.getText(_columnIndexOfAuthorThumbnail)
          }
          val _tmpDescription: String?
          if (_stmt.isNull(_columnIndexOfDescription)) {
            _tmpDescription = null
          } else {
            _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          }
          val _tmpThumbnail: String?
          if (_stmt.isNull(_columnIndexOfThumbnail)) {
            _tmpThumbnail = null
          } else {
            _tmpThumbnail = _stmt.getText(_columnIndexOfThumbnail)
          }
          val _tmpIsFavorite: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsFavorite).toInt()
          _tmpIsFavorite = _tmp != 0
          val _tmpInLibrary: LocalDateTime
          val _tmp_1: Long?
          if (_stmt.isNull(_columnIndexOfInLibrary)) {
            _tmp_1 = null
          } else {
            _tmp_1 = _stmt.getLong(_columnIndexOfInLibrary)
          }
          val _tmp_2: LocalDateTime? = __converters().fromTimestamp(_tmp_1)
          if (_tmp_2 == null) {
            error("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.")
          } else {
            _tmpInLibrary = _tmp_2
          }
          val _tmpFavoriteTime: LocalDateTime?
          val _tmp_3: Long?
          if (_stmt.isNull(_columnIndexOfFavoriteTime)) {
            _tmp_3 = null
          } else {
            _tmp_3 = _stmt.getLong(_columnIndexOfFavoriteTime)
          }
          _tmpFavoriteTime = __converters().fromTimestamp(_tmp_3)
          val _tmpListEpisodes: List<String>
          val _tmp_4: String?
          if (_stmt.isNull(_columnIndexOfListEpisodes)) {
            _tmp_4 = null
          } else {
            _tmp_4 = _stmt.getText(_columnIndexOfListEpisodes)
          }
          val _tmp_5: List<String>? = __converters().fromString(_tmp_4)
          if (_tmp_5 == null) {
            error("Expected NON-NULL 'kotlin.collections.List<kotlin.String>', but it was NULL.")
          } else {
            _tmpListEpisodes = _tmp_5
          }
          _item = PodcastsEntity(_tmpPodcastId,_tmpTitle,_tmpAuthorId,_tmpAuthorName,_tmpAuthorThumbnail,_tmpDescription,_tmpThumbnail,_tmpIsFavorite,_tmpInLibrary,_tmpFavoriteTime,_tmpListEpisodes)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getPodcast(podcastId: String): PodcastsEntity? {
    val _sql: String = "SELECT * FROM podcast_table WHERE podcastId = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, podcastId)
        val _columnIndexOfPodcastId: Int = getColumnIndexOrThrow(_stmt, "podcastId")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfAuthorId: Int = getColumnIndexOrThrow(_stmt, "authorId")
        val _columnIndexOfAuthorName: Int = getColumnIndexOrThrow(_stmt, "authorName")
        val _columnIndexOfAuthorThumbnail: Int = getColumnIndexOrThrow(_stmt, "authorThumbnail")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfThumbnail: Int = getColumnIndexOrThrow(_stmt, "thumbnail")
        val _columnIndexOfIsFavorite: Int = getColumnIndexOrThrow(_stmt, "isFavorite")
        val _columnIndexOfInLibrary: Int = getColumnIndexOrThrow(_stmt, "inLibrary")
        val _columnIndexOfFavoriteTime: Int = getColumnIndexOrThrow(_stmt, "favoriteTime")
        val _columnIndexOfListEpisodes: Int = getColumnIndexOrThrow(_stmt, "listEpisodes")
        val _result: PodcastsEntity?
        if (_stmt.step()) {
          val _tmpPodcastId: String
          _tmpPodcastId = _stmt.getText(_columnIndexOfPodcastId)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpAuthorId: String
          _tmpAuthorId = _stmt.getText(_columnIndexOfAuthorId)
          val _tmpAuthorName: String
          _tmpAuthorName = _stmt.getText(_columnIndexOfAuthorName)
          val _tmpAuthorThumbnail: String?
          if (_stmt.isNull(_columnIndexOfAuthorThumbnail)) {
            _tmpAuthorThumbnail = null
          } else {
            _tmpAuthorThumbnail = _stmt.getText(_columnIndexOfAuthorThumbnail)
          }
          val _tmpDescription: String?
          if (_stmt.isNull(_columnIndexOfDescription)) {
            _tmpDescription = null
          } else {
            _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          }
          val _tmpThumbnail: String?
          if (_stmt.isNull(_columnIndexOfThumbnail)) {
            _tmpThumbnail = null
          } else {
            _tmpThumbnail = _stmt.getText(_columnIndexOfThumbnail)
          }
          val _tmpIsFavorite: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsFavorite).toInt()
          _tmpIsFavorite = _tmp != 0
          val _tmpInLibrary: LocalDateTime
          val _tmp_1: Long?
          if (_stmt.isNull(_columnIndexOfInLibrary)) {
            _tmp_1 = null
          } else {
            _tmp_1 = _stmt.getLong(_columnIndexOfInLibrary)
          }
          val _tmp_2: LocalDateTime? = __converters().fromTimestamp(_tmp_1)
          if (_tmp_2 == null) {
            error("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.")
          } else {
            _tmpInLibrary = _tmp_2
          }
          val _tmpFavoriteTime: LocalDateTime?
          val _tmp_3: Long?
          if (_stmt.isNull(_columnIndexOfFavoriteTime)) {
            _tmp_3 = null
          } else {
            _tmp_3 = _stmt.getLong(_columnIndexOfFavoriteTime)
          }
          _tmpFavoriteTime = __converters().fromTimestamp(_tmp_3)
          val _tmpListEpisodes: List<String>
          val _tmp_4: String?
          if (_stmt.isNull(_columnIndexOfListEpisodes)) {
            _tmp_4 = null
          } else {
            _tmp_4 = _stmt.getText(_columnIndexOfListEpisodes)
          }
          val _tmp_5: List<String>? = __converters().fromString(_tmp_4)
          if (_tmp_5 == null) {
            error("Expected NON-NULL 'kotlin.collections.List<kotlin.String>', but it was NULL.")
          } else {
            _tmpListEpisodes = _tmp_5
          }
          _result = PodcastsEntity(_tmpPodcastId,_tmpTitle,_tmpAuthorId,_tmpAuthorName,_tmpAuthorThumbnail,_tmpDescription,_tmpThumbnail,_tmpIsFavorite,_tmpInLibrary,_tmpFavoriteTime,_tmpListEpisodes)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getPodcastEpisodes(
    podcastId: String,
    limit: Int,
    offset: Int,
  ): List<EpisodeEntity> {
    val _sql: String = "SELECT * FROM podcast_episode_table WHERE podcastId = ? LIMIT ? OFFSET ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, podcastId)
        _argIndex = 2
        _stmt.bindLong(_argIndex, limit.toLong())
        _argIndex = 3
        _stmt.bindLong(_argIndex, offset.toLong())
        val _columnIndexOfVideoId: Int = getColumnIndexOrThrow(_stmt, "videoId")
        val _columnIndexOfPodcastId: Int = getColumnIndexOrThrow(_stmt, "podcastId")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfAuthorName: Int = getColumnIndexOrThrow(_stmt, "authorName")
        val _columnIndexOfAuthorId: Int = getColumnIndexOrThrow(_stmt, "authorId")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfCreatedDay: Int = getColumnIndexOrThrow(_stmt, "createdDay")
        val _columnIndexOfDurationString: Int = getColumnIndexOrThrow(_stmt, "durationString")
        val _columnIndexOfThumbnail: Int = getColumnIndexOrThrow(_stmt, "thumbnail")
        val _result: MutableList<EpisodeEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: EpisodeEntity
          val _tmpVideoId: String
          _tmpVideoId = _stmt.getText(_columnIndexOfVideoId)
          val _tmpPodcastId: String
          _tmpPodcastId = _stmt.getText(_columnIndexOfPodcastId)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpAuthorName: String
          _tmpAuthorName = _stmt.getText(_columnIndexOfAuthorName)
          val _tmpAuthorId: String
          _tmpAuthorId = _stmt.getText(_columnIndexOfAuthorId)
          val _tmpDescription: String?
          if (_stmt.isNull(_columnIndexOfDescription)) {
            _tmpDescription = null
          } else {
            _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          }
          val _tmpCreatedDay: String?
          if (_stmt.isNull(_columnIndexOfCreatedDay)) {
            _tmpCreatedDay = null
          } else {
            _tmpCreatedDay = _stmt.getText(_columnIndexOfCreatedDay)
          }
          val _tmpDurationString: String?
          if (_stmt.isNull(_columnIndexOfDurationString)) {
            _tmpDurationString = null
          } else {
            _tmpDurationString = _stmt.getText(_columnIndexOfDurationString)
          }
          val _tmpThumbnail: String?
          if (_stmt.isNull(_columnIndexOfThumbnail)) {
            _tmpThumbnail = null
          } else {
            _tmpThumbnail = _stmt.getText(_columnIndexOfThumbnail)
          }
          _item = EpisodeEntity(_tmpVideoId,_tmpPodcastId,_tmpTitle,_tmpAuthorName,_tmpAuthorId,_tmpDescription,_tmpCreatedDay,_tmpDurationString,_tmpThumbnail)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getEpisode(videoId: String): EpisodeEntity? {
    val _sql: String = "SELECT * FROM podcast_episode_table WHERE videoId = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, videoId)
        val _columnIndexOfVideoId: Int = getColumnIndexOrThrow(_stmt, "videoId")
        val _columnIndexOfPodcastId: Int = getColumnIndexOrThrow(_stmt, "podcastId")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfAuthorName: Int = getColumnIndexOrThrow(_stmt, "authorName")
        val _columnIndexOfAuthorId: Int = getColumnIndexOrThrow(_stmt, "authorId")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfCreatedDay: Int = getColumnIndexOrThrow(_stmt, "createdDay")
        val _columnIndexOfDurationString: Int = getColumnIndexOrThrow(_stmt, "durationString")
        val _columnIndexOfThumbnail: Int = getColumnIndexOrThrow(_stmt, "thumbnail")
        val _result: EpisodeEntity?
        if (_stmt.step()) {
          val _tmpVideoId: String
          _tmpVideoId = _stmt.getText(_columnIndexOfVideoId)
          val _tmpPodcastId: String
          _tmpPodcastId = _stmt.getText(_columnIndexOfPodcastId)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpAuthorName: String
          _tmpAuthorName = _stmt.getText(_columnIndexOfAuthorName)
          val _tmpAuthorId: String
          _tmpAuthorId = _stmt.getText(_columnIndexOfAuthorId)
          val _tmpDescription: String?
          if (_stmt.isNull(_columnIndexOfDescription)) {
            _tmpDescription = null
          } else {
            _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          }
          val _tmpCreatedDay: String?
          if (_stmt.isNull(_columnIndexOfCreatedDay)) {
            _tmpCreatedDay = null
          } else {
            _tmpCreatedDay = _stmt.getText(_columnIndexOfCreatedDay)
          }
          val _tmpDurationString: String?
          if (_stmt.isNull(_columnIndexOfDurationString)) {
            _tmpDurationString = null
          } else {
            _tmpDurationString = _stmt.getText(_columnIndexOfDurationString)
          }
          val _tmpThumbnail: String?
          if (_stmt.isNull(_columnIndexOfThumbnail)) {
            _tmpThumbnail = null
          } else {
            _tmpThumbnail = _stmt.getText(_columnIndexOfThumbnail)
          }
          _result = EpisodeEntity(_tmpVideoId,_tmpPodcastId,_tmpTitle,_tmpAuthorName,_tmpAuthorId,_tmpDescription,_tmpCreatedDay,_tmpDurationString,_tmpThumbnail)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getFavoritePodcasts(limit: Int, offset: Int): List<PodcastsEntity> {
    val _sql: String = "SELECT * FROM podcast_table WHERE isFavorite = 1 ORDER BY favoriteTime DESC LIMIT ? OFFSET ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, limit.toLong())
        _argIndex = 2
        _stmt.bindLong(_argIndex, offset.toLong())
        val _columnIndexOfPodcastId: Int = getColumnIndexOrThrow(_stmt, "podcastId")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfAuthorId: Int = getColumnIndexOrThrow(_stmt, "authorId")
        val _columnIndexOfAuthorName: Int = getColumnIndexOrThrow(_stmt, "authorName")
        val _columnIndexOfAuthorThumbnail: Int = getColumnIndexOrThrow(_stmt, "authorThumbnail")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfThumbnail: Int = getColumnIndexOrThrow(_stmt, "thumbnail")
        val _columnIndexOfIsFavorite: Int = getColumnIndexOrThrow(_stmt, "isFavorite")
        val _columnIndexOfInLibrary: Int = getColumnIndexOrThrow(_stmt, "inLibrary")
        val _columnIndexOfFavoriteTime: Int = getColumnIndexOrThrow(_stmt, "favoriteTime")
        val _columnIndexOfListEpisodes: Int = getColumnIndexOrThrow(_stmt, "listEpisodes")
        val _result: MutableList<PodcastsEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: PodcastsEntity
          val _tmpPodcastId: String
          _tmpPodcastId = _stmt.getText(_columnIndexOfPodcastId)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpAuthorId: String
          _tmpAuthorId = _stmt.getText(_columnIndexOfAuthorId)
          val _tmpAuthorName: String
          _tmpAuthorName = _stmt.getText(_columnIndexOfAuthorName)
          val _tmpAuthorThumbnail: String?
          if (_stmt.isNull(_columnIndexOfAuthorThumbnail)) {
            _tmpAuthorThumbnail = null
          } else {
            _tmpAuthorThumbnail = _stmt.getText(_columnIndexOfAuthorThumbnail)
          }
          val _tmpDescription: String?
          if (_stmt.isNull(_columnIndexOfDescription)) {
            _tmpDescription = null
          } else {
            _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          }
          val _tmpThumbnail: String?
          if (_stmt.isNull(_columnIndexOfThumbnail)) {
            _tmpThumbnail = null
          } else {
            _tmpThumbnail = _stmt.getText(_columnIndexOfThumbnail)
          }
          val _tmpIsFavorite: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsFavorite).toInt()
          _tmpIsFavorite = _tmp != 0
          val _tmpInLibrary: LocalDateTime
          val _tmp_1: Long?
          if (_stmt.isNull(_columnIndexOfInLibrary)) {
            _tmp_1 = null
          } else {
            _tmp_1 = _stmt.getLong(_columnIndexOfInLibrary)
          }
          val _tmp_2: LocalDateTime? = __converters().fromTimestamp(_tmp_1)
          if (_tmp_2 == null) {
            error("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.")
          } else {
            _tmpInLibrary = _tmp_2
          }
          val _tmpFavoriteTime: LocalDateTime?
          val _tmp_3: Long?
          if (_stmt.isNull(_columnIndexOfFavoriteTime)) {
            _tmp_3 = null
          } else {
            _tmp_3 = _stmt.getLong(_columnIndexOfFavoriteTime)
          }
          _tmpFavoriteTime = __converters().fromTimestamp(_tmp_3)
          val _tmpListEpisodes: List<String>
          val _tmp_4: String?
          if (_stmt.isNull(_columnIndexOfListEpisodes)) {
            _tmp_4 = null
          } else {
            _tmp_4 = _stmt.getText(_columnIndexOfListEpisodes)
          }
          val _tmp_5: List<String>? = __converters().fromString(_tmp_4)
          if (_tmp_5 == null) {
            error("Expected NON-NULL 'kotlin.collections.List<kotlin.String>', but it was NULL.")
          } else {
            _tmpListEpisodes = _tmp_5
          }
          _item = PodcastsEntity(_tmpPodcastId,_tmpTitle,_tmpAuthorId,_tmpAuthorName,_tmpAuthorThumbnail,_tmpDescription,_tmpThumbnail,_tmpIsFavorite,_tmpInLibrary,_tmpFavoriteTime,_tmpListEpisodes)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getYourYouTubePlaylistList(emailPageId: String): YourYouTubePlaylistList? {
    val _sql: String = "SELECT * FROM your_youtube_playlist_list WHERE emailPageId = ? LIMIT 1"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, emailPageId)
        val _columnIndexOfEmailPageId: Int = getColumnIndexOrThrow(_stmt, "emailPageId")
        val _columnIndexOfListBrowseIds: Int = getColumnIndexOrThrow(_stmt, "listBrowseIds")
        val _result: YourYouTubePlaylistList?
        if (_stmt.step()) {
          val _tmpEmailPageId: String
          _tmpEmailPageId = _stmt.getText(_columnIndexOfEmailPageId)
          val _tmpListBrowseIds: List<String>
          val _tmp: String?
          if (_stmt.isNull(_columnIndexOfListBrowseIds)) {
            _tmp = null
          } else {
            _tmp = _stmt.getText(_columnIndexOfListBrowseIds)
          }
          val _tmp_1: List<String>? = __converters().fromString(_tmp)
          if (_tmp_1 == null) {
            error("Expected NON-NULL 'kotlin.collections.List<kotlin.String>', but it was NULL.")
          } else {
            _tmpListBrowseIds = _tmp_1
          }
          _result = YourYouTubePlaylistList(_tmpEmailPageId,_tmpListBrowseIds)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getPlaybackEventsByOffset(offset: Int, limit: Int): List<PlaybackEventEntity> {
    val _sql: String = "SELECT * FROM playback_event ORDER BY timestamp DESC LIMIT ? OFFSET ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, limit.toLong())
        _argIndex = 2
        _stmt.bindLong(_argIndex, offset.toLong())
        val _columnIndexOfEventId: Int = getColumnIndexOrThrow(_stmt, "eventId")
        val _columnIndexOfTimestamp: Int = getColumnIndexOrThrow(_stmt, "timestamp")
        val _columnIndexOfVideoId: Int = getColumnIndexOrThrow(_stmt, "videoId")
        val _columnIndexOfAlbumBrowseId: Int = getColumnIndexOrThrow(_stmt, "albumBrowseId")
        val _columnIndexOfDurationSecond: Int = getColumnIndexOrThrow(_stmt, "durationSecond")
        val _columnIndexOfListenedSecond: Int = getColumnIndexOrThrow(_stmt, "listenedSecond")
        val _result: MutableList<PlaybackEventEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: PlaybackEventEntity
          val _tmpEventId: Long
          _tmpEventId = _stmt.getLong(_columnIndexOfEventId)
          val _tmpTimestamp: LocalDateTime
          val _tmp: Long?
          if (_stmt.isNull(_columnIndexOfTimestamp)) {
            _tmp = null
          } else {
            _tmp = _stmt.getLong(_columnIndexOfTimestamp)
          }
          val _tmp_1: LocalDateTime? = __converters().fromTimestamp(_tmp)
          if (_tmp_1 == null) {
            error("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.")
          } else {
            _tmpTimestamp = _tmp_1
          }
          val _tmpVideoId: String
          _tmpVideoId = _stmt.getText(_columnIndexOfVideoId)
          val _tmpAlbumBrowseId: String?
          if (_stmt.isNull(_columnIndexOfAlbumBrowseId)) {
            _tmpAlbumBrowseId = null
          } else {
            _tmpAlbumBrowseId = _stmt.getText(_columnIndexOfAlbumBrowseId)
          }
          val _tmpDurationSecond: Long
          _tmpDurationSecond = _stmt.getLong(_columnIndexOfDurationSecond)
          val _tmpListenedSecond: Long
          _tmpListenedSecond = _stmt.getLong(_columnIndexOfListenedSecond)
          _item = PlaybackEventEntity(_tmpEventId,_tmpTimestamp,_tmpVideoId,_tmpAlbumBrowseId,_tmpDurationSecond,_tmpListenedSecond)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getPlaybackEventsByOffsetAndTimestamp(
    offset: Int,
    limit: Int,
    cutoffTimestamp: LocalDateTime,
  ): List<PlaybackEventEntity> {
    val _sql: String = "SELECT * FROM playback_event WHERE timestamp > ? ORDER BY timestamp DESC LIMIT ? OFFSET ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        val _tmp: Long? = __converters().dateToTimestamp(cutoffTimestamp)
        if (_tmp == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindLong(_argIndex, _tmp)
        }
        _argIndex = 2
        _stmt.bindLong(_argIndex, limit.toLong())
        _argIndex = 3
        _stmt.bindLong(_argIndex, offset.toLong())
        val _columnIndexOfEventId: Int = getColumnIndexOrThrow(_stmt, "eventId")
        val _columnIndexOfTimestamp: Int = getColumnIndexOrThrow(_stmt, "timestamp")
        val _columnIndexOfVideoId: Int = getColumnIndexOrThrow(_stmt, "videoId")
        val _columnIndexOfAlbumBrowseId: Int = getColumnIndexOrThrow(_stmt, "albumBrowseId")
        val _columnIndexOfDurationSecond: Int = getColumnIndexOrThrow(_stmt, "durationSecond")
        val _columnIndexOfListenedSecond: Int = getColumnIndexOrThrow(_stmt, "listenedSecond")
        val _result: MutableList<PlaybackEventEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: PlaybackEventEntity
          val _tmpEventId: Long
          _tmpEventId = _stmt.getLong(_columnIndexOfEventId)
          val _tmpTimestamp: LocalDateTime
          val _tmp_1: Long?
          if (_stmt.isNull(_columnIndexOfTimestamp)) {
            _tmp_1 = null
          } else {
            _tmp_1 = _stmt.getLong(_columnIndexOfTimestamp)
          }
          val _tmp_2: LocalDateTime? = __converters().fromTimestamp(_tmp_1)
          if (_tmp_2 == null) {
            error("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.")
          } else {
            _tmpTimestamp = _tmp_2
          }
          val _tmpVideoId: String
          _tmpVideoId = _stmt.getText(_columnIndexOfVideoId)
          val _tmpAlbumBrowseId: String?
          if (_stmt.isNull(_columnIndexOfAlbumBrowseId)) {
            _tmpAlbumBrowseId = null
          } else {
            _tmpAlbumBrowseId = _stmt.getText(_columnIndexOfAlbumBrowseId)
          }
          val _tmpDurationSecond: Long
          _tmpDurationSecond = _stmt.getLong(_columnIndexOfDurationSecond)
          val _tmpListenedSecond: Long
          _tmpListenedSecond = _stmt.getLong(_columnIndexOfListenedSecond)
          _item = PlaybackEventEntity(_tmpEventId,_tmpTimestamp,_tmpVideoId,_tmpAlbumBrowseId,_tmpDurationSecond,_tmpListenedSecond)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun queryTopPlayedSongsInRange(startTimestamp: LocalDateTime, endTimestamp: LocalDateTime): List<TopPlayedTracks> {
    val _sql: String = """
        |SELECT 
        |  videoId,
        |  COUNT(*) AS playCount,
        |  SUM(listenedSecond) AS totalListeningTime
        |FROM playback_event
        |WHERE timestamp BETWEEN ? AND ?
        |GROUP BY videoId
        |ORDER BY playCount DESC
        |LIMIT 100
        """.trimMargin()
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        val _tmp: Long? = __converters().dateToTimestamp(startTimestamp)
        if (_tmp == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindLong(_argIndex, _tmp)
        }
        _argIndex = 2
        val _tmp_1: Long? = __converters().dateToTimestamp(endTimestamp)
        if (_tmp_1 == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindLong(_argIndex, _tmp_1)
        }
        val _columnIndexOfVideoId: Int = 0
        val _columnIndexOfPlayCount: Int = 1
        val _columnIndexOfTotalListeningTime: Int = 2
        val _result: MutableList<TopPlayedTracks> = mutableListOf()
        while (_stmt.step()) {
          val _item: TopPlayedTracks
          val _tmpVideoId: String
          _tmpVideoId = _stmt.getText(_columnIndexOfVideoId)
          val _tmpPlayCount: Int
          _tmpPlayCount = _stmt.getLong(_columnIndexOfPlayCount).toInt()
          val _tmpTotalListeningTime: Long
          _tmpTotalListeningTime = _stmt.getLong(_columnIndexOfTotalListeningTime)
          _item = TopPlayedTracks(_tmpVideoId,_tmpPlayCount,_tmpTotalListeningTime)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun queryTopArtistsInRange(startTimestamp: LocalDateTime, endTimestamp: LocalDateTime): List<TopPlayedArtist> {
    val _sql: String = "SELECT channelId, COUNT(*) AS playCount FROM event_artist WHERE timestamp BETWEEN ? AND ? GROUP BY channelId ORDER BY playCount DESC LIMIT 100"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        val _tmp: Long? = __converters().dateToTimestamp(startTimestamp)
        if (_tmp == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindLong(_argIndex, _tmp)
        }
        _argIndex = 2
        val _tmp_1: Long? = __converters().dateToTimestamp(endTimestamp)
        if (_tmp_1 == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindLong(_argIndex, _tmp_1)
        }
        val _columnIndexOfChannelId: Int = 0
        val _columnIndexOfPlayCount: Int = 1
        val _result: MutableList<TopPlayedArtist> = mutableListOf()
        while (_stmt.step()) {
          val _item: TopPlayedArtist
          val _tmpChannelId: String
          _tmpChannelId = _stmt.getText(_columnIndexOfChannelId)
          val _tmpPlayCount: Int
          _tmpPlayCount = _stmt.getLong(_columnIndexOfPlayCount).toInt()
          _item = TopPlayedArtist(_tmpChannelId,_tmpPlayCount)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun queryTopAlbumsInRange(startTimestamp: LocalDateTime, endTimestamp: LocalDateTime): List<TopPlayedAlbum> {
    val _sql: String = """
        |SELECT 
        |  albumBrowseId,
        |  COUNT(*) AS playCount
        |FROM playback_event
        |WHERE timestamp BETWEEN ? AND ?
        |AND albumBrowseId IS NOT NULL GROUP BY albumBrowseId
        |ORDER BY playCount DESC
        |LIMIT 100
        """.trimMargin()
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        val _tmp: Long? = __converters().dateToTimestamp(startTimestamp)
        if (_tmp == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindLong(_argIndex, _tmp)
        }
        _argIndex = 2
        val _tmp_1: Long? = __converters().dateToTimestamp(endTimestamp)
        if (_tmp_1 == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindLong(_argIndex, _tmp_1)
        }
        val _columnIndexOfAlbumBrowseId: Int = 0
        val _columnIndexOfPlayCount: Int = 1
        val _result: MutableList<TopPlayedAlbum> = mutableListOf()
        while (_stmt.step()) {
          val _item: TopPlayedAlbum
          val _tmpAlbumBrowseId: String
          _tmpAlbumBrowseId = _stmt.getText(_columnIndexOfAlbumBrowseId)
          val _tmpPlayCount: Int
          _tmpPlayCount = _stmt.getLong(_columnIndexOfPlayCount).toInt()
          _item = TopPlayedAlbum(_tmpAlbumBrowseId,_tmpPlayCount)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getTotalPlaybackEventCount(): Long {
    val _sql: String = "SELECT COUNT(*) FROM playback_event"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _result: Long
        if (_stmt.step()) {
          val _tmp: Long
          _tmp = _stmt.getLong(0)
          _result = _tmp
        } else {
          _result = 0L
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getTotalEventArtistCount(): Long {
    val _sql: String = "SELECT COUNT(*) FROM event_artist"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _result: Long
        if (_stmt.step()) {
          val _tmp: Long
          _tmp = _stmt.getLong(0)
          _result = _tmp
        } else {
          _result = 0L
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getTotalListeningTimeInSeconds(): Long {
    val _sql: String = "SELECT SUM(listenedSecond) FROM playback_event"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _result: Long
        if (_stmt.step()) {
          val _tmp: Long
          _tmp = _stmt.getLong(0)
          _result = _tmp
        } else {
          _result = 0L
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getPlaybackEventCountInRange(startTimestamp: LocalDateTime, endTimestamp: LocalDateTime): Long {
    val _sql: String = "SELECT COUNT(*) FROM playback_event WHERE timestamp BETWEEN ? AND ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        val _tmp: Long? = __converters().dateToTimestamp(startTimestamp)
        if (_tmp == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindLong(_argIndex, _tmp)
        }
        _argIndex = 2
        val _tmp_1: Long? = __converters().dateToTimestamp(endTimestamp)
        if (_tmp_1 == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindLong(_argIndex, _tmp_1)
        }
        val _result: Long
        if (_stmt.step()) {
          val _tmp_2: Long
          _tmp_2 = _stmt.getLong(0)
          _result = _tmp_2
        } else {
          _result = 0L
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun deleteSearchHistory() {
    val _sql: String = "DELETE FROM search_history"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun updateTotalPlayTime(videoId: String) {
    val _sql: String = "UPDATE song SET totalPlayTime = totalPlayTime + 1 WHERE videoId = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, videoId)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun resetTotalPlayTime(videoId: String) {
    val _sql: String = "UPDATE song SET totalPlayTime = 0 WHERE videoId = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, videoId)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun updateSongInLibrary(inLibrary: LocalDateTime, videoId: String): Int {
    val _sql: String = "UPDATE song SET inLibrary = ? WHERE videoId = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        val _tmp: Long? = __converters().dateToTimestamp(inLibrary)
        if (_tmp == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindLong(_argIndex, _tmp)
        }
        _argIndex = 2
        _stmt.bindText(_argIndex, videoId)
        _stmt.step()
        getTotalChangedRows(_connection)
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun updateLiked(
    liked: Int,
    videoId: String,
    favoriteAt: LocalDateTime?,
  ) {
    val _sql: String = "UPDATE song SET liked = ?, favoriteAt = ? WHERE videoId = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, liked.toLong())
        _argIndex = 2
        val _tmp: Long? = __converters().dateToTimestamp(favoriteAt)
        if (_tmp == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindLong(_argIndex, _tmp)
        }
        _argIndex = 3
        _stmt.bindText(_argIndex, videoId)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun refreshAlbumIfPlaceholder(
    videoId: String,
    albumName: String,
    albumId: String?,
  ) {
    val _sql: String = "UPDATE song SET albumName = ?, albumId = ? WHERE videoId = ? AND (albumName IS NULL OR albumName = '' OR albumName = 'Album')"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, albumName)
        _argIndex = 2
        if (albumId == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindText(_argIndex, albumId)
        }
        _argIndex = 3
        _stmt.bindText(_argIndex, videoId)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun refreshArtists(
    videoId: String,
    artistName: List<String>,
    artistId: List<String>?,
  ) {
    val _sql: String = "UPDATE song SET artistName = ?, artistId = ? WHERE videoId = ? AND artistName IS NOT ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        val _tmp: String? = __converters().fromArrayList(artistName)
        if (_tmp == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindText(_argIndex, _tmp)
        }
        _argIndex = 2
        val _tmp_1: String? = __converters().fromArrayList(artistId)
        if (_tmp_1 == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindText(_argIndex, _tmp_1)
        }
        _argIndex = 3
        _stmt.bindText(_argIndex, videoId)
        _argIndex = 4
        val _tmp_2: String? = __converters().fromArrayList(artistName)
        if (_tmp_2 == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindText(_argIndex, _tmp_2)
        }
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun updateCanvasUrl(videoId: String, canvasUrl: String) {
    val _sql: String = "UPDATE song SET canvasUrl = ? WHERE videoId = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, canvasUrl)
        _argIndex = 2
        _stmt.bindText(_argIndex, videoId)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun updateCanvasThumbUrl(videoId: String, canvasThumbUrl: String) {
    val _sql: String = "UPDATE song SET canvasThumbUrl = ? WHERE videoId = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, canvasThumbUrl)
        _argIndex = 2
        _stmt.bindText(_argIndex, videoId)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun updateThumbnailsSongEntity(thumbnails: String, videoId: String): Int {
    val _sql: String = "UPDATE song SET thumbnails = ? WHERE videoId = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, thumbnails)
        _argIndex = 2
        _stmt.bindText(_argIndex, videoId)
        _stmt.step()
        getTotalChangedRows(_connection)
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun updateDownloadState(
    downloadState: Int,
    videoId: String,
    downloadedAt: LocalDateTime?,
  ) {
    val _sql: String = "UPDATE song SET downloadState = ?, downloadedAt = ? WHERE videoId = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, downloadState.toLong())
        _argIndex = 2
        val _tmp: Long? = __converters().dateToTimestamp(downloadedAt)
        if (_tmp == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindLong(_argIndex, _tmp)
        }
        _argIndex = 3
        _stmt.bindText(_argIndex, videoId)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun updateDurationSeconds(durationSeconds: Int, videoId: String) {
    val _sql: String = "UPDATE song SET durationSeconds = ? WHERE videoId = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, durationSeconds.toLong())
        _argIndex = 2
        _stmt.bindText(_argIndex, videoId)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun updateArtistImage(channelId: String, thumbnails: String) {
    val _sql: String = "UPDATE artist SET thumbnails = ? WHERE channelId = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, thumbnails)
        _argIndex = 2
        _stmt.bindText(_argIndex, channelId)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun updateArtistNameLogo(
    channelId: String,
    nameLogoUrl: String?,
    nameLogoColor: String?,
  ) {
    val _sql: String = "UPDATE artist SET nameLogoUrl = ?, nameLogoColor = ? WHERE channelId = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        if (nameLogoUrl == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindText(_argIndex, nameLogoUrl)
        }
        _argIndex = 2
        if (nameLogoColor == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindText(_argIndex, nameLogoColor)
        }
        _argIndex = 3
        _stmt.bindText(_argIndex, channelId)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun updateFollowed(
    followed: Int,
    channelId: String,
    followedAt: LocalDateTime?,
  ) {
    val _sql: String = "UPDATE artist SET followed = ?, followedAt = ? WHERE channelId = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, followed.toLong())
        _argIndex = 2
        val _tmp: Long? = __converters().dateToTimestamp(followedAt)
        if (_tmp == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindLong(_argIndex, _tmp)
        }
        _argIndex = 3
        _stmt.bindText(_argIndex, channelId)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun updateArtistInLibrary(inLibrary: LocalDateTime, channelId: String) {
    val _sql: String = "UPDATE artist SET inLibrary = ? WHERE channelId = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        val _tmp: Long? = __converters().dateToTimestamp(inLibrary)
        if (_tmp == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindLong(_argIndex, _tmp)
        }
        _argIndex = 2
        _stmt.bindText(_argIndex, channelId)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun updateAlbumLiked(
    liked: Int,
    browseId: String,
    favoriteAt: LocalDateTime?,
  ) {
    val _sql: String = "UPDATE album SET liked = ?, favoriteAt = ?  WHERE browseId = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, liked.toLong())
        _argIndex = 2
        val _tmp: Long? = __converters().dateToTimestamp(favoriteAt)
        if (_tmp == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindLong(_argIndex, _tmp)
        }
        _argIndex = 3
        _stmt.bindText(_argIndex, browseId)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun updateAlbumInLibrary(inLibrary: LocalDateTime, browseId: String) {
    val _sql: String = "UPDATE album SET inLibrary = ? WHERE browseId = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        val _tmp: Long? = __converters().dateToTimestamp(inLibrary)
        if (_tmp == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindLong(_argIndex, _tmp)
        }
        _argIndex = 2
        _stmt.bindText(_argIndex, browseId)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun updateAlbumDownloadState(
    downloadState: Int,
    browseId: String,
    downloadedAt: LocalDateTime?,
  ) {
    val _sql: String = "UPDATE album SET downloadState = ?, downloadedAt = ? WHERE browseId = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, downloadState.toLong())
        _argIndex = 2
        val _tmp: Long? = __converters().dateToTimestamp(downloadedAt)
        if (_tmp == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindLong(_argIndex, _tmp)
        }
        _argIndex = 3
        _stmt.bindText(_argIndex, browseId)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun updatePlaylistLiked(
    liked: Int,
    playlistId: String,
    favoriteAt: LocalDateTime?,
  ) {
    val _sql: String = "UPDATE playlist SET liked = ?, favoriteAt = ? WHERE id = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, liked.toLong())
        _argIndex = 2
        val _tmp: Long? = __converters().dateToTimestamp(favoriteAt)
        if (_tmp == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindLong(_argIndex, _tmp)
        }
        _argIndex = 3
        _stmt.bindText(_argIndex, playlistId)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun updatePlaylistInLibrary(inLibrary: LocalDateTime, playlistId: String) {
    val _sql: String = "UPDATE playlist SET inLibrary = ? WHERE id = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        val _tmp: Long? = __converters().dateToTimestamp(inLibrary)
        if (_tmp == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindLong(_argIndex, _tmp)
        }
        _argIndex = 2
        _stmt.bindText(_argIndex, playlistId)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun updatePlaylistDownloadState(
    downloadState: Int,
    playlistId: String,
    downloadedAt: LocalDateTime?,
  ) {
    val _sql: String = "UPDATE playlist SET downloadState = ?, downloadedAt = ? WHERE id = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, downloadState.toLong())
        _argIndex = 2
        val _tmp: Long? = __converters().dateToTimestamp(downloadedAt)
        if (_tmp == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindLong(_argIndex, _tmp)
        }
        _argIndex = 3
        _stmt.bindText(_argIndex, playlistId)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun deleteLocalPlaylist(id: Long) {
    val _sql: String = "DELETE FROM local_playlist WHERE id = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, id)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun updateLocalPlaylistTitle(title: String, id: Long) {
    val _sql: String = "UPDATE local_playlist SET title = ? WHERE id = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, title)
        _argIndex = 2
        _stmt.bindLong(_argIndex, id)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun updateLocalPlaylistTracks(tracks: List<String>, id: Long) {
    val _sql: String = "UPDATE local_playlist SET tracks = ? WHERE id = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        val _tmp: String? = __converters().fromArrayList(tracks)
        if (_tmp == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindText(_argIndex, _tmp)
        }
        _argIndex = 2
        _stmt.bindLong(_argIndex, id)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun updateLocalPlaylistThumbnail(thumbnail: String, id: Long) {
    val _sql: String = "UPDATE local_playlist SET thumbnail = ? WHERE id = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, thumbnail)
        _argIndex = 2
        _stmt.bindLong(_argIndex, id)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun updateLocalPlaylistInLibrary(inLibrary: LocalDateTime, id: Long) {
    val _sql: String = "UPDATE local_playlist SET inLibrary = ? WHERE id = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        val _tmp: Long? = __converters().dateToTimestamp(inLibrary)
        if (_tmp == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindLong(_argIndex, _tmp)
        }
        _argIndex = 2
        _stmt.bindLong(_argIndex, id)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun updateLocalPlaylistDownloadState(
    downloadState: Int,
    id: Long,
    downloadedAt: LocalDateTime?,
  ) {
    val _sql: String = "UPDATE local_playlist SET downloadState = ?, downloadedAt = ? WHERE id = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, downloadState.toLong())
        _argIndex = 2
        val _tmp: Long? = __converters().dateToTimestamp(downloadedAt)
        if (_tmp == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindLong(_argIndex, _tmp)
        }
        _argIndex = 3
        _stmt.bindLong(_argIndex, id)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun updateLocalPlaylistYouTubePlaylistId(id: Long, youtubePlaylistId: String?) {
    val _sql: String = "UPDATE local_playlist SET youtubePlaylistId = ? WHERE id = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        if (youtubePlaylistId == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindText(_argIndex, youtubePlaylistId)
        }
        _argIndex = 2
        _stmt.bindLong(_argIndex, id)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun updateLocalPlaylistYouTubePlaylistSyncState(id: Long, state: Int) {
    val _sql: String = "UPDATE local_playlist SET youtube_sync_state = ? WHERE id = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, state.toLong())
        _argIndex = 2
        _stmt.bindLong(_argIndex, id)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun unsyncLocalPlaylist(id: Long) {
    val _sql: String = "UPDATE local_playlist SET youtube_sync_state = 0, youtubePlaylistId = NULL WHERE id = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, id)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun deleteQueue() {
    val _sql: String = "DELETE FROM queue"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun editPositionOfSongInPlaylist(
    playlistId: Long,
    videoId: String,
    newPosition: Int,
  ) {
    val _sql: String = "UPDATE pair_song_local_playlist SET position = ? WHERE playlistId = ? AND songId = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, newPosition.toLong())
        _argIndex = 2
        _stmt.bindLong(_argIndex, playlistId)
        _argIndex = 3
        _stmt.bindText(_argIndex, videoId)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun shiftPositionsForward(
    playlistId: Long,
    from: Int,
    to: Int,
  ) {
    val _sql: String = "UPDATE pair_song_local_playlist SET position = position + 1 WHERE playlistId = ? AND position >= ? AND position < ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, playlistId)
        _argIndex = 2
        _stmt.bindLong(_argIndex, from.toLong())
        _argIndex = 3
        _stmt.bindLong(_argIndex, to.toLong())
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun shiftPositionsBackward(
    playlistId: Long,
    from: Int,
    to: Int,
  ) {
    val _sql: String = "UPDATE pair_song_local_playlist SET position = position - 1 WHERE playlistId = ? AND position > ? AND position <= ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, playlistId)
        _argIndex = 2
        _stmt.bindLong(_argIndex, from.toLong())
        _argIndex = 3
        _stmt.bindLong(_argIndex, to.toLong())
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun deletePairSongLocalPlaylist(playlistId: Long, videoId: String) {
    val _sql: String = "DELETE FROM pair_song_local_playlist WHERE songId = ? AND playlistId = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, videoId)
        _argIndex = 2
        _stmt.bindLong(_argIndex, playlistId)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun updateGoogleAccountUsed(isUsed: Boolean, email: String): Int {
    val _sql: String = "UPDATE googleaccountentity SET isUsed = ? WHERE email = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        val _tmp: Int = if (isUsed) 1 else 0
        _stmt.bindLong(_argIndex, _tmp.toLong())
        _argIndex = 2
        _stmt.bindText(_argIndex, email)
        _stmt.step()
        getTotalChangedRows(_connection)
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun deleteGoogleAccount(email: String) {
    val _sql: String = "DELETE FROM googleaccountentity WHERE email = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, email)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun setInLibrary(videoId: String, inLibrary: LocalDateTime) {
    val _sql: String = "UPDATE song SET inLibrary = ? WHERE videoId = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        val _tmp: Long? = __converters().dateToTimestamp(inLibrary)
        if (_tmp == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindLong(_argIndex, _tmp)
        }
        _argIndex = 2
        _stmt.bindText(_argIndex, videoId)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun deleteFollowedArtistSingleAndAlbum(channelId: String) {
    val _sql: String = "DELETE FROM followed_artist_single_and_album WHERE channelId = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, channelId)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun deleteNotification(id: Long) {
    val _sql: String = "DELETE FROM notification WHERE id = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, id)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun removeTranslatedLyrics(videoId: String, language: String) {
    val _sql: String = "DELETE FROM translated_lyrics WHERE videoId = ? AND language = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, videoId)
        _argIndex = 2
        _stmt.bindText(_argIndex, language)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun updatePodcastInLibrary(id: String, inLibrary: LocalDateTime): Int {
    val _sql: String = "UPDATE podcast_table SET inLibrary = ? WHERE podcastId = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        val _tmp: Long? = __converters().dateToTimestamp(inLibrary)
        if (_tmp == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindLong(_argIndex, _tmp)
        }
        _argIndex = 2
        _stmt.bindText(_argIndex, id)
        _stmt.step()
        getTotalChangedRows(_connection)
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun deletePodcast(podcastId: String): Int {
    val _sql: String = "DELETE FROM podcast_table WHERE podcastId = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, podcastId)
        _stmt.step()
        getTotalChangedRows(_connection)
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun deleteAllYourYouTubePlaylist() {
    val _sql: String = "DELETE FROM your_youtube_playlist_list"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun deleteOldPlaybackEvents(cutoffTimestamp: LocalDateTime) {
    val _sql: String = "DELETE FROM playback_event WHERE timestamp < ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        val _tmp: Long? = __converters().dateToTimestamp(cutoffTimestamp)
        if (_tmp == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindLong(_argIndex, _tmp)
        }
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun raw(supportSQLiteQuery: RoomRawQuery): Int {
    val _sql: String = supportSQLiteQuery.sql
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        supportSQLiteQuery.getBindingFunction().invoke(_stmt)
        val _result: Int
        if (_stmt.step()) {
          _result = _stmt.getLong(0).toInt()
        } else {
          _result = 0
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  private fun __converters(): Converters = __converters.value

  private fun __fetchRelationshippodcastEpisodeTableAscomDecibelMusicDomainDataEntitiesEpisodeEntity(_connection: SQLiteConnection, _map: MutableMap<String, MutableList<EpisodeEntity>>) {
    val __mapKeySet: Set<String> = _map.keys
    if (__mapKeySet.isEmpty()) {
      return
    }
    if (_map.size > 999) {
      recursiveFetchMap(_map, true) { _tmpMap ->
        __fetchRelationshippodcastEpisodeTableAscomDecibelMusicDomainDataEntitiesEpisodeEntity(_connection, _tmpMap)
      }
      return
    }
    val _stringBuilder: StringBuilder = StringBuilder()
    _stringBuilder.append("SELECT `videoId`,`podcastId`,`title`,`authorName`,`authorId`,`description`,`createdDay`,`durationString`,`thumbnail` FROM `podcast_episode_table` WHERE `podcastId` IN (")
    val _inputSize: Int = __mapKeySet.size
    appendPlaceholders(_stringBuilder, _inputSize)
    _stringBuilder.append(")")
    val _sql: String = _stringBuilder.toString()
    val _stmt: SQLiteStatement = _connection.prepare(_sql)
    var _argIndex: Int = 1
    for (_item: String in __mapKeySet) {
      _stmt.bindText(_argIndex, _item)
      _argIndex++
    }
    try {
      val _itemKeyIndex: Int = getColumnIndex(_stmt, "podcastId")
      if (_itemKeyIndex == -1) {
        return
      }
      val _columnIndexOfVideoId: Int = 0
      val _columnIndexOfPodcastId: Int = 1
      val _columnIndexOfTitle: Int = 2
      val _columnIndexOfAuthorName: Int = 3
      val _columnIndexOfAuthorId: Int = 4
      val _columnIndexOfDescription: Int = 5
      val _columnIndexOfCreatedDay: Int = 6
      val _columnIndexOfDurationString: Int = 7
      val _columnIndexOfThumbnail: Int = 8
      while (_stmt.step()) {
        val _tmpKey: String
        _tmpKey = _stmt.getText(_itemKeyIndex)
        val _tmpRelation: MutableList<EpisodeEntity>? = _map.get(_tmpKey)
        if (_tmpRelation != null) {
          val _item_1: EpisodeEntity
          val _tmpVideoId: String
          _tmpVideoId = _stmt.getText(_columnIndexOfVideoId)
          val _tmpPodcastId: String
          _tmpPodcastId = _stmt.getText(_columnIndexOfPodcastId)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpAuthorName: String
          _tmpAuthorName = _stmt.getText(_columnIndexOfAuthorName)
          val _tmpAuthorId: String
          _tmpAuthorId = _stmt.getText(_columnIndexOfAuthorId)
          val _tmpDescription: String?
          if (_stmt.isNull(_columnIndexOfDescription)) {
            _tmpDescription = null
          } else {
            _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          }
          val _tmpCreatedDay: String?
          if (_stmt.isNull(_columnIndexOfCreatedDay)) {
            _tmpCreatedDay = null
          } else {
            _tmpCreatedDay = _stmt.getText(_columnIndexOfCreatedDay)
          }
          val _tmpDurationString: String?
          if (_stmt.isNull(_columnIndexOfDurationString)) {
            _tmpDurationString = null
          } else {
            _tmpDurationString = _stmt.getText(_columnIndexOfDurationString)
          }
          val _tmpThumbnail: String?
          if (_stmt.isNull(_columnIndexOfThumbnail)) {
            _tmpThumbnail = null
          } else {
            _tmpThumbnail = _stmt.getText(_columnIndexOfThumbnail)
          }
          _item_1 = EpisodeEntity(_tmpVideoId,_tmpPodcastId,_tmpTitle,_tmpAuthorName,_tmpAuthorId,_tmpDescription,_tmpCreatedDay,_tmpDurationString,_tmpThumbnail)
          _tmpRelation.add(_item_1)
        }
      }
    } finally {
      _stmt.close()
    }
  }

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = listOf(Converters::class)
  }
}
