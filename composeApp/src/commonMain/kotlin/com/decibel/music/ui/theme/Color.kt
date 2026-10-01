package com.decibel.music.ui.theme

import androidx.compose.ui.graphics.Color

// ===== Brand =====

/**
 * Brand seed color. The whole Material 3 ColorScheme is generated from this
 * color at runtime — see [AppTheme].
 */
val seed = Color(0xFF6366F1)

// ===== Semantic colors (not derivable from the color scheme) =====

/** Liked/favorite state (heart buttons, favorite tiles). */
val favoriteColor = Color(0xFFFF4081)

/** Currently playing lyric line. */
val lyricActiveColor = Color(0xFFFFFF00)

val shimmerBackground = Color(0xFF0A0A0A)
val shimmerLine = Color(0xFF1A1A1A)

// Light-theme counterparts of the shimmer tokens.
val shimmerBackgroundLight = Color(0xFFF0F0F0)
val shimmerLineLight = Color(0xFFE0E0E0)

val overlay = Color(0xCC000000)
val blackMoreOverlay = Color(0xE6000000)

// ===== Legacy — do not add new usages =====

/**
 * Old M3 primary (lavender). Kept only for the SettingScreen storage bar,
 * which stays untouched by owner's decision.
 */
@Deprecated("Legacy storage bar color only — use MaterialTheme.colorScheme.primary in new code")
val md_theme_dark_primary = Color(0xFFB2C5FF)

// Decibel Design System — Surface Hierarchy (AMOLED Pitch Black & Crisp White Accents)
val decibelSurface = Color(0xFF000000)        // Pure AMOLED black primary surface
val decibelSurfaceContainer = Color(0xFF000000) // Pure AMOLED black container
val decibelSurfaceHigh = Color(0xFF080808)     // Minimal elevated surface / bottom sheets
val decibelSurfaceHighest = Color(0xFF121212)  // Elevated elements
val decibelDivider = Color(0xFF1E1E1E)         // Subtle dark divider
val decibelTextPrimary = Color(0xFFFFFFFF)     // Pure white primary text
val decibelTextSecondary = Color(0xFFB3B3B3)   // Muted secondary text (Spotify style)
val decibelTextTertiary = Color(0xFF737373)    // Subtle tertiary text
val decibelAccent = Color(0xFFFFFFFF)          // Pure white active accent
val decibelAccentLight = Color(0xFFE5E5E5)     // Pure white light accent
val decibelBrandIndigo = Color(0xFF6366F1)     // Brand indigo touch
