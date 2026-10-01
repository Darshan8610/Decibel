package com.decibel.music.`data`.db

import androidx.room.migration.Migration
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import javax.`annotation`.processing.Generated
import kotlin.Suppress

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
internal class MusicDatabase_AutoMigration_6_7_Impl : Migration {
  public constructor() : super(6, 7)

  public override fun migrate(connection: SQLiteConnection) {
    connection.execSQL("CREATE TABLE IF NOT EXISTS `GoogleAccountEntity` (`email` TEXT NOT NULL, `name` TEXT NOT NULL, `thumbnailUrl` TEXT NOT NULL, `cache` TEXT, `isUsed` INTEGER NOT NULL, PRIMARY KEY(`email`))")
  }
}
