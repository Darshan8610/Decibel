package com.decibel.music.data.di.loader

import com.decibel.music.data.di.databaseModule
import com.decibel.music.data.di.mediaHandlerModule
import com.decibel.music.data.di.repositoryModule
import org.koin.core.context.loadKoinModules

fun loadAllModules() {
    loadKoinModules(
        listOf(
            databaseModule,
            repositoryModule,
        ),
    )
    loadKoinModules(mediaHandlerModule)
    loadMediaService()
}

expect fun loadMediaService()