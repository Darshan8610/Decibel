package com.decibel.music.data.di.loader

import com.decibel.music.media_jvm.di.loadDesktopPlayerModule

actual fun loadMediaService() {
    loadDesktopPlayerModule()
}
