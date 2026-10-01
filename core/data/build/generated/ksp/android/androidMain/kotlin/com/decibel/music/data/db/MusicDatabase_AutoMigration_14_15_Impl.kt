package com.decibel.music.`data`.db

import androidx.room.migration.Migration
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import javax.`annotation`.processing.Generated
import kotlin.Suppress

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
internal class MusicDatabase_AutoMigration_14_15_Impl : Migration {
  public constructor() : super(14, 15)

  public override fun migrate(connection: SQLiteConnection) {
    connection.execSQL("CREATE TABLE IF NOT EXISTS `translated_lyrics` (`videoId` TEXT NOT NULL, `language` TEXT NOT NULL, `error` INTEGER NOT NULL, `lines` TEXT, `syncType` TEXT, PRIMARY KEY(`videoId`))")
  }
}
