package com.maxrave.simpmusic.utils

import platform.UIKit.UIImpactFeedbackGenerator
import platform.UIKit.UIImpactFeedbackStyle

actual object DecibelHaptics {
    actual fun performClick() {
        try {
            UIImpactFeedbackGenerator(UIImpactFeedbackStyle.UIImpactFeedbackStyleHeavy).impactOccurred()
        } catch (_: Exception) {}
    }
    actual fun performHeavyClick() {
        try {
            UIImpactFeedbackGenerator(UIImpactFeedbackStyle.UIImpactFeedbackStyleHeavy).impactOccurred()
        } catch (_: Exception) {}
    }
    actual fun performStrongClick() {
        try {
            UIImpactFeedbackGenerator(UIImpactFeedbackStyle.UIImpactFeedbackStyleHeavy).impactOccurred()
        } catch (_: Exception) {}
    }
}