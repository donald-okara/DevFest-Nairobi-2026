package ke.don.demos.talk

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalGridApi
import androidx.compose.foundation.layout.Grid
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DesktopWindows
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.github.donald_okara.components.text.SlideTitle
import ke.don.design.theme.DevFestColors
import ke.don.domain.LocalRevealStep
import ke.don.domain.values.Values
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

// Visual slides for the "why" half of the talk. Each reveal pops in with a spring.

/** 0 to 1 as the deck reaches [index], with a little overshoot. */
@Composable
private fun revealProgress(index: Int): Float {
    val shown = LocalRevealStep.current >= index
    val progress by animateFloatAsState(
        targetValue = if (shown) 1f else 0f,
        animationSpec = spring(dampingRatio = 0.55f, stiffness = Spring.StiffnessLow),
        label = "reveal-$index",
    )
    return progress
}

/** Pops content in: scales up from 60%, fades in, and settles from a slight [tilt]. */
private fun Modifier.popIn(progress: Float, tilt: Float = 0f) = graphicsLayer {
    val p = progress.coerceAtLeast(0f)
    alpha = p.coerceAtMost(1f)
    scaleX = 0.6f + 0.4f * p
    scaleY = 0.6f + 0.4f * p
    rotationZ = tilt * (1f - p.coerceAtMost(1f))
}

@Composable
private fun SlideFrame(title: String, kicker: String?, content: @Composable () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(Values.Dimens.largePadding),
        verticalArrangement = Arrangement.spacedBy(Values.Dimens.largePadding),
    ) {
        SlideTitle(title = title, kicker = kicker)
        Box(Modifier.fillMaxWidth().weight(1f)) { content() }
    }
}

// Slide 2: the feedback loop

private data class LoopNode(val heading: String, val body: String, val color: Color)

private val loopNodes = listOf(
    LoopNode("Better apps", "Apps that use the space", DevFestColors.Blue),
    LoopNode("More useful devices", "The foldable is worth opening", DevFestColors.Red),
    LoopNode("More users", "People buy the hardware", DevFestColors.Yellow),
    LoopNode("More incentive", "Developers build better apps", DevFestColors.Green),
)
val FEEDBACK_LOOP_STEPS = loopNodes.size

/**
 * Four cards around an ellipse, joined by arrows that draw themselves. The last step closes the
 * loop, and the arrows keep flowing around it.
 */
@Composable
fun FeedbackLoopSlide() = SlideFrame(title = "Chicken, meet egg", kicker = "No offence, Google") {
    val nodeProgress = loopNodes.indices.map { revealProgress(it + 1) }
    // Arrow i runs from node i to node i + 1 and draws when node i + 1 appears; the closing arrow comes with the last node.
    val arrowProgress = loopNodes.indices.map { i ->
        val target = if (LocalRevealStep.current >= minOf(i + 2, loopNodes.size)) 1f else 0f
        animateFloatAsState(target, tween(700, easing = FastOutSlowInEasing), label = "arrow-$i").value
    }
    val flow by rememberInfiniteTransition(label = "loop-flow").animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(2400, easing = LinearEasing)),
        label = "loop-flow-phase",
    )
    val closed = LocalRevealStep.current >= loopNodes.size
    val arrowColor = MaterialTheme.colorScheme.onSurfaceVariant

    BoxWithConstraints(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        val rx = maxWidth * 0.34f
        val ry = maxHeight * 0.36f
        // Angles of the four nodes: top, right, bottom, left.
        val angles = listOf(-90f, 0f, 90f, 180f)

        Canvas(Modifier.fillMaxSize()) {
            val center = Offset(size.width / 2, size.height / 2)
            val radii = Offset(rx.toPx(), ry.toPx())
            angles.forEachIndexed { i, start ->
                val end = angles[(i + 1) % angles.size].let { if (it < start) it + 360f else it }
                val gap = 34f
                drawLoopArrow(
                    center = center,
                    radii = radii,
                    startDeg = start + gap,
                    endDeg = end - gap,
                    progress = arrowProgress[i],
                    color = if (closed) loopNodes[i].color else arrowColor,
                    flowPhase = if (closed) flow else null,
                )
            }
        }

        if (closed) {
            Text(
                "Which came first?",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.popIn(revealProgress(loopNodes.size)),
            )
        }

        loopNodes.forEachIndexed { i, node ->
            val rad = angles[i] * PI.toFloat() / 180f
            LoopCard(
                node = node,
                modifier = Modifier
                    .offset(x = rx * cos(rad), y = ry * sin(rad))
                    .popIn(nodeProgress[i], tilt = if (i % 2 == 0) -6f else 6f),
            )
        }
    }
}

