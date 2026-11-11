package ke.don.design.brand

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import ke.don.design.theme.DevFestColors
import ke.don.resources.Resources
import org.jetbrains.compose.resources.Font
import kotlin.math.PI
import kotlin.math.sin

private val Letters = listOf(
    'D' to DevFestColors.Red,
    'E' to DevFestColors.Yellow,
    'V' to DevFestColors.Green,
    'F' to DevFestColors.Blue,
    'E' to DevFestColors.Yellow,
    'S' to DevFestColors.Red,
    'T' to DevFestColors.Green,
)

/** Google Sans cap height as a fraction of the em; used to crop the canvas to the capitals. */
private const val CAP_HEIGHT = 0.72f

/** Thickness of the same-color outline that puffs each glyph up, as a fraction of the font size. */
private const val INFLATE = 0.045f

/** Letters overlap by this fraction of the font size, so the balloons press into each other. */
private const val OVERLAP = 0.035f

/** At full squeeze the word is this much narrower than at rest. */
private const val SQUEEZE_WIDTH = 0.16f

/**
 * The DEVFEST balloon wordmark from the DevFest Nairobi 2026 template, drawn live
 * rather than as an image so it can breathe.
 *
 * Each letter is a fill plus a thick round-joined stroke of the same color, which
 * gives the inflated balloon outline, and a shine highlight clipped to the letter.
 * When [animate] is on, the letters scrunch together, hold, then spring back past
 * rest like a squeezed balloon, on an infinite loop, while each one bobs and tilts
 * a little out of phase with its neighbours.
 *
 * @param letterSize Font size of the DEVFEST letters. Everything else scales with it.
 * @param showNairobi Whether to draw the NAIROBI tag under the letters.
 * @param showYear Whether to draw the stacked 20/26 after the T.
 * @param inkColor Color of the NAIROBI and year text.
 * @param tagBackground Fill behind the NAIROBI tag. Match the slide background so the tag cuts into the letters as in the template.
 */
@Composable
fun DevFestWordmark(
    modifier: Modifier = Modifier,
    letterSize: TextUnit = 72.sp,
    animate: Boolean = true,
    showNairobi: Boolean = true,
    showYear: Boolean = true,
    inkColor: Color = MaterialTheme.colorScheme.onSurface,
    tagBackground: Color = MaterialTheme.colorScheme.surface,
) {
    val measurer = rememberTextMeasurer()
    val density = LocalDensity.current

    val balloonStyle = TextStyle(
        fontFamily = FontFamily(Font(Resources.Font.GOOGLE_SANS_FLEX_BALLOON, FontWeight.Black)),
        fontWeight = FontWeight.Black,
        fontSize = letterSize,
    )
    val tagStyle = TextStyle(
        fontFamily = FontFamily(Font(Resources.Font.GOOGLE_SANS_BOLD, FontWeight.Bold)),
        fontWeight = FontWeight.Bold,
        fontSize = letterSize * 0.24f,
        letterSpacing = 0.04.em,
        color = inkColor,
    )
    val yearStyle = tagStyle.copy(fontSize = letterSize * 0.3f, lineHeight = letterSize * 0.3f, letterSpacing = 0.em)

    val glyphs = remember(balloonStyle, measurer) {
        Letters.map { (char, _) -> measurer.measure(char.toString(), balloonStyle) }
    }
    val nairobi = remember(tagStyle, measurer) { measurer.measure("NAİROBİ", tagStyle) }
    val year = remember(yearStyle, measurer) { measurer.measure("20\n26", yearStyle) }

    val fontPx = with(density) { letterSize.toPx() }
    val inflate = fontPx * INFLATE
    val pad = fontPx * 0.08f // room for the outline and the bob on every side
    val capPx = fontPx * CAP_HEIGHT

    // Rest-pose left edges of each glyph box.
    val lefts = remember(glyphs, fontPx) {
        var x = 0f
        glyphs.map { g -> x.also { x += g.size.width - fontPx * OVERLAP } }
    }
    val wordWidth = lefts.last() + glyphs.last().size.width
    val yearGap = fontPx * 0.12f
    val tagDrop = if (showNairobi) nairobi.size.height * 0.55f else 0f

    val width = pad * 2 + wordWidth + if (showYear) yearGap + year.size.width else 0f
    val height = pad * 2 + capPx + tagDrop

    val motion = if (animate) rememberBalloonMotion() else null

    Canvas(
        modifier
            .size(with(density) { Size(width, height).toDpSize() })
            .semantics { contentDescription = "DevFest Nairobi 2026" }
    ) {
        val squeeze = motion?.squeeze?.value ?: 0f
        val phase = motion?.phase?.value ?: 0f
        val centerX = pad + wordWidth / 2f
        val narrow = 1f - SQUEEZE_WIDTH * squeeze

        // Where each letter's center sits this frame: pulled toward the word's middle.
        val centers = glyphs.mapIndexed { i, g ->
            centerX + (pad + lefts[i] + g.size.width / 2f - centerX) * narrow
        }

        glyphs.forEachIndexed { i, glyph ->
            val wobble = phase + i * 0.9f
            drawBalloonLetter(
                glyph = glyph,
                color = Letters[i].second,
                center = Offset(centers[i], pad + capPx / 2f + sin(wobble) * fontPx * 0.025f),
                capPx = capPx,
                inflate = inflate,
                // Squashed from the sides while scrunched, stretched wide on the rebound.
                scaleX = 1f - 0.10f * squeeze,
                scaleY = 1f + 0.07f * squeeze,
                rotation = sin(wobble + 1f) * 2.5f,
            )
        }

        if (showNairobi) {
            // Sits under V and F, biting into the letters like the printed tag.
            val tagCenter = (centers[2] + centers[3]) / 2f
            val tagPadX = fontPx * 0.05f
            val tagTop = pad + capPx - nairobi.size.height * 0.45f
            drawRect(
                color = tagBackground,
                topLeft = Offset(tagCenter - nairobi.size.width / 2f - tagPadX, tagTop),
                size = Size(nairobi.size.width + tagPadX * 2, nairobi.size.height.toFloat()),
            )
            drawText(nairobi, topLeft = Offset(tagCenter - nairobi.size.width / 2f, tagTop))
        }

        if (showYear) {
            // Follows the T so the year rides the squeeze.
            val tRight = centers.last() + glyphs.last().size.width / 2f * (1f - 0.10f * squeeze)
            drawText(
                year,
                topLeft = Offset(tRight + yearGap, pad + capPx - year.size.height * 0.92f),
            )
        }
    }
}

