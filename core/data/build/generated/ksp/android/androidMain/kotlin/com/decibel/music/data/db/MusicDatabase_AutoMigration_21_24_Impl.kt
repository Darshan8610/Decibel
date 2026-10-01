package com.decibel.music.`data`.db

import androidx.room.migration.Migration
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import javax.`annotation`.processing.Generated
import kotlin.Suppress

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
internal class MusicDatabase_AutoMigration_21_24_Impl : Migration {
  public constructor() : super(21, 24)

  public override fun migrate(connection: SQLiteConnection) {
    connection.execSQL("ALTER TABLE `new_format` ADD COLUMN `bpm` INTEGER DEFAULT NULL")
    connection.execSQL("ALTER TABLE `new_format` ADD COLUMN `music_key` TEXT DEFAULT NULL")
    connection.execSQL("ALTER TABLE `new_format` ADD COLUMN `keyScale` TEXT DEFAULT NULL")
    connection.execSQL("ALTER TABLE `artist` ADD COLUMN `nameLogoUrl` TEXT DEFAULT NULL")
    connection.execSQL("ALTER TABLE `artist` ADD COLUMN `nameLogoColor` TEXT DEFAULT NULL")
    connection.execSQL("ALTER TABLE `notification` ADD COLUMN `type` TEXT NOT NULL DEFAULT 'artist'")
    connection.execSQL("ALTER TABLE `notification` ADD COLUMN `link` TEXT DEFAULT NULL")
    connection.execSQL("ALTER TABLE `notification` ADD COLUMN `description` TEXT DEFAULT NULL")
  }
}
