package ke.don.ski.presentation.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ke.don.domain.LocalRevealStep
import ke.don.domain.SlideConfig
import ke.don.domain.frames.SkiFrame
import ke.don.domain.values.Values

/** The size slides are laid out at before they're scaled down into a thumbnail. */
private val VirtualSlideWidth = 1280.dp
private val VirtualSlideHeight = 720.dp
private val ThumbnailWidth = 192.dp

/**
 * The presenter panel's bottom bar (toggled with ↓ or H): live thumbnails of the previous and next
 * slides either side of the current position. Both show fully revealed, so slides with progressive
 * reveals preview everything they'll show. Clicking a thumbnail jumps to that slide.
 */
@Composable
fun SlidePreviewBar(
    slides: List<SlideConfig>,
    currentIndex: Int,
    frame: SkiFrame,
    onJumpTo: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    frame.Render(
        modifier = modifier.padding(Values.Dimens.mediumPadding),
        header = null,
        footer = null,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(Values.Dimens.smallPadding),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Values.Dimens.mediumPadding),
        ) {
            SlidePreview(
                caption = "Previous",
                index = currentIndex - 1,
                slides = slides,
                revealStep = { it.steps },
                onJumpTo = onJumpTo,
                modifier = Modifier.weight(1f),
            )

            Column(
                modifier = Modifier.weight(0.8f),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "Slide ${currentIndex + 1} of ${slides.size}",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Text(
                    text = slides[currentIndex].label,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = "↓, H or swipe up hides this · F for flashcards",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }

            SlidePreview(
                caption = "Next",
                index = currentIndex + 1,
                slides = slides,
                revealStep = { it.steps },
                onJumpTo = onJumpTo,
                modifier = Modifier.weight(1f),
                thumbnailFirst = false,
            )
        }
    }
}

/**
 * One side of the bar: a thumbnail and its caption, or an end-of-deck message when [index] is out
 * of range. [thumbnailFirst] puts the thumbnail on the outer edge, so both sides mirror each other.
 */
@Composable
private fun SlidePreview(
    caption: String,
    index: Int,
    slides: List<SlideConfig>,
    revealStep: (SlideConfig) -> Int,
    onJumpTo: (Int) -> Unit,
    modifier: Modifier = Modifier,
    thumbnailFirst: Boolean = true,
) {
    val slide = slides.getOrNull(index)
    val align = if (thumbnailFirst) Alignment.Start else Alignment.End

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(
            Values.Dimens.smallPadding,
            if (thumbnailFirst) Alignment.Start else Alignment.End,
        ),
    ) {
        val labels: @Composable () -> Unit = {
            Column(horizontalAlignment = align, modifier = Modifier.weight(1f, fill = false)) {
                Text(
                    text = caption,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                )
                Text(
                    text = slide?.label ?: if (index < 0) "Start of deck" else "End of deck",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    textAlign = if (thumbnailFirst) TextAlign.Start else TextAlign.End,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                slide?.section?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
        }

        if (!thumbnailFirst) labels()
        if (slide != null) {
            SlideThumbnail(
                slide = slide,
                revealStep = revealStep(slide),
                onClick = { onJumpTo(index) },
            )
        }
        if (thumbnailFirst) labels()
    }
}

/**
 * Renders [slide] at [VirtualSlideWidth] x [VirtualSlideHeight] and scales it down to [width], so
 * the thumbnail keeps the slide's real layout. An overlay takes every pointer event, so the
 * miniature slide is never interactive: a click jumps to it instead.
 */
@Composable
private fun SlideThumbnail(
    slide: SlideConfig,
    revealStep: Int,
    onClick: () -> Unit,
    width: Dp = ThumbnailWidth,
) {
    val scale = width / VirtualSlideWidth
    val shape = RoundedCornerShape(8.dp)
    Box(
        modifier = Modifier
            .size(width, width * 9 / 16)
            .clip(shape)
            .border(1.dp, MaterialTheme.colorScheme.outlineVariant, shape),
    ) {
        Box(
            modifier = Modifier
                .wrapContentSize(Alignment.TopStart, unbounded = true)
                .requiredSize(VirtualSlideWidth, VirtualSlideHeight)
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                    transformOrigin = TransformOrigin(0f, 0f)
                },
        ) {
            Surface(Modifier.fillMaxSize()) {
                CompositionLocalProvider(LocalRevealStep provides revealStep) {
                    Box(Modifier.fillMaxSize().padding(Values.Dimens.mediumPadding)) {
                        slide.Render()
                    }
                }
            }
        }
        Box(Modifier.matchParentSize().clickable(onClick = onClick))
    }
}
