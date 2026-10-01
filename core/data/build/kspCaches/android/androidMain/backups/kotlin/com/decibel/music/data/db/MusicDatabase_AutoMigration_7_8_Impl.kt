package com.decibel.music.`data`.db

import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import javax.`annotation`.processing.Generated
import kotlin.Suppress

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
internal class MusicDatabase_AutoMigration_7_8_Impl : Migration {
  private val callback: AutoMigrationSpec = AutoMigration7_8()

  public constructor() : super(7, 8)

  public override fun migrate(connection: SQLiteConnection) {
    connection.execSQL("DROP TABLE `format`")
    connection.execSQL("CREATE TABLE IF NOT EXISTS `new_format` (`videoId` TEXT NOT NULL, `itag` INTEGER NOT NULL, `mimeType` TEXT, `codecs` TEXT, `bitrate` INTEGER, `sampleRate` INTEGER, `contentLength` INTEGER, `loudnessDb` REAL, `lengthSeconds` INTEGER, `playbackTrackingVideostatsPlaybackUrl` TEXT, `playbackTrackingAtrUrl` TEXT, `playbackTrackingVideostatsWatchtimeUrl` TEXT, `cpn` TEXT, PRIMARY KEY(`videoId`))")
    connection.execSQL("CREATE TABLE IF NOT EXISTS `song_info` (`videoId` TEXT NOT NULL, `author` TEXT, `authorId` TEXT, `authorThumbnail` TEXT, `description` TEXT, `subscribers` TEXT, `viewCount` INTEGER, `uploadDate` TEXT, `like` INTEGER, `dislike` INTEGER, PRIMARY KEY(`videoId`))")
    callback.onPostMigrate(connection)
  }
}
