package com.maxrave.simpmusic.utils

import androidx.compose.foundation.clickable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.semantics.Role

expect object DecibelHaptics {
    fun performClick()
    fun performHeavyClick()
    fun performStrongClick()
}

fun Modifier.hapticClickable(
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    onClick: () -> Unit,
): Modifier = composed {
    this.clickable(
        enabled = enabled,
        onClickLabel = onClickLabel,
        role = role,
    ) {
        DecibelHaptics.performStrongClick()
        onClick()
    }
}