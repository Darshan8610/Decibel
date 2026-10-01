package com.decibel.music.`data`.db

import androidx.room.migration.Migration
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import javax.`annotation`.processing.Generated
import kotlin.Suppress

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
internal class MusicDatabase_AutoMigration_17_19_Impl : Migration {
  public constructor() : super(17, 19)

  public override fun migrate(connection: SQLiteConnection) {
    connection.execSQL("ALTER TABLE `song` ADD COLUMN `favoriteAt` INTEGER DEFAULT NULL")
    connection.execSQL("ALTER TABLE `song` ADD COLUMN `downloadedAt` INTEGER DEFAULT NULL")
    connection.execSQL("ALTER TABLE `artist` ADD COLUMN `followedAt` INTEGER DEFAULT NULL")
    connection.execSQL("ALTER TABLE `album` ADD COLUMN `favoriteAt` INTEGER DEFAULT NULL")
    connection.execSQL("ALTER TABLE `album` ADD COLUMN `downloadedAt` INTEGER DEFAULT NULL")
    connection.execSQL("ALTER TABLE `playlist` ADD COLUMN `favoriteAt` INTEGER DEFAULT NULL")
    connection.execSQL("ALTER TABLE `playlist` ADD COLUMN `downloadedAt` INTEGER DEFAULT NULL")
    connection.execSQL("ALTER TABLE `local_playlist` ADD COLUMN `downloadedAt` INTEGER DEFAULT NULL")
    connection.execSQL("ALTER TABLE `GoogleAccountEntity` ADD COLUMN `pageId` TEXT DEFAULT NULL")
  }
}
