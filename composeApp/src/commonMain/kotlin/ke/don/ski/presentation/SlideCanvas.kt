package ke.don.ski.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * How the slide area fits whatever surface the deck runs on.
 *
 * The slide is letterboxed to [aspectRatio] and centred, so a phone, an ultrawide monitor and a
 * projector all show the same composition. When the box is narrower than [referenceWidth] the slide
 * is laid out as if it were [referenceWidth] wide and scaled down (by lowering the density, so text
 * stays sharp and touch targets line up). Slides therefore keep their layout on small screens
 * instead of reflowing or overflowing.
 *
 * @param aspectRatio Width divided by height of the slide area. 16:9 by default.
 * @param referenceWidth The width slides are designed for. Matches the preview thumbnails.
 * @param scaleUp Also scale up when the box is wider than [referenceWidth], so the slide looks
 *   identical on every screen. Off by default to keep large windows at their native text size.
 */
data class SlideCanvas(
    val aspectRatio: Float = 16f / 9f,
    val referenceWidth: Dp = 1280.dp,
    val scaleUp: Boolean = false,
)

/**
 * Hosts [content] in a box locked to [canvas]'s aspect ratio and centred in the available space.
 * A null [canvas] leaves the content filling the space, as before aspect ratio locking existed.
 */
@Composable
fun SlideCanvasBox(
    canvas: SlideCanvas?,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    if (canvas == null) {
        Box(modifier.fillMaxSize()) { content() }
        return
    }

    BoxWithConstraints(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        val ratio = canvas.aspectRatio
        val width = when {
            !constraints.hasBoundedHeight -> maxWidth
            !constraints.hasBoundedWidth -> maxHeight * ratio
            maxWidth / maxHeight > ratio -> maxHeight * ratio
            else -> maxWidth
        }
        val height = width / ratio

        val rawScale = if (canvas.referenceWidth > 0.dp) width / canvas.referenceWidth else 1f
        val scale = if (canvas.scaleUp) rawScale else rawScale.coerceAtMost(1f)
        val density = LocalDensity.current

        Box(Modifier.size(width, height).clipToBounds()) {
            if (scale == 1f || scale <= 0f) {
                content()
            } else {
                CompositionLocalProvider(
                    LocalDensity provides Density(density.density * scale, density.fontScale)
                ) {
                    content()
                }
            }
        }
    }
}
