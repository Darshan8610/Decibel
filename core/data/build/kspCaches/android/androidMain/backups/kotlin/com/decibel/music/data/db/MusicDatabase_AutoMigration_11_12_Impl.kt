package com.decibel.music.`data`.db

import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import javax.`annotation`.processing.Generated
import kotlin.Suppress

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
internal class MusicDatabase_AutoMigration_11_12_Impl : Migration {
  private val callback: AutoMigrationSpec = AutoMigration11_12()

  public constructor() : super(11, 12)

  public override fun migrate(connection: SQLiteConnection) {
    connection.execSQL("ALTER TABLE `song` ADD COLUMN `canvasUrl` TEXT DEFAULT NULL")
    connection.execSQL("CREATE TABLE IF NOT EXISTS `_new_local_playlist` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `title` TEXT NOT NULL, `thumbnail` TEXT, `inLibrary` INTEGER NOT NULL, `downloadState` INTEGER NOT NULL, `youtubePlaylistId` TEXT, `youtube_sync_state` INTEGER NOT NULL DEFAULT 0, `tracks` TEXT)")
    connection.execSQL("INSERT INTO `_new_local_playlist` (`id`,`title`,`thumbnail`,`inLibrary`,`downloadState`,`youtubePlaylistId`,`youtube_sync_state`,`tracks`) SELECT `id`,`title`,`thumbnail`,`inLibrary`,`downloadState`,`youtubePlaylistId`,`youtube_sync_state`,`tracks` FROM `local_playlist`")
    connection.execSQL("DROP TABLE `local_playlist`")
    connection.execSQL("ALTER TABLE `_new_local_playlist` RENAME TO `local_playlist`")
    callback.onPostMigrate(connection)
  }
}
