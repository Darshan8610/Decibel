package com.decibel.music.`data`.db

import androidx.room.migration.Migration
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import javax.`annotation`.processing.Generated
import kotlin.Suppress

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
internal class MusicDatabase_AutoMigration_2_4_Impl : Migration {
  public constructor() : super(2, 4)

  public override fun migrate(connection: SQLiteConnection) {
    connection.execSQL("CREATE TABLE IF NOT EXISTS `queue` (`queueId` INTEGER NOT NULL, `listTrack` TEXT NOT NULL, PRIMARY KEY(`queueId`))")
    connection.execSQL("CREATE TABLE IF NOT EXISTS `_new_format` (`videoId` TEXT NOT NULL, `itag` INTEGER, `mimeType` TEXT, `bitrate` INTEGER, `contentLength` INTEGER, `lastModified` INTEGER, `loudnessDb` REAL, `uploader` TEXT, `uploaderId` TEXT, `uploaderSubCount` TEXT, `uploaderThumbnail` TEXT, `description` TEXT, `playbackTrackingVideostatsPlaybackUrl` TEXT, `playbackTrackingAtrUrl` TEXT, `playbackTrackingVideostatsWatchtimeUrl` TEXT, PRIMARY KEY(`videoId`))")
    connection.execSQL("INSERT INTO `_new_format` (`videoId`,`itag`,`mimeType`,`bitrate`,`contentLength`,`lastModified`,`loudnessDb`,`uploader`,`uploaderId`,`uploaderSubCount`,`uploaderThumbnail`) SELECT `videoId`,`itag`,`mimeType`,`bitrate`,`contentLength`,`lastModified`,`loudnessDb`,`uploader`,`uploaderId`,`uploaderSubCount`,`uploaderThumbnail` FROM `format`")
    connection.execSQL("DROP TABLE `format`")
    connection.execSQL("ALTER TABLE `_new_format` RENAME TO `format`")
  }
}
