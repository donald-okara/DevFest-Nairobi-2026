package ke.don.introduction

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ke.don.design.brand.DevFestWordmark
import ke.don.design.theme.DevFestColors
import ke.don.domain.WORDMARK_SHARED_KEY
import ke.don.domain.deckSharedBounds
import ke.don.resources.Resources
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

/**
 * Title slide in the DevFest Nairobi 2026 style: the animated DEVFEST wordmark,
 * the talk title, the speaker, and the event details, with the speaker photo in
 * the template's broken Google-color ring.
 */
@Composable
fun IntroductionScreen(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 48.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextSegment(
            title = "Adaptive UI in Jetpack Compose",
            presenter = "Donald Isoe",
            role = "Meet your users where they are",
        )

        SpeakerPhoto(image = Resources.Images.DON)
    }
}

@Composable
private fun TextSegment(
    modifier: Modifier = Modifier,
    title: String,
    presenter: String,
    role: String? = null,
) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .fillMaxWidth(0.6f),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        DevFestWordmark(letterSize = 96.sp, modifier = Modifier.deckSharedBounds(WORDMARK_SHARED_KEY))

        Spacer(Modifier.height(56.dp))

        Text(
            title,
            style = MaterialTheme.typography.displayMedium,
            textAlign = TextAlign.Start
        )

        Spacer(Modifier.height(32.dp))

        Text(
            presenter,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        role?.let {
            Spacer(Modifier.height(8.dp))
            Text(
                it,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(Modifier.height(32.dp))

        Text(
            "November 14 · The Sarit Expo Centre",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

/** Arcs of the template's photo ring: start angle, sweep, color, and whether it sits on the outer track. */
private val RingArcs = listOf(
    RingArc(start = 190f, sweep = 78f, color = DevFestColors.Green, outer = true),
    RingArc(start = 276f, sweep = 70f, color = DevFestColors.Blue, outer = false),
    RingArc(start = 318f, sweep = 72f, color = DevFestColors.Yellow, outer = true),
    RingArc(start = 25f, sweep = 60f, color = DevFestColors.Blue, outer = false),
    RingArc(start = 95f, sweep = 72f, color = DevFestColors.Red, outer = true),
    RingArc(start = 172f, sweep = 62f, color = DevFestColors.Blue, outer = false),
)

private data class RingArc(val start: Float, val sweep: Float, val color: Color, val outer: Boolean)

@Composable
internal fun SpeakerPhoto(
    image: DrawableResource,
    modifier: Modifier = Modifier,
    size: Dp = 440.dp,
) {
    val spin by rememberInfiniteTransition(label = "ring").animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(tween(60_000, easing = LinearEasing)),
        label = "ring-spin"
    )

    Box(modifier.size(size), contentAlignment = Alignment.Center) {
        Canvas(Modifier.fillMaxSize().rotate(spin)) {
            val stroke = this.size.minDimension * 0.022f
            RingArcs.forEach { arc ->
                val inset = if (arc.outer) stroke / 2f else stroke * 2.6f
                drawArc(
                    color = arc.color,
                    startAngle = arc.start,
                    sweepAngle = arc.sweep,
                    useCenter = false,
                    topLeft = Offset(inset, inset),
                    size = Size(this.size.width - inset * 2, this.size.height - inset * 2),
                    style = Stroke(width = stroke, cap = StrokeCap.Round)
                )
            }
        }

        Image(
            painter = painterResource(image),
            contentDescription = "Speaker photo",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(size * 0.8f)
                .clip(CircleShape)
        )
    }
}
