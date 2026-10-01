package com.decibel.music.`data`.db

import androidx.room.migration.Migration
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import javax.`annotation`.processing.Generated
import kotlin.Suppress

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
internal class MusicDatabase_AutoMigration_2_3_Impl : Migration {
  public constructor() : super(2, 3)

  public override fun migrate(connection: SQLiteConnection) {
    connection.execSQL("ALTER TABLE `format` ADD COLUMN `description` TEXT DEFAULT NULL")
    connection.execSQL("CREATE TABLE IF NOT EXISTS `queue` (`queueId` INTEGER NOT NULL, `listTrack` TEXT NOT NULL, PRIMARY KEY(`queueId`))")
  }
}
