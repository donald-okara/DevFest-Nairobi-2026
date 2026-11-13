package ke.don.introduction

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.donald_okara.components.charts.Confetti
import ke.don.design.brand.DevFestWordmark
import ke.don.design.theme.DevFestColors
import ke.don.domain.WORDMARK_SHARED_KEY
import ke.don.domain.deckSharedBounds
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.math.sin
import kotlin.random.Random

/**
 * Closing slide, everything centred:
 * - the DEVFEST wordmark grows back out of the header,
 * - "Thank you!" drops in letter by letter in Google colours, then keeps a gentle wave,
 * - "Asante sana" follows underneath,
 * - balloons drift up and confetti falls the whole time,
 * - a click anywhere fires a confetti burst.
 */
@Composable
fun ClosingScreen(
    modifier: Modifier = Modifier,
    slidesUrl: String = "devfest-nairobi-2026-tau.vercel.app/?slides",
) {
    val time by rememberSeconds()
    var bursts by remember { mutableIntStateOf(0) }
    var burst by remember { mutableStateOf(false) }
    LaunchedEffect(bursts) {
        if (bursts == 0) delay(900) // first burst lands with the letters
        burst = true
        delay(1300)
        burst = false
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .clickable(interactionSource = remember { MutableInteractionSource() }, indication = null) { bursts++ },
        contentAlignment = Alignment.Center,
    ) {
        val balloons = remember { List(9) { Balloon.random(it) } }
        val pieces = remember { List(90) { ConfettiPiece.random() } }
        Canvas(Modifier.fillMaxSize()) {
            balloons.forEach { drawBalloon(it, time) }
            pieces.forEach { drawConfettiPiece(it, time) }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(horizontal = 48.dp),
        ) {
            DevFestWordmark(letterSize = 64.sp, modifier = Modifier.deckSharedBounds(WORDMARK_SHARED_KEY))
            Spacer(Modifier.height(40.dp))
            WavingTitle(text = "Thank you!", time = time)
            Spacer(Modifier.height(8.dp))
            FadeUp(delayMillis = 1100) {
                Text(
                    "Asante sana",
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            Spacer(Modifier.height(32.dp))
            FadeUp(delayMillis = 1500) {
                Text(
                    "Meet your users where they are.",
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center,
                )
            }
            Spacer(Modifier.height(32.dp))
            FadeUp(delayMillis = 1900) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        "Donald Isoe",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(Modifier.height(12.dp))
                    Box(
                        Modifier
                            .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
                            .padding(horizontal = 20.dp, vertical = 8.dp)
                    ) {
                        Text(
                            "Slides and demos · $slidesUrl",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary,
                        )
                    }
                }
            }
        }

        Confetti(trigger = burst, modifier = Modifier.fillMaxSize())
    }
}

/** Seconds since this composable entered, ticking every frame. */
@Composable
private fun rememberSeconds(): State<Float> = produceState(0f) {
    val start = withFrameNanos { it }
    while (true) {
        withFrameNanos { now -> value = (now - start) / 1_000_000_000f }
    }
}

/** Each letter springs down into place in turn, then bobs on a wave that runs along the word. */
@Composable
private fun WavingTitle(text: String, time: Float) {
    val drops = remember(text) { text.map { Animatable(0f) } }
    LaunchedEffect(text) {
        drops.forEachIndexed { i, drop ->
            launch {
                delay(80L * i)
                drop.animateTo(1f, spring(dampingRatio = 0.45f, stiffness = Spring.StiffnessLow))
            }
        }
    }
    Row(verticalAlignment = Alignment.Bottom) {
        text.forEachIndexed { i, char ->
            val p = drops[i].value
            val wave = sin(time * 3f - i * 0.6f) * 10f * p.coerceIn(0f, 1f)
            Text(
                text = char.toString(),
                fontSize = 120.sp,
                fontWeight = FontWeight.Black,
                color = if (char == ' ') Color.Transparent else DevFestColors.cycle[i % DevFestColors.cycle.size],
                modifier = Modifier.graphicsLayer {
                    alpha = p.coerceIn(0f, 1f)
                    translationY = (1f - p) * -220f + wave
                    rotationZ = (1f - p) * (if (i % 2 == 0) -25f else 25f)
                },
            )
        }
    }
}