private class BalloonMotion(val squeeze: State<Float>, val phase: State<Float>)

@Composable
private fun rememberBalloonMotion(): BalloonMotion {
    val transition = rememberInfiniteTransition(label = "devfest-balloon")

    // 0 = rest, 1 = fully scrunched, negative = overshooting wide on the rebound.
    val squeeze = transition.animateFloat(
        initialValue = 0f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 3200
                0f at 0
                0f at 400 using CubicBezierEasing(0.5f, 0f, 0.75f, 1f)
                1f at 1300 // slow squeeze in
                1f at 1600 using CubicBezierEasing(0.2f, 0f, 0f, 1f)
                -0.28f at 1900 // let go: spring past rest
                0.12f at 2200 using FastOutSlowInEasing
                -0.04f at 2450
                0f at 2700
                0f at 3200
            },
            repeatMode = RepeatMode.Restart,
        ),
        label = "squeeze",
    )

    val phase = transition.animateFloat(
        initialValue = 0f,
        targetValue = (2 * PI).toFloat(),
        animationSpec = infiniteRepeatable(tween(4000, easing = LinearEasing)),
        label = "bob",
    )

    return remember(squeeze, phase) { BalloonMotion(squeeze, phase) }
}

private fun DrawScope.drawBalloonLetter(
    glyph: TextLayoutResult,
    color: Color,
    center: Offset,
    capPx: Float,
    inflate: Float,
    scaleX: Float,
    scaleY: Float,
    rotation: Float,
) {
    val w = glyph.size.width.toFloat()
    // Puts the top of the capitals at y = 0 in the letter's local space.
    val textTop = Offset(0f, -(glyph.firstBaseline - capPx))
    val local = Rect(-inflate, -inflate, w + inflate, capPx + inflate)

    withTransform({
        translate(center.x - w / 2f, center.y - capPx / 2f)
        rotate(rotation, pivot = Offset(w / 2f, capPx / 2f))
        scale(scaleX, scaleY, pivot = Offset(w / 2f, capPx / 2f))
    }) {
        drawIntoCanvas { canvas ->
            // Own layer, so the shine below can be clipped to this letter's pixels only.
            canvas.saveLayer(local, Paint())
            drawText(
                glyph,
                color = color,
                topLeft = textTop,
                drawStyle = Stroke(width = inflate * 2f, join = StrokeJoin.Round, cap = StrokeCap.Round),
            )
            drawText(glyph, color = color, topLeft = textTop, drawStyle = Fill)

            val shine = Path().apply {
                moveTo(w * 0.16f, capPx * 0.42f)
                quadraticTo(w * 0.14f, capPx * 0.08f, w * 0.42f, capPx * 0.02f)
            }
            drawPath(
                path = shine,
                color = Color.White.copy(alpha = 0.45f),
                style = Stroke(width = capPx * 0.05f, cap = StrokeCap.Round),
                blendMode = BlendMode.SrcAtop,
            )
            canvas.restore()
        }
    }
}
