package com.decibel.music.`data`.db

import androidx.room.migration.Migration
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import javax.`annotation`.processing.Generated
import kotlin.Suppress

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
internal class MusicDatabase_AutoMigration_18_21_Impl : Migration {
  public constructor() : super(18, 21)

  public override fun migrate(connection: SQLiteConnection) {
    connection.execSQL("ALTER TABLE `GoogleAccountEntity` ADD COLUMN `pageId` TEXT DEFAULT NULL")
    connection.execSQL("CREATE TABLE IF NOT EXISTS `your_youtube_playlist_list` (`emailPageId` TEXT NOT NULL, `listBrowseIds` TEXT NOT NULL, PRIMARY KEY(`emailPageId`))")
    connection.execSQL("CREATE TABLE IF NOT EXISTS `playback_event` (`eventId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `timestamp` INTEGER NOT NULL, `videoId` TEXT NOT NULL, `albumBrowseId` TEXT, `durationSecond` INTEGER NOT NULL, `listenedSecond` INTEGER NOT NULL)")
    connection.execSQL("CREATE TABLE IF NOT EXISTS `event_artist` (`eventId` INTEGER NOT NULL, `channelId` TEXT NOT NULL, `timestamp` INTEGER NOT NULL, PRIMARY KEY(`eventId`, `channelId`), FOREIGN KEY(`eventId`) REFERENCES `playback_event`(`eventId`) ON UPDATE NO ACTION ON DELETE CASCADE )")
    connection.execSQL("CREATE INDEX IF NOT EXISTS `index_event_artist_eventId` ON `event_artist` (`eventId`)")
    connection.execSQL("CREATE INDEX IF NOT EXISTS `index_event_artist_channelId` ON `event_artist` (`channelId`)")
    connection.execSQL("CREATE INDEX IF NOT EXISTS `index_event_artist_timestamp_channelId` ON `event_artist` (`timestamp`, `channelId`)")
  }
}
