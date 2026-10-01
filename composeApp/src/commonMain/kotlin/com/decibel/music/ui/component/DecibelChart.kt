package com.decibel.music.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.decibel.music.ui.theme.LocalIsDarkTheme

@Composable
fun DecibelChartButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val isDark = LocalIsDarkTheme.current
    // Dark: indigo-tinted pill on the dark page. Light: soft indigo pill on white page.
    val containerColor = if (isDark) Color(0xFF0F0F1A) else Color(0xFFE8E6FB)
    val borderColor = if (isDark) Color(0xFF28283D) else Color(0xFFB0B0EB)
    val labelColor = if (isDark) Color(0xFFB8B8B8) else Color(0xFF2A2A5C)
    Surface(
        modifier = modifier,
        onClick = onClick,
        shape = RoundedCornerShape(24.dp),
        color = containerColor,
        border = BorderStroke(1.dp, borderColor)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            // Sparkles icon
            Text(
                text = "",
                fontSize = 13.sp,
                modifier = Modifier.padding(end = 8.dp)
            )

            // Text
            Text(
                text = "Introducing Decibel Charts",
                fontSize = 13.sp,
                color = labelColor,
                fontWeight = FontWeight.Normal
            )
        }
    }
}


@Preview
@Composable
fun PreviewDecibelChartButton() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        DecibelChartButton(onClick = {})
    }
}