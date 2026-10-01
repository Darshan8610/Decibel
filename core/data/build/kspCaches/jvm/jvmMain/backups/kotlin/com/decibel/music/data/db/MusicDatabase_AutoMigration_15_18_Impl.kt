package com.decibel.music.`data`.db

import androidx.room.migration.Migration
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import javax.`annotation`.processing.Generated
import kotlin.Suppress

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
internal class MusicDatabase_AutoMigration_15_18_Impl : Migration {
  public constructor() : super(15, 18)

  public override fun migrate(connection: SQLiteConnection) {
    connection.execSQL("ALTER TABLE `song` ADD COLUMN `favoriteAt` INTEGER DEFAULT NULL")
    connection.execSQL("ALTER TABLE `song` ADD COLUMN `downloadedAt` INTEGER DEFAULT NULL")
    connection.execSQL("ALTER TABLE `artist` ADD COLUMN `followedAt` INTEGER DEFAULT NULL")
    connection.execSQL("ALTER TABLE `album` ADD COLUMN `favoriteAt` INTEGER DEFAULT NULL")
    connection.execSQL("ALTER TABLE `album` ADD COLUMN `downloadedAt` INTEGER DEFAULT NULL")
    connection.execSQL("ALTER TABLE `playlist` ADD COLUMN `favoriteAt` INTEGER DEFAULT NULL")
    connection.execSQL("ALTER TABLE `playlist` ADD COLUMN `downloadedAt` INTEGER DEFAULT NULL")
    connection.execSQL("ALTER TABLE `local_playlist` ADD COLUMN `downloadedAt` INTEGER DEFAULT NULL")
    connection.execSQL("ALTER TABLE `GoogleAccountEntity` ADD COLUMN `netscapeCookie` TEXT DEFAULT NULL")
    connection.execSQL("CREATE TABLE IF NOT EXISTS `podcast_table` (`podcastId` TEXT NOT NULL, `title` TEXT NOT NULL, `authorId` TEXT NOT NULL, `authorName` TEXT NOT NULL, `authorThumbnail` TEXT, `description` TEXT, `thumbnail` TEXT, `isFavorite` INTEGER NOT NULL, `inLibrary` INTEGER NOT NULL, `favoriteTime` INTEGER, `listEpisodes` TEXT NOT NULL, PRIMARY KEY(`podcastId`))")
    connection.execSQL("CREATE TABLE IF NOT EXISTS `podcast_episode_table` (`videoId` TEXT NOT NULL, `podcastId` TEXT NOT NULL, `title` TEXT NOT NULL, `authorName` TEXT NOT NULL, `authorId` TEXT NOT NULL, `description` TEXT, `createdDay` TEXT, `durationString` TEXT, `thumbnail` TEXT, PRIMARY KEY(`videoId`), FOREIGN KEY(`podcastId`) REFERENCES `podcast_table`(`podcastId`) ON UPDATE NO ACTION ON DELETE CASCADE )")
    connection.execSQL("CREATE INDEX IF NOT EXISTS `index_podcast_episode_table_podcastId` ON `podcast_episode_table` (`podcastId`)")
  }
}
