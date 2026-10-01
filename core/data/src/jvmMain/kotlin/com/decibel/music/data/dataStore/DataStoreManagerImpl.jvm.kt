package com.decibel.music.data.dataStore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.decibel.music.common.SETTINGS_FILENAME
import com.decibel.music.data.io.getHomeFolderPath
import createDataStore
import java.io.File

actual fun createDataStoreInstance(): DataStore<Preferences> = createDataStore(
    producePath = {
        val decibelDir = File(getHomeFolderPath(listOf(".decibel")))
        val legacyDir = File(getHomeFolderPath(listOf(".decibel")))
        if (!decibelDir.exists() && legacyDir.exists()) {
            legacyDir.renameTo(decibelDir)
        }
        val file = File(decibelDir, "$SETTINGS_FILENAME.preferences_pb")
        file.absolutePath
    }
)