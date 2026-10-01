package com.decibel.music.`data`.db

import androidx.room.migration.Migration
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import javax.`annotation`.processing.Generated
import kotlin.Suppress

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
internal class MusicDatabase_AutoMigration_9_10_Impl : Migration {
  public constructor() : super(9, 10)

  public override fun migrate(connection: SQLiteConnection) {
    connection.execSQL("ALTER TABLE `new_format` ADD COLUMN `expired_time` INTEGER NOT NULL DEFAULT 0")
  }
}
