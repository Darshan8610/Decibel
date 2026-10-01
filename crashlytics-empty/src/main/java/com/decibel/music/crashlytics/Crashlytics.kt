package com.decibel.music.crashlytics

import android.content.Context
import com.decibel.music.domain.data.player.PlayerError
import com.decibel.music.logger.Logger

// Sent crash to Sentry
fun reportCrash(throwable: Throwable) {
    Logger.e("Crashlytics", "NON-SENTRY crash: ${throwable.localizedMessage}")
}

fun configCrashlytics(applicationContext: Context, dsn: String) {
    Logger.d("Crashlytics", "NON-SENTRY start app")
}

fun pushPlayerError(error: PlayerError) {
    Logger.e("Crashlytics", "NON-SENTRY Player Error: ${error.message}, code: ${error.errorCode}, code name: ${error.errorCodeName}")
}