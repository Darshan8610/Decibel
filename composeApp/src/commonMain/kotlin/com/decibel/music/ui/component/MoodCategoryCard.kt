package com.decibel.music.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import com.decibel.music.utils.hapticClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.decibel.music.extension.angledGradientBackground
import com.decibel.music.ui.theme.typo
import org.jetbrains.compose.resources.painterResource
import com.decibel.music.composeapp.generated.resources.Res
import com.decibel.music.composeapp.generated.resources.monochrome

/**
 * A "Moods & Genres" browse category tile in dark grey.
 */
@Composable
fun MoodCategoryCard(
    title: String,
    artworkUrl: String?,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val badge = painterResource(Res.drawable.monochrome)
    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .aspectRatio(2f)
                .clip(RoundedCornerShape(10.dp))
                .background(Color(0xFF1E1E1E))
                .hapticClickable(onClick = onClick),
    ) {
        if (artworkUrl != null) {
            AsyncImage(
                model = artworkUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier =
                    Modifier
                        .align(Alignment.BottomEnd)
                        // Offset past the corner first, THEN rotate: the tile clips its children,
                        // so letting the cover run off the edge is what produces the cut-off
                        // diagonal instead of a square pasted inside the card.
                        .offset(x = 8.dp, y = 12.dp)
                        .size(64.dp)
                        .rotate(25f)
                        .clip(RoundedCornerShape(2.dp)),
            )
        }
        Text(
            text = title,
            style = typo().titleSmall,
            color = Color.White,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier =
                Modifier
                    .align(Alignment.TopStart)
                    // Safe width ends at the LEFT edge of the tilted cover, not just clear of the
                    // badge. The cover is 64.dp rotated 25°, so its bounding box grows to
                    // 64*(cos25+sin25) ≈ 85.dp — about 10.dp wider on each side — and it is offset
                    // 8.dp past the right edge. That leaves ~66.dp of it inside the tile.
                    .padding(start = 12.dp, top = 12.dp, bottom = 12.dp, end = 72.dp),
        )
    }
}
