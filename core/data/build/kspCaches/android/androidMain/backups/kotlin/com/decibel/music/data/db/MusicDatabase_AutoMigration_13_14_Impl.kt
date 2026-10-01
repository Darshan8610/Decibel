package com.decibel.music.`data`.db

import androidx.room.migration.Migration
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import javax.`annotation`.processing.Generated
import kotlin.Suppress

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
internal class MusicDatabase_AutoMigration_13_14_Impl : Migration {
  public constructor() : super(13, 14)

  public override fun migrate(connection: SQLiteConnection) {
    connection.execSQL("ALTER TABLE `new_format` ADD COLUMN `audioUrl` TEXT DEFAULT NULL")
    connection.execSQL("ALTER TABLE `new_format` ADD COLUMN `videoUrl` TEXT DEFAULT NULL")
    connection.execSQL("ALTER TABLE `song` ADD COLUMN `canvasThumbUrl` TEXT DEFAULT NULL")
  }
}
