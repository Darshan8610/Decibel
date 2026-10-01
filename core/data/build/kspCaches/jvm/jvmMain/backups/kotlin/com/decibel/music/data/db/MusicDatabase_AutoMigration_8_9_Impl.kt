package com.decibel.music.`data`.db

import androidx.room.migration.Migration
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import javax.`annotation`.processing.Generated
import kotlin.Suppress

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
internal class MusicDatabase_AutoMigration_8_9_Impl : Migration {
  public constructor() : super(8, 9)

  public override fun migrate(connection: SQLiteConnection) {
    connection.execSQL("CREATE TABLE IF NOT EXISTS `followed_artist_single_and_album` (`channelId` TEXT NOT NULL, `name` TEXT NOT NULL, `single` TEXT NOT NULL, `album` TEXT NOT NULL, PRIMARY KEY(`channelId`))")
    connection.execSQL("CREATE TABLE IF NOT EXISTS `notification` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `channelId` TEXT NOT NULL, `thumbnail` TEXT, `name` TEXT NOT NULL, `single` TEXT NOT NULL, `album` TEXT NOT NULL, `time` INTEGER NOT NULL)")
  }
}
