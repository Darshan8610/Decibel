package com.decibel.music.`data`.db

import androidx.room.migration.Migration
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import javax.`annotation`.processing.Generated
import kotlin.Suppress

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
internal class MusicDatabase_AutoMigration_4_5_Impl : Migration {
  public constructor() : super(4, 5)

  public override fun migrate(connection: SQLiteConnection) {
    connection.execSQL("ALTER TABLE `local_playlist` ADD COLUMN `synced_with_youtube_playlist` INTEGER NOT NULL DEFAULT 0")
    connection.execSQL("ALTER TABLE `local_playlist` ADD COLUMN `youtubePlaylistId` TEXT DEFAULT NULL")
    connection.execSQL("ALTER TABLE `local_playlist` ADD COLUMN `youtube_sync_state` INTEGER NOT NULL DEFAULT 0")
    connection.execSQL("CREATE TABLE IF NOT EXISTS `set_video_id` (`videoId` TEXT NOT NULL, `setVideoId` TEXT, PRIMARY KEY(`videoId`))")
  }
}