@Composable
private fun LoopCard(node: LoopNode, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.width(260.dp),
        shape = RoundedCornerShape(20.dp),
        color = MaterialTheme.colorScheme.surfaceContainerHigh,
        shadowElevation = 6.dp,
    ) {
        Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Box(Modifier.size(width = 36.dp, height = 6.dp).clip(CircleShape).background(node.color))
            Text(node.heading, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Text(node.body, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

private fun ellipsePoint(center: Offset, radii: Offset, deg: Float): Offset {
    val rad = deg * PI.toFloat() / 180f
    return Offset(center.x + radii.x * cos(rad), center.y + radii.y * sin(rad))
}

/** Draws part of the ellipse from [startDeg] towards [endDeg], with an arrowhead, up to [progress]. */
private fun DrawScope.drawLoopArrow(
    center: Offset,
    radii: Offset,
    startDeg: Float,
    endDeg: Float,
    progress: Float,
    color: Color,
    flowPhase: Float?,
) {
    if (progress <= 0f) return
    val sweep = (endDeg - startDeg) * progress
    val stroke = 6.dp.toPx()
    drawArc(
        color = color.copy(alpha = 0.85f),
        startAngle = startDeg,
        sweepAngle = sweep,
        useCenter = false,
        topLeft = Offset(center.x - radii.x, center.y - radii.y),
        size = Size(radii.x * 2, radii.y * 2),
        style = Stroke(width = stroke, cap = StrokeCap.Round),
    )
    // A bright pulse travelling along each arrow once the loop is closed.
    flowPhase?.let { phase ->
        val dot = ellipsePoint(center, radii, startDeg + (endDeg - startDeg) * phase)
        drawCircle(color = color, radius = stroke * 1.4f, center = dot)
    }
    // Arrowhead at the tip, pointing along the tangent.
    val tipDeg = startDeg + sweep
    val tip = ellipsePoint(center, radii, tipDeg)
    val behind = ellipsePoint(center, radii, tipDeg - 2f)
    val angle = atan2(tip.y - behind.y, tip.x - behind.x)
    val head = 22.dp.toPx()
    val spread = 0.5f
    val path = Path().apply {
        moveTo(tip.x, tip.y)
        lineTo(tip.x - head * cos(angle - spread), tip.y - head * sin(angle - spread))
        lineTo(tip.x - head * cos(angle + spread), tip.y - head * sin(angle + spread))
        close()
    }
    drawPath(path, color)
}

// Slide 4: follow the money

private data class Stat(
    val target: Int,
    val prefix: String,
    val suffix: String,
    val label: String,
    val source: String,
    val color: Color,
)

private val stats = listOf(
    Stat(580, "", "M+", "large-screen Android devices in use", "Android Developers, 2026", DevFestColors.Blue),
    Stat(9, "", "x", "more spent by people with a phone and a large screen", "Google internal data, 2026", DevFestColors.Green),
    Stat(14, "", "x", "more spent by foldable users", "Google internal data, 2026", DevFestColors.Red),
    Stat(3, "~", "x", "more time in-app, phone + tablet (entertainment, US)", "Google I/O 2025", DevFestColors.Yellow),
)
val FOLLOW_THE_MONEY_STEPS = stats.size + 1

/** Four numbers in a 2 x 2 grid with no visible tracks. Each counts up as it is revealed. */
@OptIn(ExperimentalGridApi::class)
@Composable
fun FollowTheMoneySlide() {
    Column(
        modifier = Modifier.fillMaxSize().padding(Values.Dimens.largePadding),
        verticalArrangement = Arrangement.spacedBy(Values.Dimens.mediumPadding),
    ) {
        SlideTitle(title = "Follow the money", kicker = "Source: Google")
        Grid(
            modifier = Modifier.fillMaxWidth().weight(1f),
            config = {
                val gap = 32.dp
                val cellWidth = ((constraints.maxWidth.toDp() - gap) / 2).coerceAtLeast(0.dp)
                val cellHeight = ((constraints.maxHeight.toDp() - gap) / 2).coerceAtLeast(0.dp)
                repeat(2) { column(cellWidth) }
                repeat(2) { row(cellHeight) }
                gap(gap)
            },
        ) {
            stats.forEachIndexed { i, stat -> StatCell(stat, index = i + 1) }
        }
        Text(
            text = "Correlation, not causation. Still your users.",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.popIn(revealProgress(stats.size + 1)),
        )
    }
}

@Composable
private fun StatCell(stat: Stat, index: Int) {
    val shown = LocalRevealStep.current >= index
    val count = remember { Animatable(0f) }
    LaunchedEffect(shown) {
        if (shown) {
            count.animateTo(stat.target.toFloat(), tween(1400, easing = FastOutSlowInEasing))
        } else {
            count.snapTo(0f)
        }
    }
    Column(
        modifier = Modifier.fillMaxSize().popIn(revealProgress(index)),
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "${stat.prefix}${count.value.roundToInt()}${stat.suffix}",
            style = MaterialTheme.typography.displayLarge,
            fontWeight = FontWeight.Black,
            color = stat.color,
        )
        Text(stat.label, style = MaterialTheme.typography.headlineSmall)
        Text(
            stat.source,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

// Slide 5: apps already doing this

private data class AppExample(
    val name: String,
    val bigScreen: String,
    val phone: String,
    val color: Color,
)

private val apps = listOf(
    AppExample("Notion", "Databases, docs, many columns", "Capture, search, quick edits", DevFestColors.Blue),
    AppExample("Canva", "The full design canvas", "Quick edits and sharing", DevFestColors.Green),
    AppExample("Lightroom", "Finish and organise", "Shoot and edit on the go", DevFestColors.Red),
    AppExample("Figma", "Design authoring", "Review and prototypes", DevFestColors.Yellow),
    AppExample("Power BI", "Build reports and models", "Check the metric", DevFestColors.Blue),
    AppExample("Notability", "Handwritten notes with a stylus", "Review notes, record audio", DevFestColors.Green),
)
val APPS_DOING_THIS_STEPS = apps.size

/** Six app cards in a 3 x 2 grid: what each does on a big screen and on a phone. */
@OptIn(ExperimentalGridApi::class)
@Composable
fun AppsDoingThisSlide() = SlideFrame(title = "Apps already doing this", kicker = "Same product, different jobs") {
    Grid(
        modifier = Modifier.fillMaxSize(),
        config = {
            val gap = 24.dp
            val cellWidth = ((constraints.maxWidth.toDp() - gap * 2) / 3).coerceAtLeast(0.dp)
            val cellHeight = ((constraints.maxHeight.toDp() - gap) / 2).coerceAtLeast(0.dp)
            repeat(3) { column(cellWidth) }
            repeat(2) { row(cellHeight) }
            gap(gap)
        },
    ) {
        apps.forEachIndexed { i, app ->
            AppCard(
                app = app,
                modifier = Modifier.popIn(revealProgress(i + 1), tilt = if (i % 2 == 0) -5f else 5f),
            )
        }
    }
}

@Composable
private fun AppCard(app: AppExample, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxSize(),
        shape = RoundedCornerShape(24.dp),
        color = MaterialTheme.colorScheme.surfaceContainerHigh,
        shadowElevation = 4.dp,
    ) {
        Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Box(
                    Modifier.size(44.dp).clip(RoundedCornerShape(12.dp)).background(app.color),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(app.name.first().toString(), color = Color.White, fontWeight = FontWeight.Black)
                }
                Text(app.name, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            }
            UseRow(Icons.Filled.DesktopWindows, app.bigScreen)
            UseRow(Icons.Filled.PhoneAndroid, app.phone)
        }
    }
}

@Composable
private fun UseRow(icon: ImageVector, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
        Text(text, style = MaterialTheme.typography.titleMedium)
    }
}

// Slide 8: different devices, different contexts

private enum class DeviceKind { Desktop, Tablet, Phone }

private data class DeviceContext(
    val kind: DeviceKind,
    val name: String,
    val engagement: String,
    val time: String,
    val color: Color,
)

private val contexts = listOf(
    DeviceContext(DeviceKind.Desktop, "Desktop", "Deep engagement", "Hours", DevFestColors.Blue),
    DeviceContext(DeviceKind.Tablet, "Tablet / foldable", "Expanded engagement", "Minutes to hours", DevFestColors.Green),
    DeviceContext(DeviceKind.Phone, "Phone", "Immediate engagement", "Seconds", DevFestColors.Red),
)
val CONTEXTS_STEPS = contexts.size

/** Three drawn devices, each with the kind of engagement it tends to get. */
@Composable
fun ContextsSlide() = SlideFrame(title = "Different devices, different contexts", kicker = "Tendencies, not rules") {
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Bottom,
    ) {
        contexts.forEachIndexed { i, context ->
            Column(
                modifier = Modifier.weight(1f).popIn(revealProgress(i + 1)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                DeviceDrawing(context.kind, context.color)
                Text(context.name, style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(
                    context.engagement,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                )
                Box(
                    Modifier
                        .clip(CircleShape)
                        .background(context.color.copy(alpha = 0.15f))
                        .border(1.dp, context.color, CircleShape)
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                ) {
                    Text(context.time, color = context.color, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

/** A simple device outline with a sketch of the layout that suits it. */
@Composable
private fun DeviceDrawing(kind: DeviceKind, accent: Color) {
    val (width, height) = when (kind) {
        DeviceKind.Desktop -> 300.dp to 220.dp
        DeviceKind.Tablet -> 240.dp to 180.dp
        DeviceKind.Phone -> 100.dp to 190.dp
    }
    val outline = MaterialTheme.colorScheme.onSurface
    val block = MaterialTheme.colorScheme.surfaceVariant
    Canvas(Modifier.size(width, height)) {
        val stroke = 4.dp.toPx()
        val screenBottom = if (kind == DeviceKind.Desktop) size.height * 0.78f else size.height
        val corner = CornerRadius(if (kind == DeviceKind.Phone) 18.dp.toPx() else 12.dp.toPx())
        drawRoundRect(outline, size = Size(size.width, screenBottom), cornerRadius = corner, style = Stroke(stroke))
        if (kind == DeviceKind.Desktop) {
            drawLine(outline, Offset(size.width / 2, screenBottom), Offset(size.width / 2, size.height - stroke), stroke)
            drawLine(outline, Offset(size.width * 0.3f, size.height - stroke), Offset(size.width * 0.7f, size.height - stroke), stroke, StrokeCap.Round)
        }
        val pad = 14.dp.toPx()
        val inner = Size(size.width - pad * 2, screenBottom - pad * 2)
        val r = CornerRadius(6.dp.toPx())
        fun rect(x: Float, y: Float, w: Float, h: Float, color: Color = block) =
            drawRoundRect(color, Offset(pad + x * inner.width, pad + y * inner.height), Size(w * inner.width, h * inner.height), r)
        when (kind) {
            DeviceKind.Desktop -> {
                rect(0f, 0f, 0.18f, 1f)
                rect(0.22f, 0f, 0.48f, 1f)
                rect(0.74f, 0f, 0.26f, 0.48f, accent.copy(alpha = 0.5f))
                rect(0.74f, 0.52f, 0.26f, 0.48f)
            }
            DeviceKind.Tablet -> {
                rect(0f, 0f, 0.38f, 1f)
                rect(0.42f, 0f, 0.58f, 1f, accent.copy(alpha = 0.35f))
            }
            DeviceKind.Phone -> {
                rect(0f, 0.04f, 1f, 0.3f)
                rect(0f, 0.4f, 1f, 0.3f)
                rect(0.1f, 0.78f, 0.8f, 0.16f, accent)
            }
        }
    }
}
