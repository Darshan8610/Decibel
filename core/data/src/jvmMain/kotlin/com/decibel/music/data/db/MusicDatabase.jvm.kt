package com.decibel.music.data.db

import androidx.room.Room
import androidx.room.RoomDatabase
import com.decibel.music.common.DB_NAME
import com.decibel.music.data.io.getHomeFolderPath
import java.io.File

actual fun getDatabaseBuilder(
    converters: Converters
): RoomDatabase.Builder<MusicDatabase> {
    return Room.databaseBuilder<MusicDatabase>(
        name = getDatabasePath()
    ).addTypeConverter(converters)
}

actual fun getDatabasePath(): String {
    val decibelDb = File(getHomeFolderPath(listOf(".decibel", "db")), DB_NAME)
    val legacyDb = File(getHomeFolderPath(listOf(".decibel", "db")), DB_NAME)
    if (!decibelDb.exists() && legacyDb.exists()) {
        decibelDb.parentFile?.mkdirs()
        legacyDb.copyTo(decibelDb, overwrite = true)
    }
    return decibelDb.absolutePath
}