/** Fades and lifts [content] in after [delayMillis]. */
@Composable
private fun FadeUp(delayMillis: Int, content: @Composable () -> Unit) {
    val p = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        delay(delayMillis.toLong())
        p.animateTo(1f, tween(600))
    }
    Box(Modifier.graphicsLayer {
        alpha = p.value
        translationY = (1f - p.value) * 40f
    }) { content() }
}

// Balloons: they rise from below the slide, sway, and loop.

private data class Balloon(
    val x: Float,      // 0..1 across the slide
    val speed: Float,  // slide heights per second
    val phase: Float,  // 0..1 head start
    val radius: Float, // dp
    val color: Color,
) {
    companion object {
        fun random(i: Int) = Random(i * 7919).let { r ->
            // Keep the middle third mostly clear so the text stays readable.
            val side = if (i % 2 == 0) r.nextFloat() * 0.3f else 0.7f + r.nextFloat() * 0.3f
            Balloon(
                x = side,
                speed = 0.05f + r.nextFloat() * 0.05f,
                phase = r.nextFloat(),
                radius = 28f + r.nextFloat() * 22f,
                color = DevFestColors.cycle[i % DevFestColors.cycle.size],
            )
        }
    }
}

private fun DrawScope.drawBalloon(b: Balloon, time: Float) {
    val r = b.radius.dp.toPx()
    val travel = size.height + r * 6
    val y = size.height + r * 2 - ((time * b.speed + b.phase) % 1f) * travel
    val x = b.x * size.width + sin(time * 1.3f + b.phase * 10f) * r * 0.6f
    val bodyHeight = r * 2.4f
    // String
    val string = Path().apply {
        moveTo(x, y + bodyHeight / 2)
        cubicTo(x - r * 0.3f, y + bodyHeight, x + r * 0.3f, y + bodyHeight * 1.3f, x, y + bodyHeight * 1.7f)
    }
    drawPath(string, b.color.copy(alpha = 0.6f), style = Stroke(width = 2.dp.toPx()))
    // Body and knot
    drawOval(b.color.copy(alpha = 0.85f), Offset(x - r, y - bodyHeight / 2), Size(r * 2, bodyHeight))
    val knot = Path().apply {
        moveTo(x, y + bodyHeight / 2 - 2f)
        lineTo(x - r * 0.18f, y + bodyHeight / 2 + r * 0.25f)
        lineTo(x + r * 0.18f, y + bodyHeight / 2 + r * 0.25f)
        close()
    }
    drawPath(knot, b.color)
    // Shine, like the wordmark's letters
    drawOval(Color.White.copy(alpha = 0.45f), Offset(x - r * 0.55f, y - bodyHeight * 0.35f), Size(r * 0.35f, r * 0.7f))
}

// Confetti: small spinning rectangles falling and drifting.

private data class ConfettiPiece(
    val x: Float,
    val speed: Float,
    val phase: Float,
    val spin: Float,
    val drift: Float,
    val w: Float,
    val h: Float,
    val color: Color,
) {
    companion object {
        private val random = Random(2026)
        fun random() = ConfettiPiece(
            x = random.nextFloat(),
            speed = 0.08f + random.nextFloat() * 0.1f,
            phase = random.nextFloat(),
            spin = (random.nextFloat() - 0.5f) * 360f,
            drift = random.nextFloat() * 2f * PI.toFloat(),
            w = 6f + random.nextFloat() * 6f,
            h = 10f + random.nextFloat() * 8f,
            color = DevFestColors.cycle[random.nextInt(DevFestColors.cycle.size)],
        )
    }
}

private fun DrawScope.drawConfettiPiece(c: ConfettiPiece, time: Float) {
    val margin = 40f
    val y = ((time * c.speed + c.phase) % 1f) * (size.height + margin * 2) - margin
    val x = c.x * size.width + sin(time * 1.5f + c.drift) * 30f
    val w = c.w.dp.toPx()
    val h = c.h.dp.toPx()
    rotate(degrees = time * c.spin, pivot = Offset(x, y)) {
        drawRect(c.color.copy(alpha = 0.8f), Offset(x - w / 2, y - h / 2), Size(w, h))
    }
}
