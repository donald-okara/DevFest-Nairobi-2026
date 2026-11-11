package ke.don.demos.playground

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.Posture
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.computeWindowSizeClass
import kotlin.math.ceil
import kotlin.math.min
import kotlin.math.roundToInt

/** Width classes, including the large and extra-large classes added in androidx.window 1.5. */
enum class WidthClass(val label: String, val minDp: Float) {
    Compact("Compact", 0f),
    Medium("Medium", 600f),
    Expanded("Expanded", 840f),
    Large("Large", 1200f),
    ExtraLarge("Extra-large", 1600f);

    companion object {
        fun of(widthDp: Float): WidthClass = entries.last { widthDp >= it.minDp }
    }
}

/**
 * The simulated window the demo app runs in.
 *
 * Inside the deck, `currentWindowAdaptiveInfo()` would report the real (fullscreen) window,
 * so the demo app reads this instead. In a real app, call `currentWindowAdaptiveInfo()`.
 */
@Immutable
data class PlaygroundWindow(val widthDp: Float, val heightDp: Float) {
    val widthClass: WidthClass get() = WidthClass.of(widthDp)

    val adaptiveInfo: WindowAdaptiveInfo
        get() = WindowAdaptiveInfo(
            windowSizeClass = WindowSizeClass.BREAKPOINTS_V1.computeWindowSizeClass(widthDp, heightDp),
            windowPosture = Posture(),
        )
}

val LocalPlaygroundWindow = staticCompositionLocalOf { PlaygroundWindow(360f, PLAYGROUND_HEIGHT_DP) }

/** A named window width the presenter can snap to. */
data class WindowPreset(val label: String, val widthDp: Float)

val PlaygroundPresets = listOf(
    WindowPreset("Phone", 360f),
    WindowPreset("Foldable", 673f),
    WindowPreset("Tablet", 900f),
    WindowPreset("Laptop", 1280f),
    WindowPreset("Desktop", 1600f),
)

const val PLAYGROUND_HEIGHT_DP = 720f
private const val MIN_WIDTH_DP = 320f
private const val MAX_WIDTH_DP = 1700f

/**
 * A resizable, scaled-down window. The content is laid out at the simulated size in dp, then
 * scaled to fit the slide, so a 1600dp "desktop" really gets 1600dp of layout space.
 *
 * Drag the handle on the right edge, or tap a preset. The scale is fixed by the largest width,
 * so the window visibly grows and shrinks as you resize it.
 */
@Composable
fun PlaygroundWindowFrame(
    modifier: Modifier = Modifier,
    initialWidthDp: Float = 360f,
    content: @Composable () -> Unit,
) {
    var targetWidth by rememberSaveable { mutableFloatStateOf(initialWidthDp) }
    var dragging by remember { mutableStateOf(false) }
    val animatedWidth by animateFloatAsState(targetWidth, label = "playgroundWidth")
    // Follow the finger exactly while dragging; animate only for preset jumps.
    val widthDp = if (dragging) targetWidth else animatedWidth
    val window = PlaygroundWindow(widthDp, PLAYGROUND_HEIGHT_DP)

    Column(modifier, verticalArrangement = Arrangement.spacedBy(12.dp)) {
        WindowToolbar(
            window = window,
            onPreset = { targetWidth = it.widthDp },
        )

        BoxWithConstraints(Modifier.fillMaxWidth().weight(1f)) {
            val density = LocalDensity.current
            val handleWidth = 20.dp
            val scale = min(
                (maxWidth - handleWidth).value / MAX_WIDTH_DP,
                maxHeight.value / PLAYGROUND_HEIGHT_DP,
            ).coerceAtMost(1f)

            Row(verticalAlignment = Alignment.CenterVertically) {
                ScaledWindow(widthDp = widthDp, heightDp = PLAYGROUND_HEIGHT_DP, scale = scale) {
                    CompositionLocalProvider(LocalPlaygroundWindow provides window) {
                        Surface(color = MaterialTheme.colorScheme.surface) {
                            Box(Modifier.fillMaxSize()) { content() }
                        }
                    }
                }
                ResizeHandle(
                    modifier = Modifier
                        .width(handleWidth)
                        .height((PLAYGROUND_HEIGHT_DP * scale).dp)
                        .draggable(
                            orientation = Orientation.Horizontal,
                            state = rememberDraggableState { deltaPx ->
                                val deltaDp = with(density) { deltaPx.toDp().value } / scale
                                targetWidth = (targetWidth + deltaDp).coerceIn(MIN_WIDTH_DP, MAX_WIDTH_DP)
                            },
                            onDragStarted = { dragging = true },
                            onDragStopped = { dragging = false },
                        )
                )
            }
        }
    }
}

@Composable
private fun WindowToolbar(window: PlaygroundWindow, onPreset: (WindowPreset) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        PlaygroundPresets.forEach { preset ->
            FilterChip(
                selected = window.widthDp.roundToInt() == preset.widthDp.roundToInt(),
                onClick = { onPreset(preset) },
                label = { Text(preset.label) },
            )
        }
        Text(
            text = "${window.widthDp.roundToInt()} dp · ${window.widthClass.label}",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(start = 8.dp),
        )
    }
}

/** Lays [content] out at [widthDp] x [heightDp] and draws it scaled by [scale], taking only the scaled space. */
@Composable
private fun ScaledWindow(
    widthDp: Float,
    heightDp: Float,
    scale: Float,
    content: @Composable () -> Unit,
) {
    val shape = RoundedCornerShape(12.dp)
    Layout(
        content = {
            Box(
                Modifier
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                        transformOrigin = TransformOrigin(0f, 0f)
                    }
                    .clip(shape)
                    .border(2.dp, MaterialTheme.colorScheme.outline, shape)
            ) { content() }
        },
    ) { measurables, _ ->
        val w = widthDp.dp.roundToPx()
        val h = heightDp.dp.roundToPx()
        val placeable = measurables.single().measure(Constraints.fixed(w, h))
        layout(ceil(w * scale).toInt(), ceil(h * scale).toInt()) {
            placeable.place(0, 0)
        }
    }
}

@Composable
private fun ResizeHandle(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.pointerHoverIcon(PointerIcon.Crosshair),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            Modifier
                .size(width = 6.dp, height = 64.dp)
                .clip(RoundedCornerShape(3.dp))
                .background(MaterialTheme.colorScheme.primary)
        )
    }
}
