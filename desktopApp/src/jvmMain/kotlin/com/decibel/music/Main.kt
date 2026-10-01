package com.decibel.music

import java.awt.Toolkit

private fun forceLinuxWmClass(appName: String = "Decibel") {
    if (!System.getProperty("os.name").orEmpty().contains("linux", ignoreCase = true)) return
    runCatching {
        val toolkit = Toolkit.getDefaultToolkit()
        if (toolkit.javaClass.name != "sun.awt.X11.XToolkit") return
        toolkit.javaClass.getDeclaredField("awtAppClassName").apply {
            isAccessible = true
            set(null, appName)
        }
    }.onFailure { System.err.println("forceLinuxWmClass failed: ${it.message}") }
}

fun main(args: Array<String>) {
    forceLinuxWmClass()
    runDesktopApp(args)
}
